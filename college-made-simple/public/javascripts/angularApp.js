var MyHelpers = MyHelpers ||{};

MyHelpers.helpers = {
	loadById: function(numbers, string){
		for (var i = numbers.length - 1; i >= 0; i--) {
			if(numbers[i].id==string){
				return i;
			}

		}
		return 0;
	}
}


var app = angular.module('materializeApp', ['ui.materialize','ui.router','chart.js'])

app.factory('numbers', ['$http', function($http){
	var o = {
		default: [],
		current: [],
		old: []
	};

	o.getAll = function() {
		if (true){
			$http.get('data/test.json')
			.success(function(data){
				angular.copy(data.numbers, o.default);
				//console.log('Test Data loaded');
			})
		}
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

	o.update = function(section, id){
		this.default[id]= section;
		return $http.post('/numbers', this.default).success(function(data){
			console.log(data);
		})
	}

	return o;
}]);

app.controller('SideBar', [
	'$scope',
	function($scope){
		$scope.test1 = "Hello navbar";
		$scope.onSignIn=function(response){
			console.log(response);
			var currentUser = gapi.auth2.getBasicProfile();
			console.log(currentUser);
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
                                	console.log(response);
                                });
                            };
                        });
                    }]
                };
            });


app.controller('SumController', ['$scope', 
	'numbers',
	function ($scope, numbers) {
		$scope.test = 'Hello world';
		//load the numbers
		$scope.numbers = numbers.default;
		//load my helpers
		$scope.helpers = MyHelpers.helpers;

		//testing
		//console.log($scope.helpers.loadById($scope.numbers,"grants"));
		
		//labels for the graphs
		$scope.lables = ["scholarships", "grants", "job", "parents", "other", "utilities", "other", "food", "rent", "tuition"];
		//graph for the in side
		$scope.labelsIN = ["Scholarships", "Grants", "Job", "Parents", "Other"];
		$scope.dataIN = [$scope.numbers[$scope.helpers.loadById($scope.numbers,"scholarships")].total,$scope.numbers[$scope.helpers.loadById($scope.numbers,"grants")].total,$scope.numbers[$scope.helpers.loadById($scope.numbers,"job")].total,$scope.numbers[$scope.helpers.loadById($scope.numbers,"parents")].total,$scope.numbers[$scope.helpers.loadById($scope.numbers,"otherIn")].total];
		//graph for the out side
		$scope.labelsOUT = ["Utilities", "Personal", "Food", "Rent", "Tuition", "Supplies", "Transportation"];
		$scope.dataOUT = [$scope.numbers[$scope.helpers.loadById($scope.numbers,"utilities")].total,$scope.numbers[$scope.helpers.loadById($scope.numbers,"personal")].total,$scope.numbers[$scope.helpers.loadById($scope.numbers,"food")].total,$scope.numbers[$scope.helpers.loadById($scope.numbers,"rent")].total,$scope.numbers[$scope.helpers.loadById($scope.numbers,"tuition")].total,$scope.numbers[$scope.helpers.loadById($scope.numbers,"tuition")].supplies,$scope.numbers[$scope.helpers.loadById($scope.numbers,"tuition")].transportation];
		
		//loading the sumary screen
		$scope.tuition = $scope.numbers[$scope.helpers.loadById($scope.numbers,"tuition")].total;
		$scope.personal =$scope.numbers[$scope.helpers.loadById($scope.numbers,"personal")].total;
		$scope.supplies = $scope.numbers[$scope.helpers.loadById($scope.numbers,"supplies")].total;
		$scope.rent = $scope.numbers[$scope.helpers.loadById($scope.numbers,"rent")].total;
		$scope.transportation = $scope.numbers[$scope.helpers.loadById($scope.numbers,"transportation")].total; 
		$scope.personal = $scope.numbers[$scope.helpers.loadById($scope.numbers,"personal")].total;
		$scope.totalOut = ($scope.tuition-0) + ($scope.personal-0) + ($scope.supplies-0) + ($scope.rent-0)
		+ ($scope.transportation-0) + ($scope.personal-0);

		console.log($scope.numbers);
		
	}]);

app.controller('EditController', [
	'$scope',
	'numbers',
	'sourceId',
	function ($scope, numbers, sourceId) {
		//load my helpers
		$scope.helpers = MyHelpers.helpers;
		$scope.numbers = numbers.default;
		//testing
		//console.log($scope.numbers);
		//console.log(sourceId);
		var id = $scope.helpers.loadById($scope.numbers,sourceId);
		//console.log(id);
		if(id==0){
			console.log('error help!');
		}
		else {
			$scope.value1=$scope.numbers[id].value1;
			$scope.value2=$scope.numbers[id].value2;
			$scope.value3=$scope.numbers[id].value3;
			$scope.value4=$scope.numbers[id].value4;
			$scope.value5=$scope.numbers[id].value5;
			$scope.title1= $scope.numbers[id].title1;
			$scope.title2= $scope.numbers[id].title2;
			$scope.title3= $scope.numbers[id].title3;
			$scope.title4= $scope.numbers[id].title4;
			$scope.title5= $scope.numbers[id].title5;
		}
		$scope.update = function(){
			//console.log("it works");
			var total = ($scope.value1-0) + ($scope.value2-0) +($scope.value3-0) +($scope.value4-0) +($scope.value5-0);
			var tot = total.toString();
			var temp = {
				"id" : sourceId,
				"total":tot,

				"value1": $scope.value1,
				"title1": $scope.title1,
				"value2": $scope.value2,
				"title2": $scope.title2,
				"value3": $scope.value3,
				"title3": $scope.title3,
				"value4": $scope.value4,
				"title4": $scope.title4,
				"value5": $scope.value5,
				"title5": $scope.title5

			};
			numbers.update(temp,sourceId)
			console.log(temp);
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
			url: '/edit/{editId}',
			templateUrl: '/edit.html',
			controller: 'EditController',
			resolve: {
				sourceId: ['$stateParams', function($stateParams) {
					return $stateParams.editId;
				}],
				postPromise: ['numbers', function(numbers){
					return numbers.getAll();
				}]
			}
		});

		$urlRouterProvider.otherwise('home');
	}]);