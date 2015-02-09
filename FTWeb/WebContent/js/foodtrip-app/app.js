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
