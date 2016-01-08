define(['./position_detail', 'uuid', 'knockout', 'plugins/http', 'durandal/app', 'plugins/dialog', 'plugins/router', 'slimscroll', 'nestable', 'chosen', 'ma'], function (position_detail, uuid, ko, http, app, dialog, router) {
   var vm = function() {
       var self = this;
       this.model = {
           Position: ko.observableArray([]),
           SeletedItem: ko.observable(""),
           filter_position: [],
           ExpandItems: [],
           TotalPosition: ko.observable('0')
       };
       this.attached = function (view) {
           self.view = view;
           self.loadData();

           var positionFilter = $(self.view).find('.position-filter');
           positionFilter.css({ display: 'none' });//inline
           positionFilter.chosen().change(function () { self.filter_handler(); });
       }
       this.backupState = function () {
           Array.clear(self.model.ExpandItems);
           var expandItems = $(self.view).find(".dd-item.node-item").not(".dd-collapsed");
           if (expandItems.length >= 0) {
               $(expandItems).each(function (idx, item) {
                   self.model.ExpandItems.push($(item).attr("dataid"));
               });
           }
       }
       this.restoreState = function () {
           $(self.model.ExpandItems).each(function (idx, item) {
               var liitem = $(self.view).find(String.format(".dd-item.node-item[dataid='{0}']", item));
               if (liitem.length > 0)
                   self.tree_control.data("nestable").expandItem(liitem);
               //self.tree_control.nestable('expandItem', liitem[0]);
           });
       }
       this.loadData = function () {
           http.get('/Position/GetAll').then(function (data) {
               self.backupState();
               self.model.TotalPosition(data.length);
               self.model.Position.removeAll();
               $(data).each(function (idx, item) {
                   self.model.Position.push({
                       Id: item.Id,
                       Name: item.Name,
                       Code: item.Code,
                       Filter: ko.observable(1)
                   });
               });

               self.tree_control = $(self.view).find('.dd');
               self.tree_control.removeData();
               self.tree_control.nestable({});
               self.tree_control.nestable('collapseAll');
               self.restoreState();

               var height = $(window).height();
               var width = $(window).width();

               $(self.view).find('.full-height-scroll').slimscroll({
                   height: height - 206,
                   wheelStep: 2,
                   alwaysVisible: true
               });

               
               $(self.view).find('.position-filter').trigger("chosen:updated");
               
               self.filter_handler();

           });
           $(self.view).find(".position-filter").chosen({ width: '100%' });
       }
       this.position_add = function() {
           self.position_update_handler({
               Id: uuid.v4(),
               Name: '',
               Code:''
           });
       }
       this.selectItem = function (param) {
           self.model.SeletedItem(param.Id);
           
       }
       this.position_remove = function (param) {
           dialog.showMessage(LOCALIZATION.COMMON.CONFIRM_DELETE_MESSAGE, LOCALIZATION.COMMON.CONFIRM_TITLE, [LOCALIZATION.COMMON.ACCEPT, LOCALIZATION.COMMON.CANCEL]).then(function (dialogResult) {
               if (dialogResult === LOCALIZATION.COMMON.ACCEPT) {
                   http.post('/Position/Delete', { id: param.Id }).then(function () {
                       self.loadData();
                   });
               }
           });
       }
       this.position_edit = function (param) {
           self.position_update_handler(param);
       }
       this.filter_handler = function() {
           self.backupState();
           if (self.model.filter_position.length > 0) {
               $(self.model.Position()).each(function (idx, item) {
                   if (self.model.filter_position.indexOf(item.Id) != -1) {
                       item.Filter(1);
                   } else {
                       item.Filter(0);
                   }
               });
           } else {
               $(self.model.Position()).each(function (idx, item) {
                   item.Filter(1);
               });
           }
       }
       this.position_update_handler = function (param) {
           position_detail.show(param).then(function (dialogResult) {
               if (dialogResult.result) {
                   http.post("/Position/Save", { obj: dialogResult.model }).then(function () {
                       self.loadData();
                   });
               }
           });
       }
   }
    return vm;
});