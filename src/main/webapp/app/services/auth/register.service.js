(function () {
    'use strict';

    angular
        .module('vanityTvmlApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
