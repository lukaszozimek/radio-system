(function () {
    'use strict';

    angular
        .module('protoneApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/user/register', {}, {});
    }
})();
