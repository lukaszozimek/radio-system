(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('TRAIndustry', TRAIndustry);

    TRAIndustry.$inject = ['$resource'];

    function TRAIndustry ($resource) {
        var resourceUrl =  'api/t-ra-industries/:id';

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
