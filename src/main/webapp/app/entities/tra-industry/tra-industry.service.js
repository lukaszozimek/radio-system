(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('TraIndustry', TraIndustry);

    TraIndustry.$inject = ['$resource'];

    function TraIndustry ($resource) {
        var resourceUrl =  'api/tra-industries/:id';

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
