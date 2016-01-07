define(['knockout'], function(ko) {    
    var vm = {
        userId: ko.observable(),
        userName: ko.observable(),
        userRole: ko.observable(0),
       // isleader: ko.observable(false),
       // createapp: ko.observable(false),
        isAuthenticated: function () {            
            var isAuthen = false;            
            $.ajax({
                url: '/user/isAuthenticated',
                async: false,
                success: function(result) {
					console.log('result isAuthenticated:' + JSON.stringify(result));
                    isAuthen = result.userId.length > 0;
                    vm.userId(result.UserId);
                    vm.userName(result.LoginName);
                    vm.userRole(result.Type);
                //    vm.createapp(result.CreateApp);
                //    vm.isleader(result.IsLeader);
                }
            });
            return isAuthen;
        }
    };
    return vm;
});