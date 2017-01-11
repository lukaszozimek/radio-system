(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CRMLeadStatus', CRMLeadStatus);

    CRMLeadStatus.$inject = ['$resource'];

    function CRMLeadStatus ($resource) {
        var resourceUrl =  'api/c-rm-lead-statuses/:id';

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
