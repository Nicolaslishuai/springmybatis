/**
 *
 *APPÄ£¿é
 *
 **/

(function(){
	
	var myapp=angular.module("myapp",["ngResource","ui.router"]);
	myapp.constant("baseUrl","http://127.0.0.1:8080");
	myapp.run(function($rootScope){
		//TODO
		
	});
	
	
	myapp.config(['$stateProvider', '$urlRouterProvider',
	             function($stateProvider,$urlRouterProvider){
		
		         $urlRouterProvider.otherwise("/");
		         
		         $stateProvider.state("index",{
		        	url:"/",
		        	templateUrl:"./app/template/main.html",
		        	controller:"userCtrl"
		         });
		
	             }]);
	myapp.controller("userCtrl",function($scope,$resource){
		
		$scope.usersResource=$resource("./user/select.htm",{},{
			save:{method:"PUT",url:"./user/put.htm"}
		});
		
		$scope.listUsers=function(){
			$scope.userStore=$scope.usersResource.query({page:"1",pageSize:"8"});
		};
		
		$scope.putUser=function(user){
			new $scope.usersResource(user).$save().then(function(data){
				console.log(data);
			});
		};
		
		$scope.listUsers();
		
	});
	
}());