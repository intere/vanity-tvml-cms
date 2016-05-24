(function() {
    'use strict';

    angular
        .module('vanityTvmlApp')
        .controller('TagDialogController', TagDialogController);

    TagDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tag'];

    function TagDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tag) {
        var vm = this;
        vm.tag = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('vanityTvmlApp:tagUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.tag.id !== null) {
                Tag.update(vm.tag, onSaveSuccess, onSaveError);
            } else {
                Tag.save(vm.tag, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
