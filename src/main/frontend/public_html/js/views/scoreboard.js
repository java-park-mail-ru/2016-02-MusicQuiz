define(function (require) {

	var Backbone = require('backbone'),
		tmpl = require('tmpl/scoreboard'),
		scores = require('collections/score')

	var scoreboardView = Backbone.View.extend({
		template: tmpl,

		initialize: function() {

		},

		render: function() {
			var html = this.template(this.collection.toJSON());
			this.$el.html(html)
		},

		show: function () {
			this.collection = new scores();

			var Show = function() {
				this.trigger('show');
            	this.render();
            	this.$el.show();
			}.bind(this)

			this.collection.fetch({
				success: Show,
				error: Show
			});


        },

		hide: function() {
			this.$el.hide()
		}
	})

	return scoreboardView;

});