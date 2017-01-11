(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CRMTaskStatus', CRMTaskStatus);

    CRMTaskStatus.$inject = ['$resource'];

    function CRMTaskStatus ($resource) {
        var resourceUrl =  'api/c-rm-task-statuses/:id';

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
