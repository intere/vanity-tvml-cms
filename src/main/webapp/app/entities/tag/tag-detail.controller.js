(function() {
    'use strict';

    angular
        .module('vanityTvmlApp')
        .controller('TagDetailController', TagDetailController);

    TagDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Tag'];

    function TagDetailController($scope, $rootScope, $stateParams, entity, Tag) {
        var vm = this;
        vm.tag = entity;
        
        var unsubscribe = $rootScope.$on('vanityTvmlApp:tagUpdate', function(event, result) {
            vm.tag = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
