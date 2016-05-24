(function() {
    'use strict';

    angular
        .module('vanityTvmlApp')
        .controller('VideoDeleteController',VideoDeleteController);

    VideoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Video'];

    function VideoDeleteController($uibModalInstance, entity, Video) {
        var vm = this;
        vm.video = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Video.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
