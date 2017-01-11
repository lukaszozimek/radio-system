(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CORPerson', CORPerson);

    CORPerson.$inject = ['$resource'];

    function CORPerson ($resource) {
        var resourceUrl =  'api/c-or-people/:id';

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
