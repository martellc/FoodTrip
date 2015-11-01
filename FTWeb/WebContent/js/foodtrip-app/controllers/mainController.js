app.controller("mainController", function($window,$scope, $rootScope, $http, $location, peopleGraph){

	$scope.typeTooltip="<b>Produttore</b>: Utilizza questa tipologia se sei un produttore diretto od una categoria<br><br><b>Intermediario</b>: Aziende di trasporto, intermediari generici, aziende di stoccaggio, pulitura o grossisti rientrano in questa categoria<br><br><b>Distrubutore finale</b>: Nel caso la tua attivita' principale sia la distrubuzione diretta al cliente, scegli questa tipologia"
	$scope.endCompany = '';
	$scope.cy;
	
	$scope.mainTabIndex = -1;

	$scope.newCompany = null;
	$scope.userInfo = null;
	
	$scope.birthDate = null;
	$scope.foundingDate = null;
	
	
	$scope.changeLocation = function(address) {
		$location.path(address);
	};
	
	$scope.load = function() {
		$rootScope.loading = true;
	};
	

	$scope.changeMainTab = function(index) {
		$scope.mainTabIndex = index;
		
		//clean trip variables
		$rootScope.showtrip=false;
		$rootScope.tripID = null;
	    
		$rootScope.product = null;
		$rootScope.producer = null;

		$rootScope.tripView = null;
		$rootScope.paths = null;
		
		if(index==5) {
			//generate guid
			$rootScope.guid = guid();
		}
	
	};

	$scope.tabIndex=0;
	$scope.showTripMap = function() {
		$scope.tabIndex = 2;
		$("#divMap").hide();
		$("#divMap").show();
	};


	$scope.showProductTrip = function() {
		$scope.tabIndex = 1;
		peopleGraph( $scope.tripView.flatFoodGraph ).then(function( peopleCy ){
			$scope.cy = peopleCy;

			// use this variable to hide ui until cy loaded if you want
			$scope.cyLoaded = true;
		});
		
		$("#trip-table").hide();
		$("#trip-table").show();
	};

	$scope.showProductDetail = function() {
		$scope.tabIndex = 0;
	};

	$scope.getTrip = function(path,tripID) {

		$scope.code = null;
		$scope.response = null;

		$scope.tripID = tripID;
		
		$rootScope.tripID = tripID;
		
		$rootScope.loading = true;	   
		var postObject = new Object();
		postObject.id = tripID;
		
		$http.post("http://localhost:8080/FTServiceRestFul/trip", postObject).success(function (data) {
			console.log(data);
			$location.path( "foodtrip" );
		    
			$rootScope.product = data.product;
			$rootScope.producer = data.producer;

			$rootScope.tripView = data;
			$rootScope.paths = $scope.tripView.paths;
			$scope.showProductDetail();
			  
			$rootScope.loading = false;
			$rootScope.showtrip=true;
		}).
		error(function(data, status, headers, config) {
			$rootScope.loading = false;
		});

	};
	
	$scope.showRegistrationPhase2 = function(info,foundingDate) {
		$rootScope.loading = true;
		
		$rootScope.companyInfo = info;
		if(foundingDate != null) {
			$rootScope.companyInfo.foundingDate = foundingDate.getTime();
		}

		$location.path( "register2" );
		$rootScope.loading = false;
		
	}
	
	$scope.addCompany = function(info, birthDate) {

		$scope.code = null;
		$scope.response = null;
		
		$rootScope.companyInfo.creator = info;
		if(birthDate != null) {
			$rootScope.companyInfo.creator.birthDate = birthDate.getTime();	
		}
		
		$rootScope.companyInfo.companyKey = guid();
		var postObject = new Object();
		postObject = $rootScope.companyInfo;
		
		$rootScope.loading = true;
		$http.post("http://localhost:8080/FTServiceRestFul/company", postObject).success(function (data) {
			console.log(data);
			$rootScope.loading = false;
			$location.path( "register3" );
			//generate guid
			$rootScope.guid = $rootScope.companyInfo.companyKey;
			$scope.mainTabIndex = 5;

		}).
		error(function(data, status, headers, config) {
			$rootScope.loading = false;
			//$rootScope.guid = guid();
			$location.path( "error" );
		});

	};
});
