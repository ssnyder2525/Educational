var myApp = angular.module('myApp',[]);

myApp.controller('CommentsController', ['$scope', '$http', function($scope, $http) {
    $scope.comments = [];
    $scope.name = "Mickey";
    $scope.pass = "mickeypass";
    $scope.comment = "Todo item";
    $scope.loggedIn = false;
    $scope.newUser = false;
    
    $scope.login = function() {
        var myobj = {"Name"     : $scope.name, 
                     "Password" : $scope.pass };
        $http({
            url: "http://52.34.7.124/credentials",
            method: "GET",
            params: myobj
        })
        .then(function(res) {
            console.log("Logged in");
            $scope.comments = [];
            $http.get("http://52.34.7.124/comment?Name="+$scope.name).success(function(data) {
                data.forEach(function(item) {
                    $scope.addComment(item.Comment);
                });
            });
            $scope.loggedIn = true;
            
        }).catch(function(err) {
            console.log(err);
            console.log("Not Logged in");
            $scope.loggedIn = false;
        });
    };
    
    $scope.logout = function() {
	$scope.comments = [];
        $scope.loggedIn = false;
        $scope.newUser = false;
    }
    
    $scope.register = function() {
        $scope.comments = [];
        $scope.newUser = true;
        $scope.postComment("Add todo items and get started on a very planned life!");
        $scope.loggedIn = true;
    }
    
    $scope.checkAuthThenComment = function() {
        
        if ($scope.loggedIn || $scope.newUser) {
            $scope.postComment($scope.comment);
        } else {
            $scope.postMessage = "Sorry, you are not logged in or have not registered";
            console.log($scope.postMessage);
        }
    };
    
    $scope.postComment = function(comment) {
        var myobj = {"Name"     : $scope.name, 
                     "Password" : $scope.pass, 
                     "Comment"  : $scope.comment };
        console.log(myobj);
console.log($scope.comment);
        var jobj = JSON.stringify(myobj);
        $http.post("http://52.34.7.124/comment", jobj, {headers: { 'Content-Type' : "application/json; charset=utf-8"}})
        .then(function(res) {
          console.log(res);
          var data = JSON.parse(res.config.data);
          console.log(data);
          $scope.addComment(data.Comment);
        })
        .catch(function(data, status) {
            console.log(data);
            console.log(status);
        });
    }
    
    $scope.addComment = function(addComment) {
    $scope.comments.push({comment:addComment});
    };

    $scope.removeComment = function(index) {
        $scope.comments.splice(index, 1);
    };
}]);