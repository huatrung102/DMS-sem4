define(['plugins/dialog', 'knockout', 'viewmodels/file_view'], function (dialog, ko, file_view) {
    var vm = function (param) {
        var self = this;
        self.model = {
            appid: param.appid,
            files: ko.observableArray([])
        }
        this.cancel = function () {
            dialog.close(this, { result: false });
        }
        self.attached = function (view) {
            self.view = view;
            $.ajax({
                url: '/FileAttachment/GetFiles',
                data: { id: self.model.appid },
                async: false,
                success: function (data) {
                    self.model.files.removeAll();
                    self.model.files(data);
                }
            });
        }
        this.view_file = function (fileid) {
            file_view.show({ appid: param.appid, fileid: fileid }).then(function () {
            });
        }
    };

    vm.show = function (param) {
        return dialog.show(new vm(param));
    };

    return vm;
});