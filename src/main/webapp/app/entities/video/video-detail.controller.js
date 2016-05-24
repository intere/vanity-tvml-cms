(function() {
    'use strict';

    angular
        .module('vanityTvmlApp')
        .controller('VideoDetailController', VideoDetailController);

    VideoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Video'];

    function VideoDetailController($scope, $rootScope, $stateParams, entity, Video) {
        var vm = this;
        vm.video = entity;
        
        var unsubscribe = $rootScope.$on('vanityTvmlApp:videoUpdate', function(event, result) {
            vm.video = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
