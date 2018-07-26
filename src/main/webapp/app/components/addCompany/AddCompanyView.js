define(['text!components/addCompany/AddCompanyTemplate.html','sweetalert'], function (template,sweetalert) {
    var addCompanyTemplate = Handlebars.compile(template);

    var CompanyModel = Backbone.Model.extend({
        idAttribute:'companyId'
    });

    var CompanyCollection = Backbone.Collection.extend({
        url: "/api/company/secured",
        model: CompanyModel
    });

    var Login = Backbone.Model.extend({
        urlRoot: "/api/login"
    });

    return Backbone.View.extend({
        el: "#content",

        initialize: function () {
            var that = this;
            this.login = new Login();

            this.login.fetch({
                cache:false,
                success:function (m_login) {
                    if (m_login.toJSON().username == null)//Kullanıcı girişi yapılmamışsa.
                        Backbone.history.navigate('#', {trigger:true});
                    else {
                        that.companies = new CompanyCollection();
                        that.listenTo(that.companies, "reset add change remove", that.render);
                        var self = that;
                        that.companies.fetch({
                            success:function () {
                                self.companies.fetch({reset:true});
                            },
                            error:function () {
                                Backbone.history.navigate('error', {trigger:true});
                            }});
                    }
                }
            });
        },

        events: {
            'submit #companyForm': 'saveCompany',
            'click .deleteCompany': 'deleteCompany',
            'click .editCompany': 'openEditMode',
            'click .cancel': 'cancelUpdate',
            'click .updateCompany': 'updateCity'
        },

        updateCity:function (e) {
            var row = $(e.currentTarget).closest("tr");
            var newCompanyName = row.find("input").val();
            var id = $(e.currentTarget).data("id");
            var company = this.companies.findWhere({companyId: id});
            company.set({companyName: newCompanyName});
            company.save({},{
                error:function (model,response) {
                    swal("Başarısız", response.responseText, "error");
                }});
        },

        openEditMode:function (e) {
            var row = $(e.currentTarget).closest("tr");
            row.find(".editModeElement").show();
            row.find(".normalModeElement").hide();
        },

        deleteCompany:function (e) {
            var id = $(e.currentTarget).data("id");
            this.companies.findWhere({companyId:id}).destroy();
        },

        saveCompany: function (e) {
            e.preventDefault();

            if($("#companyName").val() == ""){
                swal("Lütfen Bir Firma Adı Giriniz.");
            }else {
                var company = new CompanyModel({companyName: $("#companyName").val()});
                this.companies.create(company, {
                    wait: true,
                    error:function (model,response) {
                        swal("Başarısız", response.responseText, "error");
                    }});
            }
        },

        cancelUpdate: function () {
            this.render();
        },

        render: function () {
            this.$el.html(addCompanyTemplate({companies: this.companies.toJSON()}));
        }
    });
});
