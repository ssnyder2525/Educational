var app = angular.module("mainApp", []);

app.controller('MainController', ['$scope',function($scope){

	$scope.list = [];


	$scope.todoAdd = function() {

		$scope.list.push({outList:$scope.sentence});

        $scope.sentence = "";
    };
	
}]);