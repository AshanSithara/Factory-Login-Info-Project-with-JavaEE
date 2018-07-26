define(['text!components/errorPage/ErrorPageTemplate.html'], function (template) {
    var errorPageTemplate = Handlebars.compile(template);

    var Login = Backbone.Model.extend({
        urlRoot: "/api/login"
    });

    return Backbone.View.extend({
        el: "#content",

        initialize: function () {

            this.login = new Login();
            this.login.fetch({
                cache:false,
                success:function (m_login) {
                    if (m_login.toJSON().username == null)//Kullanıcı girişi yapılmamışsa.
                        Backbone.history.navigate('#', {trigger:true});
                }
            });
            this.render();
        },

        render: function () {
            this.$el.html(errorPageTemplate);
        }
    });
});