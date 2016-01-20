
var tournamentCrudProvider = function() {
	
	// Set the default url
	var _url = '/tournament/ws/rest/tournament/demo';
	
	// Override the default url with a specific url
	this.setUrl = function(url) {
		_url = url;
	};
	
	this.$get = function($http, $q, utilsService){
		return {
			// Define form submission function to aquire the tournament json.
			getTournamentData : function(tournamentParms) {
				var data = {};
				var deferred = $q.defer();
				$http({
					method: 'POST',
					url: _url,
					data: tournamentParms
				}).then(
					function successCallback(response){
						data = response;
						deferred.resolve(data);
					}, 
					function errorCallback(response){
						data = response;
						data.ajaxErrorStatus = 'status = ' + response.status;
						data.ajaxErrorData = utilsService.getWordWrapped(response.data, 100);
						deferred.reject(data);
					}
				);
				return deferred.promise;
			}
		};
		return null;
	};
};