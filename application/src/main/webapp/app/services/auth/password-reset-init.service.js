(function() {
    'use strict';

    angular
        .module('protoneApp')
        .factory('PasswordResetInit', PasswordResetInit);

    PasswordResetInit.$inject = ['$resource'];

    function PasswordResetInit($resource) {
        var service = $resource('api/user/reset_password/init', {}, {});

        return service;
    }
})();
