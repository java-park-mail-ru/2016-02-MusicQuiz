define(function(require){

	var Backbone = require('backbone'),
		model = require('models/score');

	var ScoreBoard = Backbone.Collection.extend({

		model: model,
		url: 'api/scores',

		comparator : function(userScore) {
			return -userScore.get('score')
		}

	});

	return ScoreBoard;
})

