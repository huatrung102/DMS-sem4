define(['plugins/http', 'plugins/router', 'knockout', './../authenticate/manage'], function (http, router, ko, authenticate) {
    var vm = function () {
        var self = this;
        this.username = ko.observable("");
        this.password = ko.observable("");
        this.loginFailed = ko.observable(false);
        this.enableLogin = ko.observable(true);
        this.loginInput = function(d, e) {
            if (e.keyCode === 13) {
                self.login();
            }
            return true;
        }
        this.login = function () {
            self.enableLogin(false);
            //http.post("/SBank/GetData", { cif: "0400005849" }).then(function (data) {
                
            });


            http.post("/user/Login", { username: self.username(), password: self.password() }).then(function(data) {
                self.enableLogin(true);
                if (data) {
                    self.loginFailed(false);
                    authenticate.isAuthenticated();
                    router.navigate("#inbox");
                }
                else
                    self.loginFailed(true);
            });                      
        }

      
    }
    return vm;
})