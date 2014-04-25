var AppState = Backbone.Model.extend({
    defaults: {
        originPlanet: "",
        alternativePlanet: "",
        viewType: "",
        year: 0,
        month: 0,
        week: 0
    }
});

var appState = new AppState();


var LinkedMonth = Backbone.Model.extend({
    defaults: {
        "origin": null,
        "alternative": null
    }
});

function getSample() {
    sampleMonths = new LinkedMonths();
    sampleMonths.fetch({url: "mars-month-example.json", async: false});
    return sampleMonths
}