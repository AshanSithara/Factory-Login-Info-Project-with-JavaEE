define(['text!components/accountApproval/AccountApprovalTemplate.html','sweetalert'], function (template,sweetalert) {
    var accountApprovalTemplate = Handlebars.compile(template);

    var RoleModel = Backbone.Model.extend({});

    var RoleCollection = Backbone.Collection.extend({
        url:"api/roles",
        model:RoleModel
    });

    var UserModel = Backbone.Model.extend({
        idAttribute:'userId'
    });

    var UserCollection = Backbone.Collection.extend({
        url:"api/user/accountApproval",
        model:UserModel
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
                        that.users = new UserCollection();

                        var self = that;

                        that.users.fetch({
                            success:function () {
                                self.roles = new RoleCollection();
                                self.listenTo(self.roles, "reset add change remove", self.render);
                                self.roles.fetch({reset:true});
                            },
                            error:function () {
                                Backbone.history.navigate('error', {trigger:true});
                            }});
                    }
                }
            });

        },
        events: {
            'click .approvalSaveButton':'approvalSave'
        },

        approvalSave:function (e) {
            var row = $(e.currentTarget).closest("tr");


            var roleName = row.find('.secilenYetki option:selected').text();
            var onay = 0;
            if($(".onayTiki").is(':checked')) onay=1;  else onay=0;
            var username = row.find('.userid').text().trim();


            var role = this.roles.findWhere({roleName:roleName});
            var user = this.users.findWhere({username:username});


            var that = this;
            if(role == null){
                swal("Lütfen Bir Yetki Seçiniz.");
            }else{
                user.set({ok:onay,roles:role});
                user.save({},{success:function () {
                    that.users.fetch({reset:true});
                }});
            }
        },

        render: function () {
            this.$el.html(accountApprovalTemplate({users:this.users.toJSON(),roles:this.roles.toJSON()}));
        }
    });
});
