(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CrmStage', CrmStage);

    CrmStage.$inject = ['$resource'];

    function CrmStage ($resource) {
        var resourceUrl =  'api/crm-stages/:id';

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
