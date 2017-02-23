(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CorPerson', CorPerson);

    CorPerson.$inject = ['$resource'];

    function CorPerson ($resource) {
        var resourceUrl =  'api/cor-people/:id';

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
