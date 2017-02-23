var app = angular.module('materializeApp', ['ui.materialize','ui.router'])

app.factory('numbers', ['$http', function($http){
	var o = {
		old: {},
		current: {}
	};

	o.getAll = function() {
		//error here
		return 0;
		return $http.get('/numbers').success(function(data){
			angular.copy(data, o);
		});
	};

	o.create = function(numbers) {
		return $http.post('/numbers', numbers).success(function(data){
			angular.copy(o.current, o.old);
			angular.copy(data, o.current);
		});
	};

	return o;
}]);

app.controller('SumController', ["$scope", 
	'numbers',
	function ($scope, numbers) {
	$scope.test = 'Hello world';
	$scope.numbers = numbers.current;
}]);

app.controller('EditController', [
	'$scope',
	'numbers',
	function ($scope, numbers) {
	$scope.test = 'Hello world';
	$scope.edit = function(){
		if(!$scope.name || $scope.name === ''){return;}
		numbers.create({
			title: $scope.name,
			jobs: $scope.jobs,
		});
	}
}]);

app.config([
  '$stateProvider',
  '$urlRouterProvider',
  function($stateProvider, $urlRouterProvider) {

    $stateProvider
    .state('home', {
      url: '/home',
      templateUrl: '/home.html',
      controller: 'SumController',
      resolve: {
      	postPromise: ['numbers', function(numbers){
      		return numbers.getAll();
      	}]
      }
    });

    $stateProvider
    .state('edit', {
    	url: '/edit',
    	templateUrl: '/edit.html',
    	controller: 'EditController'
    });

    $urlRouterProvider.otherwise('home');
  }]);