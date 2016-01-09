define(['plugins/dialog', 'knockout','toastr','plugins/http','glDatePicker', 'k/kendo.upload.min']
    , function (dialog, ko,toastr,http) {
    var vm = function (param) {
        var self = this;
        this.AllowEdit = true;
        self.model = {
            products: param.products,
            appId: '',
            documentTypeId: '',
            docDetailFileName : '',
            Document: {
                docId: ko.observable(),
                docNumber: ko.observable(),
                docSourceNumber: ko.observable(),
                docUpdateDate: ko.observable(),
                docCreateDate: ko.observable(),
                docContent: ko.observable(),
                docStatus: ko.observable(),
                docIsValid: ko.observable(),
                docValidFrom: ko.observable(),
                docValidTo: ko.observable(),
                docDate: ko.observable(),
                docType: ko.observable(),                
                
            },
            DocumentDetail:{
                docDetailId : ko.observable(),
                docDetailUserCreate : ko.observable(),
                docDetailDepCreate : ko.observable(),
                docDetailFileName : ko.observable(),
                docDetailFileContent : ko.observable(),
                docDetailDescription : ko.observable(),
                docDetailIsActive : ko.observable(),
                actId : ko.observable(),
                docId : ko.observable(),
                workFlowId : ko.observable(),
            },
            DocumentType: ko.observableArray([]),     
            
        }
        function getContextPath() {
            return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
        }

        this.attached = function (view) {
            self.view = view;
            self.loadData();           
        }
        this.save = function () {
          //  dialog.close(this, { result: true, model: {appId: self.model.appId,document: self.model.Document} });
            http.post(getContextPath() + "/upload").then(function () {
                toastr["info"](String.format(LOCALIZATION.APPLICATION.UPLOAD_FILE_REMOVED_SUCCESSED, "test"));

            });
        }
        this.cancel = function () {
            dialog.close(this, { result: false });
        }
        this.compositionComplete = function (view) {
            self.view = view;
            self.loadCalendar();
            self.makeUploadControl();
            
        }
        this.loadCalendar = function(){
             $(".date:enabled").each(function (idx, item) {
                $(item).css("border", "");
                $(item).glDatePicker({
                    onClick: function (target, cell, date) {
                        target.val(date.format("dd/MM/yyyy")).change();
                    }
                });
            });            
        };
        this.makeUploadControl = function () {
            var inputfiles = $(self.view).find("input[type='file']");
            $(inputfiles).each(function (idx, ctl) {
                if (typeof $(ctl).data("kendoUpload") === 'undefined') {
                    $(ctl).kendoUpload({
                        async: {
                          //  saveUrl: String.format("/FileAttachment/Upload?ApplicationId={0}&FileAttachmentId={1}&StageId={2}&Revision={3}", self.model.appid, $(ctl).attr("fileid"), self.model.application.StageId(), self.model.application.Revision())
                          saveUrl :getContextPath() +  "/upload"
                        },
                        multiple: false,
                        showFileList: true,
                        autoUpload: true,
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
//                            $(self.model.application.Tasks()).each(function(idx, task) {
//                                $(task.Files()).each(function(idx1, file) {
//                                    if (file.Id() === fileid) {
//                                        file.Exist(true);
//                                        self.checkFinishTask(task);
//                                        return;
//                                    }
//                                });
//                            });
                        }                        
                    });
                }
            });
        }
    //  /*
        this.loadData = function () {
            $.ajax({
                url: 'rest/documentType/getAll',                
                async: false,
                success: function (data) { 
                   $(data).each(function (idx, item) {
                       self.model.DocumentType.push({
                       docTypeId: item.docTypeId,
                       docTypeName: item.docTypeName                      
                       })
                   })}                
            })};
     //   */
            
        
        
        
    };

    vm.show = function (param) {
        return dialog.show(new vm(param));        
    };

    return vm;
});