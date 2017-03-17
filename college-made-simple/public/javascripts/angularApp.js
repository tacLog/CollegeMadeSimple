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

app.factory('numbers', ['$http','auth', function($http, auth){
	var o = {
		default: [],
		old: []
	};

	o.getAll = function(id) {
		if(!auth.isLoggedIn()){
			return this.getDef();
		}
		else{
			return $http({
				headers: {Authorization: 'Bearer '+auth.getToken()},
				method:"GET",
				url:'/numbers/'+auth.currentUser()
			}).then(function successCallback(data){
				console.log(data);
				if (data.data.VID=="0"){
					angular.copy(data.data, o.default);
					//console.log('this should have worked')
				}
				else{
					angular.copy(data.data[0].numbers, o.default);
				}
			}, function errorCallback(response){
				
				console.log(response);

			});
		}
	};
	o.getDef= function(){
		return $http.get('data/test.json')
		.success(function(data){
			angular.copy(data.numbers, o.default);
					//console.log('Test Data loaded');
				})
	}

	o.create = function(numbers) {
		console.log('ok we are now posting:');
		console.log(numbers);
		$http.post('/numbers', numbers, {
			headers: {Authorization: 'Bearer '+auth.getToken()}
		}).then(function successCallback(data){
			console.log('Returned from post numbers: ');
			console.log(data);
			angular.copy(o.default, o.old);
			angular.copy(data, o.default);
		}, function errorCallback(response){
			console.log(response);
		});
	};

	o.update = function(section, id){
		this.default.numbers[id]= section;
		if(auth.isLoggedIn()){
			this.create(this.default);
		}
	}

	return o;
}]);

app.factory('auth', ['$http', '$window', function($http, $window){
	var auth = {};

	auth.saveToken = function (token){
		//console.log(token);
		$window.localStorage['college-made-simple-token'] = token;
	};

	auth.getToken = function (){
		return $window.localStorage['college-made-simple-token'];
	}

	auth.isLoggedIn = function(){
		var token = auth.getToken();

		if(token){
			var payload = JSON.parse($window.atob(token.split('.')[1]));

			return payload.exp > Date.now() / 1000;
		} else {
			return false;
		}
	};

	auth.currentUser = function(){
		if(auth.isLoggedIn()){
			var token = auth.getToken();
			var payload = JSON.parse($window.atob(token.split('.')[1]));

			return payload.username;
		}
	};

	auth.register = function(user){
		return $http.post('/register', user).success(function(data){
			auth.saveToken(data.token);
		});
	};

	auth.logIn = function(user){
		return $http.post('/login', user).success(function(data){
			auth.saveToken(data.token);
		});
	};

	auth.logOut = function(){
		$window.localStorage.removeItem('college-made-simple-token');
	};
	return auth;
}])

app.controller('AuthCtrl', [
	'$scope',
	'$state',
	'auth',
	function($scope, $state, auth){
		$scope.user = {};

		$scope.register = function(){
			auth.register($scope.user).error(function(error){
				console.log(error);
				$scope.error = error;
			}).then(function(){
				$state.go('home');
			});
		};

		$scope.logIn = function(){
			auth.logIn($scope.user).error(function(error){
				$scope.error = error;
			}).then(function(){
				$state.go('home');
			});
		};
	}])

app.controller('NavCtrl', [
	'$scope',
	'auth',
	function($scope, auth){
		$scope.isLoggedIn = auth.isLoggedIn;
		$scope.currentUser = auth.currentUser;
		$scope.logOut = auth.logOut;
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
		$scope.numbers = numbers.default.numbers;
		//load my helpers
		$scope.helpers = MyHelpers.helpers;
		//console.log($scope.numbers);
		//testing
		//console.log($scope.helpers.loadById($scope.numbers,"grants"));
		
		//labels for the graphs
		$scope.lables = ["scholarships", "grants", "job", "parents", "other", "utilities", "other", "food", "rent", "tuition"];
		//graph for the in side
		$scope.labelsIN = ["Scholarships", "Grants", "Job", "Parents", "Other"];
		$scope.dataIN = [$scope.numbers[$scope.helpers.loadById($scope.numbers,"scholarships")].total,$scope.numbers[$scope.helpers.loadById($scope.numbers,"grants")].total,$scope.numbers[$scope.helpers.loadById($scope.numbers,"job")].total,$scope.numbers[$scope.helpers.loadById($scope.numbers,"parents")].total,$scope.numbers[$scope.helpers.loadById($scope.numbers,"otherIn")].total];
		//graph for the out side
		$scope.labelsOUT = ["Utilities", "Personal", "Food", "Rent", "Tuition", "Supplies", "Transportation"];
		$scope.dataOUT = [
		$scope.numbers[$scope.helpers.loadById($scope.numbers,"utilities")].total,
		$scope.numbers[$scope.helpers.loadById($scope.numbers,"personal")].total,
		$scope.numbers[$scope.helpers.loadById($scope.numbers,"food")].total,
		$scope.numbers[$scope.helpers.loadById($scope.numbers,"rent")].total,
		$scope.numbers[$scope.helpers.loadById($scope.numbers,"tuition")].total,
		$scope.numbers[$scope.helpers.loadById($scope.numbers,"tuition")].total,
		$scope.numbers[$scope.helpers.loadById($scope.numbers,"tuition")].total];
		
		//loading the sumary screen
		//new way of loading the data:
		$scope.dataIn =[];
		$scope.dataOut=[];
		$scope.totalIn = 0;
		$scope.totalOut = 0;
		for (var cat in $scope.numbers){
			var obj = $scope.numbers[cat];
			if (obj.id == "error"){
				continue;
			}
			//console.log(obj.type);
			if (obj.type =="in") {
				$scope.dataIn.push({
					title: obj.id.charAt(0).toUpperCase() + obj.id.slice(1),
					link: obj.id,
					total: obj.total,
				})
				$scope.totalIn = $scope.totalIn + (obj.total- 0);
			}
			if (obj.type =="out") {
				$scope.dataOut.push({
					title: obj.id.charAt(0).toUpperCase() + obj.id.slice(1),
					link: obj.id,
					total: obj.total,
				})
				$scope.totalOut = $scope.totalOut + (obj.total -0) ;
			}
		}

		//console.log('scope.in:');
		//console.log($scope.in);
		/* Old way of loading shit
		//in data
		$scope.scholarships = $scope.numbers[$scope.helpers.loadById($scope.numbers,"scholarships")].total;
		$scope.grants =$scope.numbers[$scope.helpers.loadById($scope.numbers,"grants")].total;
		$scope.job = $scope.numbers[$scope.helpers.loadById($scope.numbers,"job")].total;
		$scope.parents = $scope.numbers[$scope.helpers.loadById($scope.numbers,"parents")].total;
		$scope.other = $scope.numbers[$scope.helpers.loadById($scope.numbers,"other")].total; 
		$scope.totalIn = 
		($scope.grants-0) 
		+ ($scope.scholarships-0) 
		+ ($scope.job-0)
		+ ($scope.parents-0) 
		+ ($scope.other-0);
		//out data
		$scope.tuition = $scope.numbers[$scope.helpers.loadById($scope.numbers,"tuition")].total;
		$scope.personal =$scope.numbers[$scope.helpers.loadById($scope.numbers,"personal")].total;
		$scope.supplies = $scope.numbers[$scope.helpers.loadById($scope.numbers,"supplies")].total;
		$scope.rent = $scope.numbers[$scope.helpers.loadById($scope.numbers,"rent")].total;
		$scope.transportation = $scope.numbers[$scope.helpers.loadById($scope.numbers,"transportation")].total; 
		$scope.food = $scope.numbers[$scope.helpers.loadById($scope.numbers,"food")].total; 
		$scope.personal = $scope.numbers[$scope.helpers.loadById($scope.numbers,"personal")].total;
		$scope.totalOut = ($scope.tuition-0) + ($scope.supplies-0) + ($scope.rent-0)
		+ ($scope.transportation-0) + ($scope.personal-0);
		*/
		//console.log($scope.numbers);
		
	}]);

app.controller('EditController', [
	'$scope',
	'numbers',
	'sourceId',
	function ($scope, numbers, sourceId) {
		//load my helpers
		$scope.helpers = MyHelpers.helpers;
		$scope.numbers = numbers.default.numbers;
		$scope.title = sourceId;
		//testing
		//console.log($scope.numbers);
		//console.log(sourceId);
		var id = $scope.helpers.loadById($scope.numbers,sourceId);
		//console.log(id);
		//new way of updating these forms
		$scope.subcat =[];
		for (var cat in $scope.numbers[id].fields){
			obj = $scope.numbers[id].fields[cat];
			//console.log(obj);
			$scope.subcat.push(obj);
		}

		//functions that control the changing of the number automatically
		$scope.changeYear = function(post){
			var value = post.year;
			post.month = value/12;
			post.unit = value/3;
		}
		$scope.changeUnit = function(post){
			var value = post.unit;
			post.year = value*3;
			post.month = post.year/12;
		}
		$scope.changeMonth = function(post){
			var value = post.month;
			post.year = value*12;
			post.unit = post.year/3;
		}
		//the saving function
		$scope.update = function(){
			//console.log("it works");
			var total = 0;
			var newFields =[];
			for (var cat in $scope.subcat){
				obj = $scope.subcat[cat];
				total = total + (obj.year-0);
				newFields.push(obj);
			}
			total = total.toString();
			var temp = {
				"id" : sourceId,
				"total":total,
				"type":$scope.numbers[id].type,
				"fields":newFields

			};
			numbers.update(temp,id);
			//console.log(temp);
		}
		$scope.addOne = function(){
			$scope.subcat.push({
				"year":"120",
            "month":"10",
            "unit":"40",
            "title":"",
            "order":$scope.subcat.length.toString
			})
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
		$stateProvider
		.state('login', {
			url: '/login',
			templateUrl: '/login.html',
			controller: 'AuthCtrl',
			onEnter: ['$state', 'auth', function($state, auth){
				if(auth.isLoggedIn()){
					$state.go('home');
				}
			}]
		});
		$stateProvider
		.state('register', {
			url: '/register',
			templateUrl: '/register.html',
			controller: 'AuthCtrl',
			onEnter: ['$state', 'auth', function($state, auth){
				if(auth.isLoggedIn()){
					$state.go('home');
				}
			}]
		});

		$urlRouterProvider.otherwise('home');
	}]);