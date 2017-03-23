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
		default: {},
		old: []
	};

	o.getAll = function(override) {
		if(!auth.isLoggedIn()){
			return this.getDef();
		}
		else{
			if (this.default.numbers == undefined || override){
				return $http({
					headers: {Authorization: 'Bearer '+auth.getToken()},
					method:"GET",
					url:'/numbers/user'
				}).then(function successCallback(data){
					console.log('data returned from get request');
					console.log(data);
					if (data.data.VID=="-1"){
						//This doesn't work, it just overwrites because it is undefined
						console.log('We are in error mode because we failed to get user data');
						angular.copy(data.data, o.default);
						console.log('patched in test data')
						console.log('this is o right now:')
						console.log(o);
					}
					else{
						console.log('We found user data and here is');
						console.log(data);
						angular.copy(data.data[0], o.default);
						data.data.splice(0,1)
						angular.copy(data.data,o.old);
						console.log('We added things to old, lenght: ' + o.old.length);
					}
				}, function errorCallback(response){
					
					console.log(response);

				});
			}
			else{
				console.log('we just skipped the get command');
			}
		}
	};
	o.getDef= function(){
		return $http.get('data/test.json')
		.success(function(data){
			console.log(data);
			angular.copy(data, o.default);
					//console.log('Test Data loaded');
				})
	}

	o.create = function(updatedNumbers) {
		console.log('ok we are now at numbers.create:');
		console.log(this.default);
		console.log('This is what we are posting');
		console.log(updatedNumbers);
		$http.post('/numbers', updatedNumbers, {
			headers: {Authorization: 'Bearer '+auth.getToken()}
		}).then(function successCallback(data){
			//console.log('Returned from POST:numbers:');
			//console.log(data);
		}, function errorCallback(response){
			console.log('This is the error recieved from POST:numbers');
			console.log(response);
		});
	};

	o.save = function(){
		$http.post('/numbers', this.default, {
			headers: {Authorization: 'Bearer '+auth.getToken()}
		}).then(function successCallback(data){
			console.log('SAVEER: Returned from POST:numbers:');
			console.log(data);
		}, function errorCallback(response){
			console.log('This is the error recieved from POST:numbers');
			console.log(response);
		});
	};

	o.update = function(section, index){
		var temp = {};
		angular.copy(this.default, temp);
		this.old.unshift(temp);
		console.log('Updated old, New length of old: ' + o.old.length);
		console.log('This is the numbers.default we are editing in numbers.update');
		console.log(this.default);
		//We are updated out numbers.default
		this.default.numbers[index]= section;
		this.default.VID = ((this.default.VID-0)+1);
		//angular.copy(data.data[0], o.default);
		if(auth.isLoggedIn()){
			this.create(this.default);
		}
		else{
			//it would be great to add a login script here 
		}
	};

	o.revert = function(index){
		angular.copy(this.old[index],this.default);
	};

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
	'$rootScope',
	'$state',
	'numbers',
	'auth',
	function($scope, $rootScope, $state, numbers, auth){
		$scope.isLoggedIn = auth.isLoggedIn;
		$scope.currentUser = auth.currentUser;
		$scope.logOut = auth.logOut;
		$scope.version = -1;
		$scope.revert = function(dirction){
			$scope.version = $scope.version +dirction;
			if($scope.version > -1){
				numbers.revert($scope.version);
				console.log('we reverted to ' +$scope.version);
				$state.reload();
			}
			else{
				$scope.version = -1;
			}
		}
		$scope.save = function(){
			$scope.version=-1;
			console.log('starting save');
			numbers.save();
			numbers.getAll(true);
		}
	}]);


/*
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
*/

app.controller('SumController', ['$scope','$rootScope',
	'numbers',
	function ($scope, $rootScope, numbers) {
		//$scope.test = 'Hello world';
		//load the numbers
		$scope.numbers = numbers.default.numbers;
		//looking at things
		console.log('what we see on the SumController');
		console.log(numbers.default);
		console.log($scope.numbers);
		//load my helpers
		$scope.helpers = MyHelpers.helpers;
		//console.log($scope.numbers);
		//testing
		//console.log($scope.helpers.loadById($scope.numbers,"grants"));


		//creating mre listener
		$scope.$on('updateValues', function(event, args){
			$scope.update();
		});

		//Defining empty vars
		//graph for the in side
		$scope.labelsIN = [];
		$scope.dataIN = [];
		//graph for the out side
		$scope.labelsOUT = [];
		$scope.dataOUT = []
		

		//loading the sumary screen
		//new way of loading the data:
		$scope.dataIn =[];
		$scope.dataOut=[];
		$scope.totalInY = 0;
		$scope.totalInM = 0;
		$scope.totalInU = 0;
		$scope.totalOutY = 0;
		$scope.totalOutU = 0;
		$scope.totalOutM = 0;
		$scope.update = function(){
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
							totalY: obj.totalYear,
							totalU: obj.totalUnit,
							totalM: obj.totalMonth
						})
						//update the graph
						$scope.labelsIN.push(obj.id.charAt(0).toUpperCase() + obj.id.slice(1))
						$scope.dataIN.push(obj.totalYear);
						//totals
						$scope.totalInY = $scope.totalInY + (obj.totalYear- 0);
						$scope.totalInU = $scope.totalInU + (obj.totalUnit- 0);
						$scope.totalInM = $scope.totalInM + (obj.totalMonth- 0);
					}
					if (obj.type =="out") {
						$scope.dataOut.push({
							title: obj.id.charAt(0).toUpperCase() + obj.id.slice(1),
							link: obj.id,
							totalY: obj.totalYear,
							totalU: obj.totalUnit,
							totalM: obj.totalMonth
						})
						//graph
						$scope.labelsOUT.push(obj.id.charAt(0).toUpperCase() + obj.id.slice(1))
						$scope.dataOUT.push(obj.totalYear);
						//totals
						$scope.totalOutY = $scope.totalOutY + (obj.totalYear- 0);
						$scope.totalOutU = $scope.totalOutU + (obj.totalUnit- 0);
						$scope.totalOutM = $scope.totalOutM + (obj.totalMonth- 0);
					}
				}
			}
		//our error path
		if($scope.numbers == undefined || $scope.numbers.length==0 ){
			$scope.error = "We are sorry it seems we couldn't get a response from the server to load your data";
		}
		else{
		$scope.update();
		}
		
		

	}]);

app.controller('EditController', [
	'$scope',
	'$state',
	'numbers',
	'sourceId',
	function ($scope, $state, numbers, sourceId) {
		//load my helpers
		$scope.helpers = MyHelpers.helpers;
		$scope.numbers = numbers.default.numbers;
		$scope.title = sourceId.charAt(0).toUpperCase() + sourceId.slice(1);
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
			var totalYear = 0;
			var totalMonth = 0;
			var totalUnit= 0;
			var newFields =[];
			for (var cat in $scope.subcat){
				obj = $scope.subcat[cat];
				totalYear = totalYear + (obj.year-0);
				totalMonth = totalMonth + (obj.month-0);
				totalUnit = totalUnit + (obj.unit-0);
				newFields.push(obj);
			}
			var temp = {
				"id" : sourceId,
				"totalYear":totalYear,
				"totalUnit":totalUnit,
				"totalMonth":totalMonth,
				"type":$scope.numbers[id].type,
				"fields":newFields

			};
			console.log('This is what we are sending from EditController:')
			console.log(temp);
			numbers.update(temp,id);

		}
		$scope.addOne = function(){
			$scope.subcat.push({
				"year":120,
				"month":10,
				"unit":40,
				"title":"",
				"order":($scope.subcat.length-0)
			})
			//console.log('Testing addOne in edit');
			//console.log($scope.subcat[$scope.subcat.length-1]);
		}
		$scope.removeOne = function(place){
			console.log('We called removeOne on possition: '+place);
			for (var cat in $scope.subcat){
				var obj = $scope.subcat[cat];
				if (obj.order > place){
					obj.order = obj.order -1;
				}
			}
			$scope.subcat.splice(place,1);
		}
		$scope.back = function(){
			$state.go('home');
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
					return numbers.getAll(false);
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
					return numbers.getAll(false);
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