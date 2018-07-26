define(['text!components/jobSecurityApproval/JobSecurityApprovalTemplate.html'], function (template) {
    var jobSecurityApprovalTemplate = Handlebars.compile(template);

    var LoginPermissionModel = Backbone.Model.extend({
        idAttribute:'permissionId'
    });

    var LoginPermissionCollection = Backbone.Collection.extend({
        url:"/api/loginpermission/notJobSecurity",
        model:LoginPermissionModel
    });

    var Login = Backbone.Model.extend({
        urlRoot: "/api/login"
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
                    else{//Eğer giriş yapmışşsa
                        that.loginpermissions = new LoginPermissionCollection();
                        that.listenTo(that.loginpermissions, "reset add change remove", that.render);
                        var self = that;
                        that.loginpermissions.fetch({
                            success:function () {
                                self.loginpermissions.fetch({reset:true});
                            },error:function () {
                                Backbone.history.navigate('error', {trigger:true});
                            }});
                    }
                }
            });


        },
        events: {
            'click #loginPermissionApprovalButton':'loginPermissionApproval'
        },

        loginPermissionApproval:function (e) {
            var that = this;

            var row = $(e.currentTarget).closest("tr");
            var permissionId = row.find('#loginPermissionApprovalButton').attr('data-id');

            var loginpermission = this.loginpermissions.findWhere({permissionId:parseInt(permissionId)});
            loginpermission.set({isJobSecurity:1,outHours:new Date(),loginHours:new Date()});
            loginpermission.save({},{
                success:function () {
                    that.loginpermissions.fetch({reset:true});
                }});
        },

        render: function () {

            this.$el.html(jobSecurityApprovalTemplate({loginpermissions:this.loginpermissions.toJSON(),length:this.loginpermissions.toJSON().length}));
        }
    });
});
