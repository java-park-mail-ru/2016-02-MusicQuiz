define(function(require){

    var Backbone = require('backbone')

    var Scoreboard = Backbone.Model.extend({

    	default: {
    		'name': '',
    		'score': 0
    	}

    });

    return Scoreboard;
});