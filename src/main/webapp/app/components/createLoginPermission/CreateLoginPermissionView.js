define(['text!components/createLoginPermission/CreateLoginPermissionHtml.html'
        ,'datepicker'
        ,'clockpicker'
        ,'sweetalert'], function (template,datepicker,clockpicker,sweetalert) {
    var createLoginPermissionHtml = Handlebars.compile(template);

    var UserModel = Backbone.Model.extend({});

    var UserCollection = Backbone.Collection.extend({
        url:"api/user",
        model:UserModel
    });

    var PlaceModel = Backbone.Model.extend({});

    var PlaceCollection = Backbone.Collection.extend({
        url: "/api/place",
        model: PlaceModel
    });

    var ReasonModel = Backbone.Model.extend({});

    var ReasonCollection = Backbone.Collection.extend({
        url: "/api/reason",
        model: ReasonModel
    });

    var CompanyModel = Backbone.Model.extend({
    });

    var CompanyCollection = Backbone.Collection.extend({
        url: "/api/company",
        model: CompanyModel
    });

    var LoginPermissionModel = Backbone.Model.extend({});

    var LoginPermissionCollection = Backbone.Collection.extend({
        url:"api/loginpermission",
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
                    else{
                        that.loginpermission = new LoginPermissionCollection();
                        var self = that;
                        that.loginpermission.fetch({
                            success:function () {

                                self.places = new PlaceCollection();
                                self.reasons = new ReasonCollection();
                                self.companies = new CompanyCollection();
                                self.users = new UserCollection();
                                self.listenTo(self.places, "reset add change remove", self.render);
                                self.listenTo(self.reasons, "reset add change remove", self.render);
                                self.listenTo(self.companies, "reset add change remove", self.render);
                                self.listenTo(self.users, "reset add change remove", self.render);
                                self.places.fetch({reset: true});
                                self.reasons.fetch({reset: true});
                                self.companies.fetch({reset:true});
                                self.users.fetch({reset:true});

                            },
                            error:function () {
                                Backbone.history.navigate('error', {trigger:true});
                            }});
                    }
                }
            });
        },
        events: {
            'submit #submitLoginPermissionForm':'saveLoginPermission'
        },

        saveLoginPermission:function (e) {

            e.preventDefault();

            var kisiler = $('#girisYapacakKisiler').val().split('\n');
            var userJson = [];
            for(var i=0;i<kisiler.length;i++){
                var user = this.users.findWhere({name:kisiler[i]});
                if(user == null){
                    swal("Başarısız","(" + kisiler[i]+")" + " Böyle Bir Kullanıcı Yok","error");
                    userJson = [];
                    return;
                }else {
                    userJson.push(user.toJSON());
                }
            }

            var tarih1 = $('#girisTarihi').val().split('.');
            var girisTarihi = new Date(); girisTarihi.setDate(tarih1[0]); girisTarihi.setMonth(tarih1[1] - 1); girisTarihi.setFullYear(tarih1[2]);

            var tarih2 = $('#cikisTarihi').val().split('.');
            var cikisTarihi = new Date(); cikisTarihi.setDate(tarih2[0]); cikisTarihi.setMonth(tarih2[1] - 1); cikisTarihi.setFullYear(tarih2[2]);

            var firma = $('#secilenFirma').val();

            var secilenNedenler = $('#secilenNedenler').val();
            var secilenNedenlerJson = [];
            for(var i=0;i<secilenNedenler.length;i++){
                var reason = this.reasons.findWhere({reasonId:parseInt(secilenNedenler[i])});
                secilenNedenlerJson.push(reason.toJSON());
            }

            var secilenYerler = $('#secilenYerler').val();
            var secilenYerlerJson = [];
            for(var i=0;i<secilenYerler.length;i++){
                var place = this.places.findWhere({placeId:parseInt(secilenYerler[i])});
                secilenYerlerJson.push(place.toJSON());
            }

            var secilenYerler = $('#secilenYerler').val();
            var eslikEdecekPersonel = $('#eslikEdecekPersonel').val();
            var isGüvenligi = parseInt($("input[name='inlineRadioOptions']:checked").val());

            var saat = $('#girisSaati').val().split(':');
            var girisSaati = new Date(); girisSaati.setHours(saat[0]); girisSaati.setMinutes(saat[1]);

            var saat2 = $('#cikisSaati').val().split(':');
            var cikisSaati = new Date(); cikisSaati.setHours(saat2[0]); cikisSaati.setMinutes(saat2[1]);

            if($("#secilenFirma").val() == null){
                swal("Başarısız", 'Lütfen Bir Firma Seçiniz', "error");
            }else if($('#secilenNedenler').val().length == 0){
                swal("Başarısız", 'Lütfen En Az Bir Tane Giriş Nedeni Seçiniz', "error");
            }else if($('#secilenYerler').val().length == 0){
                swal("Başarısız", 'Lütfen En Az Bir Tane Giriş Yapılacak Yer Seçiniz', "error");
            }else if($('#eslikEdecekPersonel').val() == ""){
                swal("Başarısız", 'Lütfen Bir Personel Adı Giriniz', "error");
            }else if(isNaN(isGüvenligi)){
                swal("Başarısız", 'Lütfen İş Güvenliği Eğitim Durumunu Belirtiniz.', "error");
            }
            else {
                var loginpermission2 = new LoginPermissionModel({isJobSecurity:0,permissionOk:0
                    ,personelName:eslikEdecekPersonel,companyId:firma,
                    dateOfEntry:girisTarihi,dateOfOut:cikisTarihi,loginHours:girisSaati,outHours:cikisSaati,
                    isJobSecurity:isGüvenligi,introductionReasons:secilenNedenlerJson,places:secilenYerlerJson,users:userJson});

                var that = this;

                this.loginpermission.create(loginpermission2,{
                    success:function (model,response) {
                        swal('Başarılı!', 'Giriş İzni Kaydedildi', 'success');
                        that.places.fetch({reset: true});
                    },
                    error:function (model,response) {
                        swal("Başarısız",response.responseText,"error");
                    }},this);
            }
        },

        render: function () {
            this.$el.html(createLoginPermissionHtml({places: this.places.toJSON(),reasons:this.reasons.toJSON()
                ,companies:this.companies.toJSON()}));
        }
    });
});
