
lmsApp.controller("authorController", function($scope, $http, $window, $location,Pagination,authorService,toastr){

    $http.get("http://localhost:8081/admin/getAllAuthors").success(function (data) {
        $scope.authorsList = data;
        $scope.pagination = Pagination.getNew(10);
        $scope.pagination.numPages = Math.ceil($scope.authorsList.length / $scope.pagination.perPage);
    })
    $http.get("http://localhost:8081/admin/getAllBooks").success(function (data) {$scope.booksList=data;})
    $http.get("http://localhost:8081/admin/initAuthor").success(function(data){$scope.author = data;})
    $scope.saveAuthor= function() {
        $http.post("http://localhost:8081/admin/addAuthor", $scope.author).success(function (data) {
            authorService.getAllAuthors().then(function(data){
                $scope.authorsList = data;
                toastr.success('Author Added');
            })
        })
    }

    $scope.searchAuthor = function(){
        $http.get("http://localhost:8081/admin/searchauthors/"+$scope.searchString).success(function(data){
            $scope.authorsList = data;
            $scope.pagination = Pagination.getNew(10);
            $scope.pagination.numPages = Math.ceil($scope.authorsList.length / $scope.pagination.perPage);
        })
    }


    $scope.showEditModal = function(authorId){
        $http.get("http://localhost:8081/admin/editAuthor/"+authorId).success(function(data){
            $scope.author = data;
            $scope.editAuthorModal = true;
        })
    }

    $scope.closeEditModal = function(){
        $scope.editAuthorModal = false;
    }

    $scope.editAuthor = function(author){
        $scope.editAuthorModal = false;
        $http.post("http://localhost:8081/admin/updateAuthor", author).success(function(data){
            toastr.success('Author Updated');
            $scope.editAuthorModal = false;
            authorService.getAllAuthors().then(function(data){
                $scope.authorsList = data;
            })
        })
    }
    $scope.deleteAuthor= function(authorId) {
        $scope.deleteAuthorID=authorId;
        $scope.deleteConfirmModal=true;
    }
    $scope.noDeleteModal=function() {
        $scope.deleteConfirmModal=false;
    }
    $scope.yesDeleteModal=function () {
        $http.get("http://localhost:8081/admin/deleteAuthor/"+ $scope.deleteAuthorID).success(function () {
            authorService.getAllAuthors().then(function(data){
                toastr.success('Author Deleted');
                $scope.authorsList = data;
                $scope.deleteConfirmModal=false;
            })
        })
    }

})
