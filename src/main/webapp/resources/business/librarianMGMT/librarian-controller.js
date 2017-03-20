
lmsApp.controller("librarianController",function($scope, $http, $window, $location,Pagination){
    $http.get("http://localhost:8081/librarian/libraryList").success(function (data) {
        $scope.libraryList=data;
        $scope.pagination = Pagination.getNew(10);
        $scope.pagination.numPages = Math.ceil($scope.libraryList.length / $scope.pagination.perPage);
    })
    $http.get("http://localhost:8081/admin/getAllBooks").success(function (data) {
        $scope.bookList=data;
    })

    $http.get("http://localhost:8081/librarian/iniBookCopy").success(function(data){
        $scope.book_copy=data;
        console.log($scope.book_copy);
    })


    $scope.showEditModal=function(libraryId){
        $http.get("http://localhost:8081/admin/getLibraryById/"+libraryId).success(function (data){
            $scope.library=data;
        })
        $scope.editLibraryModal=true;
    }
    $scope.closeEditModal = function () {
        $scope.editLibraryModal = false;
    }
    $scope.editLibrary = function () {
        $http.post("http://localhost:8081/librarian/updateLibrary", $scope.library ).success(function (data) {
            $http.get("http://localhost:8081/librarian/libraryList").success(function (data) {
                $scope.libraryList=data;
            })
            $scope.editLibraryModal = false;
        })
    }
    $scope.searchLibrary = function(){
        $http.get("http://localhost:8081/librarian/searchLibraries/"+$scope.searchString).success(function(data){
            $scope.libraryList = data;
            $scope.pagination = Pagination.getNew(10);
            $scope.pagination.numPages = Math.ceil($scope.libraryList.length / $scope.pagination.perPage);
        })
    }
    $scope.showCopyModal=function(){
        $http.get("http://localhost:8081/admin/getBookByPk/"+$scope.book_copy.bookId).success(function (data){
            $scope.book_copy_book=data;
        })
        $http.get("http://localhost:8081/admin/getLibraryById/"+$scope.book_copy.branchId).success(function (data){
            $scope.book_copy_library=data;
        })
       //initial
        $http.get("http://localhost:8081/admin/getBookCopyById/"+$scope.book_copy.branchId+"/"+$scope.book_copy.bookId).success(
            function(data){
                if(data.noOfCopies)
                    $scope.book_copy.noOfCopies=data.noOfCopies;
                else
                    $scope.book_copy.noOfCopies=0;
            })


        $scope.editCopyModal=true;
    }
    $scope.closeCopyModal = function () {
        $scope.editCopyModal = false;
    }
    $scope.editCopy = function () {
        $http.post("http://localhost:8081/librarian/updateBookCopy", $scope.book_copy ).success(function (data) {
            $scope.editCopyModal = false;
        })
    }

})