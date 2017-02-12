(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CrmTaskStatus', CrmTaskStatus);

    CrmTaskStatus.$inject = ['$resource'];

    function CrmTaskStatus ($resource) {
        var resourceUrl =  'api/crm-task-statuses/:id';

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
