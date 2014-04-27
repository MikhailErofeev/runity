/**
 * @author m-erofeev
 * @since 12.04.14
 */

var Controller = Backbone.Router.extend({
    routes: {
        "": "main",
        "!/data": "data",
        "!/employee/:person": "employee",
        "!/search/:query": "search"
    },

    main: function () {
        mainView.render();
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
        var employeeCollection = new EmployeeCollection();
        employeeCollection.url = employeeCollection.url + query;
        employeeCollection.fetch({ async: false });
        employeeCollectionView.model.set(employeeCollection.toJSON());
        employeeCollectionView.render();
    }
});

$(document).ready(function () {
    var controller = new Controller();
    Backbone.history.start();

    $('#employee-search-button').mouseover(function() {
        var inputValue = $('#employee-search-input').val();
        if (inputValue.length != 0) {
            $('#employee-search-button').attr('href', '#!/search/' + inputValue);
        } else {
            $('#employee-search-button').attr('href', '#');
        }
    });
});