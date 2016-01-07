//define(['plugins/http', 'plugins/router', 'knockout', './authenticate/manage'], function (http, router, ko, authenticate) {
define(['plugins/http', 'plugins/router', 'knockout'], function (http, router, ko) {
    var viewmodel = {
        router: router,
        activate: function () {
            router.guardRoute = function (instance, instruction) {
               
               // if (instruction.fragment === "login")
               //     return true;
               // if (authenticate.isAuthenticated()) {
               //     return true;
               // }
               // else {
                //    return "#login";
                //}
                return true;
            }
            return router.map([
                { route: ['', 'inbox'], moduleId: 'inbox/display' },
                { route: 'sendbox', moduleId: 'sendbox/display' },
                { route: 'application/detail/:id', moduleId: 'application/detail' },
                { route: 'application/workflow/:id', moduleId: 'application/workflow', title: LOCALIZATION.WORKFLOW.TITLE },
                { route: 'application/history/:id', moduleId: 'application/history', title: LOCALIZATION.APPLICATION.HISTORY },
                { route: 'application/history_graph/:id', moduleId: 'application/history_graph', title: LOCALIZATION.APPLICATION.HISTORY_GRAPH },
                { route: 'organization', moduleId: 'organization/display' },
                { route: 'product', moduleId: 'product/display' },
                { route: 'product_workflow/:id', moduleId: 'product_workflow/nworkflow' },
                { route: 'datamodel/:id', moduleId: 'datamodel/manage', title: LOCALIZATION.DATAMODEL.TITLE },
                { route: 'login', moduleId: 'user/login' },
                { route: 'profile', moduleId: 'account/profile' },
                { route: 'tools', moduleId: 'tools/tools', title: "Công cụ tư vấn" },
                { route: 'sbank/:id', moduleId: 'service_mapping/manage' },
                { route: 'productoverview', moduleId: 'productoverview/manage', title: "Product Overview" },
                { route: 'search', moduleId: 'search/manage', title: "Search" }
            ]).buildNavigationModel()
              .mapUnknownRoutes('inbox/display')
              .activate();
        },
        displayMenu: function () {
            return router.activeInstruction().fragment !== 'login';
        }
    }
    return viewmodel;
});