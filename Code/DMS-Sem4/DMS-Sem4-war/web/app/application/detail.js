define(['plugins/dialog', 'plugins/router', 'toastr', 'durandal/app', 'plugins/http', 'knockout', 'komapping', '../authenticate/manage', 'viewmodels/application_approve', 'viewmodels/application_assign', 'ma', 'linq', 'k/kendo.upload.min'], function (dialog, router, toastr, app, http, ko, komapping, authenticate, application_approve, application_assign) {
    var vm = function () {
        var self = this;
        self.model = {
         //   appid: '',
            application: ko.observable(),       
            DocumentDetail : ko.observable(),
            WorkFlowNext : ko.observable(),
            Action : ko.observableArray([]),
            action : ko.observable(),
            WorkFlow : ko.observable(),
        }
        self.JSON = {
            DocumentDetail : ko.observable(),
        }
        
        
        this.activate = function (id) {           
            $.ajax({
                url: String.format('rest/document/getById/{0}', id),
                async: false,
                success: function (data) {
                    self.model.DocumentDetail = komapping.fromJS(data);
                    self.JSON.DocumentDetail = komapping.toJSON(self.model.DocumentDetail);
                    self.model.WorkFlow = self.model.DocumentDetail.workFlowId;
                     self.loadData();
                 //   self.model.action = self.getAction(actId);
                    self.model.application.submit = function (nextStep) {                         
                        http.post('rest/document/getNextStageDetail', {data: self.JSON.DocumentDetail,nextStep: nextStep}).then(function(data) {
                            application_approve.show({ title: "Next Stage",DocumentDetail: self.model.DocumentDetail ,DocumentDetailNext: komapping.fromJS(data) }).then(function (dialogResult) {
                                if (dialogResult.result) {
                                    http.post("/Application/Approve", { ApplicationId: self.model.appid, 
                                        BeforeStageId: self.model.application.StageId(), 
                                        StageId: dialogResult.model.stage().Id, 
                                        AssignedDepartmentId: dialogResult.model.department().Id, 
                                        Comment: dialogResult.model.comment, 
                                        AssignedUserId: dialogResult.model.user() ? dialogResult.model.user().Id : null, 
                                        Urgent: dialogResult.model.urgent, UserCC: dialogResult.userccArray }).then(function (data) {
                                        router.navigate('#inbox');
                                    });
                                }
                            });
                        });                        
                    }
                    self.model.application.remove = function () {
                        dialog.showMessage("Are you sure want to delete this document???", "Warning", ["OK", "Cancel"]).then(function(dialogResult) {
                            if (dialogResult === "OK") {
                                http.post("rest/document/remove", { docId: self.model.DocumentDetail.docId.docId() }).then(function() {
                                    router.navigate("/#");
                                });
                            }
                        });
                    }
                    self.model.application.sendback = function () {
                        application_approve.show({ title: LOCALIZATION.APPLICATION.SENDBACK }).then(function (dialogResult) {
                            if (dialogResult.result) {
                                http.post("/Application/SendBack", { ApplicationId: self.model.DocumentDetail.docDetailId, Comment: dialogResult.model.comment }).then(function () {
                                    router.navigateBack();
                                });
                            }
                        });
                    }
                    /*
                    self.finish = function () {
                        application_approve.show({ title: LOCALIZATION.APPLICATION.FINISH }).then(function (dialogResult) {
                            if (dialogResult.result) {
                                http.post("/Application/Finish", { ApplicationId: self.model.appid, Comment: dialogResult.model.comment }).then(function () {
                                    router.navigateBack();
                                });
                            }
                        });
                    }
                    
                    self.download = function(params) {
                        window.open(String.format("/FileAttachment/Download?ApplicationId={0}&FileAttachmentId={1}&StageId={2}&Revision={3}", self.model.appid, params.fileid, self.model.application.StageId(), self.model.application.Revision()));
                    }
                    */
                    self.model.application.view_file = function (params) {
                        file_view.show({ appid: self.model.DocumentDetail.docDetailId, fileName: self.model.DocumentDetail.docDetailFileName }).then(function () {
                        });
                    }

                        /*
                    self.model.application.show_workflow = function() {
                        router.navigate(String.format("#application/workflow/{0}", self.model.DocumentDetail.docDetailId));
                    }

                    self.model.application.show_history = function () {
                        router.navigate(String.format("#application/history/{0}", self.model.DocumentDetail.docDetailId));
                    }
                      */  
                    self.model.application.clickButton = function(actId){
                        self.model.action = self.getAction(actId);
                        if(self.model.action !== undefined){
                            switch(actId){
                                case 2:   
                                
                                case 4:
                                case 5:
                                case 10:
                                self.model.application.submit(self.model.action.actStep());                                
                                break;
                                case 3:
                                    self.model.application.remove();
                                break;
                            }
                        }
                            
                    }
                 //  self.model.application.DocumentDetail = self.model.DocumentDetail;
                   
                   
                }
            });
        }
       
        

        this.compositionComplete = function (view) {
            self.view = view;
            
          //  self.makeUploadControl();
        }
        this.loadData = function(){
            http.get('rest/action/getAll').then(function(data) {
                self.model.Action = komapping.fromJS(data);
            });
        }
        this.getAction = function(actionId){
           var result = $.Enumerable.From(self.model.Action())
            .Where(function (x) { 
                console.log('x.actId:' + x.actId());
                return x.actId() === actionId;
                });
            return result === undefined ? null : result.ToArray()[0];
        }
        this.makeUploadControl = function () {
            var inputfiles = $(self.view).find("input[type='file']");
            $(inputfiles).each(function (idx, ctl) {
                if (typeof $(ctl).data("kendoUpload") === 'undefined') {
                    $(ctl).kendoUpload({
                        async: {
                            saveUrl: "",//String.format("/FileAttachment/Upload?ApplicationId={0}&FileAttachmentId={1}&StageId={2}&Revision={3}", self.model.appid, $(ctl).attr("fileid"), self.model.application.StageId(), self.model.application.Revision())
                        },
                        multiple: false,
                        showFileList: false,
                        localization: {
                            select: $(ctl).attr("filename")
                        },
                        upload: function() {
                        },
                        progress: function(e) {                           
                        },
                        error: function () {
                            toastr["error"](String.format(LOCALIZATION.APPLICATION.UPLOAD_FILE_FAILED, $(ctl).attr("filename")));
                        },
                        success: function() {
                            toastr["info"](String.format(LOCALIZATION.APPLICATION.UPLOAD_FILE_SUCCESSED, $(ctl).attr("filename")));
                            var fileid = $(ctl).attr("fileid");
                            $(self.model.application.Tasks()).each(function(idx, task) {
                                $(task.Files()).each(function(idx1, file) {
                                    if (file.Id() === fileid) {
                                        file.Exist(true);
                                        self.checkFinishTask(task);
                                        return;
                                    }
                                });
                            });
                        }                        
                    });
                }
            });
        }

        //this.get_applications = function () {
        //    //data.get(DATAKEY).type = self.model.type();
        //    //data.get(DATAKEY).ipage = self.model.ipage();
        //    http.post(self.model.application_url(), { ItemsPerPage: self.model.ipp, PageIndex: self.model.ipage(), FilterDisbursement: self.model.filter_Disbursement() }).then(function (result) {
        //        self.model.applications(result.Items);
        //        self.model.total_page(result.TotalPage);
        //        self.model.information.PC(result.InboxInformation.PC);
        //        self.model.information.AC(result.InboxInformation.AC);
        //        self.model.information.MC(result.InboxInformation.MC);
        //        app.trigger(EVENT.APPLICATION_INBOX_TOTAL, result.InboxInformation.Total);
        //    });
        //}

    }
    return vm;
})