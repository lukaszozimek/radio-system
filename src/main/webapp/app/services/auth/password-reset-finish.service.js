(function() {
    'use strict';

    angular
        .module('protoneApp')
        .factory('PasswordResetFinish', PasswordResetFinish);

    PasswordResetFinish.$inject = ['$resource'];

    function PasswordResetFinish($resource) {
        var service = $resource('api/user/reset_password/finish', {}, {});

        return service;
    }
})();
