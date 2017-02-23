(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CrmLeadStatus', CrmLeadStatus);

    CrmLeadStatus.$inject = ['$resource'];

    function CrmLeadStatus ($resource) {
        var resourceUrl =  'api/crm-lead-statuses/:id';

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
