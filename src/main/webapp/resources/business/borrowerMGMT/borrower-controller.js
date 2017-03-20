lmsApp.controller("borrowerMGMTController",function($scope, $http, $window, $location,Pagination,toastr){
    $http.get("http://localhost:8081/borrower/iniBorrower").success(function (data) {
        $scope.borrower=data;
    })
    $http.get("http://localhost:8081/borrower/iniLibrary").success(function (data) {
        $scope.library=data;
    })
    $http.get("http://localhost:8081/librarian/libraryList").success(function (data) {
        $scope.libraryList = data;
    })
    $scope.loginModal=true;
    $http.get("http://localhost:8081/borrower/iniBookLoanList").success(function (data) {
        $scope.bookLoans = data;
    })
    $scope.loginModal=false;
    $scope.showLogin=function(){
        $scope.loginModal=true;
    }
    $scope.closeLoginModal=function(){
        $scope.loginModal=false;
    }
    $scope.submit = function(){

        $http.get("http://localhost:8081/borrower/getBorrowerByPk/"+$scope.borrower.cardNo).success(function (data) {
            if(data)
            {$scope.borrower=data;
                $http.get("http://localhost:8081/borrower/getBookLoanByBorrower/"+$scope.borrower.cardNo).success(function(data1){
                    toastr.success($scope.borrower.name+" log in");
                    $scope.bookLoans=data1;
                    $scope.loginModal=false;
                })

            }
            })
    }
    $scope.reloadBook=function(){
        $http.get("http://localhost:8081/borrower/getBooksByBranchId/"+$scope.library.libraryId).success(function(data){
            $scope.bookList=data;
            console.log( $scope.bookList);
        });
        $http.get("http://localhost:8081/borrower/getBookLoanByBorrower/"+$scope.borrower.cardNo).success(function(data){
            $scope.bookLoans=data;
            console.log($scope.borrower);
        });
    }
    $scope.checkOut=function(bookId){
        $http.get("http://localhost:8081/borrower/checkOutBook/"+$scope.borrower.cardNo+"/"+bookId+"/"+$scope.library.libraryId)
            .success(function(){
                $http.get("http://localhost:8081/borrower/getBookLoanByBorrower/"+$scope.borrower.cardNo).success(function(data){
                    $scope.bookLoans=data;
                })
            })

    }
    $scope.return=function(book_loan1){
        $http.post("http://localhost:8081/borrower/returnBook",book_loan1).success(function(){
            $http.get("http://localhost:8081/borrower/getBookLoanByBorrower/"+$scope.borrower.cardNo).success(function(data){
                $scope.bookLoans=data;
            })
        })

    }

    // $scope.reloadBorrower=function(){
    //     $http.get("http://localhost:8081/borrower/getBorrowerByPk/"+$scope.borrower.cardNo).success(function (data) {
    //         if(data)
    //         {$scope.borrower=data;}
    // })}
    $scope.reloadBookLoan=function(){
        $http.get("http://localhost:8081/borrower/getBookLoanByBorrower/"+$scope.borrower.cardNo).success(function(data){
            $scope.bookLoans=data;
        })
    }


})