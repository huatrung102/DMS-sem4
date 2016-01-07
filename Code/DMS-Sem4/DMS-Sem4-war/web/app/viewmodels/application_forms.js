define(['plugins/http', 'plugins/dialog', 'knockout'], function (http, dialog, ko) {
    var vm = function (param) {
        var self = this;
        self.model = {
            appid: param.appid,
            link: ko.observable(''),
            forms: ko.observableArray(ko.toJS(param.beforeFormTask)),
            activeid: ko.observable(''),
            //isBefore: ko.observable(false)
        }
        this.attached = function(view) {
            self.view = view;
            //if (param.isBefore) {
            //    if (self.model.forms().length > 0) {
            //        self.model.activeid(self.model.forms()[0].Id);
            //        self.load_form();
            //        self.model.isBefore(true);
            //    } else {
            //        alert("Không có biểu mẫu.");
            //    }
            //} else {
                http.get('/Application/GetForms', { id: self.model.appid }).then(function (items) {
                    if (items.length > 0) {
                        self.model.forms(items);
                        self.model.activeid(items[0].Id);
                        self.load_form();
                        //self.model.isBefore(false);
                    } else {
                        alert("Không có biểu mẫu.");
                    }
                });
            //}
        }
        this.cancel = function () {
            dialog.close(this, { result: false });
        }
        this.change_form = function(formid) {
            self.model.activeid(formid);
            self.load_form();
        }
        this.print = function () {
            window.open(String.format("{0}&print=1", $(self.view).find("iframe")[0].src));
        }
        this.printAll = function() {
            $(self.model.forms()).each(function (idx, item) {
                //window.open(String.format('/ApplicationForm/Input?appid={0}&formid={1}&view=1&print=1', param.appid, item.Id));
                var frame = $('<iframe/>');
                frame[0].name = "framePrintAll";
                frame.css({ "position": "absolute", "top": "-1000000px" });
                frame.attr('src', String.format('/ApplicationForm/Input?appid={0}&stageId={1}&formid={2}&view=1&print=1', param.appid, param.StageId, item.Id));
                $("body").append(frame);
                //frame.remove();
            });
            var body = document.getElementById('body');
            var iframe = body.getElementsByTagName("iframe");
            for (var f = 0; f < iframe.length; f++) {
                if (iframe[f].name === "framePrintAll")
                    iframe[f].remove();
            }
            



        }
        this.load_form = function () {
            self.model.link('about:blank');
            //$(self.view).find('iframe')[0].contentWindow.document.close();
            setTimeout(function() {
                self.model.link(String.format('/ApplicationForm/Input?appid={0}&stageId={1}&formid={2}&isBefore={3}&view=1', param.appid,param.stageId, self.model.activeid(), false));
            }, 500);                        
        }
    };

    vm.show = function (param) {
        return dialog.show(new vm(param), { hfull: true, wfull: true });
    };

    return vm;
});