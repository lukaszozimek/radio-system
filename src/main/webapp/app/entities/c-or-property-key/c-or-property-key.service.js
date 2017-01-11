(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CORPropertyKey', CORPropertyKey);

    CORPropertyKey.$inject = ['$resource'];

    function CORPropertyKey ($resource) {
        var resourceUrl =  'api/c-or-property-keys/:id';

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
