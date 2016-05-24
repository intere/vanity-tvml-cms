(function() {
    'use strict';

    angular
        .module('vanityTvmlApp')
        .controller('TagController', TagController);

    TagController.$inject = ['$scope', '$state', 'Tag', 'ParseLinks', 'AlertService'];

    function TagController ($scope, $state, Tag, ParseLinks, AlertService) {
        var vm = this;
        vm.tags = [];
        vm.predicate = 'id';
        vm.reverse = true;
        vm.page = 0;
        vm.loadAll = function() {
            Tag.query({
                page: vm.page,
                size: 20,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.tags.push(data[i]);
                }
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        };
        vm.reset = function() {
            vm.page = 0;
            vm.tags = [];
            vm.loadAll();
        };
        vm.loadPage = function(page) {
            vm.page = page;
            vm.loadAll();
        };

        vm.loadAll();

    }
})();
