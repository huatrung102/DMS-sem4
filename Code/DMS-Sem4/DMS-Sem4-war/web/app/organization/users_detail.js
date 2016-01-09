define(['plugins/dialog', 'knockout'], function (dialog, ko) {
    var vm = function (param) {
        var self = this;
        this.model = {
            Users: {
                userId: param.userId,
                userName: ko.observable(param.LoginName),
                userFullName: ko.observable(param.FullName),
                userPassword: ko.observable(param.Password),
                userNewPassword: ko.observable(param.Password),
                userPassword_Retype: ko.observable(param.Password),                
                depId: param.depId,
                depName : param.depName,                
                userRole : ko.observable(param.userRole),
                userStatus: ko.observable(param.userStatus),
                userEmail: ko.observable(param.userEmail),
                userDOB : ko.observable(param.userDOB),
                roleId: ko.observable(param.roleId),
                roleName: ko.observable(param.roleName),
            },            
            Branches: ko.observableArray(param.Branches),
            BranchId: ko.observable(''),
            Department: ko.observableArray(param.Department),
            DepartmentMove: ko.observableArray([]),
            DepartmentIdMove: ko.observable(''),
            Position: ko.observable(param.Position),
            Type: ko.observableArray([]),
            MoveBranchCheck: ko.observableArray([]),
            isEdit: ko.observable(param.isEdit)
        }
        self.CheckEmail = ko.computed(function() {
            var email = self.model.Users.Email();
            var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(email);
        });
                
        this.save = function () {
            if (self.model.MoveBranchCheck().length > 0) {
                if (self.model.DepartmentIdMove() == undefined || self.model.DepartmentIdMove() === "") {
                    alert("Chưa chọn phòng ban");
                    dialog.close(this, { result: false });
                } else {
                    self.model.Users.Department.Id = self.model.DepartmentIdMove();
                }
            }
            dialog.close(this, { result: true, model: ko.toJS(self.model.Users) });
        }
        this.cancel = function () {
            dialog.close(this, { result: false });
        }
        this.attached = function (view) {
            $(view).find("input").focus();
           
            self.view = view;
            $(view).find(".branches_filter").chosen();
            $(view).find(".chosen-container").each(function () {
                $(this).attr('style', 'width: 100%');
            });
        }
    };

    vm.show = function (obj) {
        return dialog.show(new vm(obj));
    };

    return vm;
});