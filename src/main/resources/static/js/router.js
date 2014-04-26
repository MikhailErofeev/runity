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
    },

    employee: function (employeeID) {
        console.log("at employee " + employeeID)
    }
});

$(document).ready(function () {
    var controller = new Controller();
    Backbone.history.start();
    controller.route("#!/");
});