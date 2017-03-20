//package LMS.MVC;
//
//import LMS.Service.BorrowerService;
//import LMS.Service.LibrarianService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//
///**
// * Created by cj on 3/3/17.
// */
//@Controller
//public class HomeController {
//    @Autowired
//    BorrowerService borrowerService;
//    @Autowired
//    LibrarianService librarianService;
//
//    @RequestMapping(value = "/")
//    public String home() {
//        return "welcome";
//    }
//
//    @RequestMapping("/admin")
//    public String admin(){return "admin";}
//    @RequestMapping("/login")
//    public String borrower(Model model){
//        model.addAttribute("borrowerService",borrowerService);
//        return "borrowerLogIn";}
//    @RequestMapping("/librarian")
//    public String librarian(Model model){
//        model.addAttribute("librarianService",librarianService);
//        return "librarianMGMT";}
//
//}
