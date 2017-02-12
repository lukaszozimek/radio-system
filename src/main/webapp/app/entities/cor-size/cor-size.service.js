(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CorSize', CorSize);

    CorSize.$inject = ['$resource'];

    function CorSize ($resource) {
        var resourceUrl =  'api/cor-sizes/:id';

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
