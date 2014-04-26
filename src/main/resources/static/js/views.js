var EmployeeView = Backbone.View.extend({
//    el: '#content-container',
    model: new Employee(),

    initialize: function () {
        this.model.bind('change', this.render, this);
    },

    render: function () {
//        console.log("start render employee view");
//        console.log(this.model);

        this.template = _.template($('#employee-template').html());
        $('#content-container').html(this.template(this.model.attributes));
        disqusInit('http://localhost:8080#!/employee/' + this.model.get("id"));
        return this;
    }

});

var employeeView = new EmployeeView();