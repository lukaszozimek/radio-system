(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CORRange', CORRange);

    CORRange.$inject = ['$resource'];

    function CORRange ($resource) {
        var resourceUrl =  'api/c-or-ranges/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
