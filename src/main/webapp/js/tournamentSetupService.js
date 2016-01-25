
var tournamentSetupFactory = function($http, $q, utilsService) {
	
	// Set the default url
	var _url = '/tournament/ws/rest/tournament/demo/parms';
	// Set a the json returned through ajax here so it is part of the factory singleton (survives between controller refreshes)
	var _data = '';
	
	return {
		// Override the default url with a specific url
		setUrl : function(url) {
			_url = url;
		},
		getModelOptions : function() {
			var deferred = $q.defer();
			if(_data) {
				// _data is cached, so use the cached value to prevent another ajax call.
				deferred.resolve(_data);
			}
			else {
				// Make an ajax call to get the model - in this case, drop-down selections.
				$http.get(_url)
					.success(function(response){
						_data = response;
						_data.gamesPerMatch = [1,3,5,7];
						_data.playerCount = new utilsService.NumberArray(255);
						deferred.resolve(_data);
					}).error(function(response){
						_data = response;
						_data.ajaxErrorStatus = 'status = ' + response.status;
						_data.ajaxErrorData = utilsService.getWordWrapped(response.data, 100);
						deferred.reject(_data);
					});
			}
			return deferred.promise;
		},
		getDefaultSelections : {
			// NOTE: You can bind anonymous field objects to their select elements as long as 
			// a "track by" clause is included in their ng-options attribute and each object  
			// contains the one field specified in that corresponding "track by" clause.
			format : {name:'SINGLE_ELIMINATION'},
			gameType: {name:'SINGLES'},
			matchingMethod: {value:'HIGHEST_WITH_LOWEST_RANK'},
			playerCount: 19, 
			players: [],
			gamesPerMatch: 3
		}
	};

};