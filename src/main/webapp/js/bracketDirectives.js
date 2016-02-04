var errorDiv = "<div class='error' style='clear:both;'>#</div>";

var bracketDirectiveFactory = function(crudService) {	
	return {
		directive1 : {	// Outputs static tournament display html - no data binding and no DOM manipulation.
			transclude: false,
			restrict: 'E',
			compile: function(element, attributes, transclude){
				var html = '';
				var firstRnd = "<div class='round' style='clear:both;'>round #<br> <matches></div>\r\n";
				var nextRnd = "<div class='round'>round #<br> <matches></div>\r\n";
				var matchDiv = "<div class='match'>match #<br> <sides></div>\r\n";
				var sideDiv = "<div class='side'>side #<br> <members></div>\r\n";
				var memberDiv = "<div class='member'>#</div>\r\n";
				var tournament = crudService.getData();
				if(tournament == undefined || tournament.brackets == undefined) {
					element.html(errorDiv.replace('#', 'No tournament data! Must request tournament from server.'));
					element.css('background-color', 'red');
					return;
				}
				var rounds = tournament.brackets[0].rounds;
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
			}
		},
//		directive2_bracket : {
//			transclude: false,
//			restrict: 'E',
//			link: function(scope, element, attrs) {
//				alert(scope);
//			},
//			scope: {
//				rounds: '=roundItems'
//			},
//			template: "<round ng-repeat='round in rounds'></div>\r\n"
//			compile: function(element, attributes, transclude){
//				element.html(errorDiv.replace('#', 'DIRECTIVE2 NOT IMPLEMENTED'));
//				element.css('backgroundColor', 'red');
//			}
//		},
		directive2_round : {
//			transclude: false,
			restrict: 'E',
//			link: function(scope, element, attrs) {
//				alert(scope);
//			},
			scope: {
				round: '=roundItem'
			},
			template: "<div class='round' style='clear:both;'>{{round.matches.length}}</div>\r\n",
//			compile: function(element, attributes, transclude){
//				element.html(errorDiv.replace('#', 'DIRECTIVE2_ROUND NOT IMPLEMENTED'));
//				element.css('backgroundColor', 'red');
//			}
		},
		directive3 : {
			transclude: false,
			restrict: 'E',
			compile: function(element, attributes, transclude){
				element.html(errorDiv.replace('#', 'DIRECTIVE3 NOT IMPLEMENTED'));
				element.css('backgroundColor', 'red');
			}
		}
	};
};