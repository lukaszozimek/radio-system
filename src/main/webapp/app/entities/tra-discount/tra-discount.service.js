(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('TraDiscount', TraDiscount);

    TraDiscount.$inject = ['$resource', 'DateUtils'];

    function TraDiscount ($resource, DateUtils) {
        var resourceUrl =  'api/tra-discounts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.validFrom = DateUtils.convertLocalDateFromServer(data.validFrom);
                        data.validTo = DateUtils.convertLocalDateFromServer(data.validTo);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.validFrom = DateUtils.convertLocalDateToServer(copy.validFrom);
                    copy.validTo = DateUtils.convertLocalDateToServer(copy.validTo);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.validFrom = DateUtils.convertLocalDateToServer(copy.validFrom);
                    copy.validTo = DateUtils.convertLocalDateToServer(copy.validTo);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();