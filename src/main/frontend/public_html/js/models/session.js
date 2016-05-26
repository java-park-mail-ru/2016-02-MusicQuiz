define(function(require){

    var Backbone = require('backbone');
    var $ = require('jquery');

    var Session = Backbone.Model.extend({
        defaults: {
    		authorized: false
        },

    	url: "/api/session/",

        isAuthorized: function() {
            return !!this.get('auth');
        },

        initialize: function() {},

        sync: function (method, model, options) {
            if (method === "create") {
                method = "update";
            }
            options || (options = {});
            options.url = this.url;
            return Backbone.sync.apply(this, arguments)
        },

        validate: function(attrs, options) {

            emailValidator = /^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i
            if($.trim(attrs.email) === "" || !emailValidator.test($.trim(attrs.email))) {
                var error = "Ошибка: невалидный e-mail";
                return error;
            }
            $.trim(attrs.password);

            if(attrs.password.length < 6) {
                var error = "Ошибка: невалидный пароль";
                return error;
            }

        },

        check: function() {
            this.set('id', null);
            this.fetch({
                success: (function(obj, result) {
                    this.set('auth', true);
                    this.trigger('auth', {
                        result: true,
                        id: result.id
                    });
                }).bind(this),
                error: (function(obj, result) {
                    this.set('auth', false);
                }).bind(this)
            });
        },

        tryLogin: function(email, password) {
            this.save({
                email: email,
                password: password
            }, {
                success: (function(obj, result) {
                    this.trigger('clear');
                    $('.sign-form__password').val('')
                    $('.sign-form__email').val('')
                    this.set('auth', true);
                    this.trigger('auth', {
                        result: true,
                        id: result.id
                    });
                }).bind(this),
                error: (function(obj, result) {
                    this.set('auth', false);
                    /*this.trigger('auth', {
                        result: false,
                        error: 'Ошибка! Неверный логин и/или пароль!'
                    });*/
                    this.trigger('formError', 'Ошибка! Неверный логин и/или пароль!')
                }).bind(this)
            });
        },

        logout: function() {
            this.destroy({
                success: (function(data) {
                    this.set('auth', false);
                    this.trigger('logout');
                    console.log(this.get('auth'))
                }).bind(this)
            });
        }

    });

        return Session;

    });

