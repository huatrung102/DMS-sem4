define(['services/data', 'durandal/app', 'plugins/router', 'plugins/http', 'toastr', 'knockout', 'komapping', 'viewmodels/application_create', 'viewmodels/application_approve', 'viewmodels/application_assign', '../authenticate/manage', 'ma', 'linq'], function (data, app, router, http, toastr, ko, komapping, application_create, application_approve, application_assign, authenticate) {
    var vm = function () {
        var self = this;
        var DATAKEY = "DATA_INBOX";
        self.model = {
            userRole: authenticate.userRole(),
            ipp: 10,
            total_page: ko.observable(1),
            ipage: ko.observable(0),
            filter_Disbursement: ko.observable("false"),
            type: ko.observable(CONSTANT.APPLICATION_TYPE_PROCESSING),
            applications: ko.observableArray([]),
            allow_create: true,//authenticate.createapp(),
            products: [],
            change_view: function (view_name) {
                self.model.ipage(0);
                self.model.type(view_name);                
            },
            application_url: function() {
                switch (this.type()) {
                    case CONSTANT.APPLICATION_TYPE_PROCESSING:
                    {
                        return "Application/GetInbox";
                    }
                    case CONSTANT.APPLICATION_TYPE_NEED_ASSIGN:
                    {
                        return "Application/GetDepartmentAssign";
                    }
                    case CONSTANT.APPLICATION_TYPE_MANAGEMENT:
                    {
                        return "Application/GetDepartmentInbox";
                    }
                }
            },
            information: {
                PC: ko.observable(0),
                AC: ko.observable(0),
                MC: ko.observable(0)
            }
        }
        self.model.type.subscribe(function() {
            self.get_applications();
        });
        self.model.filter_Disbursement.subscribe(function () {
            self.get_applications();
        });
        this.next_page = function() {
            self.model.ipage(self.model.ipage() + 1);
            self.get_applications();
        }
        this.previous_page = function() {
            self.model.ipage(self.model.ipage() - 1);
            self.get_applications();
        }
        this.activate = function () {
            http.get('application/getAll').then(function(data) {
                self.model.products = data;
            });
            if (typeof data.get(DATAKEY) === 'undefined')
                data.add(DATAKEY, { type: self.model.type(), ipage: self.model.ipage() });
            else {
                self.model.type(data.get(DATAKEY).type);
                self.model.ipage(data.get(DATAKEY).ipage);
            }
            self.get_applications();
        }

        this.app_create = function() {
            application_create.show({ products: self.model.products }).then(function (dialogResult) {
                if(dialogResult.result)
                    http.post('document/Create', { ProductId: dialogResult.model.productid }).then(function () {
                        self.get_applications();
                        toastr["info"](LOCALIZATION.APPLICATION.CREATE_SUCCESSFUL);
                    });
            });
        }
        this.get_applications = function () {
            data.get(DATAKEY).type = self.model.type();
            data.get(DATAKEY).ipage = self.model.ipage();
            http.post(self.model.application_url(), { ItemsPerPage: self.model.ipp, PageIndex: self.model.ipage(), FilterDisbursement: self.model.filter_Disbursement() }).then(function(result) {
                self.model.applications(result.Items);
                self.model.total_page(result.TotalPage);
                self.model.information.PC(result.InboxInformation.PC);
                self.model.information.AC(result.InboxInformation.AC);
                self.model.information.MC(result.InboxInformation.MC);
                app.trigger(EVENT.APPLICATION_INBOX_TOTAL, result.InboxInformation.Total);
            });
        }

        this.view_detail = function(param) {
            router.navigate(String.format('#application/detail/{0}', param.id));            
        }
        this.assign = function(param) {
            var application = $.Enumerable.From(self.model.applications()).Single(function(x) {
                return x.Id === param.id;
            });
            if (application.CanDoQuickAssignment) {
                application_approve.show({ title: String.format("{0} & {1}", LOCALIZATION.APPLICATION.ASSIGN, LOCALIZATION.APPLICATION.APPROVE), nextstages:application.NextStages }).then(function (dialogResult) {
                    if (dialogResult.result) {
                        http.post("/Application/Approve", { ApplicationId: param.id, BeforeStageId: application.StageId, StageId: dialogResult.model.stage().Id, AssignedDepartmentId: dialogResult.model.department().Id, Comment: dialogResult.model.comment, AssignedUserId: dialogResult.model.user() ? dialogResult.model.user().Id : null }).then(function (data) {
                            self.get_applications();
                        });
                    }
                });
            } else {
                application_assign.show({ title: LOCALIZATION.APPLICATION.ASSIGN, assigned_users: application.AssignedUsers, userid: authenticate.userid() }).then(function(dialogResult) {
                    if (dialogResult.result) {
                        http.post("/Application/Assign", { ApplicationId: param.id, AssignedUserId: dialogResult.model.userid() }).then(function() {
                            self.get_applications();
                        });
                    }
                });
            }
        }
    }
    return vm;
})