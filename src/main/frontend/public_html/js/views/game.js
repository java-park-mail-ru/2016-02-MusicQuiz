define(function(require) {

	var Backbone = require('backbone'),
		tmpl = require('tmpl/game'),
		WaveSurfer = require('wavesurfer'),
		SinglePlayer = require('views/game/singleplayer');
		app = require('app');


	var gameView = Backbone.View.extend({
		template: tmpl,
		events: {
			"click .song_name": "postAnswer",
		//	"click #single": "singlePlayer",
		},
		initialize: function() {
			this.render()
			

		},

		render: function() {
			this.$el.html(this.template)
			return this;
		},

		show: function () {
            this.trigger('show');
            this.$el.show();
            this.exampleSocket = new WebSocket("ws://0.0.0.0:9000/api/gameplay");
            console.log(app.getAuthData().isAuth)
        },

		hide: function() {
			this.$el.hide()
		},

		startGame: function() {
			this.buttons = {
				'btn1': this.$('.song__button#1'),
				'btn2': this.$('.song__button#2'),
				'btn3': this.$('.song__button#3'),
				'btn4': this.$('.song__button#4')
			}

			//this.exampleSocket = new WebSocket("ws://0.0.0.0:9000/api/gameplay");

			this.exampleSocket.onmessage = function(event) {
				var msg = JSON.parse(event.data);
				this.idGamesession = msg.idGamesession;
				this.idSound = msg.idSound;
				this.answers = msg.answers;
				this.time = msg.time;
			}
		},

		click: function(answers) {
			changeText(answers);
			this.$('.song__button').once('click', function(event) {
				var selected = $(event.target)
				postAnswer(selected);
				getSound();
				click(this.answers);
			});

		},

		postAnswer: function(answer) {
			var msg = {
				id: this.idGamesession,
				answer: answer
			}
			this.exampleSocket.send(JSON.stringify(msg))
		},

		getSound: function() {
			exampleSocket.onmessage = function(event) {
				var msg = JSON.parse(event.data);
				this.rightAnswer = msg.rightAnswer;
				this.idSound = msg.idSound;
				this.answers = msg.answers;
			}
		},

		getGameResult: function() {
			exampleSocket.onmessage = function(event) {
				var msg = JSON.parse(event.data);
				this.winner = msg.winner;
				this.points = msg.points;
				this.enemyPoints = msg.enemyPoints;
			} 
		},

		game: function() {
			startGame();
			click(this.answers);

		},


		changeText: function(answers) {
			this.buttons.btn1.text(answers[0])
			this.buttons.btn2.text(answers[1])
			this.buttons.btn3.text(answers[2])
			this.buttons.btn4.text(answers[3])
		},


	})

	return gameView;

});