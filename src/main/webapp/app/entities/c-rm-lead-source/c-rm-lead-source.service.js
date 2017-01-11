(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CRMLeadSource', CRMLeadSource);

    CRMLeadSource.$inject = ['$resource'];

    function CRMLeadSource ($resource) {
        var resourceUrl =  'api/c-rm-lead-sources/:id';

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
