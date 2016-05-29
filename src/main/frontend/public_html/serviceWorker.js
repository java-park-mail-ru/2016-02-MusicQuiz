var CACHE = 'v3';
this.addEventListener('install', function(event) {
    event.waitUntil(
        caches.open(CACHE).then(function(cache) {
            return cache.addAll([
                '/',
                '/img/vynil.png'
                '/js/lib/require.js',
                '/css/out/main.css',
                '/js/lib/jquery.js',
                '/js/lib/underscore.js',
                '/js/lib/backbone.js',
                '/js/views/main.js',
                '/js/tmpl/main.js',
                '/js/router.js',
            ]);
        })
    );
});
this.addEventListener('fetch', function(event) {
    event.respondWith(
        fetch(event.request).then(function(response) {
            caches.open(CACHE).then(function(cache) {
                cache.put(event.request, response.clone());
            });
            return response;
        }).catch(function(error) {
            return caches.match(event.request);
        })
    );
});

this.addEventListener('activate', function(event) {
    var cache_white_list = [CACHE];
    console.log('activate');
    event.waitUntil(
        caches.keys().then(function(keyList) {
            return Promise.all(keyList.map(function(key) {
                if (cache_white_list.indexOf(key) === -1) {
                    return caches.delete(key);
                }
            }));
        })
    );
});