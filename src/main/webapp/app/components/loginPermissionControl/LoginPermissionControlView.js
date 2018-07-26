define(['text!components/loginPermissionControl/LoginPermissionControlTemplate.html',
        'text!components/loginPermissionControl/LoginPermissionControlTableTemplate.html',
        'clockpicker',
        'sweetalert'], function (template,tabletemplate,clockpicker,sweetalert) {
    var loginPermissionControlTemplate = Handlebars.compile(template);
    var loginPermissionControlTableTemplate = Handlebars.compile(tabletemplate);

    Handlebars.registerHelper('control',function (conditional,block) {
        if(conditional == null){
            return block.fn(this);
        }else {
            return block.inverse(this);
        }
    });

    Handlebars.registerHelper('isJsOk',function (conditional,block) {
        if(conditional == 0){
            return block.fn(this);
        }else {
            return block.inverse(this);
        }
    });
    var i = 0;
    var rowId = 0;

    var UserModel = Backbone.Model.extend({});

    var UserCollection = Backbone.Collection.extend({
        url:"/api/user",
        model:UserModel
    });

    var CompanyModel = Backbone.Model.extend({});

    var CompanyCollection = Backbone.Collection.extend({
        url: "/api/company",
        model: CompanyModel
    });

    var LoginControlModel = Backbone.Model.extend({
        idAttribute:'loginContolId'
    });

    var LoginControlCollection = Backbone.Collection.extend({
        url:"api/logincontrol/company",
        model:LoginControlModel
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
                        that.logincontrols = new LoginControlCollection();
                        that.listenTo(that.logincontrols, "reset add change remove", that.renderTable);

                        var self = that;
                        that.logincontrols.fetch({
                            reset:true,
                            data:$.param({ companyName:"******"}),
                            success:function () {
                                self.users = new UserCollection();
                                self.companies = new CompanyCollection();
                                self.listenTo(self.companies, "reset add change remove", self.render);
                                self.users.fetch({reset:true});
                                self.companies.fetch({reset:true});
                            },
                            error:function () {
                                Backbone.history.navigate('error', {trigger:true});
                            }});

                    }
                }
            });
        },
        events: {
            'change #secilenFirma2':'changeSecilenFirma',
            'click #permissionControlSaveButton':'permissionControlSave'
        },

        permissionControlSave:function (e) {

            var row = $(e.currentTarget).closest("tr");
            var girisInput = '.giris' + rowId;
            var cikisInput = '.cikis' + rowId;

            var girisInputVal = row.find(girisInput).val();
            var cikisInputVal = row.find(cikisInput).val();

            if(girisInputVal == ""){
                girisInputVal = row.find(girisInput).attr("data-id");
            }
            if(cikisInputVal == ""){
                cikisInputVal = row.find(cikisInput).attr("data-id");
            }

            var userId = row.find('#loginControlUser').attr("data-id");
            var loginControlId = row.find('#permissionControlSaveButton').attr('data-id');
            var loginControl = this.logincontrols.findWhere({loginContolId:parseInt(loginControlId)});
            var user = this.users.findWhere({userId:parseInt(userId)});

            var loginHourParse = loginControl.toJSON().loginHour.split(':');
            var loginHour = new Date(); loginHour.setHours(loginHourParse[0]); loginHour.setMinutes(loginHourParse[1]);

            var outHourParse = loginControl.toJSON().outHour.split(':');
            var outHour = new Date(); outHour.setHours(outHourParse[0]); outHour.setMinutes(outHourParse[1]);

            if(girisInputVal == null){
                swal("Lütfen Bir Giriş Saati Giriniz.");
            }
            else {
                var kontrol = true;

                var saat = girisInputVal.split(':');
                var girisSaati = new Date(); girisSaati.setHours(saat[0]); girisSaati.setMinutes(saat[1]); girisSaati.setMilliseconds(00);

                if(cikisInputVal != null){
                    var saat2 = cikisInputVal.split(':');
                    var cikisSaati = new Date(); cikisSaati.setHours(saat2[0]); cikisSaati.setMinutes(saat2[1]); cikisSaati.setMilliseconds(00);

                    if(isNaN(cikisSaati.getTime())){
                        swal('Başarısız','Çıkış Saati Geçerli Değil','error');
                        kontrol = false;
                    }
                }
                if(isNaN(girisSaati.getTime())) {
                    swal('Başarısız', 'Giriş Saati Geçerli Değil', 'error');
                }
                else if(kontrol){
                    //900000 15dk'nın milisaniye karşılığı -> 15dk önce girebilecek
                    if(girisSaati.getTime()+ 900000<= loginHour.getTime()){
                        swal("Uyarı", "Şuanda Giriş İşlemi Yapamazsınız.", "warning");
                    }else {
                        try {
                            if(girisSaati.getTime() - 900000 >= loginHour.getTime()){
                                swal("Uyarı","Geç kaldınız. Lütfen tekrarlamayınız","warning");
                            }

                            if(cikisSaati.getTime() - 900000 >= outHour.getTime()){
                                swal("Uyarı", "Çıkış saatinizi geciktirdiniz. Lütfen tekrarlamayınız.", "warning");
                            }
                        }catch (err){
                        }


                        var that = this;
                        loginControl.save({},{headers : {'loginHour':girisSaati,'outHour':cikisSaati},
                                              success:function () {
                                                  that.changeSecilenFirma();
                                              },
                                              error:function (model,response) {
                                                  swal("Başarısız",response.responseText,"error");
                                              }});
                    }

                }
            }

        },

        dates:function () {
            function getWeekNumber(d) {
                d = new Date(Date.UTC(d.getFullYear(), d.getMonth(), d.getDate()));
                // Set to nearest Thursday: current date + 4 - current day number
                // Make Sunday's day number 7
                d.setUTCDate(d.getUTCDate() + 4 - (d.getUTCDay()||7));
                // Get first day of year
                var yearStart = new Date(Date.UTC(d.getUTCFullYear(),0,1));
                // Calculate full weeks to nearest Thursday
                var weekNo = Math.ceil(( ( (d - yearStart) / 86400000) + 1)/7);
                // Return array of year and wee k number
                return [d.getUTCFullYear(), weekNo];
            }

            var result = getWeekNumber(new Date());

            function getDateOfWeek(weekNumber,year,day){
                //Create a date object starting january first of chosen year, plus the number of days in a week     multiplied by the week number to get the right date.
                return new Date(year, 0, (1+((weekNumber-1)*7)) + day);
            }

            var date = new Date();
            var currentTimeBasicFormat = date.getDate() + "/" + (parseInt(date.getMonth()) + 1) + "/" + date.getFullYear();


            var ay = (parseInt(date.getMonth()) + 1).toString();
            if(ay.length == 1){
                ay = 0 + ay;
            }

            var currentTimeDbFormat = date.getFullYear() + "-" + ay + "-" + date.getDate();


            var logincontrol = this.logincontrols.findWhere({controlDate:currentTimeDbFormat});

            for(var i = 0; i<7;i++){
                var myDate = getDateOfWeek(result[1],result[0],i);
                var myDateSimple = myDate.getDate() + "/" + (parseInt(myDate.getMonth()) + 1) + "/" + myDate.getFullYear();
                var id = "#tarih" + i;

                if(myDateSimple != currentTimeBasicFormat){
                    var giris = ".giris" + (i);
                    var cikis = ".cikis" + (i);
                    $(giris).prop('disabled',true);
                    $(cikis).prop('disabled',true);
                }else{
                    rowId = i;
                }
                $(id).text(myDateSimple);
            }
        },

        changeSecilenFirma:function () {
            var firma = $('#secilenFirma2').val();
            this.logincontrols.fetch({reset:true,data:$.param({ companyName:firma})});
        },

        render: function () {
            this.$el.html(loginPermissionControlTemplate({companies:this.companies.toJSON()}));

        },
        
        renderTable: function () {
            $('#loginPermissionControlTable').html(loginPermissionControlTableTemplate({logincontrols:this.logincontrols.toJSON()}));
            this.dates();
            $('#permissionControlTable').css('visibility','visible');
        }
    });
});
