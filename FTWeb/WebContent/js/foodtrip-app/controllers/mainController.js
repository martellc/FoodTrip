app.controller("mainController", function($window,$scope, $http, $location){

    $scope.tripID = '366-350';
    $scope.endCompany = '';
    $scope.results = [];
    $scope.product = '';
    $scope.producer = '';
    $scope.path = '';

    $scope.loading = false;
    $scope.showtrip = false;

    $scope.mainTabIndex = 0;

    $scope.changeMainTab = function(index) {
	 $scope.loading = true;
	 $scope.mainTabIndex = index;
 	 $scope.loading = false;
    }

    $scope.tabIndex=0;
    $scope.showTripMap = function() {
	 $scope.tabIndex = 2;
    }
    

    $scope.showProductTrip = function() {

        $scope.tabIndex = 1;
    }

    $scope.showProductDetail = function() {

        $scope.tabIndex = 0;
    }
    
    $scope.getTrip = function(path) {

	    $scope.code = null;
	    $scope.response = null;

      	    $scope.loading = true;	   
	    var postObject = new Object();
	    postObject.id = $scope.tripID;
	    $http.post("http://localhost:8080/FTServiceRestFul/trip", postObject).success(function (data) {
                         console.log(data);
			 $scope.product = data.product;
			 $scope.producer = data.producer;
			 angular.forEach(data.steps, function(step, index){
				console.log(step.company.name);
		                $scope.results.push(step);
			$scope.path = data.path;             		
			$scope.loading = false;
 		        $scope.showtrip=true;
		         });
	       });

    };
});
