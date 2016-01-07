define(['plugins/router', 'plugins/dialog', 'knockout', 'toastr', 'ma'], function (router, dialog, ko, toastr) {
    var vm = function (param) {
        var self = this;
        self.model = {
            form_id: param.form_id,
            form_name: param.form_name,
            app_id: param.app_id,
            link: typeof param.disbursementid === 'undefined' ? String.format('/ApplicationForm/Input?appid={0}&stageId={1}&formid={2}&crid={3}', param.app_id, param.stageId, param.form_id, param.crid) : String.format('/ApplicationForm/Input?appid={0}&stageId={1}&formid={2}&disbursementId={3}&view=1&crid={4}', param.app_id, param.stageId, param.form_id, param.disbursementid, param.crid),
            readonly: typeof param.disbursementid !== 'undefined'
        }
      
        this.resultVali = ko.observable(param.Valid);
        this.isBefore = ko.observable(param.isBefore === undefined ? false : param.isBefore);
        this.attached = function(view) {
            self.view = view;
        }
        this.print = function () {
            window.open(String.format("{0}&print=1", $(self.view).find("iframe")[0].src));
        }

        setInterval(function() {
            // Auto Save Form //
            console.log("Auto save run!!!");
        }, 900000);

        this.save = function () {
            var result = $(self.view).find("iframe")[0].contentWindow.SaveForm();
            self.resultVali(result.formValid);
            toastr.options.positionClass = 'toast-top-center';
            if (self.resultVali() && result.saveStatus === 1)
                toastr["info"](String.format(LOCALIZATION.COMMON.SAVE_SUCCESS + ". " + LOCALIZATION.PRODUCT_FORM.FORM_VALIDATE));
            else if (!self.resultVali() && result.saveStatus === 1)
                toastr["error"](String.format(LOCALIZATION.COMMON.SAVE_SUCCESS + ". " + LOCALIZATION.PRODUCT_FORM.FORM_ERROR));
            else if (self.resultVali() && result.saveStatus === 2)
                toastr["error"](String.format(LOCALIZATION.COMMON.SAVE_ERROR + ". " + LOCALIZATION.PRODUCT_FORM.FORM_VALIDATE));
            else if (!self.resultVali() && result.saveStatus === 2)
                toastr["error"](String.format(LOCALIZATION.COMMON.SAVE_ERROR + ". " + LOCALIZATION.PRODUCT_FORM.FORM_ERROR));
            else
                toastr["error"](String.format(LOCALIZATION.COMMON.SAVE_ERROR));
            //dialog.close(this, { result: true, model: { valid: result } });
        }
        this.cancel = function () {
            //dialog.close(this, { result: false });
            dialog.close(this, { result: true, model: { valid: self.resultVali() } });
        }
    };

    vm.show = function (param) {
        return dialog.show(new vm(param), { hfull: true, wfull: true });
    };

    return vm;
});