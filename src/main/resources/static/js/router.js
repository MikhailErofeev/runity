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
        var template = $('#employee-template');

        if (template.length == 0) {

        }

//        if (employeeView.template === null)
//            employeeView.template = _.template(template.html());

        var employeeLoaded = loadEmployee(employeeID);
        employeeView.model.set(employeeLoaded);
    }
});

$(document).ready(function () {
    var controller = new Controller();
    Backbone.history.start();
    controller.route("#!/");
});