app.controller('ModalCtrl', function ($scope, $modal, $log) {
	
	$scope.open = function (title,msg) {

		  var modalScope = $scope.$new();
		  modalScope.modalTitle =title;
		  modalScope.msg =msg;
		  
		  var modalInstance = $modal.open({
	      templateUrl: 'modal.html',
	      controller: 'ModalInstanceCtrl',
	      scope:modalScope
	    });
	  };
	  
  });

app.controller('ModalInstanceCtrl', function ($scope, $modalInstance) {

	  $scope.ok = function () {
	    $modalInstance.close();
	  };

	  $scope.cancel = function () {
	    $modalInstance.dismiss('cancel');
	  };
});