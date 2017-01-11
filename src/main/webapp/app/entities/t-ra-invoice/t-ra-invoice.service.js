(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('TRAInvoice', TRAInvoice);

    TRAInvoice.$inject = ['$resource', 'DateUtils'];

    function TRAInvoice ($resource, DateUtils) {
        var resourceUrl =  'api/t-ra-invoices/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.paymentDay = DateUtils.convertLocalDateFromServer(data.paymentDay);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.paymentDay = DateUtils.convertLocalDateToServer(copy.paymentDay);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.paymentDay = DateUtils.convertLocalDateToServer(copy.paymentDay);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
