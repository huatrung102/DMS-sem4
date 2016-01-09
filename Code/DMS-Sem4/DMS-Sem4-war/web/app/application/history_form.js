define(['plugins/http', 'plugins/dialog', 'knockout'], function (http, dialog, ko) {
    var vm = function (param) {
        var self = this;
        self.model = {
            formId: param.formId,
            appId: param.appId,
            link: ko.observable('')
        }
        this.attached = function (view) {
            self.load_form();
        }
        this.cancel = function () {
            dialog.close(this, { result: false });
        }

        this.load_form = function () {
            self.model.link('about:blank');
            setTimeout(function () {
                self.model.link(String.format('/ApplicationForm/Input?appid={0}&formid={1}&view=1', self.model.appId, self.model.formId));
            }, 500);
        }
    };

    vm.show = function (param) {
        return dialog.show(new vm(param), { hfull: true, wfull: true });
    };

    return vm;
});