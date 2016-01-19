define(['services/data', 'durandal/app', 'plugins/router', 'plugins/http', 'toastr', 'knockout', 'komapping', 'viewmodels/application_create', 'viewmodels/application_approve', 'viewmodels/application_assign', '../authenticate/manage', 'ma', 'linq'], function (data, app, router, http, toastr, ko, komapping, application_create, application_approve, application_assign, authenticate) {
    var vm = function () {
        var self = this;
        var DATAKEY = "DATA_INBOX";
        self.model = {
            userRole: authenticate.userId() === undefined? '':authenticate.userRole(),
            ipp: 10,
            total_page: ko.observable(1),
            ipage: ko.observable(0),
            
            type: ko.observable(CONSTANT.APPLICATION_TYPE_PROCESSING),
            documents: ko.observableArray([]),
            allow_create: true,//authenticate.createapp(),
            products: [],
            Users: ko.observable(),
            change_view: function (view_name) {
                self.model.ipage(0);
                self.model.type(view_name);                
            },
            //DocumentDetail : ko.observable(),
            application_url: function() {
                switch (this.type()) {
                    case CONSTANT.APPLICATION_TYPE_PROCESSING:
                    {
                        return "Document/GetPublishedProcessing";
                    }
                    case CONSTANT.APPLICATION_TYPE_SENT:
                    {
                        return "Application/GetPublishedSent";
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
        
        this.next_page = function() {
            self.model.ipage(self.model.ipage() + 1);
            self.get_applications();
        }
        this.previous_page = function() {
            self.model.ipage(self.model.ipage() - 1);
            self.get_applications();
        }
        this.activate = function () {
            http.get('rest/application/getAll').then(function(data) {                
                self.model.products = data;
            });
            http.get('rest/users/getUserTest').then(function(data) {                
                self.model.Users = data;
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
            application_create.show({ products: self.model.products,users: self.model.Users,title: "Create Document" }).then(function (dialogResult,response) {
                if(dialogResult.result){
                    if(dialogResult.response.data)
                        router.navigate(String.format('#application/detail/{0}', dialogResult.response.data));    
                }                
            });
        }
        this.get_applications = function () {
            data.get(DATAKEY).type = self.model.type();
            data.get(DATAKEY).ipage = self.model.ipage();
            http.post(self.model.application_url(), { ItemsPerPage: self.model.ipp, PageIndex: self.model.ipage() }).then(function(result) {
                self.model.documents(result.Items);
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
    }
    return vm;
})