(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LibAudioObject', LibAudioObject);

    LibAudioObject.$inject = ['$resource'];

    function LibAudioObject ($resource) {
        var resourceUrl =  'api/lib-audio-objects/:id';

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
