(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CORAssociation', CORAssociation);

    CORAssociation.$inject = ['$resource'];

    function CORAssociation ($resource) {
        var resourceUrl =  'api/c-or-associations/:id';

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
