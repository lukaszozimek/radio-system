(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('SchTemplate', SchTemplate);

    SchTemplate.$inject = ['$resource'];

    function SchTemplate ($resource) {
        var resourceUrl =  'api/sch-templates/:id';

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
