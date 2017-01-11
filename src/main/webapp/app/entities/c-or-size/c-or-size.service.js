(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CORSize', CORSize);

    CORSize.$inject = ['$resource'];

    function CORSize ($resource) {
        var resourceUrl =  'api/c-or-sizes/:id';

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
