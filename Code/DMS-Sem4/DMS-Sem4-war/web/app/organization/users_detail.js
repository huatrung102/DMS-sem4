define(['plugins/dialog', 'knockout'], function (dialog, ko) {
    var vm = function (param) {
        var self = this;
        this.model = {
            Users: {
                Id: param.Id,
                LoginName: ko.observable(param.LoginName),
                FullName: ko.observable(param.FullName),
                Password: ko.observable(param.Password),
                Password_Retype: ko.observable(param.Password),
                Type: ko.observable(param.Type),
                Department: { Id: ko.observable(param.DepartmentId), DepartmentPositionId: ko.observable(param.DepartmentPositionId) },
                PositionId: ko.observable(param.PositionId),
                IsLeader: ko.observable(param.IsLeader === true ? "true" : "false"),
                Status: ko.observable(param.Status === true ?"true" : "false"),
                Email: ko.observable(param.Email)
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
        self.model.Type.subscribe(function (x) {
            self.model.Users.Type(x.slice(-1).pop());
        });
        self.model.MoveBranchCheck.subscribe(function (x) {
            $(self.view).find(".chosen-container").each(function () {
                $(this).attr('style', 'width: 100%');
            });
        });
        self.model.BranchId.subscribe(function (x) {
            if (x != undefined) {
                $.ajax({
                    url: '/Organization/GetDepartmentPosition',
                    data: { branchId: x },
                    async: false,
                    success: function(data) {
                        self.model.DepartmentMove(data.department);
                        $(self.view).find(".branches_filter").each(function () {
                            $(this).trigger("chosen:updated");
                        });
                    }
                });
            }
        });
        self.model.Users.Department.Id.subscribe(function(x) {
            var positionId = $.Enumerable.From(self.model.Department()).Where(function(d) { return d.Id === x }).First().Position.Id;
            self.model.Users.Department.DepartmentPositionId(positionId);
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
            console.log(self.model.Users.Department.DepartmentPositionId() + " " + " : " + self.model.Users.IsLeader());
            console.log("Position ; "+self.model.Users.PositionId());
            if (self.model.Users.Department.DepartmentPositionId() === '00000000-0000-0000-0000-000000000000' && self.model.Users.IsLeader() === 'true')
                self.model.Users.PositionId(self.model.Users.Department.DepartmentPositionId());
            dialog.close(this, { result: true, model: ko.toJS(self.model.Users) });
        }
        this.cancel = function () {
            dialog.close(this, { result: false });
        }
        this.attached = function (view) {
            $(view).find("input").focus();
            self.model.Type([self.model.Users.Type()]);
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