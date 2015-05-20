app.directive('checkCF', function() {
    return {
 
      // limit usage to argument only
      restrict: 'A',
 
      // require NgModelController, i.e. require a controller of ngModel directive
      require: 'ngModel',
 
      // create linking function and pass in our NgModelController as a 4th argument
      link: function(scope, element, attr, ctrl) {
    	  function controllaCF(ngModelValue) {
    		  	
    		  	var pattern = /^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$/;
    		  	
    		  	 if (ngModelValue.search(pattern) == -1)
    			 {
    		  		ctrl.$setValidity('checkCF', false);
    			 }
    	      
    	        // we need to return our ngModelValue, to be displayed to the user(value of the input)
    	        return ngModelValue;
    	        
    	        
    	  }
    	  ctrl.$parsers.push(controllaCF);
      }
    };
});