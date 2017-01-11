(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CRMTask', CRMTask);

    CRMTask.$inject = ['$resource', 'DateUtils'];

    function CRMTask ($resource, DateUtils) {
        var resourceUrl =  'api/c-rm-tasks/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.activityDate = DateUtils.convertLocalDateFromServer(data.activityDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.activityDate = DateUtils.convertLocalDateToServer(copy.activityDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.activityDate = DateUtils.convertLocalDateToServer(copy.activityDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
