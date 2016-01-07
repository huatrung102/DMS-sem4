define(['plugins/dialog', 'knockout'], function (dialog, ko) {
    var vm = function (param) {
        var self = this;
        self.model = {
            title: param.title,
            assigned_users: param.assigned_users,
            userid: ko.observable(param.userid)
        }
        this.save = function () {
            dialog.close(this, { result: true, model: self.model });
        }
        this.cancel = function () {
            dialog.close(this, { result: false });
        }
    };

    vm.show = function (param) {
        return dialog.show(new vm(param));
    };
    
    return vm;
});