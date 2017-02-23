(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LibCloudObject', LibCloudObject);

    LibCloudObject.$inject = ['$resource', 'DateUtils'];

    function LibCloudObject ($resource, DateUtils) {
        var resourceUrl =  'api/lib-cloud-objects/:id';

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
