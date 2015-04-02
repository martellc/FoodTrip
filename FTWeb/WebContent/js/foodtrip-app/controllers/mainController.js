app.controller("mainController", function($window,$scope, $http, $location, peopleGraph){

	$scope.tripID = '615-634';
	$scope.endCompany = '';
	$scope.tripView;
	$scope.results = [];
	$scope.product = '';
	$scope.producer = '';
	$scope.paths = '';
	$scope.cy;
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
		$("#divMap").hide();
		$("#divMap").show();
	}


	$scope.showProductTrip = function() {
		$scope.tabIndex = 1;
		peopleGraph( $scope.tripView.flatFoodGraph ).then(function( peopleCy ){
			$scope.cy = peopleCy;

			// use this variable to hide ui until cy loaded if you want
			$scope.cyLoaded = true;
		});
		
		$("#trip-table").hide();
		$("#trip-table").show();
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

//			angular.forEach(data.steps, function(step, index){
//			console.log(step.company.name);
//			$scope.results.push(step);         		
//			});
			$scope.tripView = data;
			$scope.paths = $scope.tripView.paths;
			
			$scope.loading = false;
			$scope.showtrip=true;
		});

	};
});
