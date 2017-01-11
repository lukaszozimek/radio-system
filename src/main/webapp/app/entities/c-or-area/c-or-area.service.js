(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CORArea', CORArea);

    CORArea.$inject = ['$resource'];

    function CORArea ($resource) {
        var resourceUrl =  'api/c-or-areas/:id';

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
