lmsApp.config(["$routeProvider", function($routeProvider){
    return $routeProvider.when("/",{
        redirectTo: "/home"
    }).when("/home",{
        templateUrl: "welcome.html"
    }).when("/admin/author",{
        templateUrl: "author.html"
    }).when("/initauthor",{
        templateUrl: "addauthor.html"
    }).when("/admin/book",{
        templateUrl:"book.html"
    }).when("/admin/borrower",{
        templateUrl:"borrower.html"
    }).when("/admin/library",{
        templateUrl:"library.html"
    }).when("/admin/publisher",{
        templateUrl:"publisher.html"
    }).when("/admin/change_due",{
        templateUrl:"change_due.html"
    }).when("/librarian/library",{
        templateUrl:"librarianMGMT.html"
    }).when("/borrower",{
        templateUrl:"login.html"
    })
}])