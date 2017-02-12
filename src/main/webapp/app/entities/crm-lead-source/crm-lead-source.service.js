(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CrmLeadSource', CrmLeadSource);

    CrmLeadSource.$inject = ['$resource'];

    function CrmLeadSource ($resource) {
        var resourceUrl =  'api/crm-lead-sources/:id';

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
