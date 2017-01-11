(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LIBAlbum', LIBAlbum);

    LIBAlbum.$inject = ['$resource', 'DateUtils'];

    function LIBAlbum ($resource, DateUtils) {
        var resourceUrl =  'api/l-ib-albums/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.releaseDate = DateUtils.convertLocalDateFromServer(data.releaseDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.releaseDate = DateUtils.convertLocalDateToServer(copy.releaseDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.releaseDate = DateUtils.convertLocalDateToServer(copy.releaseDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
