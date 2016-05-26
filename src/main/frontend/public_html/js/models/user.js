define(function(require){

    var Backbone = require('backbone')
    var $ = require('jquery')

    var User = Backbone.Model.extend({

    	default: {
    		login: "",
    		email: "",
            points: 0
    	},

        urlRoot: "/api/user/",

        initialize: function() {},

        sync: function (method, model, options) {
            if (method === "create") {
                method = "update";
            }
            options || (options = {});
            options.url = this.url();
            return Backbone.sync.apply(this, arguments)
        },

    	validate: function(attrs, options) {
            loginValidator = /^[a-z0-9~!$%^&*_=+]*$/i
    		if($.trim(attrs.login) === "" || !loginValidator.test($.trim(attrs.login))) {
    			var error = "Ошибка: невалидный логин"
    			return error;
    		}
            emailValidator = /^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i
            if($.trim(attrs.email) === "" || !emailValidator.test($.trim(attrs.email))) {
                var error = "Ошибка: невалидный e-mail"
                return error;
            }

            $.trim(attrs.password)
            $.trim(attrs.submitPassword)
            
            if(attrs.password.length < 6) {
                var error = "Ошибка: невалидный пароль"
                return error;
            }
    	},

        signup: function(attrs) {
            this.save({
                login: attrs.login,
                email: attrs.email,
                password: attrs.password},
                {
                success: (function(obj, result) {
                    this.trigger('register', {
                        result: true,
                        email: attrs.email,
                        password: attrs.password
                    });
                }).bind(this),
                error: (function(obj, result) {
                    console.log('err')
                    this.trigger('register', {
                        result: false,
                        error: 'Ошибка! Данная почта уже используется!'
                    });
                }).bind(this)
            });
        }
    });

    return User;
});