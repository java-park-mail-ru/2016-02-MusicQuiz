define(function(require) {
  var Backbone = require('backbone');
  QUnit.module('models/score');

  QUnit.test('Разные варианты создания объектов Score', function() {
    var Score = require('models/score');

    var toTest = [
      [{ name: 'Name', score: 2 }, 'Name'],
      [{ name: 'Имя', score: 1 }, 'Имя'],
      [{}, 'Без параметров'],
      [{ name: 'Name' }, 'Без очков'],
      [{ score: 15 }, 'Без имени'],
      [{ name: 'Negative', score: -2 }, 'С отрицательными очками']
    ];

    _.each(toTest, function(item) {
      var obj = new Score(item[0]);
      QUnit.ok(obj instanceof Backbone.Model, item[1]);
    });
  });
});