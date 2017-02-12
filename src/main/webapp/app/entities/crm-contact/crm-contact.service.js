(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CrmContact', CrmContact);

    CrmContact.$inject = ['$resource', 'DateUtils'];

    function CrmContact ($resource, DateUtils) {
        var resourceUrl =  'api/crm-contacts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.paymentDate = DateUtils.convertLocalDateFromServer(data.paymentDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.paymentDate = DateUtils.convertLocalDateToServer(copy.paymentDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.paymentDate = DateUtils.convertLocalDateToServer(copy.paymentDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
