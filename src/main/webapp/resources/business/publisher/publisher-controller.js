
lmsApp.controller("publisherController", function($scope, $http, $window, $location,Pagination,toastr) {
    $http.get("http://localhost:8081/admin/getAllPublisher").success(function (data) {
        $scope.publisherList = data;
        $scope.pagination = Pagination.getNew(10);
        $scope.pagination.numPages = Math.ceil($scope.publisherList.length / $scope.pagination.perPage);
    })
    $http.get("http://localhost:8081/admin/iniPublisher").success(function (data) {
        $scope.publisher = data;
    })
    $http.get("http://localhost:8081/admin/iniPublisher").success(function (data) {
        $scope.newPublisher = data;
    })
    $scope.showModify = function (publisherId) {
        $http.get("http://localhost:8081/admin/getPublisherByPk/"+publisherId).success(function (data) {
            $scope.publisher = data;
                $scope.editPublisherModal = true;
        })
    }

    $scope.closeEditModal = function () {
        $scope.editPublisherModal = false;
    }
    $scope.editPublisher = function () {
        $http.post("http://localhost:8081/admin/updatePublisher", $scope.publisher).success(function () {
            $http.get("http://localhost:8081/admin/getAllPublisher").success(function (data) {
                $scope.publisherList = data;
                toastr.success('Publisher Updated');
                $scope.editPublisherModal = false;
            })
        })
    }
    $scope.deletePublisher=function(publisherId){
        $http.get("http://localhost:8081/admin/deletePublisher/"+ publisherId).success(function () {
            $http.get("http://localhost:8081/admin/getAllPublisher").success(function (data) {
                $scope.publisherList = data;
                toastr.success('Publisher deleted');
            })
        })
        }
     $scope.addPublisher=function(){
         $http.post("http://localhost:8081/admin/addPublisher", $scope.newPublisher).success(function () {
             $http.get("http://localhost:8081/admin/getAllPublisher").success(function (data) {
                 $scope.publisherList = data;
                 toastr.success('Publisher Added');
             })
         } )
    }
})