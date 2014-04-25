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

    employee: function (person) {
        console.log("at employee " + person)
        var disqus_url = "#!/employee/" + person;
    }
});

$(document).ready(function () {
    var controller = new Controller();
    Backbone.history.start();
    controller.route("#!/");
});