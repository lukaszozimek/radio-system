(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CorNotification', CorNotification);

    CorNotification.$inject = ['$resource'];

    function CorNotification ($resource) {
        var resourceUrl =  'api/cor-notifications/:id';

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
