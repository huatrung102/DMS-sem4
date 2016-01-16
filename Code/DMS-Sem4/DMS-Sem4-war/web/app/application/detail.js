define(['plugins/dialog', 'plugins/router', 'toastr', 'durandal/app', 'plugins/http', 'knockout', 'komapping', '../authenticate/manage', 'viewmodels/application_approve', 'viewmodels/application_assign', 'viewmodels/form_view', 'viewmodels/file_view', 'viewmodels/application_forms', 'viewmodels/application_files', 'ma', 'linq', 'k/kendo.upload.min'], function (dialog, router, toastr, app, http, ko, komapping, authenticate, application_approve, application_assign, form_view, file_view, application_forms, application_files) {
    var vm = function () {
        var self = this;
        self.model = {
            appid: '',
            application: null,           
        }
        this.activate = function (id) {
            self.model.appid = id;
            
            $.ajax({
                url: String.format('/Application/Get/{0}', self.model.appid),
                async: false,
                success: function (data) {
                    self.model.application = komapping.fromJS(data);
                    self.model.application.beforeFormTask = ko.observableArray([]);
                    self.model.application.beforeFilesTask = ko.observableArray([]);
                    $(self.model.application.Tasks()).each(function(idx, task) {
                        self.checkFinishTask(task);
                    });
                    $(self.model.application.BeforeFormFileTasks()).each(function (idxx, btask) {
                        $(btask.Forms()).each(function (idform, forms) {
                            self.model.application.beforeFormTask.remove(forms);
                            self.model.application.beforeFormTask.push(forms);
                        });
                        $(btask.Files()).each(function (idfile, file) {
                            self.model.application.beforeFilesTask.remove(file);
                            self.model.application.beforeFilesTask.push(file);
                        });
                    });
                    self.model.application.AllowApprove = ko.computed(function() {
                        return !this.isStageEnd && $.Enumerable.From(this.Tasks()).All(function(x) {
                            return x.IsFinished();
                        });
                    }, self.model.application);
                    self.model.application.approve = function () {
                        http.get('/Application/GetNextStages', {id: self.model.appid}).then(function(data) {
                            application_approve.show({ title: LOCALIZATION.APPLICATION.APPROVE, nextstages: data }).then(function (dialogResult) {
                                if (dialogResult.result) {
                                    http.post("/Application/Approve", { ApplicationId: self.model.appid, BeforeStageId: self.model.application.StageId(), StageId: dialogResult.model.stage().Id, AssignedDepartmentId: dialogResult.model.department().Id, Comment: dialogResult.model.comment, AssignedUserId: dialogResult.model.user() ? dialogResult.model.user().Id : null, Urgent: dialogResult.model.urgent, UserCC: dialogResult.userccArray }).then(function (data) {
                                        router.navigate('#inbox');
                                    });
                                }
                            });
                        });                        
                    }
                    self.model.application.remove = function () {
                        dialog.showMessage(String.format(LOCALIZATION.COMMON.CONFIRM_DELETE_MESSAGE, LOCALIZATION.APPLICATION.TITLE), LOCALIZATION.COMMON.CONFIRM_TITLE, [LOCALIZATION.COMMON.CONFIRM_DELETE_OK, LOCALIZATION.COMMON.CONFIRM_DELETE_CANCEL]).then(function(dialogResult) {
                            if (dialogResult === LOCALIZATION.COMMON.CONFIRM_DELETE_OK) {
                                http.post("/Application/Remove", { ApplicationId: self.model.appid }).then(function() {
                                    router.navigateBack();
                                });
                            }
                        });
                    }
                    self.model.application.sendback = function () {
                        application_approve.show({ title: LOCALIZATION.APPLICATION.SENDBACK }).then(function (dialogResult) {
                            if (dialogResult.result) {
                                http.post("/Application/SendBack", { ApplicationId: self.model.appid, Comment: dialogResult.model.comment }).then(function () {
                                    router.navigateBack();
                                });
                            }
                        });
                    }
                    self.model.application.finish = function () {
                        application_approve.show({ title: LOCALIZATION.APPLICATION.FINISH }).then(function (dialogResult) {
                            if (dialogResult.result) {
                                http.post("/Application/Finish", { ApplicationId: self.model.appid, Comment: dialogResult.model.comment }).then(function () {
                                    router.navigateBack();
                                });
                            }
                        });
                    }
                    self.model.application.download = function(params) {
                        window.open(String.format("/FileAttachment/Download?ApplicationId={0}&FileAttachmentId={1}&StageId={2}&Revision={3}", self.model.appid, params.fileid, self.model.application.StageId(), self.model.application.Revision()));
                    }
                    self.model.application.remove_file = function (params) {
                        http.post("/FileAttachment/Remove", { ApplicationId: self.model.appid, FileAttachmentId: params.fileid, StageId: self.model.application.StageId(), Revision: self.model.application.Revision() }).then(function () {
                            toastr["info"](String.format(LOCALIZATION.APPLICATION.UPLOAD_FILE_REMOVED_SUCCESSED, params.filename));
                            $(self.model.application.Tasks()).each(function (idx, task) {
                                $(task.Files()).each(function (idx1, file) {
                                    if (file.Id() === params.fileid) {
                                        file.Exist(false);
                                        self.checkFinishTask(task);
                                        return;
                                    }
                                });
                            });
                        });
                    }

                    self.model.application.view_form = function (param) {
                        form_view.show($.extend(param, { app_id: self.model.appid, stageId: self.model.application.StageId(), isEdit: self.model.application.AllowEdit() })).then(function (dialogResult) {
                            if (dialogResult.result) {
                                $(self.model.application.Tasks()).each(function (idx, task) {
                                    $(task.Forms()).each(function (idx1, form) {
                                        if (form.Id() === param.form_id) {
                                            form.Valid(dialogResult.model.valid);
                                            self.checkFinishTask(task);
                                            return;
                                        }
                                    });
                                });
                            }
                        });
                    }
                    self.model.application.HasFormFile = function () {
                        var formarr = [];
                        var filearr = [];
                        $(self.model.application.Tasks()).each(function (idx, task) {
                            $(task.Forms()).each(function (idx, item) {
                                formarr.push(item);
                            });
                            $(task.Files()).each(function (idx, item) {
                                filearr.push(item);
                            });
                        });
                        var validForms = $.Enumerable.From(formarr).Any(function (x) {
                            return x.Valid();
                        });

                        var validFiles = $.Enumerable.From(filearr).Any(function (x) {
                            return x.Exist();
                        });
                        return (validForms || validFiles);
                    }

                    self.model.application.view_disbursementForm = function (param) {
                        form_view.show($.extend(param, { app_id: self.model.appid, disbursementId: param.disbursementid })).then(function (dialogResult) {                           
                        });
                    }

                    self.model.application.view_file = function (params) {
                        file_view.show({ appid: self.model.appid, fileid: params.fileid }).then(function () {
                        });
                    }

                    self.model.application.view_forms = function() {
                        application_forms.show({ appid: self.model.appid, stageId: self.model.application.StageId(), beforeFormTask: [], isBefore: false });
                    }

                    self.model.application.view_files = function () {
                        application_files.show({ appid: self.model.appid });
                    }

                    self.model.application.show_workflow = function() {
                        router.navigate(String.format("#application/workflow/{0}", self.model.appid));
                    }

                    self.model.application.show_history = function () {
                        router.navigate(String.format("#application/history/{0}", self.model.appid));
                    }

                    self.model.application.view_forms_before = function (params) {
                        var param = {
                            form_id: params.Id(),
                            stageId: self.model.application.StageId(),
                            form_name: params.Name(),
                            Valid: params.Valid(),
                            isBefore: true
                        }
                        form_view.show($.extend(param, { app_id: self.model.appid })).then(function (dialogResult) {});
                    }

                    self.model.application.appid = id;
                    self.model.application.assign = function () {
                        //var application = $.Enumerable.From(self.model.application).Single(function (x) {
                        //    return x.Id === param.id;
                        //});
                        if (self.model.application.CanDoQuickAssignment()) {
                            application_approve.show({ title: String.format("{0} & {1}", LOCALIZATION.APPLICATION.ASSIGN, LOCALIZATION.APPLICATION.APPROVE), nextstages: self.model.application.NextStages() }).then(function (dialogResult) {
                                if (dialogResult.result) {
                                    http.post("/Application/Approve", { ApplicationId: self.model.appid, BeforeStageId: self.model.application.StageId(), StageId: dialogResult.model.stage().Id, AssignedDepartmentId: dialogResult.model.department().Id, Comment: dialogResult.model.comment, AssignedUserId: dialogResult.model.user() ? dialogResult.model.user().Id : null }).then(function (data) {
                                        //self.get_applications();
                                        router.navigateBack();
                                    });
                                }
                            });
                        } else {
                            dialog.showMessage(String.format(LOCALIZATION.COMMON.CONFIRM_ASSIGN), LOCALIZATION.COMMON.CONFIRM_TITLE, [LOCALIZATION.COMMON.ACCEPT, LOCALIZATION.COMMON.CANCEL]).then(function (dialogResult) {
                                if (dialogResult === LOCALIZATION.COMMON.ACCEPT) {
                                    // phan cong chinh no
                                    http.post("/Application/Assign", { ApplicationId: self.model.appid, AssignedUserId: authenticate.userid() }).then(function () {
                                        window.location.reload(String.format("#application/detail/{0}", self.model.appid));
                                    });
                                } else {
                                    application_assign.show({ title: LOCALIZATION.APPLICATION.ASSIGN, assigned_users: self.model.application.AssignedUsers(), userid: authenticate.userid() }).then(function (dialogResult) {
                                        if (dialogResult.result) {
                                            http.post("/Application/Assign", { ApplicationId: self.model.appid, AssignedUserId: dialogResult.model.userid() }).then(function () {
                                                //self.get_applications();
                                                router.navigateBack();
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    }

                    // CR Vietcombank //
                    self.model.application.crid = ko.observable('');
                    self.model.application.cr = function () {
                        var param = { form_id: self.model.application.CrFormId(), crid: self.model.application.crid() }
                        form_view.show($.extend(param, { app_id: self.model.appid, stageId: self.model.application.StageId() })).then(function (dialogResult) {
                            if (dialogResult.result) {
                            }
                        });
                    }
                    // End CR Vietcombank //
                }
            });
        }

        this.checkFinishTask = function (task) {
            if (task.IsDisabled()) {
                var validForms = $.Enumerable.From(task.Forms()).All(function (x) {
                    return x.Valid();
                });
                var validFiles = $.Enumerable.From(task.Files()).All(function (x) {
                    return x.Exist();
                });
                task.IsFinished(validForms && validFiles);
            }
        }

        this.compositionComplete = function (view) {
            self.view = view;
            self.makeUploadControl();
        }

        this.makeUploadControl = function () {
            var inputfiles = $(self.view).find("input[type='file']");
            $(inputfiles).each(function (idx, ctl) {
                if (typeof $(ctl).data("kendoUpload") === 'undefined') {
                    $(ctl).kendoUpload({
                        async: {
                            saveUrl: String.format("/FileAttachment/Upload?ApplicationId={0}&FileAttachmentId={1}&StageId={2}&Revision={3}", self.model.appid, $(ctl).attr("fileid"), self.model.application.StageId(), self.model.application.Revision())
                        },
                        multiple: false,
                        showFileList: false,
                        localization: {
                            select: $(ctl).attr("filename")
                        },
                        upload: function() {
                        },
                        progress: function(e) {                           
                        },
                        error: function () {
                            toastr["error"](String.format(LOCALIZATION.APPLICATION.UPLOAD_FILE_FAILED, $(ctl).attr("filename")));
                        },
                        success: function() {
                            toastr["info"](String.format(LOCALIZATION.APPLICATION.UPLOAD_FILE_SUCCESSED, $(ctl).attr("filename")));
                            var fileid = $(ctl).attr("fileid");
                            $(self.model.application.Tasks()).each(function(idx, task) {
                                $(task.Files()).each(function(idx1, file) {
                                    if (file.Id() === fileid) {
                                        file.Exist(true);
                                        self.checkFinishTask(task);
                                        return;
                                    }
                                });
                            });
                        }                        
                    });
                }
            });
        }

        //this.get_applications = function () {
        //    //data.get(DATAKEY).type = self.model.type();
        //    //data.get(DATAKEY).ipage = self.model.ipage();
        //    http.post(self.model.application_url(), { ItemsPerPage: self.model.ipp, PageIndex: self.model.ipage(), FilterDisbursement: self.model.filter_Disbursement() }).then(function (result) {
        //        self.model.applications(result.Items);
        //        self.model.total_page(result.TotalPage);
        //        self.model.information.PC(result.InboxInformation.PC);
        //        self.model.information.AC(result.InboxInformation.AC);
        //        self.model.information.MC(result.InboxInformation.MC);
        //        app.trigger(EVENT.APPLICATION_INBOX_TOTAL, result.InboxInformation.Total);
        //    });
        //}

    }
    return vm;
})