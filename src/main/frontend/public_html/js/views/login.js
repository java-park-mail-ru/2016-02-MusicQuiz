define(function (require) {

	var Backbone = require('backbone'),
		tmpl = require('tmpl/login'),
		app = require('app');

	var loginView = Backbone.View.extend({

		events: {
			"submit ": "tryLogin"
		},

		initialize: function () {
			this.template = tmpl;
			this.render();
			this.inputs = {
				'password': this.$el.find('.sign-form__password'),
				'email': this.$el.find('.sign-form__email')
			}
			this.listenTo(app.getSession(), 'formError', this.showError);
		},

		render: function () {
			var html = this.template();
			this.$el.html(html);
		},

		show: function () {
            this.trigger('show');
            this.delegateEvents();
            this.$el.show();
        },

		hide: function () {
			this.$el.hide()
		},

		clearFields: function() {
			this.$('.sign-form__password').val('')
			this.$('.sign-form__email').val('')
		},

		tryLogin: function (e) {
			e.preventDefault()

        	var uData = {
		        'email': this.inputs.email.val(),
		        'password': this.inputs.password.val()
		    }

			var session = app.getSession();
			var errorField = this.$el.find('.sign-form-error').text('');
			var error = session.validate(uData);

			if (uData.password.length === 0 || uData.email.length === 0) {
				this.showError('Ошибка! Неверный логин и/или пароль!!!');
			} 
			else {
				session.once('auth', (function(result) {
					this.expectAuth = true;
				}).bind(this));
				session.tryLogin(uData.email, uData.password);
			}
			return false;

		},

		showError: function (err) {
			errorField = this.$el.find('.sign-form-error').text(err);
		},

		onAuth: function(result) {
			var router = require('router');
			if(result.isAuth && this.expectAuth) {
		        this.expectAuth = false;
		        router.go('');
      		}
		}
	});


	return loginView;
});