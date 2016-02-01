
var tournamentCrudProvider = function() {
	
	// Set the default url
	var _url = '/tournament/ws/rest/tournament/demo';
	// Set a the json returned through ajax here so it is part of the provider singleton (survives between controller refreshes)
	var _data = {};

	// Override the default url with a specific url
	this.setUrl = function(url) {
		_url = url;
	};
	
	this.$get = function($http, $q, utilsService){
		return {
			// Define form submission function to aquire the tournament json.
			getTournamentData : function(tournamentParms) {
				var deferred = $q.defer();
				$http({
					method: 'POST',
					url: _url,
					data: tournamentParms
				}).then(
					function successCallback(response){
						_data = response.data.data;
						deferred.resolve(_data);
					}, 
					function errorCallback(response){
						_data = response;
						_data.ajaxErrorStatus = 'status = ' + response.status;
						_data.ajaxErrorData = utilsService.getWordWrapped(response.data, 100);
						deferred.reject(_data);
					}
				);
				return deferred.promise;
			},
			getData : function() {
				return _data;
			}
		};
		return null;
	};
};