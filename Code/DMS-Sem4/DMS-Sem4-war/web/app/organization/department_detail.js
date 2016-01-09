define(['plugins/dialog', 'knockout', 'uuid', 'ma', 'linq'], function (dialog, ko, uuid) {
    var vm = function (param) {        
        var self = this;
        this.model = {
            depId: param.depId === undefined ? uuid.v4() : param.depId,
            depName: ko.observable(param.depName),       
            depCode: ko.observable(param.depCode),
            depStatus: ko.observable(param.depStatus)
        };
        this.dialog_title = LOCALIZATION.DEPARTMENT.TITLE;

        this.department_property_name = LOCALIZATION.DEPARTMENT.PROPERTY_NAME;

        this.save = function () {                        
            dialog.close(this, { result: true, model: ko.toJS(self.model) });
        };
        this.cancel = function () {
            dialog.close(this, { result: false });
        };
        this.attached = function (view) {
            $(view).find("input").focus();
        };
    };

    vm.show = function (obj) {
        return dialog.show(new vm(obj));        
    };

    return vm;
});