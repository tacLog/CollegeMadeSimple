var myApp = angular.module('myApp', []);
myApp.controller('AppCtrl', ['$scope', '$http',
function($scope, $http){
   console.log("Hello from controller");

$http.get('/userlist').then(function(response){
   console.log("received the data "+response.data);
   $scope.userlist = response.data;
});

}]);
