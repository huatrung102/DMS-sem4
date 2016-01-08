define(['./branch_detail', './department_detail', 'uuid', 'knockout', 'plugins/http', 'durandal/app', 'plugins/dialog', 'plugins/router', 'slimscroll', 'nestable', 'chosen', 'ma'], function (branch_detail, department_detail, uuid, ko, http, app, dialog, router) {
    var vm = function () {
        var self = this;
        this.model = {
            Organization: {
                Branches: ko.observableArray([]),
                Positions: [],
                CenterBankId: uuid.empty
            },
            Branches: ko.observableArray([]),
            filter_branches: ko.observableArray([]),
            filter_branches_backup: ko.observableArray([]),
            filter_branches_status:ko.observable('false'),
            ExpandItems: [],
            SeletedItem: ko.observable(uuid.empty),
            TotalBranch: ko.observable('0'),
            TotalDepartments: ko.observable('0'),
            TotalStaff: ko.observable('0'),
            Regions: ko.observableArray([])
        };
        app.on("userUpdateTree: updateTree").then(function () {
            self.loadData();
        });
        this.attached = function (view) {
            self.view = view;
            self.loadData();

            var branchFilter = $(self.view).find('.branch-filter');
            branchFilter.css({ display: 'none' });//inline
            branchFilter.chosen().change(function() {self.filter_handler(); });
        }
        this.bindingComplete = function (view) {
            
        }
        this.loadData = function () {
            $.ajax({
                url: 'rest/department/getAll',
                async: false,
                success: function (data) {
                    self.backupState();
                    if (self.model.TotalBranch() !== data.length) {
                        self.model.filter_branches_status('true');
                        //self.model.Department.removeAll();
                        self.model.Department(data);
                    }
                    self.model.TotalDepartment(data.length);                    
                   
                    self.model.TotalEmployee(data.TotalEmployee);

                    self.model.Regions(data.Regions);

                    self.model.Department.removeAll();
                    $(data.Department).each(function (idx, item) {
                        self.model.Department.push({
                            depId: item.Id,
                            depName: item.Name,
                            depCode: item.Code,                            
                            Filter: ko.observable(1),
                            Departments: item.Departments,
                            Subdepartments: item.Subdepartments
                        });
                    });

                    self.tree_control = $(self.view).find('.dd');
                    self.tree_control.removeData();
                    self.tree_control.nestable({});
                    self.tree_control.nestable('collapseAll');
                    self.restoreState();

                    var height = $(window).height();
                    var width = $(window).width();

                    $(self.view).find('.full-height-scroll').slimscroll({
                        height: height - 206,
                        wheelStep: 2,
                        alwaysVisible: true
                    });

                    if (self.model.filter_branches_status()) {                        
                        $(self.view).find('.branch-filter').trigger("chosen:updated");
                        self.filter_handler();
                    }
                }
            });
            //$(self.view).find(".position-filter").chosen({ width: '100%' });
            $(self.view).find(".branch-filter").chosen({ width: '100%' });
            //$(self.view).find(".user-filter").chosen({ width: '100%' });
        }

        this.backupState = function () {
            Array.clear(self.model.ExpandItems);
            var expandItems = $(self.view).find(".dd-item.node-item").not(".dd-collapsed");
            if (expandItems.length >= 0) {
                $(expandItems).each(function (idx, item) {
                    self.model.ExpandItems.push($(item).attr("dataid"));
                });
            }
        }

        this.restoreState = function () {
            $(self.model.ExpandItems).each(function (idx, item) {
                var liitem = $(self.view).find(String.format(".dd-item.node-item[dataid='{0}']", item));
                if (liitem.length > 0)
                    self.tree_control.data("nestable").expandItem(liitem);
                    //self.tree_control.nestable('expandItem', liitem[0]);
            });
        }

        this.filter_handler = function () {
            self.backupState();
            if (self.model.filter_branches_status() === 'true' && self.model.filter_branches_backup().length != self.model.filter_branches().length) {
                self.model.filter_branches(self.model.filter_branches_backup());
                $('.branch-filter').val(self.model.filter_branches_backup());
                $('.branch-filter').trigger('chosen:updated');
            } else {
                self.model.filter_branches_backup(self.model.filter_branches());
                self.model.filter_branches_status('false');
            }
            
            if (self.model.filter_branches().length > 0) {                
                this.selectItem({ Id: self.model.filter_branches()[0], Type: 1, BranchId: self.model.filter_branches()[0] });
                $(self.model.Organization.Branches()).each(function (idx, item) {
                    if (self.model.filter_branches().indexOf(item.Id) !== -1) {
                        item.Filter(1);
                    } else {
                        item.Filter(0);
                    }
                });
            } else {
                $(self.model.Organization.Branches()).each(function (idx, item) {
                    item.Filter(1);
                });
            }
           
        }

        this.department_add = function (param) {
            self.department_update($.extend(param, { depName: '' }));
        };
       
        this.department_edit = function (param) {
            self.department_update(param);
        };

        this.department_update = function (param) {
            department_detail.show($.extend(param, {Positions: self.model.Positions })).then(function (dialogResult) {
                if (dialogResult.result) {
                    http.post("/de/Save", { obj: dialogResult.model }).then(function () {
                        self.loadData();
                    });
                }
            });
        }

        this.department_remove = function(param) {
            dialog.showMessage(LOCALIZATION.COMMON.CONFIRM_DELETE_MESSAGE, LOCALIZATION.COMMON.CONFIRM_TITLE, [LOCALIZATION.COMMON.ACCEPT, LOCALIZATION.COMMON.CANCEL]).then(function (dialogResult) {
                if (dialogResult === LOCALIZATION.COMMON.ACCEPT) {
                    http.post('/Department/Delete', { id: param.Id }).then(function () {
                        self.loadData();
                    });
                }
            });
        }

        this.selectItem = function(param) {
            self.model.SeletedItem(param.Id);
            app.trigger(EVENT.SHOW_USERS, param);
        }

        this.go = function () {
            router.navigate(String.format("#page/{0}", "00000000-0000-0000-0000-000000000001"));
        }
        
        this.showUsers = function () {
            app.trigger(EVENT.ORGANIZATION_DETAIL);
        }
    }

    return vm;
});