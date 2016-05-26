define(function(require){

    var Backbone = require('backbone'),
        _ = require('underscore'),
        MainView = require('views/main'),
        LoginView = require('views/login'),
        SignupView = require('views/signup'),
        GameView = require('views/game'),
        MenuView = require('views/menu'),
        ScoreboardView = require('views/scoreboard'),
        ViewManager = require('views/viewManager'),
        app = require('app')
    var $page = $('#page');

    var mainView = new MainView(),
        loginView = new LoginView(),
        signupView = new SignupView(),
        gameView = new GameView(),
        scoreboardView = new ScoreboardView(); 

    _.each([mainView,
        gameView,
        scoreboardView,        
        loginView,
        signupView], function(view) {
        ViewManager.addView(view);
    });

    var Router = Backbone.Router.extend({
        routes: {
            'game': 'gameAction',
            'scoreboard': 'scoreboardAction',
            'login': 'loginAction',
            'signup': 'signupAction',
            'logout': 'loginAction',
            '*default': 'defaultAction'
            
        },

        go: function(where) {
            return this.navigate(where, { trigger: true });
        },
        initialize: function () {
           // this.listenTo(Client.getSession(), 'login', function () { this.navigate('#main', {trigger: true})}.bind(this));
        },
        scoreboardAction: function () {
            scoreboardView.show()
        },
        gameAction: function () {
            gameView.show()
        },
        loginAction: function () {
            loginView.show()
        },
        signupAction: function () {
            signupView.show()
        },
        defaultAction: function () {
            mainView.show()
        },
        logoutAction: function() {
            app.getSession().logout();
            this.go('main');
        }
    });

    return new Router();
});