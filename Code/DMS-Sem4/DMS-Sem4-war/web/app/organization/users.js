define(['uuid', 'durandal/app', 'knockout', 'plugins/http', 'plugins/dialog', './users_detail', 'slimscroll', 'chosen', 'easyui'], function (uuid, app, ko, http, dialog, users_detail) {
    var vm = function () {
        var self = this;
        this.model = {
            User: {
                userId: uuid.empty,
                DepartmentPositionId: uuid.empty,
                Type: 1,
                BranchId: ''
            },           
            CountEmployee: ko.observable('0'),
            Users: ko.observableArray([]),
            AllUsers: ko.observableArray([]),            
            AllDepartment: ko.observableArray([]),            
            filter_users: ko.observableArray([]),
            filter_users_backup: ko.observableArray([]),
            filter_users_status: ko.observable('false')
        };

        app.on(EVENT.SHOW_USERS).then(function (data) {
            self.model.User = data;
            self.loadData();
        });
        this.attached = function (view) {
            self.view = view;
            self.loadData();
            var userFilter = $(self.view).find(".user-filter");
            userFilter.css({ display: 'none' });//inline
            userFilter.chosen().change(function () { self.filter_users_handler(); });
        }

        this.loadData = function () {
            $.ajax({
                url: 'rest/users/getAll',                
                async: false,
                success: function (data) {
                    if (self.model.CountStaff() !== data.length) {
                        self.model.filter_users_status('true');
                        self.model.CountEmployee(data.length);
                    }
                    self.model.AllUsers.removeAll();
                    self.model.AllUsers.push({
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
                        roleId: param.roleId,
                        roleName: param.roleName,
                    });
                    self.model.Users.removeAll();
                    $(data).each(function (idx, item) {
                        self.model.AllUsers.push({
                            Id: item.Id,
                            FullName: item.FullName,
                            LoginName: item.LoginName,
                            Type: item.Type,
                            IsLeader: item.IsLeader,
                            Status: item.Status,
                            Email: item.Email,
                            DepartmentId: item.DepartmentId,
                            DepartmentPositionId: item.DepartmentPositionId,
                            DepartmentName: item.DepartmentName,
                            PositionId: item.PositionId,
                            PositionName: item.PositionName
                        });

                        self.model.Users.push({
                            Id: ko.observable(item.Id),
                            FullName: ko.observable(item.FullName),
                            LoginName: ko.observable(item.LoginName),
                            Type: ko.observable(item.Type),
                            IsLeader: ko.observable(item.IsLeader),
                            Status: ko.observable(item.Status),
                            Email: ko.observable(item.Email),
                            DepartmentId: ko.observable(item.DepartmentId),
                            DepartmentPositionId: ko.observable(item.DepartmentPositionId),
                            DepartmentName: ko.observable(item.DepartmentName),
                            PositionId: ko.observable(item.PositionId),
                            PositionName: ko.observable(item.PositionName)
                        });
                    });

                    $(self.view).find(".user-filter option[value='" + 0 + "']").remove();
                    $(self.view).find(".user-filter").trigger("chosen:updated");
                }
            });

            var height = $(window).height();
            var width = $(window).width();

            $(self.view).find('.full-height-scroll').slimscroll({
                height: height - 206,
                wheelStep: 2,
                alwaysVisible: true,
                position: 'left'
            });
            $(self.view).find(".user-filter").chosen({ width: '100%' });
        }
        this.filter_users_handler = function () {
            if (self.model.filter_users_status() === "true" && self.model.filter_users_backup().length > self.model.filter_users().length) {
                self.model.filter_users(self.model.filter_users_backup());
            } else {
                self.model.filter_users_status('false');
                self.model.filter_users_backup(self.model.filter_users());
            }

            if (self.model.filter_users().length > 0) {
                self.model.Users.removeAll();
                $(self.model.AllUsers()).each(function (idx, item) {
                    if (self.model.filter_users().indexOf(item.Id) !== -1) {
                        self.model.Users.push({
                            Id: ko.observable(item.Id),
                            FullName: ko.observable(item.FullName),
                            LoginName: ko.observable(item.LoginName),
                            Type: ko.observable(item.Type),
                            IsLeader: ko.observable(item.IsLeader),
                            Status: ko.observable(item.Status),
                            Email: ko.observable(item.Email),
                            DepartmentId: ko.observable(item.DepartmentId),
                            DepartmentPositionId: ko.observable(item.DepartmentPositionId),
                            DepartmentName: ko.observable(item.DepartmentName),
                            PositionId: ko.observable(item.PositionId),
                            PositionName: ko.observable(item.PositionName)
                        });
                    }
                });
            } else {
                self.model.Users.removeAll();
                $(self.model.AllUsers()).each(function (idx, item) {
                    if (item.Id !== 0) {
                        self.model.Users.push({
                            Id: ko.observable(item.Id),
                            FullName: ko.observable(item.FullName),
                            LoginName: ko.observable(item.LoginName),
                            Type: ko.observable(item.Type),
                            IsLeader: ko.observable(item.IsLeader),
                            Status: ko.observable(item.Status),
                            Email: ko.observable(item.Email),
                            DepartmentId: ko.observable(item.DepartmentId),
                            DepartmentPositionId: ko.observable(item.DepartmentPositionId),
                            DepartmentName: ko.observable(item.DepartmentName),
                            PositionId: ko.observable(item.PositionId),
                            PositionName: ko.observable(item.PositionName)
                        });
                    }
                });
            }
            $(self.view).find(".user-filter").trigger("chosen:updated");
        }
        this.users_add = function () {
            $.ajax({
                url: '/Organization/GetDepartmentPosition',
                data: { branchId: self.model.User.Type === 1 ? self.model.User.Id : self.model.User.BranchId },
                async: false,
                success: function (data) {
                    self.model.AllBranches(data.branches);
                    self.model.AllPosition(data.position);
                    self.model.AllDepartment(data.department);
                }
            });
            self.users_update_handler({
                Id: uuid.v4(),
                LoginName: '',
                FullName: '',
                Password: '',
                Type: '1',
                Branches: self.model.AllBranches(),
                Department: self.model.AllDepartment(),
                DepartmentId: self.model.User.Type !== 1 ? self.model.User.Id : "",
                DepartmentPositionId: self.model.User.Type !== 1 ? self.model.User.DepartmentPositionId : "",
                Position: self.model.AllPosition(),
                PositionId: self.model.User.Type !== 1 ? self.model.User.DepartmentPositionId : "",
                IsLeader: "false",
                Status: "false",
                Email: "",
                isEdit: '0'
            });
        }
        this.users_edit = function (param) {
            if (typeof param !== "undefined") {
                $.ajax({
                    url: "/Organization/GetDepartmentPosition",
                    data: { branchId: self.model.User.Type === 1 ? self.model.User.Id : self.model.User.BranchId },
                    async: false,
                    success: function (data) {
                        self.model.AllBranches(data.branches);
                        self.model.AllPosition(data.position);
                        self.model.AllDepartment(data.department);
                    }
                });
                var userSelected = $.Enumerable.From(self.model.AllUsers()).Where(function (x) { return x.Id === param.Id; }).ToArray();
                self.users_update_handler({
                    Id: userSelected[0].Id,
                    LoginName: userSelected[0].LoginName,
                    FullName: userSelected[0].FullName,
                    Password: "",
                    Type: userSelected[0].Type,
                    Branches: self.model.AllBranches(),
                    Department: self.model.AllDepartment(),
                    DepartmentId: userSelected[0].DepartmentId,
                    DepartmentPositionId: userSelected[0].DepartmentPositionId,
                    Position: self.model.AllPosition(),
                    PositionId: userSelected[0].PositionId,
                    IsLeader: userSelected[0].IsLeader,
                    Status: userSelected[0].Status,
                    Email: userSelected[0].Email,
                    isEdit: '1'
                });
            }
        }
        this.users_remove = function (param) {
            if (typeof param !== 'undefined') {
                dialog.showMessage(LOCALIZATION.COMMON.CONFIRM_DELETE_MESSAGE, LOCALIZATION.COMMON.CONFIRM_TITLE, [LOCALIZATION.COMMON.ACCEPT, LOCALIZATION.COMMON.CANCEL]).then(function (dialogResult) {
                    if (dialogResult === LOCALIZATION.COMMON.ACCEPT) {
                        http.get('/User/Delete', { id: param.Id }).then(function (data) {
                            self.model.AllUsers.remove(function (x) { return x.Id === param.Id; });
                            self.model.Users.remove(function (x) { return x.Id === param.Id; });
                            //self.loadData();
                            //app.trigger("userUpdateTree: updateTree");
                        });
                    }
                });
            }
        }
        this.users_active = function (param) {
            if (typeof param !== 'undefined') {
                http.post("/User/ActiveUsers", { id: param.Id }).then(function (data) {
                    var userchangeactive = $.Enumerable.From(self.model.Users()).Where(function (x) { return x.Id() == data.Id; }).Select(function (s) {
                        s.Status(data.Status);
                        return s;
                    }).ToArray();
                    var allUserchangeactive = $.Enumerable.From(self.model.AllUsers()).Where(function (x) { return x.Id == data.Id; }).Select(function (s) {
                        s.Status = data.Status;
                        return s;
                    }).ToArray();
                });
            }
        }
        this.users_update_handler = function (param) {
            users_detail.show(param).then(function (dialogResult) {
                if (dialogResult.result) {
                    http.post("/User/Save", { obj: dialogResult.model }).then(function (data) {
                        if (param.isEdit === '1') {
                            var userchange = $.Enumerable.From(self.model.Users()).Where(function (x) { return x.Id() === data.Id; }).Select(function (s) {
                                s.FullName(data.FullName);
                                s.LoginName(data.LoginName);
                                s.Type(data.Type);
                                s.IsLeader(data.IsLeader);
                                s.Status(data.Status);
                                s.Email(data.Email);
                                s.DepartmentId(data.DepartmentId);
                                s.DepartmentPositionId(data.DepartmentPositionId),
                                s.DepartmentName(data.DepartmentName);
                                s.PositionId(data.PositionId);
                                return s;
                            }).ToArray();
                            var allUserchange = $.Enumerable.From(self.model.AllUsers()).Where(function (x) { return x.Id === data.Id; }).Select(function (s) {
                                s.FullName = data.FullName;
                                s.LoginName = data.LoginName;
                                s.Type = data.Type;
                                s.IsLeader = data.IsLeader;
                                s.Status = data.Status;
                                s.Email = data.Email;
                                s.DepartmentId = data.DepartmentId;
                                s.DepartmentPositionId = data.DepartmentPositionId;
                                s.DepartmentName = data.DepartmentName;
                                s.PositionId = data.PositionId;
                                return s;
                            }).ToArray();
                        } else if (param.isEdit === '0') {
                            self.model.AllUsers.push({
                                Id: data.Id,
                                FullName: data.FullName,
                                LoginName: data.LoginName,
                                Type: data.Type,
                                IsLeader: data.IsLeader,
                                Status: data.Status,
                                Email: data.Email,
                                DepartmentId: data.DepartmentId,
                                DepartmentPositionId: data.DepartmentPositionId,
                                DepartmentName: data.DepartmentName,
                                PositionId: data.PositionId,
                                PositionName: data.PositionName
                            });
                            self.model.Users.push({
                                Id: ko.observable(data.Id),
                                FullName: ko.observable(data.FullName),
                                LoginName: ko.observable(data.LoginName),
                                Type: ko.observable(data.Type),
                                IsLeader: ko.observable(data.IsLeader),
                                Status: ko.observable(data.Status),
                                Email: ko.observable(data.Email),
                                DepartmentId: ko.observable(data.DepartmentId),
                                DepartmentPositionId: ko.observable(data.DepartmentPositionId),
                                DepartmentName: ko.observable(data.DepartmentName),
                                PositionId: ko.observable(data.PositionId),
                                PositionName: ko.observable(data.PositionName)
                            });
                        }
                        $(self.view).find(".user-filter").trigger("chosen:updated");
                    });
                }
            });
        }
    }
    return vm;
});