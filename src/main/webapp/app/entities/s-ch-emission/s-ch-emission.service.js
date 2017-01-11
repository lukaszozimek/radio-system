(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('SCHEmission', SCHEmission);

    SCHEmission.$inject = ['$resource', 'DateUtils'];

    function SCHEmission ($resource, DateUtils) {
        var resourceUrl =  'api/s-ch-emissions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startTime = DateUtils.convertDateTimeFromServer(data.startTime);
                        data.endTime = DateUtils.convertDateTimeFromServer(data.endTime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
