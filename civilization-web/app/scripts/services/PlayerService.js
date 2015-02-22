'use strict';
(function (civApp) {

  civApp.factory('PlayerService', function ($http, $q, $log, growl, currentUser, BASE_URL, GameService) {
    var baseUrl = BASE_URL + "/player/";

    /**
     * Returns next element in the object
     */
    function nextElement(obj) {
      if (obj) {
        var keys = Object.keys(obj);
        if (keys && keys.length > 0) {
          return obj[keys[0]];
        }
      }
      return obj;
    }

    var revealItem = function (gameId, item) {
      var url = baseUrl + gameId + "/item/reveal";

      var itemDTO = {
        "name": nextElement(item).name,
        "ownerId": nextElement(item).ownerId,
        "sheetName": nextElement(item).sheetName,
        "pbfId": gameId
      };

      $log.info("Before calling put, json is ", angular.toJson(itemDTO));
      var configuration = {
        headers: {
          "Content-Type": "application/json"
        }
      };

      return $http.put(url, itemDTO, configuration)
        .success(function (response) {
          growl.success("Item revealed");
          return response;
        }).success(function (response) {
          GameService.fetchGameByIdFromServer(gameId);
          return response;
        })
        .error(function () {
          growl.error("Item could not be revealed");
        });
    };

    var drawItem = function (gameId, sheetName) {
      var url = baseUrl + gameId + "/draw/" + sheetName;
      return $http.post(url)
        .success(function (response) {
          growl.success("Item successfully drawn");
          return response;
        }).success(function (response) {
          GameService.fetchGameByIdFromServer(gameId);
          return response;
        })
        .error(function (data, status) {
          if (status == 410) {
            growl.error("There are no more " + sheetName + " to draw!");
          } else {
            growl.error("Item could not be drawn");
          }
        });
    };

    var discardItem = function (gameId, item) {
      var url = baseUrl + gameId + "/item/discard";

      var itemDTO = {
        "name": nextElement(item).name,
        "ownerId": nextElement(item).ownerId,
        "sheetName": nextElement(item).sheetName,
        "pbfId": gameId
      };

      $log.info("Before calling post, json is ", angular.toJson(itemDTO));

      var configuration = {
        headers: {
          "Content-Type": "application/json"
        }
      };

      $http.post(url, itemDTO, configuration)
        .success(function (response) {
          growl.success("Item discarded");
          return response;
        }).success(function (response) {
          GameService.fetchGameByIdFromServer(gameId);
          return response;
        }).error(function (data) {
          growl.error("Item could not be discarded");
          return data;
        });
    };

    var endTurn = function (gameId) {
      var url = baseUrl + gameId + "/endturn";
      return $http.put(url)
        .success(function (response) {
          growl.success("Turn ended");
          return response;
        }).success(function (response) {
          GameService.fetchGameByIdFromServer(gameId);
          return response;
        })
        .error(function (data) {
          growl.error("Could not end turn");
          return data;
        });
    };

    var getChosenTechs = function (gameId) {
      var url = baseUrl + gameId + "/tech/" + currentUser.profile.id;
      return $http.get(url)
        .then(function (response) {
          return response.data;
        }, function (data) {
          $log.error(data);
          growl.error("Could not get chosen techs");
          $q.reject();
        });
    };

    var selectTech = function (gameId, selectedTech) {
      var url = baseUrl + gameId + "/tech/choose";

      return $http({
        url: url,
        method: "PUT",
        params: {name: selectedTech.tech.name}
      })
      .success(function (response) {
        growl.success("Tech chosen successfully");
        return response;
      }).success(function (response) {
        GameService.fetchGameByIdFromServer(gameId);
        return response;
      }).error(function (data) {
          growl.error("Could not choose tech");
          return data;
        });
    };

    return {
      revealItem: revealItem,
      drawItem: drawItem,
      discardItem: discardItem,
      endTurn: endTurn,
      selectTech: selectTech,
      getChosenTechs: getChosenTechs
    };

  });

}(angular.module("civApp")));
