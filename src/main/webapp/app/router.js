define(function (require) {
    "use strict";

    var AppRouter = Backbone.Router.extend({
        initialize: function () {
            require('utilities'); //Utility fonksiyonlarını her yerde kullanmak için
        },
        routes: {
            '': 'login',
            'home':'home',
            'addCompany':'addCompany',
            'addPlace':'addPlace',
            'addReason':'addReason',
            'createLoginPermission':'createLoginPermission',
            'accountApproval':'accountApproval',
            'loginPermissionApproval':'loginPermissionApproval',
            'jobSecurityApproval':'jobSecurityApproval',
            'loginPermissionControl':'loginPermissionControl',
            'error':'error',
            'report':'report'
        },
        report:function () {
            var ReportView = require('components/report/ReportView');
            showView(new ReportView());

            this.showNavbar();
        },

        error:function () {
            var ErrorPageView = require('components/errorPage/ErrorPageView');
            showView(new ErrorPageView());

            this.showNavbar();
        },

        loginPermissionControl:function () {
            var LoginPermissionViw = require('components/loginPermissionControl/LoginPermissionControlView');
            showView(new LoginPermissionViw());

            this.showNavbar();
        },

        jobSecurityApproval:function () {
            var JobSecurityApprovalView = require('components/jobSecurityApproval/JobSecurityApprovalView');
            showView(new JobSecurityApprovalView());

            this.showNavbar();
        },

        loginPermissionApproval:function () {
            var LoginPermissionApprovalView = require('components/loginPermissionApproval/LoginPermissionApprovalView');
            showView(new LoginPermissionApprovalView());

            this.showNavbar();
        },

        accountApproval:function () {
            var AccountApprovalView = require('components/accountApproval/AccountApprovalView');
            showView(new AccountApprovalView());

            this.showNavbar();
        },

        createLoginPermission:function () {
            var CreateLoginPermissionView = require('components/createLoginPermission/CreateLoginPermissionView');
            showView(new CreateLoginPermissionView());

            this.showNavbar();
        },

        addReason:function () {
            var ReasonView = require('components/addReason/AddReasonView');
            showView(new ReasonView());

            this.showNavbar();
        },

        addPlace:function () {
            var PlaceView = require('components/addPlace/AddPlaceView');
            showView(new PlaceView());

            this.showNavbar();
        },

        addCompany:function () {
            var CompanyView = require('components/addCompany/AddCompanyView');
            showView(new CompanyView());

            this.showNavbar();
        },
        home: function () {
            var HomeView = require('components/home/HomePageView');
            //var loginView = require('components/loginPage/loginPageView');
            showView(new HomeView());

            this.showNavbar();
        },
        login:function () {

            var LoginView = require('components/loginPage/LoginPageView');
            showView(new LoginView());

            this.hideNavbar();
        },
        showNavbar:function () {

            var NavbarView = require('components/navbar/NavbarView');
            new NavbarView();

        },
        hideNavbar:function () {
            $('#toyotaNavbar').hide();
        }
        
    });

    var initialize = function () {
        window.$router = new AppRouter;
        Backbone.history.start();
    };

    return {
        initialize: initialize
    };
});
