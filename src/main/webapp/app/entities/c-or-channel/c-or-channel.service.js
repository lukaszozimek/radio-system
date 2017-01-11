(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CORChannel', CORChannel);

    CORChannel.$inject = ['$resource'];

    function CORChannel ($resource) {
        var resourceUrl =  'api/c-or-channels/:id';

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
