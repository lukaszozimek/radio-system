(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('TRACustomer', TRACustomer);

    TRACustomer.$inject = ['$resource'];

    function TRACustomer ($resource) {
        var resourceUrl =  'api/t-ra-customers/:id';

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
