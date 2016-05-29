require.config({
    urlArgs: "v=" + (new Date()).getTime(),
    baseUrl: "js",
    paths: {
        underscore: "lib/underscore",
        backbone: "lib/backbone",
        jquery: "lib/jquery",
        wavesurfer: "lib/wavesurfer"
    },
    shim: {
        'backbone': {
            deps: ['underscore', 'jquery'],
            exports: 'Backbone'
        },
        'underscore': {
            exports: '_'
        },
        'wavesurfer': {
            exports: 'WaveSurfer'
        }
    }
});

define(function(require) {
    var Backbone = require('backbone'),
        router = require('router'),
        app = require('app');
    Backbone.history.start();
})