define(['durandal/app', 'knockout', 'plugins/http', 'plugins/router', './../authenticate/manage', './../services/data'], function (app, ko, http, router, authenticate, data) {
    var vm = function () {
        var self = this;
        self.model = {
            total: ko.observable(authenticate.userRole()),
            userName: ko.observable(authenticate.userName()),
            userId: ko.observable(authenticate.userId()),
            userRole: ko.observable(authenticate.userRole())
        }
        app.on(EVENT.APPLICATION_INBOX_TOTAL).then(function(total) {
            self.model.total(total);
        });
        this.signout = function() {
            http.get("rest/users/logout").then(function () {
                data.clear();
                router.navigate("#login");
            });
        }
        this.profile = function() {
            router.navigate("#profile");
        }
        authenticate.userRole.subscribe(function(x) {
            self.model.userRole(x);
        });
        authenticate.userName.subscribe(function (x) {
            self.model.userName(x);
        });
    }
    return vm;
})