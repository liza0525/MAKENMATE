// // package com.cocktail.controller;

// // import javax.servlet.http.HttpServletRequest;

// // import org.springframework.boot.web.servlet.error.ErrorController;
// // import org.springframework.stereotype.Controller;
// // import org.springframework.ui.Model;
// // import org.springframework.web.bind.annotation.RequestMapping;
 
// // @Controller
// // public class CustomErrorController implements ErrorController {
 
// //     private static final String ERROR_PATH = "/error";
 
// //     @Override
// //     public String getErrorPath() {
// //         return ERROR_PATH;
// //     }
    
// //     @RequestMapping("/error")
// //     public String handleError(HttpServletRequest request, Model model) {
// //         return "/index";
// //     }
 
// // }
 


// import org.springframework.boot.web.servlet.error.ErrorController;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.RequestMapping;

// @Controller
// public class CustomErrorController implements ErrorController {

//     private static final String ERROR_PATH = "/error";

//     @Override
//     public String getErrorPath() {
//         return ERROR_PATH;
//     }

//     @RequestMapping(value = "/error")
//     public String handleError() {
//         return "redirect:https://localhost";
//     }

// }
