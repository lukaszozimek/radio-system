(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LIBLibrary', LIBLibrary);

    LIBLibrary.$inject = ['$resource'];

    function LIBLibrary ($resource) {
        var resourceUrl =  'api/l-ib-libraries/:id';

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
