define(['durandal/app', 'plugins/router', 'nestable', 'ma'], function (app, router) {
    function sleep(milliseconds) {
        var start = new Date().getTime();
        for (var i = 0; i < 1e7; i++) {
            if ((new Date().getTime() - start) > milliseconds) {
                break;
            }
        }
    }
    var vm = function () {
        this.activate = function() {
            sleep(1);
        }
        this.attached = function (view) {

        }
        //this.go = function() {
        //    router.navigate(String.format("#page/{0}", "00000000-0000-0000-0000-000000000001"));
        //}
        //this.go1 = function () {
        //    router.navigate(String.format("#page/{0}", "00000000-0000-0000-0000-000000000000"));
        //}
        //this.showUsers = function() {
        //    app.trigger(LOCALIZATION.EVENT.ORGANIZATION_DETAIL);
        //}
    }

    return vm;
});