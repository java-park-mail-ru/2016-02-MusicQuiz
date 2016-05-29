define(function (require) {

	var Backbone = require('backbone'),
		tmpl = require('tmpl/signup'),
		User = require('models/user'),
		app = require('app');


	var SignupView = Backbone.View.extend({
		events: {
			"submit ": "trySignup"/*,
			"click #pop-up": "popup",
			"change input[type=file][class=upload-pic]": "photo"*/
		},

		template: tmpl,
		model: User,
		expectAuth: false,

		initialize: function() {
			this.template = tmpl;
			this.render();
			this.listenTo(app.getSession(), 'formError', this.showError);
			this.inputs = {
				'login': this.$el.find('.sign-form__login'),
		        'email': this.$el.find('.sign-form__email'),
		        'password': this.$el.find('.sign-form__password'),
		        'submitPassword': this.$el.find('.sign-form__submit-password')
			}
		},

		render: function() {
			this.$el.html(this.template);
			return this;
		},

		trySignup: function(e) {
			e.preventDefault();

		    var errorField = this.$el.find('.sign-form-error').text('');
			var uData = {
        		'login': this.inputs.login.val(),
        		'email': this.inputs.email.val(),
        		'password': this.inputs.password.val()/*,
        		'submitPassword': this.inputs.submitPassword.val()*/
      		};
      		var u = new User()
			var error = u.validate(uData);

			if (error != undefined ) {
				errorField.text(error);
			} 
			else {
				var user = app.getUser();
				user.once('register', (function(result) {
					this.expectAuth = true;
				}).bind(this));

				user.signup(uData);
			}
			return false;
		},

		showError: function(err) {
			errorField = this.$el.find('.sign-form-error').text(err);
		},

		onAuth: function(result) {
			console.log('onAuth')
			var router = require('router');
			console.log(result.isAuth)
		    if(result.isAuth && this.expectAuth) {
		    	this.expectAuth = false;
		        router.go('');
      }
		},

		/*photo: function(e) {

			input = $('.upload-pic')[0];
            photo = $('#photo')[0];

            var reader = new FileReader();

	        reader.onload = function (e) {
	            $('#photo').attr('src', e.target.result);
	        }

	        reader.readAsDataURL(input.files[0]);
		},

		popup: function(e) {
			e.preventDefault();

			this.$('.hide-layout').show();
			this.$('.pop-up__camera').show();
			this.$('.pop-up__camera__record').show();
			this.$('.pop-up__camera__buttons').show();
			this.$('.pop-up__camera__btn').show();

			var video = this.$('#video')[0],
				canvas = this.$('canvas')[0],
				photo = this.$('#photo')[0],
				preview = this.$('#preview')[0],
				context = canvas.getContext('2d'),
				vendorUrl = window.URL || window.webkitURL;
			var Mystream;
			canvas.width = 480;
			canvas.height = 480;
			navigator.getMedia = (  navigator.getUserMedia ||
									navigator.webkitGetUserMedia ||
									navigator.mozGetUserMedia || 
									navigator.msGetUserMedia);
			navigator.getMedia({
			   video: true,
			   audio: false
			}, function(stream) {
				Mystream = stream;
			   	video.src = vendorUrl.createObjectURL(Mystream);
			   	video.play();
			  	}, function(error) {
			   		alert('Ошибка! Что-то пошло не так, попробуйте позже.');
			  		});
			$('#shot')[0].addEventListener('click', function() {
				context.drawImage(video, -80, 0);
				photo.setAttribute('src', canvas.toDataURL('image/png'));
				var track = Mystream.getTracks()[0];
				track.stop();
				$('.hide-layout').hide();
				$('.pop-up__camera').hide();
				$('.pop-up__camera__record').hide();
				$('.pop-up__camera__buttons').hide();
				$('.pop-up__camera__btn').hide();
			  })

			$('#cancel')[0].addEventListener('click', function(e) {
				e.preventDefault();
				if(Mystream) {
					var track = Mystream.getTracks()[0];
					track.stop();
				}
				$('.hide-layout').hide();
				$('.pop-up__camera').hide();
				$('.pop-up__camera__record').hide();
				$('.pop-up__camera__buttons').hide();
				$('.pop-up__camera__btn').hide();
			})
			
		},*/

		show: function () {

            this.trigger('show');
            this.delegateEvents();
            this.$el.show();
        },

		hide: function() {
			this.$el.hide();
		}
	})

	return SignupView;
});