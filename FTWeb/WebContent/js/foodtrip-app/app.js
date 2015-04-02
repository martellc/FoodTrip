var app = angular.module('foodtrip',['ngMap','pascalprecht.translate']);
app.config(['$translateProvider', function ($translateProvider) {
	$translateProvider.translations('en', {
		'TITLE': 'Foodtrip',
		'TRIP_TEXT_LABEL': 'Insert the "TripID" you find over the product',
		'GET_TRIP_BUTTON': 'Get trip',
		'HOME': 'Home',
		'WHY': 'Why',
		'DOWNLOAD': 'Download',
		'FOODTRIP': 'Food trip',
		'PRODUCT_DETAIL': 'Product detail',
		'MAP':'Map',
		'SAWING_DATE':'Sawed',
		'HARVEST_DATE':'Harvest',
		'PRODUCED_BY':'Produced by',
		'CERTIFICATIONS':'Certifications',
		'NO_CERTIFICATIONS':'No certifications found',
		'AGR_IPM':'Agr. Ipm',
		'AGR_BIODIN':'Biodinamyc Agr.',
		'AGR_SUSTAINABLE':'Sustainable Agr',
		'AGR_OGM' :'Ogm',
		'AGR_BIO' :'Bio',
		'AGR_TRAD': 'Tradional Agr.',
		'PRODUCT_ACQUIRED':'Acquired',
		'COMPANY_DETAIL':'Company detail'
	});

	$translateProvider.translations('de', {
		'TITLE': 'Foodtrip'
	});

	$translateProvider.translations('it', {
		'TITLE': 'Foodtrip',
		'TRIP_TEXT_LABEL': 'Inserisci il "TripID" che trovi sul prodotto', 
		'GET_TRIP_BUTTON': 'Mostra il viaggio',
		'HOME': 'Home',
		'WHY': 'Scopo',
		'DOWNLOAD': 'Download',
		'FOODTRIP': 'Food trip',
		'PRODUCT_DETAIL': 'Dettagli del prodotto',
		'MAP':'Mappa',
		'SAWING_DATE':'Seminato il',
		'HARVEST_DATE':'Raccolto il',
		'PRODUCED_BY':'Prodotto da',
		'CERTIFICATIONS':'Certificazioni',
		'NO_CERTIFICATIONS':'Nessuna certificazione',
		'COMPANY_DETAIL':'Dettagli azienda',
		'PRODUCT_ACQUIRED':'Prodotto acquisito',
		'AGR_IPM':'Agr. Ipm',
		'AGR_BIODIN':'Agr. Biodinamico',
		'AGR_SUSTAINABLE':'Agr. Sostenibile',
		'AGR_OGM' :'Ogm',
		'AGR_BIO' :'Agr. Biologico',
		'AGR_TRAD': 'Agr. tradizionale',
		'PRODUCT_ACQUIRED':'Acquisito il',
		'COMPANY_DETAIL':'Company detail'
	});

	$translateProvider.registerAvailableLanguageKeys(['en', 'de','it'], {
		'en_US': 'en',
		'en_UK': 'en',
		'de_DE': 'de',
		'de_CH': 'de',
		'it_IT': 'it',
		'IT_it': 'it'
	})
	$translateProvider.determinePreferredLanguage();
}]);


app.factory('peopleGraph', [ '$q', function( $q ){
	var cy;
	var root_id;
	var peopleGraph = function(step){
		var deferred = $q.defer();

		// put people model in cy.js
		var eles = [];
		for( var i = 0; i < step.length; i++ ){
			var color = '#337AB7'
			if(i == 0) {
				color = '#5CB85C';
			}
			if (step[i].childrenArray.length === 0) {
				color = 'red';
			}
			eles.push({
				group: 'nodes',
				data: {
					id: 'id_'+ step[i].id,
					name: step[i].company.name,
					label:step[i].company.htmlString,
					faveColor: color
				}
			});

			if( step[i].parentID != null) {
				eles.push({
					group: 'edges',
					data: {
						id: 'id_'+ step[i].parentID + step[i].id,
						weight: 3,
						source: 'id_' + step[i].parentID,
						target:'id_' + step[i].id
					}
				});
			} else {
				root_id = 'id_' + step[i].id;
			}
		}

		$(function(){ // on dom ready

			cy = cytoscape({
				container: $('#cy')[0],

				style: cytoscape.stylesheet()
				.selector('node')
				.css({
					'content': 'data(name)',
					'width':'40',
					'height':'40',
					'background-color': 'data(faveColor)'
				})
				.selector('edge')
				.css({
					'target-arrow-shape': 'triangle',
					'width': 4,
					'line-color': '#ddd',
					'target-arrow-color': '#ddd'
				})
				.selector('.highlighted')
				.css({
					'background-color': '#61bffc',
					'line-color': '#61bffc',
					'target-arrow-color': '#61bffc',
					'transition-property': 'background-color, line-color, target-arrow-color',
					'transition-duration': '0.5s'
				}),
				
				layout: {
					  name: 'grid',
					  directed: true,
			          roots: root_id,
			          padding: 10
			        },

				elements: eles,

				ready: function(){
					deferred.resolve( this );

					cy.on('cxtdrag', 'node', function(e){
						var node = this;
						var dy = Math.abs( e.cyPosition.x - node.position().x );
						var weight = Math.round( dy*2 );

						node.data('weight', weight);

						fire('onWeightChange', [ node.id(), node.data('weight') ]);
					});
				}
			        
			    
			});
			
			for( var i = 0; i < eles.length; i++ ){
				var step = eles[i];
				if(step.group != 'nodes') {
						continue;
				}
				
				cy.$('#' + step.data.id).qtip({
					  content: step.data.label,
					  style: {
					    classes: 'qtip-bootstrap',
					    tip: {
					      width: 16,
					      height: 8
					    }
					  }
					});	
			}
			
		}); // on dom ready

		return deferred.promise;
	};

	peopleGraph.listeners = {};

	function fire(e, args){
		var listeners = peopleGraph.listeners[e];

		for( var i = 0; listeners && i < listeners.length; i++ ){
			var fn = listeners[i];

			fn.apply( fn, args );
		}
	}

	function listen(e, fn){
		var listeners = peopleGraph.listeners[e] = peopleGraph.listeners[e] || [];

		listeners.push(fn);
	}

	peopleGraph.setWeight = function(id, weight){
		cy.$('#' + id).data('weight', weight);
	};

	peopleGraph.onWeightChange = function(fn){
		listen('onWeightChange', fn);
	};

	return peopleGraph;


} ]);