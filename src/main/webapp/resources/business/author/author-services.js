lmsApp.factory("authorService", function($http){
    var allAuthorsData = {};
    var authorByPkData = {};

    return{

        getAllAuthors: function(){
            return $http({
                url: "http://localhost:8081/admin/getAllAuthors",
                method: "GET",
            }).success(function(data){
                allAuthorsData = data;
            }).then(function(){
                return allAuthorsData;
            })
        }
    }

})
