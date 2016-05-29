define(function(require){
	var Backbone = require('backbone');
    var $ = require('jquery');

    var GameSession = Backbone.Model.extend({

    	defaults: {

            idGamesession: 0,
            time: 0,
            answer: 0, //-1 если время кончилось
            rightAnswer: 0,
            idSound: 0,
            answers: [],
            winner: false,
            points: 0,
            enemyPoints: 0,

    	},

        //isNew!!!!!!!!!
    	url: "/gameplay",

    	initialize: function() {

    	},

    	sync: function() {

    	},
        //как то сообщили что сейчас начнем игру
    	startGameSession: function() {
            this.fetch({
                //get idGamesession
                // idSound
                // answers
                //time
            })
        },

        postAnswer: function() {
            //PUT
            this.save({
                //idGamesession
                //answer
            })
        },

        getSound: function() {
            this.fetch({
                //rightAnswer
                //idSound
                //answers
            })
        },

        getGameResult: function() {
            this.fetch({
                //winner
                //points
                //enemyPoints
            })
        }

    });

    return GameSession;
});