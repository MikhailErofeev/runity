/**
 * @author m-erofeev
 * @since 12.04.14
 */

var Controller = Backbone.Router.extend({
    routes: {
        "": "main",
        "!/employee/:person": "employee"
    },

    main: function () {
        console.log("at main")
        disqusInit('http://localhost:8080#!');
    },

    employee: function (employeeID) {
        console.log("at employee " + employeeID);
        disqusInit('http://localhost:8080#!/employee/bg');
    }
});

$(document).ready(function () {
    var controller = new Controller();
    Backbone.history.start();
    controller.route("#!/");
});