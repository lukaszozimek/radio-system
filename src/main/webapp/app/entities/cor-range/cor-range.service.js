(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CorRange', CorRange);

    CorRange.$inject = ['$resource'];

    function CorRange ($resource) {
        var resourceUrl =  'api/cor-ranges/:id';

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
