define(['./history_form', 'viewmodels/file_view', 'plugins/router', 'plugins/http', 'toastr', 'uuid', 'knockout', 'kinetic', 'ma', 'linq'], function (history_form, file_view, router, http, toastr, uuid, ko) {
    var vm = function () {
        var self = this;
        self.logs = ko.observableArray();
        this.showForm = function (data) {
            history_form.show({ appId: self.appId, formId: data.Id });
        }
        this.showFile = function (data) {
            file_view.show({ appid: self.appId, fileid: data.Id }).then(function () {
            });
        }
        this.activate = function (id) {
            self.appId = id;
            self.appLink = String.format("#application/{0}", id);
            self.historyGraphLink = String.format("#application/history_graph/{0}", id);
            http.get('/Application/GetHistory', { appId: id }).then(function (data) {
                self.logs(data);
            });
        }
        
    }
    return vm;
});