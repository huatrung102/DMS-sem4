define(['plugins/router', 'knockout', '../authenticate/manage', 'plugins/http'], function (router, ko, authenticate, http) {
    var vm = function () {
        var self = this;
        this.model = {
            ipp: 10,
            total_page: ko.observable(1),
            ipage: ko.observable(0),
            isleader: authenticate.isleader(),
            type: ko.observable(CONSTANT.APPLICATION_TYPE_SEND),
            applications: ko.observableArray([]),
            change_view: function(type) {
                self.model.type(type);
            },
            application_url: function () {
                switch (this.type()) {
                    case CONSTANT.APPLICATION_TYPE_SEND:
                        {
                            return "Application/GetSendBox";
                        }
                    case CONSTANT.APPLICATION_TYPE_DEPARTMENT_SEND:
                        {
                            return "Application/GetDepartmentSendbox";
                        }
                }
            },
        }
        self.model.type.subscribe(function () {
            self.get_applications();
        });
        this.next_page = function () {
            self.model.ipage(self.model.ipage() + 1);
            self.get_applications();
        }
        this.previous_page = function () {
            self.model.ipage(self.model.ipage() - 1);
            self.get_applications();
        }

        this.activate = function () {            
            self.get_applications();
        }

        this.get_applications = function () {
            http.post(self.model.application_url(), { ItemsPerPage: self.model.ipp, PageIndex: self.model.ipage() }).then(function (result) {
                self.model.applications(result.Items);
                self.model.total_page(result.TotalPage);
            });
        }

        this.view_detail = function (param) {
            router.navigate(String.format('#application/detail/{0}', param.id));
        }
    }
    return vm;
})