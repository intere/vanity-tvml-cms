(function() {
    'use strict';

    angular
        .module('vanityTvmlApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('video', {
            parent: 'entity',
            url: '/video',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'vanityTvmlApp.video.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/video/videos.html',
                    controller: 'VideoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('video');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('video-detail', {
            parent: 'entity',
            url: '/video/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'vanityTvmlApp.video.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/video/video-detail.html',
                    controller: 'VideoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('video');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Video', function($stateParams, Video) {
                    return Video.get({id : $stateParams.id});
                }]
            }
        })
        .state('video.new', {
            parent: 'video',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/video/video-dialog.html',
                    controller: 'VideoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                videoURLString: null,
                                buttonTitle: null,
                                overviewTitle: null,
                                overviewSubtitle: null,
                                overviewDescription: null,
                                overviewImageURLString: null,
                                tagName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('video', null, { reload: true });
                }, function() {
                    $state.go('video');
                });
            }]
        })
        .state('video.edit', {
            parent: 'video',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/video/video-dialog.html',
                    controller: 'VideoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Video', function(Video) {
                            return Video.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('video', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('video.delete', {
            parent: 'video',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/video/video-delete-dialog.html',
                    controller: 'VideoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Video', function(Video) {
                            return Video.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('video', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
