(function() {
    'use strict';

    angular
        .module('protoneApp')
        .factory('Password', Password);

    Password.$inject = ['$resource'];

    function Password($resource) {
        var service = $resource('api/user/account/change_password', {}, {});

        return service;
    }
})();
