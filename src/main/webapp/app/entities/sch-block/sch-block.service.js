(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('SchBlock', SchBlock);

    SchBlock.$inject = ['$resource', 'DateUtils'];

    function SchBlock ($resource, DateUtils) {
        var resourceUrl =  'api/sch-blocks/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.scheduledStartTime = DateUtils.convertDateTimeFromServer(data.scheduledStartTime);
                        data.scheduledEndTime = DateUtils.convertDateTimeFromServer(data.scheduledEndTime);
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
