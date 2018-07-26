define(['text!components/navbar/NavbarTemplate.html','text!assets/img/logout.png'], function (template) {
    var navbarTemplate = Handlebars.compile(template);

    var Login = Backbone.Model.extend({
        urlRoot: "/api/login"
    });

    return Backbone.View.extend({
        el: "#toyotaNavbar",

        initialize:function () {

            this.login = new Login();
            this.listenTo(this.login, "reset add change remove", this.render);
            this.login.fetch({reset:true});

        },

        events:{
            'click #addCompany':'addCompanyPage',
            'click #addPlace':'addPlace',
            'click #addReason':'addReason',
            'click #createLoginPermission':'createLoginPermission',
            'click #accountApproval':'accountApproval',
            'click #loginPermissionApproval':'loginPermissionApproval',
            'click #jobSecurityApproval':'jobSecurityApproval',
            'click #loginPermissionControl':'loginPermissionControl',
            'click #report':'report'
        },

        report:function () {
            Backbone.history.navigate('report',{trigger:true});
        },

        loginPermissionControl:function () {
            Backbone.history.navigate('loginPermissionControl',{trigger:true});
        },

        jobSecurityApproval:function () {
            Backbone.history.navigate('jobSecurityApproval',{trigger:true});
        },

        loginPermissionApproval:function () {
            Backbone.history.navigate('loginPermissionApproval',{trigger:true});
        },
        accountApproval:function () {
            Backbone.history.navigate('accountApproval',{trigger:true});
        },

        createLoginPermission:function () {
            Backbone.history.navigate('createLoginPermission', {trigger:true});
        },

        addReason:function () {
            Backbone.history.navigate('addReason', {trigger:true});
        },

        addCompanyPage:function () {
            Backbone.history.navigate('addCompany', {trigger:true});
        },

        addPlace:function () {
            Backbone.history.navigate('addPlace', {trigger:true});
        },

        render: function () {
            this.$el.html(navbarTemplate({login:this.login.toJSON()}));
        }
    });
});
