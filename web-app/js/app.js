(function(){
	'use strict';

	var TaskApp = angular.module('TaskApp', ['ui.router', 'ngResource']);

	angular.module('TaskApp').factory('settings', ['$rootScope', function ($rootScope) {
		var settingsDev = {
				backendContext: '/grails-angular-example'
		};
		 var currentSettings = settingsDev;
		 $rootScope.settings = currentSettings;
		 return currentSettings;
	}]);
	
	TaskApp.factory('taskService', ['$resource', 'settings', function ($resource, settings) {
	    var specificResources = {
	        list: {
	            method: 'GET',
	            isArray: true,
	        },
	        save: {
	            method: 'POST',
	            isArray: false,
	        },
	        update: {
	            method: 'PUT',
	            isArray: false,
	        }
	        ,
	        show: {
	            method: 'GET',
	            isArray: false
	        },
	        remove: {
	            method: 'DELETE',
	            isArray: false
	        },
	        search: {
	            method: 'GET',
	            isArray: false
	        }
	    };
	    return $resource(settings.backendContext + "/api/:controller/:id",{
	    	id: '@id'
	    	, controller: '@controller'
	    }, specificResources);
	}]);

	TaskApp.controller('AppController',['$scope', '$rootScope',   function($scope ,$rootScope){

	}]);
	
	TaskApp.controller('TasksController',['$rootScope', '$scope', 'taskService','$state', '$stateParams'
	                                      ,  function($rootScope, $scope, taskService, $state, $stateParams){
		var vm = this;

        vm.save = save;
        vm.update = update;

        init();

        function save(form) {
            createOrUpdate(form, 'save');
        }

        function update(form) {
            createOrUpdate(form, 'update');
        }

        function createOrUpdate(form, method) {
        	var params = {controller: 'tasks'};
            if (form.$valid) {
                if ($stateParams.id) {
                    params.id = vm.tasks.id;
                }
                if(method == 'save'){
                	taskService.save(params, vm.tasks).$promise.then(
                			function success(value, responseHeaders){
                				$state.go('tasks.list');
                	});
                }else if(method == 'update'){
                	var paramsUpdate = {controller: 'tasks', id: vm.tasks.id}
                	taskService.update(paramsUpdate, vm.tasks).$promise.then(
                			function success(value, responseHeaders){
                            	$state.go('tasks.list');
                			}
                	);
                }
            }
        }
        
        function loadTask() {
        	var params = {controller:'tasks', id: $stateParams.id};
        	 taskService.show(params).$promise.then(
                function (tasks) {
                    bindTask(tasks);
                });
        }

        function cleanTask() {
            vm.tasks = {id: null}
        }

        function bindTask(tasks) {
            vm.tasks = {
                id: tasks.id,
                name: tasks.name,
                description: tasks.description,
                priority: tasks.priority
            };
        }

        function init() {
        	if($stateParams.id != undefined && $stateParams.id != ""){
            	loadTask();
        	}else{
            	cleanTask();
        	}
        }
		
	}]);

	TaskApp.controller('TasksListController',['$rootScope', '$scope','$stateParams','taskService', function($rootScope, $scope, $stateParams, taskService){
		var vm = this;
		vm.listTask = listTask;
		vm.updateTaskList = updateTaskList;
		vm.deleteTask = deleteTask;
		var params = {controller: 'tasks'};
		
		init();

		 function listTask(params ,callback) {
	         taskService.list(params, callback);
		 }

		 function updateTaskList(taskListResult) {
			 vm.taskList = taskListResult;
		 }
		 
		 function deleteTask(index){
			 var taskId = vm.taskList[index];
			 var paramsDelete = {controller: 'tasks', id: taskId.id}
			 taskService.remove(paramsDelete).$promise.then(
					 function success(value, responseHeaders){
						 vm.listTask(params, vm.updateTaskList);
					 }
			 );
		 }
		 
		 function init() {
			 vm.listTask(params, vm.updateTaskList);
		 }
	}]);
	
	TaskApp.config(['$stateProvider','$urlRouterProvider' ,function($stateProvider, $urlRouterProvider) {
		$urlRouterProvider.otherwise("/tasks");
		$stateProvider
			.state('tasks',{
				abstract: true,
				template: '<ui-view/>'
			})
			.state('tasks.list', {
				url: '/tasks',
				templateUrl: 'views/TasksListView.html',
				controller: 'TasksListController',
				controllerAs: 'vm'
			})
			.state('tasks.create', {
				url: '/tasks/create/:id',
				templateUrl: 'views/TasksCreateView.html',
				controller: 'TasksController',
				controllerAs: 'vm'
			});
	}]);

})();