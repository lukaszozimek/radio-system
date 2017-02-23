(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('TraCampaingStatus', TraCampaingStatus);

    TraCampaingStatus.$inject = ['$resource'];

    function TraCampaingStatus ($resource) {
        var resourceUrl =  'api/tra-campaing-statuses/:id';

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
