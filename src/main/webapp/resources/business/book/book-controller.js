
lmsApp.controller("bookController", function($scope, $http, $window, $location,Pagination,toastr){

    $http.get("http://localhost:8081/admin/getAllAuthors").success(function (data) {
        $scope.authorList = data;
    })
    $http.get("http://localhost:8081/admin/getAllBooks").success(function (data) {
        $scope.bookList=data;
        $scope.pagination = Pagination.getNew(9);
        $scope.pagination.numPages = Math.ceil($scope.bookList.length / $scope.pagination.perPage);})

    $http.get("http://localhost:8081/admin/initBook").success(function(data){$scope.book = data;})
    $http.get("http://localhost:8081/admin/initBook").success(function(data){$scope.newBook= data;})

    // $scope.saveBook= function() {
    //     $http.post("http://localhost:8081/admin/addAuthor", $scope.author).success(function (data) {
    //         authorService.getAllAuthors().then(function(data){
    //             $scope.authorsList = data;
    //         })
    //     })
    // }
    $http.get("http://localhost:8081/admin/getAllGenre").success(function (data) {
        $scope.genreList=data;
    })
    $http.get("http://localhost:8081/admin/getAllPublisher").success(function (data) {
        $scope.publisherList=data;
    })
    $scope.searchBook = function(){
        $http.get("http://localhost:8081/admin/searchBook/"+$scope.searchString).success(function(data){
            $scope.bookList = data;
            $scope.pagination = Pagination.getNew(9);
            $scope.pagination.numPages = Math.ceil($scope.bookList.length / $scope.pagination.perPage);
        })
    }


    $scope.showEditModal = function(bookId){
        $http.get("http://localhost:8081/admin/getBookByPk/"+bookId).success(function(data){
            $scope.book = data;
            $scope.editBookModal = true;
        })
    }

    $scope.closeEditModal = function(){
        $scope.editBookModal = false;
    }

    $scope.editBook = function(book){
        $http.post("http://localhost:8081/admin/updateBook", book).success(function(){
            $http.get("http://localhost:8081/admin/getAllBooks").success(function (data) {
                toastr.success('Book Updated');
                $scope.bookList = data;
            })
        })

        $scope.editBookModal = false;
    }
    $scope.delete=function(bookId){
        $http.get("http://localhost:8081/admin/deleteBook/"+bookId).success(function(){
            $http.get("http://localhost:8081/admin/getAllBooks").success(function (data) {
                toastr.success('Book Deleted');
                $scope.bookList = data;
            })
        })
    }
    $scope.addBook=function(){
        console.log($scope.newBook);
        $http.post("http://localhost:8081/admin/addBook",$scope.newBook).success(function () {
            $http.get("http://localhost:8081/admin/getAllBooks").success(function (data) {
                toastr.success('Book Added');
                $scope.bookList = data;
            })
        })
    }


})
