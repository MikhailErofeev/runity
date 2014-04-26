var EmployeeView = Backbone.View.extend({
//    el: '#content-container',
    model: new Employee(),

    initialize: function () {
        this.model.bind('change', this.render, this);
    },

    render: function () {
//        console.log("start render employee view");
//        console.log(this.model);

        if (this.template === undefined || this.template === null)
            this.template = _.template($('#employee-template').html());

        $('#content-container').html(this.template(this.model.changed));
        disqusInit('http://localhost:8080#!/employee/' + this.model.get("id"));
        return this;
    }

});

var UploadView = Backbone.View.extend({

    render: function () {
//        console.log("start render employee view");
//        console.log(this.model);

        if (this.template === undefined || this.template === null)
            this.template = _.template($('#data-template').html());

        $('#content-container').html(this.template());
        disqusInit('http://localhost:8080#!/data');
        return this;
    }

});

var employeeView = new EmployeeView();
var uploadView = new UploadView();