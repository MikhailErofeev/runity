var AppState = Backbone.Model.extend({
    defaults: {

    }
});

var appState = new AppState();


var Employee = Backbone.Model.extend({
    urlRoot: 'http://localhost:8080/rest/v1/employee',
    defaults: {
        "name": null,
        "param2valueAndVersion": null
    }
});

var EmployeeCollection = Backbone.Collection.extend({

});

function loadEmployee(id) {
    var employee = new Employee({id: id});
    employee.fetch({async: false});
    return employee;
}