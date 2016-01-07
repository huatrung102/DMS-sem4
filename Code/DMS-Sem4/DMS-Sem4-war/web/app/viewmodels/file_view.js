define(['plugins/router', 'plugins/dialog', 'knockout', 'toastr', 'ma'], function(router, dialog, ko) {
    var vm = function(param) {
        var self = this;
        self.model = {
            link: String.format('http://docsview_bosscb.bpnconsulting.com/Viewer.aspx?appid={0}&fileid={1}', param.appid, param.fileid)
        }
        this.resultVali = ko.observable('');
        this.attached = function(view) {
            self.view = view;
        }
    };
    this.cancel = function() {
        dialog.close(this, { result: true });
    }
    vm.show = function(param) {
        return dialog.show(new vm(param), { hfull: true, wfull: true });
    };

    return vm;
});