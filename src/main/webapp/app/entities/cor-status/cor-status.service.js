(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CorStatus', CorStatus);

    CorStatus.$inject = ['$resource'];

    function CorStatus ($resource) {
        var resourceUrl =  'api/cor-statuses/:id';

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
