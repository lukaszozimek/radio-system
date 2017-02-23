(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CrmLead', CrmLead);

    CrmLead.$inject = ['$resource'];

    function CrmLead ($resource) {
        var resourceUrl =  'api/crm-leads/:id';

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
