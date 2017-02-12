(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CrmTaskComment', CrmTaskComment);

    CrmTaskComment.$inject = ['$resource'];

    function CrmTaskComment ($resource) {
        var resourceUrl =  'api/crm-task-comments/:id';

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
