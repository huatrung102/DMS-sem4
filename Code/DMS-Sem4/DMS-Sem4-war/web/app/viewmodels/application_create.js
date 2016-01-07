define(['plugins/dialog', 'knockout'], function (dialog, ko) {
    var vm = function (param) {
        var self = this;
        self.model = {
            products: param.products,
            productid: ''
        }
        this.save = function () {
            dialog.close(this, { result: true, model: {productid: self.model.productid} });
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