/**
 * @author m-erofeev
 * @since 12.04.14
 */

var Controller = Backbone.Router.extend({
    routes: {
        "": "main",
        "#": "main",
        "!/employee/:person": "employee"
    },

    main: function () {
        console.log("at main");
        disqusInit('http://localhost:8080');
    },

    employee: function (employeeID) {
        console.log("at employee  router " + employeeID);
        var employeeLoaded = loadEmployee(employeeID);
        console.log(employeeLoaded);
        console.log(employeeLoaded.get("name"));
        employeeView.model.set(employeeLoaded);
    }
});

$(document).ready(function () {
    var controller = new Controller();
    Backbone.history.start();
    controller.route("#!/");
});