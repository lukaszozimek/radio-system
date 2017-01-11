(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LIBCloudObject', LIBCloudObject);

    LIBCloudObject.$inject = ['$resource', 'DateUtils'];

    function LIBCloudObject ($resource, DateUtils) {
        var resourceUrl =  'api/l-ib-cloud-objects/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createDate = DateUtils.convertDateTimeFromServer(data.createDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
