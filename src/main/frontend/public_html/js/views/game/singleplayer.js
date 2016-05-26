define(function(require) {
		$ = require('jquery'),
		_ = require('underscore');

	var soundCounter = 5;

	var score = 0

	var audio_urlRoot = '/api/music/'

	//var exampleSocket = new WebSocket("ws://0.0.0.0:9000/gameplay");



	function shuffle(a) {
	    var j, x, i;
	    for (i = a.length; i; i -= 1) {
	        j = Math.floor(Math.random() * i);
	        x = a[i - 1];
	        a[i - 1] = a[j];
	        a[j] = x;
	    }
	}

	function nextTrack() {
		//получаем следующий трек
		//ждем нажатия .once
		//вызываем заново с другими аргументами
		//и фиксацию времени сделать
	}



	var model = {
		'a': {
			author: '1',
			id: 1,
			answer: ['author1', 'a2', 'a3'],
			title: 'Название песни'
		},
		'b': {
			author: '2',
			id: 2,
			answer: ['1n', '2n', '3n']
		},
		'c': {
			author: '3',
			id: 3,
			answer: ['1n', '2n', '3n']
		},
		'd': {
			author: '4',
			id: 4,
			answer: ['1n', '2n', '3n']
		},
		'e': {
			author: '5',
			id: 5,
			answer: ['1n', '2n', '3n']
		},
	}

	return function() {
		var buttons = {
			'btn1': this.$('.song__button#1'),
			'btn2': this.$('.song__button#2'),
			'btn3': this.$('.song__button#3'),
			'btn4': this.$('.song__button#4')
		}


// функция которая после нажатия вызывает себя же со след аргументами то есть с новой песней
// и все это фиксируется временем
			console.log('call')
			var answers = model.a.answer.slice()
			answers.push(model.a.author)
			var right = model.a.author
			//_.shuffle(answers);
			shuffle(answers)
			var url = audio_urlRoot + a.id

			
			buttons.btn1.text(answers[0])
			buttons.btn2.text(answers[1])
			buttons.btn3.text(answers[2])
			buttons.btn4.text(answers[3])
			this.$('.song__button').once('click', function(event) {
				//console.log($(event.target).text())
				var selected = $(event.target)
				if(selected.text() === right) {
					score++;// переделать с зависимостью от времени
					console.log(score)
				}
				/*buttons.btn1.text('')
				buttons.btn2.text('')
				buttons.btn3.text('')
				buttons.btn4.text('')*/
				answers.length = 0;
			})

	}

});