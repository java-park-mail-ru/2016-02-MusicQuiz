define(function (require) {

    var Backbone = require('backbone'),
        GameView = require('views/game'),
        ScoreboardView = require('views/scoreboard'),       
        LoginView = require('views/login'),
        SignupView = require('views/signup'),
        MainView = require('views/main');
    var app = require('app');

    
    var Manager = Backbone.View.extend({

        el: '#page',

        views: [],

        initialize: function() {
            this.listenTo(app, 'auth', this.onAuth.bind(this));
        },

        addView: function(view) {
            this.views.push(view);
            this.$el.append(view.el); 
            this.listenTo(view, 'show', this.onChangeView.bind(this, view));
                       
        },

        onChangeView: function(newView) {
            var views = _.filter(this.views, function(view) {
                return view != newView;
            });
            return this.handleViewsEvent('hide', [], views);
        },

        handleViewsEvent: function(method, args, views) {
            views = views || this.views;

            _.each(views, function(view) {
                if(typeof view[method] === 'function') {
                    view[method].apply(view, args);
                }
            });
        },

        onAuth: function(result) {
            return this.handleViewsEvent('onAuth', arguments);
        }

    });

    return new Manager();

});