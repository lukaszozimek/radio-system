(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LIBLabel', LIBLabel);

    LIBLabel.$inject = ['$resource'];

    function LIBLabel ($resource) {
        var resourceUrl =  'api/l-ib-labels/:id';

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
