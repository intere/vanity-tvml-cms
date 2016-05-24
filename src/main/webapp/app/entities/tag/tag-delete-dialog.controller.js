(function() {
    'use strict';

    angular
        .module('vanityTvmlApp')
        .controller('TagDeleteController',TagDeleteController);

    TagDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tag'];

    function TagDeleteController($uibModalInstance, entity, Tag) {
        var vm = this;
        vm.tag = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Tag.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
