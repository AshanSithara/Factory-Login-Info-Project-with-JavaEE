define(['text!components/addPlace/AddPlaceTemplate.html','sweetalert'], function (template,sweetalert) {

    var addPlaceTemplate = Handlebars.compile(template);

    var PlaceModel = Backbone.Model.extend({
        idAttribute:'placeId'
    });

    var PlaceCollection = Backbone.Collection.extend({
        url: "/api/place/secured",
        model: PlaceModel
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
                        that.places = new PlaceCollection();
                        that.listenTo(that.places, "reset add change remove", that.render);
                        var self = that;
                        that.places.fetch({
                            success:function () {
                                self.places.fetch({reset:true});
                            },
                            error:function () {
                                Backbone.history.navigate('error', {trigger:true});
                        }});
                    }
                }
            });
        },

        events: {
            'submit #placeForm': 'savePlace',
            'click .deletePlace': 'deletePlace',
            'click .editPlace': 'openEditMode',
            'click .cancel': 'cancelUpdate',
            'click .updatePlace': 'updatePlace'
        },

        updatePlace:function (e) {
            var row = $(e.currentTarget).closest("tr");
            var newPlaceName = row.find("input").val();
            var id = $(e.currentTarget).data("id");
            var place = this.places.findWhere({placeId: id});
            place.set({placeName: newPlaceName});
            place.save({},{
                reset:true,
                error:function (model,response) {
                    swal("Başarısız", response.responseText, "error");
                }});
        },

        openEditMode:function (e) {
            var row = $(e.currentTarget).closest("tr");
            row.find(".editModeElement").show();
            row.find(".normalModeElement").hide();
        },

        deletePlace:function (e) {
            var id = $(e.currentTarget).data("id");
            this.places.findWhere({placeId:id}).destroy();
        },

        savePlace: function (e) {
            e.preventDefault();
            if($("#placeName").val() == ""){
                swal("Lütfen Bir Yer Adı Giriniz.");
            }else {
                var place = new PlaceModel({placeName: $("#placeName").val()});
                this.places.create(place, {
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
            this.$el.html(addPlaceTemplate({places: this.places.toJSON()}));
        }
    });
});
