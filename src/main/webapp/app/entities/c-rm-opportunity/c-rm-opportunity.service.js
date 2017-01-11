(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CRMOpportunity', CRMOpportunity);

    CRMOpportunity.$inject = ['$resource', 'DateUtils'];

    function CRMOpportunity ($resource, DateUtils) {
        var resourceUrl =  'api/c-rm-opportunities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.lastTry = DateUtils.convertLocalDateFromServer(data.lastTry);
                        data.closeDate = DateUtils.convertLocalDateFromServer(data.closeDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.lastTry = DateUtils.convertLocalDateToServer(copy.lastTry);
                    copy.closeDate = DateUtils.convertLocalDateToServer(copy.closeDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.lastTry = DateUtils.convertLocalDateToServer(copy.lastTry);
                    copy.closeDate = DateUtils.convertLocalDateToServer(copy.closeDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
