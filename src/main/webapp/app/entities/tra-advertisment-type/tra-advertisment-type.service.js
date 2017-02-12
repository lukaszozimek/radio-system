(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('TraAdvertismentType', TraAdvertismentType);

    TraAdvertismentType.$inject = ['$resource'];

    function TraAdvertismentType ($resource) {
        var resourceUrl =  'api/tra-advertisment-types/:id';

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
