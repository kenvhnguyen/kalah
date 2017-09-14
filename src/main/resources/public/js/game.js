var gameModule = angular.module('gameModule', []);

gameModule.controller('newGameController', ['$rootScope', '$scope', '$http', '$location',

    function (rootScope, scope, http, location) {

        rootScope.gameId = null;
        scope.newGameData = null;

        scope.newGameOptions = {
            availableGameTypes: [
                {name: 'COMPETITION'},
                {name: 'COMPUTER'}
            ],
            selectedBoardDimension: {name: 'COMPUTER'}
        };

        scope.createNewGame = function () {

            var data = scope.newGameData;
            var params = JSON.stringify(data);

            http.post("/game/create", params, {
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8'
                }
            }).success(function (data, status, headers, config) {
                rootScope.gameId = data.id;
                location.path('/game/' + rootScope.gameId);
            }).error(function (data, status, headers, config) {
                location.path('/player/panel');
            });
        }

    }
]);


gameModule.controller('gamesToJoinController', ['$scope', '$http', '$location',
    function (scope, http, location) {

        scope.gamesToJoin = [];

        http.get('/game/list').success(function (data) {
            scope.gamesToJoin = data;
        }).error(function (data, status, headers, config) {
            location.path('/player/panel');
        });


        scope.joinGame = function (id) {

            var params = {"id" : id}

            http.post('/game/join', params, {
                headers: {
                 'Content-Type': 'application/json; charset=UTF-8'
                }
            }).success(function (data) {
                location.path('/game/' + data.id);
            }).error(function (data, status, headers, config) {
                location.path('/player/panel');
            });
        }

    }]);


gameModule.controller('playerGamesController', ['$scope', '$http', '$location', '$routeParams',
    function (scope, http, location, routeParams) {

        scope.playerGames = [];

        http.get('/game/player/list').success(function (data) {
            scope.playerGames = data;
        }).error(function (data, status, headers, config) {
            location.path('/player/panel');
        });

        scope.loadGame = function (id) {
            console.log(id);
            location.path('/game/' + id);
        }

    }]);


gameModule.controller('gameController', ['$rootScope', '$routeParams', '$scope', '$http',
    function (rootScope, routeParams, scope, http) {
       var gameStatus;
       getInitialData()

        function getInitialData() {

           http.get('/game/' + routeParams.id).success(function (data) {
                scope.gameProperties = data;
                gameStatus = scope.gameProperties.gameStatus;
                getMoveHistory();
            }).error(function (data, status, headers, config) {
                scope.errorMessage = "Failed do load game properties";
            });
        }

        scope.rows = [
                    [
                        {'id': '10', 'content': '', 'class': 'ground'},
                        {'id': '11', 'content': 'Dots', 'class': 'house'},
                        {'id': '12', 'content': 'Dots', 'class': 'house'},
                        {'id': '13', 'content': 'Dots', 'class': 'house'},
                        {'id': '14', 'content': 'Dots', 'class': 'house'},
                        {'id': '15', 'content': 'Dots', 'class': 'house'},
                        {'id': '16', 'content': 'Dots', 'class': 'house'},
                        {'id': '17', 'content': '', 'class': 'store'}
                    ],[
                        {'id': '20', 'content': '', 'class': 'store'},
                        {'id': '21', 'content': 'Dots', 'class': 'house'},
                        {'id': '22', 'content': 'Dots', 'class': 'house'},
                        {'id': '23', 'content': 'Dots', 'class': 'house'},
                        {'id': '24', 'content': 'Dots', 'class': 'house'},
                        {'id': '25', 'content': 'Dots', 'class': 'house'},
                        {'id': '26', 'content': 'Dots', 'class': 'house'},
                        {'id': '27', 'content': '', 'class': 'ground'}

                    ],
                ];

        scope.playerMove = function (column) {

        }

    }
]);



