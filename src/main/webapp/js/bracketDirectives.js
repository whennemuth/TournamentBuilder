var bracketDirectiveFactory = function(crudService) {
	
	var compile1 = function(element, attributes, transclude) {
		var html = '';
		var firstRnd = "<div class='round' style='clear:both;'>round #<br> <matches></div>\r\n";
		var nextRnd = "<div class='round'>round #<br> <matches></div>\r\n";
		var matchDiv = "<div class='match' style='clear:both;'>match #<br> <sides></div>\r\n";
		var sideDiv = "<div class='side' style='clear:both;'>side #<br> <members></div>\r\n";
		var memberDiv = "<div class='member' style='clear:both;'>#</div>\r\n";
		var restReply = crudService.getData();
		var rounds = restReply.data.data.brackets[0].rounds;
		var sideCount = 1;
		
		for(var i=1; i<=rounds.length; i++) {
			var round = rounds[i-1];
			var r = (i == 1 ? firstRnd : nextRnd).replace(/#/, i);
			var matches = '';
			for(var x=1; x<=round.matches.length; x++) {
				var match = round.matches[x-1];
				var m = matchDiv.replace(/#/, x);
				var sides = '';
				for(var y=0; y<match.sides.length; y++) {
					var side = match.sides[y];
					var s = sideDiv.replace(/#/, sideCount++);
					var members = '';
					for(var z=0; z<side.sidePlayers.length; z++) {
						var player = sidePlayer = side.sidePlayers[z].player;
						var mbr = memberDiv.replace(/#/, player.member.email);
						members += mbr;
					}
					s = s.replace('<members>', members);
					sides += s;
				}
				m = m.replace('<sides>', sides);
				matches += m;
			} 
			r = r.replace('<matches>', matches);
			html += r;
		}
		element.html(html);
	};
	
	var compile2 = function(element, attributes, transclude) {
		compile1(element, attributes, transclude);
		//return function postLink(scope, element, attributes, controller) {
		//	element.html('goodbye<br>')
		//};		
	};
	
	var compile3 = function(element, attributes, transclude) {
		compile1(element, attributes, transclude);
		//return function postLink(scope, element, attributes, controller) {
		//	element.html('goodbye<br>')
		//};		
	};
	
	return {
		directive1 : {
			transclude: false,
			restrict: 'E',
			compile: function(element, attributes, transclude){
				compile1(element, attributes, transclude);
			}
		},
		directive2 : {
			transclude: false,
			restrict: 'E',
			compile: function(element, attributes, transclude){
				compile2(element, attributes, transclude);
			}
		},
		directive3 : {
			transclude: false,
			restrict: 'E',
			compile: function(element, attributes, transclude){
				compile3(element, attributes, transclude);
			}
		}
	};
};