(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CorChannel', CorChannel);

    CorChannel.$inject = ['$resource'];

    function CorChannel ($resource) {
        var resourceUrl =  'api/cor-channels/:id';

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
