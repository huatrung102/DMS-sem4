define(['plugins/dialog', 'knockout', 'plugins/http','komapping','linq', 'chosen'], function (dialog, ko,http,komapping) {
    var vm = function (params) {
        var self = this;
        self.model = {
            title: params.title,
            
      //      description: '',            
            DocumentDetail: params.DocumentDetail,
            DocumentDetailNext : params.DocumentDetailNext,
            DepartmentSelected : ko.observableArray([]),
            UserSelected : ko.observableArray([]),
            Urgent: false,
         //   stage: ko.observable(),
        //    department: ko.observable(),
       //     position: ko.observable(),
      //      user: ko.observable(),
      //      usercc: ko.observableArray([]),
      //      urgent: false
        }
        this.Departments = ko.observableArray([]),
        this.Users = ko.observableArray([]),
        this.visibleDepartment = true;//false;
        this.visibleUser = true;//false;
       
        $(self.view).find(".chosen").chosen(
            {
                is_multiple : self.model.DocumentDetail.workFlowId.workFlowIsTransferMultiple()?true : false,
            }
        );
        
        //self.departmentArray = ko.observableArray(params.NextStages&&params.nextstages.length>0?params.nextstages[0].Departments:[]);
        //khong chon phong ban ma cung phong ban theo role
       
        
        
        
        this.compositionComplete = function(view){
             self.view = view;
            
        }
        this.loadData = function(){
            switch(self.model.DocumentDetail.workFlowId.workFlowChooseType()){
            case 1:
                 self.model.DepartmentSelected.removeAll();
                 self.model.DepartmentSelected.push(self.model.DocumentDetail.docDetailDepCreate);
            break;
            case 2:
                $.ajax({
                url: 'rest/department/getAll',                
                async: false,
                success: function (data) { 
                    $(data).each(function (idx, item) {
                        self.Departments.push({
                        depId: item.depId,
                        depName: item.depName                      
                        });
                    });
                }                
                });
                /*
                http.get("rest/department/getAll").then(function(data) {                     
                    self.Departments(data);
                });
                */
            break;
            case 3:
                http.post("rest/users/getUserByDepAndRole", { depId: self.model.DocumentDetail.docDetailDepCreate.depId, roleId: self.model.DocumentDetail.workFlowId.roleId.roleId }).then(function(data) {
                    self.Users(data);
                   
                });  
            break;
            case 4:
                http.post("rest/users/getUserByRole", { roleId: self.model.DocumentDetail.workFlowId.roleId.roleId }).then(function(data) {
                   self.Users(data);
                   // komapping.fromJS(data, {}, self.Users);
                });
            break;
        }
        }
        this.activate = function(){
            console.log('activate');
            self.loadData();
        }
        
        this.attached = function (view) {
            self.view = view;
            $(self.view).find(".chosen").chosen({ width: '100%' });
        }
        this.save = function () {
            dialog.close(this, { result: true, model: self.model });
        }
        this.cancel = function () {
            dialog.close(this, { result: false });
        }
    };

    vm.show = function (param) {
        return dialog.show(new vm(param));
    };

    return vm;
});