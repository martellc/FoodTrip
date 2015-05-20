var app = angular.module('foodtrip',['ngRoute','ui.bootstrap','ngMap','pascalprecht.translate']);


app.config(['$routeProvider',
            function($routeProvider) {
	$routeProvider.
	when('/howitworks', {
		templateUrl: 'howitworks.html',
		controller: 'mainController'
	}).
	when('/home', {
		templateUrl: 'home.html',
		controller: 'mainController'
	}).
	when('/faq', {
		templateUrl: 'faq.html',
		controller: 'mainController'
	}).
	when('/register', {
		templateUrl: 'register.html',
		controller: 'mainController'
	}).
	when('/register2', {
		templateUrl: 'register2.html',
		controller: 'mainController'
	}).
	when('/register3', {
		templateUrl: 'register3.html',
		controller: 'mainController'
	}).
	when('/foodtrip', {
		templateUrl: 'trip.html',
		controller: 'mainController'
	}).
	when('/learnmore', {
		templateUrl: 'learnmore.html',
		controller: 'mainController'
	}).
	otherwise({
		redirectTo: '/home'
	});
}]);

app.config(['$translateProvider', function ($translateProvider) {
	$translateProvider.translations('en', {
		'TITLE': 'Foodtrip',
		'TRIP_TEXT_LABEL': 'Insert the "FoodTrip ID"',
		'TRIP_TEXT_LABEL_SMALL':'Insert a "foodtrip ID"',
		'GET_TRIP_BUTTON': 'Get trip',
		'HOME': 'Home',
		'TRIP': 'Food Trip',
		'WHY': 'Why',
		'DOWNLOAD': 'Download',
		'FOODTRIP': 'Food trip',
		'PRODUCT_DETAIL': 'Product detail',
		'MAP':'Trip Map',
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
		'COMPANY_DETAIL':'Company detail',
		'REGISTER':'Sign in',
		'MAIN_MESSAGE':'Know what you eat!',
		'INTRO_MESSAGE':'Foodtrip is the first producer to consumer tracking system that gives you everithing you need to know about a product.',
		'LEARN_MORE':'Learn more >',
		'HOW_WORKS':'How it works',
		'HOW_WORKS_MESSAGE':'Transparency and traceability, this is what really matter in Foodtrip. Using the best of mobile tecnology,a new tracking algorithm and togheter with companies of our communities we are able to give to consumers fantastic informations about foods they buy.',
		'REGISTER_YOUR_COMPANY':'Register your company',
		'REGISTER_YOUR_COMPANY_MESSAGE':'If you are a producer, an intermediary, a supplier or if your business is you food you can consider joining our community. We have a strong registration form (in order to prevent identity theft) and after that you can start using foodtrip.',
		'TRY_IT':'Try it',
		'TRY_IT_MESSAGE':'If you are interested or just curious give our service a chance. Insert for example this Foodtrip id (634-214) and click "Get trip"',
		'VIEW_DETAILS':'View details',
		'REGISTER_YOURSELF':'Something about you',
		'INSTALL_MESSAGE':'Now you can install foodtrip on your smartphone. In order to complete the installation you should insert when requested the key below',
		'REGISTRATION_COMPLETE':'Registration successfully completed',
		'COMPANY_NAME':'Company name',
		'COMPANY_VAT':'Vat number',
		'FOUNDING_DATE':'Founding date (yyyy-MM-dd)',
		'COMPANY_TYPE':'Company type',
		'ADDRESS':'Address',
		'DESCRIPTION':'Description',
		'COMPANY_EMAIL':'Company email',
		'FACEBOOK_ID':'Facebook id',
		'GPLUS_ID':'Gplus id',
		'COMPANY_CERT':'Certifications (optional)',
		'NEXT':'Next',
		'CIVICO':'Number',
		'STREETNAME':'Street name',
		'ZIP':'Zip code',
		'CITY':'City',
		'STATE':'State',
		'REGISTER':'Sign in',
		'NAME':'Name',
		'SURNAME':'Surname',
		'BIRTH_DATE':'Birth date',
		'BIRTH_PLACE':'Birth place',
		'TAX_CODE':'Tax code',
		'EMAIL':'Email',
		'SEX':'Sex',
		'Farm':'Producer',
		'Intermediary':'Intermediary',
		'Consumer distrubition':'Distrubution',
		'STEPS':'Steps',
		'FOODTRIP_NOT_FOUND':'Opssss il Foodtrip che hai inserito non esiste. Questo e\' un esempio di come avrebbe risposto il servizio con un codice corretto (676-690).'
	});

	$translateProvider.translations('de', {
		'TITLE': 'Foodtrip'
	});

	$translateProvider.translations('it', {
		'TITLE': 'Foodtrip',
		'TRIP_TEXT_LABEL': 'Inserisci il "Foodtrip ID" che trovi sul prodotto', 
		'TRIP_TEXT_LABEL_SMALL':'Inserisci un "Foodtrip ID"',
		'GET_TRIP_BUTTON': 'Mostra il viaggio',
		'TRIP': 'Food Trip',
		'HOME': 'Home',
		'WHY': 'Scopo',
		'DOWNLOAD': 'Download',
		'FOODTRIP': 'Food trip',
		'PRODUCT_DETAIL': 'Dettagli del prodotto',
		'MAP':'Mappa del viaggio',
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
		'COMPANY_DETAIL':'Dettagli compagnia',
		'REGISTER':'Registrati',
		'REGISTRATION_COMPLETE':'Il processo di registrazione si e\' concluso correttamente',
		'MAIN_MESSAGE':'Conosci cio\' che mangi!',
		'INTRO_MESSAGE':'Foodtrip e\' il primo software di tracciabilita\' dal produttore al consumatore che fornisce tutti i dettagli su quello che stai mangiando.',
		'LEARN_MORE':'Scopri i dettagli >',
		'HOW_WORKS':'Come funziona',
		'HOW_WORKS_MESSAGE':'Trasparenza e tracciabilita\' sono le parole chiave in Foodtrip. Utilizzando il meglio della tecnologia "Mobile" e grazie alle aziende che sono registrate al servizio siamo in grado di fornire una vera e propria Carta d\'identita\' dei prodotti alimetari.',
		'REGISTER_YOUR_COMPANY':'Registra la tua compagnia',
		'REGISTER_YOUR_COMPANY_MESSAGE':'Se sei un produttore, un intermediario, un distrubutore finale di prodotti alimentari o se il tuo business ruota intorno al cibo considera la possibilita\' di entrare nella nostra comunita\'. Registrati, e differenzia il tuo business con Foodtrid',
		'TRY_IT':'Provalo',
		'TRY_IT_MESSAGE':'Se sei interessato o semplicimente curioso, prova il nostro servizio. Inserisci per esempio (676-690) qui sotto e clicca "Mostra il viaggio"',
		'VIEW_DETAILS':'Dettagli',
		'REGISTER_YOURSELF':'Dicci qualcosa su di te',
		'INSTALL_MESSAGE':'Installa Foodtrip sul tuo telefonino Android ed inserisci la chiave privata al momento della registrazione.',
		'REGISTRATION_COMPLETE':'Bevenuto in Foodtrip',
		'COMPANY_NAME':'Nome della compagnia',
		'COMPANY_VAT':'Partita iva',
		'FOUNDING_DATE':'Data di fondazione (yyyy-MM-dd)',
		'COMPANY_TYPE':'Categoria',
		'ADDRESS':'Indirizzo',
		'DESCRIPTION':'Descrizione',
		'COMPANY_EMAIL':'Email',
		'FACEBOOK_ID':'Facebook id',
		'GPLUS_ID':'Googleplus id',
		'COMPANY_CERT':'Certificazioni opzionali (Separate da virgole)',
		'NEXT':'Avanti',
		'CIVICO':'Numero',
		'STREETNAME':'Via/Strada/Piazza',
		'ZIP':'CAP',
		'CITY':'Citta\'',
		'STATE':'Stato',
		'REGISTER':'Registrati',
		'NAME':'Nome',
		'SURNAME':'Cognome',
		'BIRTH_DATE':'Data di nascita',
		'BIRTH_PLACE':'Luogo di nascita',
		'TAX_CODE':'Codice fiscale',
		'EMAIL':'Email',
		'SEX':'Sesso',
		'Farm':'Produttore',
		'Intermediary':'Intermediario',
		'Consumer distrubition':'Distributore',
		'STEPS':'Passo per passo',
		'FOODTRIP_NOT_FOUND':'Opssss il Foodtrip che hai inserito non esiste. Questo e\' un esempio di come avrebbe risposto il servizio con un codice corretto (676-690).'
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
					'line-color': '#8EBAFF',
					'target-arrow-color': '#8EBAFF'
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