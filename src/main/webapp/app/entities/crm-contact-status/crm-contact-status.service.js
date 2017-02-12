(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CrmContactStatus', CrmContactStatus);

    CrmContactStatus.$inject = ['$resource'];

    function CrmContactStatus ($resource) {
        var resourceUrl =  'api/crm-contact-statuses/:id';

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
