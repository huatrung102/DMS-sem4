define(['knockout', 'plugins/http', 'toastr'], function (ko, http, toastr) {
    var vm = function() {
        var self = this;
        self.Obj = {
            OldPassword: ko.observable(),
            NewPassword: ko.observable(),
            RetypePassword: ko.observable(),
            WrongPassword: ko.observable(false)
        }
        this.changePassword = function () {
            http.post('/Account/ChangePassword', { oldPassword: self.Obj.OldPassword(), newPassword: self.Obj.NewPassword() }).then(function (result) {
                self.Obj.WrongPassword(!result);
                if (result)
                    toastr["info"](LOCALIZATION.ACCOUNT.CHANGE_PASSWORD_SUCCESS);
            });
        }
    }
    return vm;
})