define(function(require) {

	var Backbone = require('backbone'),
		tmpl = require('tmpl/main'),
		app = require('app');


	var mainView = Backbone.View.extend({

		initialize: function() {

			 if ('serviceWorker' in navigator) {
                navigator.serviceWorker.register('/serviceWorker.js').then(function(reg) {
                	console.log('Registration succeeded. Scope is ' + reg.scope);
	            }).catch(function(error) {
	            	console.log('Registration failed with ' + error);
	            });
            }

			this.template = tmpl;
			this.render();
		},

		render: function() {

			var authData = app.getAuthData();
			var html = this.template({
				isAuth: authData.isAuth,
				username: authData.user.get('login'),
				score: authData.user.get('points')
			});

			this.$el.html(html);
		},

		show: function () {
			if(!navigator.onLine) {
				console.log('Проверьте соединение с интернетом!')
			}
			this.render();
            this.trigger('show');
            this.$el.show();
        },

		hide: function() {
			this.$el.hide()
		},

		onAuth: function() {
			this.render();
		}
	});

	return mainView;
});