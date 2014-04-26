var EmployeeView = Backbone.View.extend({

    model: new Employee(),

    initialize: function () {
        this.model.bind('change', this.render, this);
    },

    render: function () {
        console.log("start render employee view");
        console.log(this.model);
        disqusInit('http://localhost:8080#!/employee/' + this.model.get("id"));
        return this;
    }

});

var employeeView = new EmployeeView();