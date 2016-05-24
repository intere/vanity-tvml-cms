(function() {
    'use strict';

    angular
        .module('vanityTvmlApp')
        .controller('VideoDialogController', VideoDialogController);

    VideoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Video'];

    function VideoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Video) {
        var vm = this;
        vm.video = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('vanityTvmlApp:videoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.video.id !== null) {
                Video.update(vm.video, onSaveSuccess, onSaveError);
            } else {
                Video.save(vm.video, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
