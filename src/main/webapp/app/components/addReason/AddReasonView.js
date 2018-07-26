define(['text!components/addReason/AddReasonTemplate.html','sweetalert'], function (template,sweetalert) {
    var addReasonTemplate = Handlebars.compile(template);

    var ReasonModel = Backbone.Model.extend({
        idAttribute:'reasonId'
    });

    var ReasonCollection = Backbone.Collection.extend({
        url: "/api/reason/secured",
        model: ReasonModel
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
                    else{
                        that.reasons = new ReasonCollection();
                        that.listenTo(that.reasons, "reset add change remove", that.render);
                        var self = that;
                        that.reasons.fetch({
                            success:function () {
                                self.reasons.fetch({reset:true});
                            },
                            error:function () {
                                Backbone.history.navigate('error', {trigger:true});
                            }
                        });
                    }
                }
            });
        },

        events: {
            'submit #reasonForm': 'saveReason',
            'click .deleteReason': 'deleteReason',
            'click .editReason': 'openEditMode',
            'click .cancel': 'cancelUpdate',
            'click .updateReason': 'updateReason'
        },

        updateReason:function (e) {
            var row = $(e.currentTarget).closest("tr");
            var newReasonName = row.find("input").val();
            var id = $(e.currentTarget).data("id");
            var reason = this.reasons.findWhere({reasonId: id});
            reason.set({reasonName: newReasonName});
            reason.save({},{
                error:function (model,response) {
                    swal("Başarısız", response.responseText, "error");
                }});
        },

        openEditMode:function (e) {
            var row = $(e.currentTarget).closest("tr");
            row.find(".editModeElement").show();
            row.find(".normalModeElement").hide();
        },

        deleteReason:function (e) {
            var id = $(e.currentTarget).data("id");
            this.reasons.findWhere({reasonId:id}).destroy();
        },

        saveReason: function (e) {
            e.preventDefault();
            if($("#reasonName").val() == ""){
                swal("Lütfen Bir Neden Giriniz.");
            }else {
                var reason = new ReasonModel({reasonName: $("#reasonName").val()});
                this.reasons.create(reason, {wait: true,
                    error:function (model,response) {
                        swal("Başarısız", response.responseText, "error");
                    }});
            }
        },

        cancelUpdate: function () {
            this.render();
        },

        render: function () {
            this.$el.html(addReasonTemplate({reasons: this.reasons.toJSON()}));
        }
    });
});
