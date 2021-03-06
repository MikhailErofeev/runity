var MainView = Backbone.View.extend({
   render: function() {
       if (this.template === undefined || this.template === null)
           this.template = _.template($('#main').html());

       $('#content-container').html(this.template());

       $('#main-search-button').mouseover(function() {
           var inputValue = $('#main-search-input').val();
           if (inputValue.length != 0) {
               $('#main-search-button').attr('href', '#!/search/' + inputValue);
           } else {
               $('#main-search-button').attr('href', '#');
           }
       });
       return this;
   }
});

var EmployeeView = Backbone.View.extend({
    model: new Employee(),
    initialize: function () {
        this.model.bind('change', this.render, this);
    },
    render: function () {
        if (this.template === undefined || this.template === null)
            this.template = _.template($('#employee-template').html());

        $('#content-container').html(this.template(this.model.attributes.toJSON()));
        disqusInit('http://localhost:8080#!/employee/' + this.model.get("id"));



        return this;
    }
});

var UploadView = Backbone.View.extend({
    render: function () {
        if (this.template === undefined || this.template === null)
            this.template = _.template($('#data-template').html());

        $('#content-container').html(this.template());
        disqusInit('http://localhost:8080#!/data');
        return this;
    }
});

var EmployeeCollectionView = Backbone.View.extend({
    model: new EmployeeCollection(),
    initialize: function () {
//        this.model.bind('change', this.render, this);
    },
    render: function() {
        if (this.template === undefined || this.template === null)
            this.template = _.template($('#employee-collection-template').html());

        $('#content-container').html(this.template(this.model));
        return this;
    }
});

var mainView = new MainView();
var employeeView = new EmployeeView();
var uploadView = new UploadView();
var employeeCollectionView = new EmployeeCollectionView();