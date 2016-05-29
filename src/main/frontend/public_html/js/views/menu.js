define(function (require) {

	var Backbone = require('backbone'),
		tmpl = require('tmpl/menu')

	var gameView = Backbone.View.extend({
		template: tmpl,

		initialize: function() {
			this.render()
		},

		render: function() {
			this.$el.html(this.template)
			return this;
		},

		show: function () {
            this.trigger('show');
            this.$el.show();
        },

		hide: function() {
			this.$el.hide()
		}
	})

	return gameView;
});