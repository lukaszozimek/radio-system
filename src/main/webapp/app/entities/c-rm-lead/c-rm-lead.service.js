(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CRMLead', CRMLead);

    CRMLead.$inject = ['$resource'];

    function CRMLead ($resource) {
        var resourceUrl =  'api/c-rm-leads/:id';

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
