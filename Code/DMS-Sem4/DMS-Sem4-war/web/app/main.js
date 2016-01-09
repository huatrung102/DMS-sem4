requirejs.config({
    waitSeconds: 0,
    paths: {
        'text': '../lib/require/text',
        'durandal':'../lib/durandal/js',
        'plugins' : '../lib/durandal/js/plugins',
        'transitions' : '../lib/durandal/js/transitions',
        'knockout': '../lib/knockout/knockout-3.1.0',
        'komapping': '../lib/knockout/knockout.mapping',
        'bootstrap': '../lib/bootstrap/js/bootstrap',
        'jquery': '../lib/jquery/jquery-1.9.1',
        'uuid': '../lib/uuid/uuid',
        'ma': '../lib/microsoftajax/MicrosoftAjax',
        'cm': '../lib/codemirror',
        "jqx-all": "../lib/jqwidgets/jqx-all",
        'toastr': '../lib/toastr/toastr.min',
        'linq': '../lib/jquery_plugins/jquery.linq.min',
        'nestable': '../lib/jquery_plugins/jquery.nestable',
        'slimscroll': '../lib/jquery_plugins/jquery.slimscroll',
        'chosen': '../lib/jquery_plugins/chosen/chosen.jquery.min',
        'easyui': '../lib/jquery_plugins/easyui/jquery.easyui.min',
        'sortable': '../lib/jquery_plugins/jquery-sortable-min',
        'k': '../lib/kendo/js',
        'kinetic': '../lib/kinetic-v5.1.0.min',
        'visjs': '../lib/visjs/vis.min',
        'tinymce': '../lib/tinymce/tinymce.min',        
        'glDatePicker': '../lib/jquery_plugins/glDatePicker.min'
    },
    shim: {
        'bootstrap': {
            deps: ['jquery'],
            exports: 'jQuery'
        },        
        "jqx-all": {
            exports: "$",
            deps: ["jquery"]
        },
        'toastr': {
            deps: ['jquery'],
            exports: 'toastr'
        },
        'linq': {
            deps: ['jquery'],
            exports: '$'
        },
        'nestable': {
            deps: ['jquery'],
            exports: '$'
        },
        'slimscroll': {
            deps: ['jquery'],
            exports: '$'
        },
        'chosen': {
            deps: ['jquery'],
            exports: '$'
        },
        'komapping': {
            deps: ['knockout'],
            exports: 'komapping'
        },
        'easyui': {
            deps: ['jquery'],
            exports: '$'
        },
        'glDatePicker': {
            deps: ['jquery'],
            exports: '$'
        },
        'tinymce': {
            deps: ['jquery'],
            exports: 'tinymce',
            init: function () {
                this.tinyMCE.DOM.events.domLoaded = true;
                return this.tinyMCE;
            }
        }
    }
});

define(['plugins/router', 'durandal/system', 'durandal/app', 'durandal/viewLocator'], function (router, system, app, viewLocator) {
    //>>excludeStart("build", true);
    system.debug(true);
    //>>excludeEnd("build");

    app.title = "DMS";

    //specify which plugins to install and their configuration
    app.configurePlugins({
        router: true,
        dialog: true
    });

    app.start().then(function () {
        viewLocator.useConvention();
        app.setRoot('shell');
    });
});