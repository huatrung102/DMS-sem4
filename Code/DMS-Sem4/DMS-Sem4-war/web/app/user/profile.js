define(['uuid', 'knockout', 'plugins/http', 'durandal/app', 'plugins/dialog', 'plugins/router', 'ma'], function(uuid, ko, http, app, dialog, router) {
    var vm = function() {
        var self = this;
        self.model = {
            Users: {                
                userId: ko.observable(""),
                userFullName: ko.observable(""),
                userName: ko.observable(""),
                userPassword: ko.observable(""),
                userNewPassword: ko.observable(""),
                userPassword_Retype: ko.observable(""),
               // Type: 0,
               // IsLeader: ko.observable(""),
                userStatus: ko.observable(true),
                userEmail: ko.observable(""),
                Department:{ depId: ko.observable(""),	            
	            depName: ko.observable("")},
                Role : {roleId:  ko.observable("0"),
                    roleName: ko.observable("0")},
               
            },
            userId: ko.observable(""),
            //Department: ko.observableArray([]),
           // Position: ko.observableArray([]),
            heading: ko.observable('Chỉnh sửa thông tin'),
            nvaSelected: ko.observable(0)
        };
        this.activate = function(obj) {
            self.model.userId(obj);
        }
        this.attached = function(view) {
            self.view = view;
            self.loadData();
        }
        this.loadData = function () {
            http.post("/users/getUserById").then(function (data) {
                if (typeof data !== "undefined") {
                    self.model.Users.Id(data[0].Id);
                    self.model.Users.FullName(data[0].FullName);
                    self.model.Users.LoginName(data[0].LoginName);
                    self.model.Users.Type = data[0].Type;
                    self.model.Users.IsLeader(data[0].IsLeader === true ? "true" : "false"); 
                    self.model.Users.Status(data[0].Status === true ? "true" : "false");
                    self.model.Users.Email(data[0].Email);
                    self.model.Users.Department.Id(data[0].DepartmentId);
                    self.model.Users.DepartmentName(data[0].DepartmentName);
                    self.model.Users.PositionId(data[0].PositionId);
                    self.model.Users.PositionName(data[0].PositionName);
                }
            });
        }
        this.users_edit = function() {
            http.post("/User/ProfileEdit", { obj: self.model.Users }).then(function (data) {
                if (typeof data !== "undefined") {
                    self.model.Users.Id(data.Id);
                    self.model.Users.FullName(data.FullName);
                    self.model.Users.LoginName(data.LoginName);
                    self.model.Users.Type = data.Type;
                    self.model.Users.IsLeader(data.IsLeader == true ? "true" : "false");
                    self.model.Users.Status(data.Status == true ? "true" : "false");
                    self.model.Users.Email(data.Email);
                    self.model.Users.Department.Id(data.DepartmentId);
                    self.model.Users.DepartmentName(data.DepartmentName);
                    self.model.Users.PositionId(data.PositionId);
                    self.model.Users.PositionName(data.PositionName);
                }
            });
        }
        this.users_update_handler = function (param) {
            
        }
        this.edit_Info = function (param) {            
            if (param.code === 0) {
                self.model.nvaSelected(param.code);
                self.model.heading("Chỉnh sửa thông tin");
            } else if (param.code === 1) {
                self.model.nvaSelected(param.code);
                self.model.heading("Thay đổi mật khẩu");
            }
        }
        this.users_changepassword = function () {
            if (self.model.Users.Password() === self.model.Users.Password_Retype()) {
                http.post("/User/ChangePassword", { obj: self.model.Users }).then(function(data) {
                    if (typeof data !== "undefined") {
                        self.model.Users.Id(data.Id);
                        self.model.Users.FullName(data.FullName);
                        self.model.Users.LoginName(data.LoginName);
                        self.model.Users.Type = data.Type;
                        self.model.Users.IsLeader(data.IsLeader === true ? "true" : "false");
                        self.model.Users.Status(data.Status === true ? "true" : "false");
                        self.model.Users.Email(data.Email);
                        self.model.Users.Department.Id(data.DepartmentId);
                        self.model.Users.DepartmentName(data.DepartmentName);
                        self.model.Users.PositionId(data.PositionId);
                        self.model.Users.PositionName(data.PositionName);
                    }
                });
            } else {
                alert("Mật khẩu nhật không giống nhau!");
            }
            
        }


        this.testPrint = function () {
            $.ajax({
                url: '/Product/PrintAll',
                data: { id: "5d7c3421-406c-4978-876f-62e0fc8a8f7a" },
                async: false,
                success: function (data) {
                    var frame = $('<iframe/>');
                    frame[0].name = "frame";
                    frame.css({ "position": "absolute", "top": "-1000000px" });
                    $("body").append(frame);

                    //var head = frame.contents().find("head");
                    //head.append($("<link/>",
                    //    { rel: "stylesheet", href: "./style.css", type: "text/css" }
                    //));

                    frame.contents().find('head').append(
                        $('<link/>', { rel: 'stylesheet', href: 'iframe.css', type: 'text/css' })
                    );


                    //<div class="container" style="margin-top: 38px; visibility: hidden" id="pnForm">


                    //$(data).each(function(idx, item) {
                    //    var contents = item.Content;
                    //    var frame1 = $('<iframe />');
                    //    frame1[0].name = "frame1";
                    //    frame1.css({ "position": "absolute", "top": "-1000000px" });
                    //    $("body").append(frame1);
                    //    var frameDoc = frame1[0].contentWindow ? frame1[0].contentWindow : frame1[0].contentDocument.document ? frame1[0].contentDocument.document : frame1[0].contentDocument;
                    //    frameDoc.document.open();

                    //    frameDoc.document.write('<html><head><title>DIV Contents</title>');
                    //    frameDoc.document.write('</head><body>');

                    //    frameDoc.document.write('<link href="./css/style.css" rel="stylesheet" type="text/css" />');

                    //    //document.write('<script >alert("HALLO");<\x2fscript>');

                    //    frameDoc.document.write(contents);
                    //    frameDoc.document.write('</body></html>');
                    //    frameDoc.document.close();
                    //    setTimeout(function () {
                    //        window.frames["frame1"].focus();
                    //        window.frames["frame1"].print();
                    //        frame1.remove();
                    //    }, 500);
                    //});
                }
            });
            
            
        }
    }
    return vm;
});