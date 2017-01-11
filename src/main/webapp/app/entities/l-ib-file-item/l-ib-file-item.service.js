(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LIBFileItem', LIBFileItem);

    LIBFileItem.$inject = ['$resource'];

    function LIBFileItem ($resource) {
        var resourceUrl =  'api/l-ib-file-items/:id';

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
