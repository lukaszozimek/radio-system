(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('TraInvoiceStatus', TraInvoiceStatus);

    TraInvoiceStatus.$inject = ['$resource'];

    function TraInvoiceStatus ($resource) {
        var resourceUrl =  'api/tra-invoice-statuses/:id';

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
