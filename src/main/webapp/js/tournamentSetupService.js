
var tournamentSetupFactory = function($http, $q, utilsService) {
	
	// Set the default url
	var _url = '/tournament/ws/rest/tournament/demo/parms';
	
	return {
		// Override the default url with a specific url
		setUrl : function(url) {
			_url = url;
		},
		getModelOptions : function() {
			var data = {};
			var deferred = $q.defer();
			// Make an ajax call to get the model - in this case, drop-down selections.
			$http.get(_url)
				.success(function(response){
					data = response;
					data.gamesPerMatch = [1,3,5,7];
					data.playerCount = new utilsService.NumberArray(255);
					deferred.resolve(data);
				}).error(function(response){
					data = response;
					data.ajaxErrorStatus = 'status = ' + response.status;
					data.ajaxErrorData = utilsService.getWordWrapped(response.data, 100);
					deferred.reject(data);
				});
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