define(['plugins/dialog', 'knockout'], function (dialog, ko) {
    var vm = function (param) {
        var self = this;
        this.model = {
            Id: param.Id,
            Name: ko.observable(param.Name),
            Code: ko.observable(param.Code),
            Address: ko.observable(param.Address),
            Regions: { Id: ko.observable(param.RegionsId) }
        }
        self.regions = ko.observableArray(param.Regions);

        this.save = function () {
            dialog.close(this, { result: true, model: ko.toJS(self.model) });
        }
        this.cancel = function () {
            dialog.close(this, { result: false });
        }
        this.attached = function (view) {
            $(view).find("input").focus();
        }
    };

    vm.show = function (obj) {
        return dialog.show(new vm(obj));        
    };

    return vm;
});