
var tournamentUtilsFactory = function() {
	
	return {
		getWordWrapped : function (original, atLineWidth) {
			var originalData = original.data ? original.data : original;
			var a = originalData.split(/\x20+/);
			var data = '';
			var lineWidth = 0;
			for(var i=0; i<a.length; i++) {
				if(lineWidth > atLineWidth) {
					data += ('\n' + a[i]);
					lineWidth = a[i].length;
				}
				else {
					data += (data.length==0 ? a[i] : (' '+a[i]));
					lineWidth += a[i].length;
				}
			}
			return data;
		},
		NumberArray : function(count) {
			this.options = [];
			for(var i=1; i<=count; i++) {
				this.options[i-1] = i;
			}
		},
		displayAjaxError : function (error, scope) {
			if(error.ajaxErrorStatus) {
				scope.ajaxErrorStatus = error.ajaxErrorStatus;
			}
			if(error.ajaxErrorData) {
				scope.ajaxErrorData = error.ajaxErrorData;
			}
		}
	};
};
		
