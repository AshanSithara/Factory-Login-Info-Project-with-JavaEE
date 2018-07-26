// RequireJS Configuration
requirejs.config({
    baseUrl: "app",
    paths: {
        'jquery': 'assets/lib/jquery-3.3.1.min',
        'backbone': 'assets/lib/backbone',
        'handlebars': 'assets/lib/handlebars-v4.0.5',
        'underscore': 'assets/lib/underscore',
        'bootstrap': 'assets/lib/bootstrap.bundle.min',
        'utilities': 'utils/utility',
        'datepicker':'assets/lib/bootstrap-datepicker',
        'datepickertr':'assets/lib/bootstrap-datepicker.tr.min',
        'clockpicker':'assets/lib/bootstrap-clockpicker',
        'sweetalert':'assets/lib/sweetalert',
        'fontawesome':'assets/lib/fontawesome',
        'router': 'router'
    },
    shim: {
        'clockpicker':{
            deps:['jquery']
        },
        'datepickertr':{
            deps:['datepicker','jquery']
        },
        'backbone': {
            deps: ['jquery', 'underscore']
        },
        'bootstrap': {
            deps: ['jquery']
        }
    }
});

require([
    'backbone',
    'jquery',
    'underscore',
    'handlebars',
    'bootstrap',
    'fontawesome'
], function (Backbone, Jquery, Underscore, Handlebars,fontawesome) {
    window.Handlebars = Handlebars;

    require(['router'], function (router) {
        router.initialize();
    });

});
