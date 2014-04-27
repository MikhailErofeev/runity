/**
 * @author m-erofeev
 * @since 12.04.14
 */

var Controller = Backbone.Router.extend({
    routes: {
        "": "main",
        "#": "main",
        "!/data": "data",
        "!/employee/:person": "employee",
        "!/search/:query": "search"
    },

    main: function () {
        console.log("at main");
        disqusInit('http://localhost:8080');
    },

    data: function () {
        console.log("data");
        uploadView.render();
    },

    employee: function (employeeID) {
        var employeeLoaded = loadEmployee(employeeID);
        employeeView.model.set(employeeLoaded);
    },

    search: function (query) {

    }
});

$(document).ready(function () {
    var controller = new Controller();
    Backbone.history.start();
    controller.route("#!/");
});