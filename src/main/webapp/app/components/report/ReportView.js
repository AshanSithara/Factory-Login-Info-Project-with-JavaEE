define(['text!components/report/ReportTemplate.html',
    'text!components/report/ReportTableTemplate.html',
    'datepicker',
    'datepickertr'],
    function (template,template2,datepicker,datepickertr) {


    var report = Handlebars.compile(template);
    var report2 = Handlebars.compile(template2);

    Handlebars.registerHelper('control',function (conditional,block) {
        if(conditional == null){
            return block.fn(this);
        }else {
            return block.inverse(this);
        }
    });

    var Login = Backbone.Model.extend({
        urlRoot: "/api/login"
    });

    var CompanyModel = Backbone.Model.extend({
    });

    var CompanyCollection = Backbone.Collection.extend({
        url: "/api/company",
        model: CompanyModel
    });

    var ReportModel = Backbone.Model.extend({
    });

    var ReportCollection = Backbone.Collection.extend({
        url:"/api/logincontrol/secured/report",
        model:ReportModel
    });

    var ExportExcel = Backbone.Model.extend({
        url:"/api/logincontrol/secured/export"
    });

    var Secured = Backbone.Model.extend({
        urlRoot:"/api/logincontrol/secured"
    });

    return Backbone.View.extend({
        el: "#content",

        initialize: function () {

            this.login = new Login();
            var that = this;

            this.login.fetch({
                cache:false,
                success:function (m_login) {
                    if (m_login.toJSON().username == null)//Kullanıcı girişi yapılmamışsa.
                        Backbone.history.navigate('#', {trigger:true});
                    else{
                        var self = that;
                        that.secured = new Secured();
                        that.listenTo(that.secured, "reset add change remove", that.render);
                        that.secured.fetch({
                            success:function () {
                                self.companies = new CompanyCollection();
                                self.reports = new ReportCollection();
                                self.export = new ExportExcel();
                                self.listenTo(self.companies, "reset add change remove", self.render);
                                self.listenTo(self.reports, "reset add change remove", self.render2);
                                self.listenTo(self.export, "reset add change remove", self.render);
                                self.companies.fetch({reset:true});
                            },error:function () {
                                Backbone.history.navigate('error', {trigger:true});
                            }});

                    }
                }
            });
        },
        events: {
            'click #sendReport':'sendReport',
            'click #exportExcel':'exportExcel'
        },

        exportExcel:function () {
            // this.export.fetch({reset:true,data: $.param({ companyName: $('#secilenFirmaReport').val(),firstDate:$('#startDate').val(),secondDate:$('#endDate').val()})});
            window.location.href = "api/logincontrol/secured/export?companyName=" + $('#secilenFirmaReport').val() +"&firstDate="+$('#startDate').val()+"&secondDate="+$('#endDate').val();
        },

        sendReport:function () {
            this.reports.fetch({reset:true,data: $.param({ companyName: $('#secilenFirmaReport').val(),firstDate:$('#startDate').val(),secondDate:$('#endDate').val()})});
        },

        render: function () {
            this.$el.html(report({companies:this.companies.toJSON()}));
        },

        render2:function () {
            $('#content2').html(report2({reports:this.reports.toJSON()}));
            $('#takeReportTable').css('visibility','visible');
        }
    });
});
