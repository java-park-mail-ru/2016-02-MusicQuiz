define(function (require) {

	var Backbone = require('backbone'),
		Session = require('models/session'),
		User = require('models/user'),
		$ = require('jquery');

	var App = Backbone.Model.extend({
		session: new Session(),
		user: new User(),

		initialize: function() {
			this.listenTo(this.session, 'auth', this._onAuth.bind(this));
			this.listenTo(this.session, 'logout', this._onLogout.bind(this));
			this.listenTo(this.session, 'clear', this.clear.bind(this));

			this.session.listenTo(this.user, 'register', (function(attrs) {
				console.log(attrs)
				this.session.tryLogin(attrs.email, attrs.password)
			}).bind(this));
		},

		clear: function() {

		},

		getSession: function() {
      		return this.session;
    	},

    	getUser: function() {
    		return this.user;
    	},

    	getAuthData: function() {
    		return {
    			isAuth: this.session.isAuthorized(),
    			user: this.user
    		}
    	},

    	_onAuth: function(result) {
    		console.log('_onAuth')
			if(result.result) {
		    	this.user.set('id', result.id);
		    	this.user.fetch({    		
		      		success: (function() {
		        		this.trigger('auth', this.getAuthData());
		      		}).bind(this),
		      		error: (function() {
		      			
		      		}).bind(this)
		    });
		  }
		},

		_onLogout: function() {

			this.trigger('auth', this.getAuthData());
		}
	});

  return new App();
});