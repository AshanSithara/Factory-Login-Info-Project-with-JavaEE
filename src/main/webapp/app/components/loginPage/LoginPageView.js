define(['text!components/loginPage/LoginPageTemplate.html','sweetalert'], function (template,sweetalert) {
    var loginPageTemplate = Handlebars.compile(template);

    var UserModel = Backbone.Model.extend({});

    var UserCollection = Backbone.Collection.extend({
        url:"api/user",
        model:UserModel
    });

    var Login = Backbone.Model.extend({
        urlRoot: "/api/login"
    });

    var visible = 'hidden';

    return Backbone.View.extend({
        el: "#content",

        initialize:function () {

            this.login = new Login();
            var that = this;
            this.login.fetch({
                cache:false,
                success:function (m_login) {
                    if(m_login.toJSON().username != null){
                        Backbone.history.navigate('#home', {trigger:true});
                        swal('Giriş İşleminiz Gerçekleştirildi.');
                    }else{
                        that.users = new UserCollection();
                        that.listenTo(that.users, "reset add change remove", that.render);
                        that.render();
                    }
                }
            });

        },

        events:{
            'click .login .message':'toggleForm',
            'click #createNewAccount':'saveUser',
            'click #userLoginButton':'userLoginError'
        },

        userLoginError:function () {

            var vars = [], hash;
            var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
            for(var i = 0; i < hashes.length; i++)
            {
                vars.push(hashes[i]);
            }
            
            if(vars[0] == "error"){
                var text = " <div class=\"alert alert-danger\" id=\"errorLoginMessage\" style=\"margin-top:0px;visibility: visible\">\n" +
                    "        <strong>Uyarı!</strong> Giriş Yapılamadı\n" +
                    "    </div>";

                $('#errorMessage').html(text);
            }

        },

        saveUser: function (e) {

            var sifre1 = $("#password").val().toString();
            var sifre2 = $("#password2").val().toString();
            var konrol = sifre1==sifre2;

            if($("#name").val() =="" || $("#username").val() =="" || $("#password").val()==""){
                swal("Lütfen tüm alanları doldurunuz.");
            }
            else if(!konrol){
                swal("Girdiğiniz şifreler birbirleri ile eşleşmiyor");
            }
            else{
                e.preventDefault();
                var user = new UserModel({name:$("#name").val(),username:$("#username").val(),password:$("#password").val()});
                this.users.create(user,{
                    wait:true,
                    success:function () {
                        swal('Tebrikler!', 'Kaydınız başarıyla gerçekleştirildi!\nHesabınız onaylandıktan sonra giriş yapabilirsiniz.', 'success');
                    },error:function (model,response) {
                        swal(response.responseText);
                    }});
            }
        },

        toggleForm:function () {
            $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
        },

        render: function () {
            this.$el.html(loginPageTemplate);
            this.userLoginError();
        }
    });
});
