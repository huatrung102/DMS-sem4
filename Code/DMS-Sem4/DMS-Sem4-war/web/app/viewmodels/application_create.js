define(['plugins/dialog', 'knockout'], function (dialog, ko) {
    var vm = function (param) {
        var self = this;
        
        self.model = {
            products: param.products,
            appId: ''
        }
        console.log('param:' + JSON.stringify(param));
        console.log('param.products:' + param.products);
        this.save = function () {
            dialog.close(this, { result: true, model: {appId: self.model.appId} });
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