var app = angular.module('magicNumbers', []);


app.factory('numbers' ['$http', '$window', function($http, $window){
	var o = {
		title: "Welcome",
		jobs: 0,
		/*
		scholarships: 0,
		other: 0,
		grants: 0,
		parents: 0,
		job: 0,
		utilities: 0,
		other: 0,
		books: 0,
		food: 0,
		rent: 0,
		*/
	};

	o.getAll = function() {
		return $http.get('/numbers').success(function(data){
			angular.copy(data, o)
		});
	};

	o.create = function(post) {
		return $http.post('/numbers', numbers).success(function(data){
			//o.numbers.push(data);
		});
	};

	return o;
}]);




app.controller('MainCtrl', [
	'$scope',
	'numbers',
	function($scope){
		//$scope.numbers = posts.posts;

		$scope.test = 'Hello world!';
		

		$scope.addPost = function(){
			if(!$scope.title || $scope.ttle === '') {return;}
			posts.create({
				title: $scope.title,
				link: $scope.link,
				upvotes: 0,
			});
		}
		

	}]);