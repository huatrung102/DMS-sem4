define(['plugins/dialog', 'knockout','komapping','toastr','plugins/http','glDatePicker', 'k/kendo.upload.min']
    , function (dialog, ko,komapping,toastr,http) {
    var vm = function (param) {
        var self = this;
        this.AllowEdit = true;
        this.Title = param.title;
        self.model = {
            products: param.products,
            Users : param.users,
            appSelected: ko.observable(),
            documentTypeSelected : ko.observable(),
            documentTypeId: '',
            docDetailFileName : '',
                       
            WorkFlow:ko.observable(),
            DocumentDetail : ko.observable(),
            DocumentType: ko.observableArray([]),     
            
        };
        self.JSON = {
            DocumentDetail : ko.observable(),
            
        }
        
       // loadDefault();
        function getContextPath() {
            return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
        }
        
        self.model.appSelected.subscribe(function(x) {
            console.log('appSelected: ' + x);
             http.post("rest/workflow/getByAppId", { appId: x.appId, workFlowStep: 1 }).then(function(data) {
                self.model.WorkFlow = komapping.fromJS(data);
            });     
        });
       
        this.attached = function (view) {
            console.log('attached');
            self.view = view;
                       
        }
        this.binding = function(){
             console.log('binding');
         
            $.ajax({
                url: 'rest/document/getDefault',                
                async: false,
                method: "POST",
                success: function (data) { 
                   self.model.DocumentDetail = komapping.fromJS(data);
               }                
            });
               
            
        }
       
        this.activate = function(){
            console.log('activate');
            self.loadData();
            
            
           // http.post("rest/document/getDefault").then(function (data) {               
              
           // });
        }
        this.save = function () {
          //getContextPath() +   dialog.close(this, { result: true, model: {appId: self.model.appId,document: self.model.Document} });
            
           
            
            
            self.model.DocumentDetail.docDetailUserCreate = self.model.Users.userId;
            self.model.DocumentDetail.docDetailDepCreate = self.model.Users.depId.depId;
            //tao moi
            self.model.DocumentDetail.actId.actId = 1;
            
            self.model.DocumentDetail.docId.appId = self.model.WorkFlow.appId;
             self.model.DocumentDetail.docId.userId = self.model.Users;
             self.model.DocumentDetail.workFlowId = komapping.toJS(self.model.WorkFlow);
             self.model.DocumentDetail.docId.docTypeId = self.model.documentTypeSelected;
             self.JSON.DocumentDetail = komapping.toJSON(self.model.DocumentDetail);
            http.post("rest/document/create",{data:self.JSON.DocumentDetail}).then(function () {
                toastr["info"](String.format(LOCALIZATION.APPLICATION.UPLOAD_FILE_REMOVED_SUCCESSED, "test"));
                    self.model.Document;
            });
        }
        function processDate(){
            
            
        }
        this.cancel = function () {
            dialog.close(this, { result: false });
        }
        this.loadWorkFlow = function(){
            //console.log('json:' +  ); 
            
            
        }
        
        
        this.compositionComplete = function (view) {
            self.view = view;
            self.loadCalendar();
            self.makeUploadControl();
            http.post("rest/workflow/getByAppId", { appId: self.model.appSelected.appId, workFlowStep: 1 }).then(function(data) {
                self.model.WorkFlow = komapping.fromJS(data);
            });
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
                       });
                   });
               }                
            })
            
        
        };
     //   */
            
        
        
        
    };

    vm.show = function (param) {
        return dialog.show(new vm(param));        
    };

    return vm;
});