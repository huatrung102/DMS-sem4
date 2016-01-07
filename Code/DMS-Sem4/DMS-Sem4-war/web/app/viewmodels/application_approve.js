define(['plugins/dialog', 'knockout', 'linq', 'chosen'], function (dialog, ko) {
    var vm = function (params) {
        var self = this;
        self.model = {
            title: params.title,
            comment: '',            
            nextstages: params.nextstages,
            stage: ko.observable(),
            department: ko.observable(),
            position: ko.observable(),
            user: ko.observable(),
            usercc: ko.observableArray([]),
            urgent: false
        }
        $(self.view).find(".chosen").chosen();
        self.departmentArray = ko.observableArray(params.NextStages&&params.nextstages.length>0?params.nextstages[0].Departments:[]);
        self.model.position.subscribe(function(y) {
            var department = $.Enumerable.From(self.model.stage().Departments).Where(function (x) {
                return x.PositionId === y;
            }).ToArray();
            self.departmentArray(department);
        });
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