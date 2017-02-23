(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CfgMarkerConfiguration', CfgMarkerConfiguration);

    CfgMarkerConfiguration.$inject = ['$resource'];

    function CfgMarkerConfiguration ($resource) {
        var resourceUrl =  'api/cfg-marker-configurations/:id';

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
