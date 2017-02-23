(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('TraOrderStatus', TraOrderStatus);

    TraOrderStatus.$inject = ['$resource'];

    function TraOrderStatus ($resource) {
        var resourceUrl =  'api/tra-order-statuses/:id';

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
