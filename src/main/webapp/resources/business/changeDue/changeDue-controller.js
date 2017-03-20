
lmsApp.controller("changDueController", function($scope, $http, $window, $location,Pagination) {
    $http.get("http://localhost:8081/admin/getAllBookLoans").success(function (data) {
        $scope.book_loans = data;
        $scope.pagination = Pagination.getNew(10);
        $scope.pagination.numPages = Math.ceil($scope.book_loans.length / $scope.pagination.perPage);
    })
    $http.get("http://localhost:8081/admin/initBookLoan").success(function (data) {
        $scope.book_loan = data;
    })
    $scope.showModify = function (book_loan) {
        $http.post("http://localhost:8081/admin/editDue",book_loan).success(function (data) {
            $scope.book_loan_temp = data;
                $scope.editDueModal = true;
        })

    }
    $scope.closeEditModal = function () {
        $scope.editDueModal = false;
    }
    $scope.editDueDate = function () {
        $http.post("http://localhost:8081/admin/updateDueDate", $scope.book_loan_temp).success(function (data) {
            $http.get("http://localhost:8081/admin/getAllBookLoans").success(function (data) {
                $scope.book_loans = data;
                $scope.editDueModal = false;
            })
        })
    }
})