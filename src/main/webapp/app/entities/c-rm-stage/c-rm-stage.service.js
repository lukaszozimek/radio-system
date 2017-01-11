(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CRMStage', CRMStage);

    CRMStage.$inject = ['$resource'];

    function CRMStage ($resource) {
        var resourceUrl =  'api/c-rm-stages/:id';

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
