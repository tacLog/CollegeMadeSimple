var app = angular.module('materializeApp', ['ui.materialize','ui.router'])

app.factory('numbers', ['$http', function($http){
	var o = {
		current: [],
		old: []
	};

	o.getAll = function() {
		return $http.get('/numbers').success(function(data){
			angular.copy(data, o.current);
			//console.log(data);
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

app.controller('SideBar', [
	'$scope',
	function($scope){
		$scope.test1 = "Hello navbar";
		$scope.onSignIn=function(response){
			console.log(response);
		}
	}]);

app.directive('googleSignInButton',function(){
	return {
		scope:{
			gClientId:'@',
			callback: '&onSignIn'
		},
		template: '<button class="btn blue lighten-2 waves-effect waves-light" ng-click="onSignInButtonClick()">Sign in</button>',
		controller: ['$scope','$attrs',function($scope, $attrs){
                        gapi.load('auth2', function() {//load in the auth2 api's, without it gapi.auth2 will be undefined
                        	gapi.auth2.init(
                        	{
                        		client_id: $attrs.gClientId
                        	}
                        	);
                            var GoogleAuth  = gapi.auth2.getAuthInstance();//get's a GoogleAuth instance with your client-id, needs to be called after gapi.auth2.init
                            $scope.onSignInButtonClick=function(){//add a function to the controller so ng-click can bind to it
                                GoogleAuth.signIn().then(function(response){//request to sign in
                                	$scope.callback({response:response});
                                });
                            };
                        });
                    }]
                };
            });


app.controller('SumController', ["$scope", 
	'numbers',
	function ($scope, numbers) {
		$scope.test = 'Hello world';
		$scope.numbers = numbers.current;
	//console.log($scope.numbers);
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