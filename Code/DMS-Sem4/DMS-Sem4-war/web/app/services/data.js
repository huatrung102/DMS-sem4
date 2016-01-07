define([], function() {
    var vm = {
        data: {},
        add: function(key, data) {
            vm.data[key] = data;
        },
        get: function(key) {
            return vm.data[key];
        },
        remove: function(key) {
            delete vm.data[key];
        },
        clear: function() {
            for (var key in vm.data) {
                delete vm.data[key];
            }
        }
    }
    return vm;
})