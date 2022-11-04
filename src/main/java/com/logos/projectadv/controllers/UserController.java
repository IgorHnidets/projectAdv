package com.logos.projectadv.controllers;

import com.logos.projectadv.models.Item;
import com.logos.projectadv.models.Role;
import com.logos.projectadv.models.User;
import com.logos.projectadv.repository.UserRepository;
import com.logos.projectadv.service.BucketService;
import com.logos.projectadv.service.ProductService;
import com.logos.projectadv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    private final ProductService productService;
    private final BucketService bucketService;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, ProductService productService, BucketService bucketService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.productService = productService;
        this.bucketService = bucketService;
    }


    @RequestMapping(value = "/cabinet", method = RequestMethod.GET)
    @GetMapping("/cabinet")
    public String getAll(Principal principal, Model model){
        User user = userRepository.findByEmailOrderByEmail(principal.getName());
        System.out.println(user);
        model.addAttribute("user", user);
        return "cabinet";
    }

//    @GetMapping("/index")
//    public String getAdmin(Model model,HttpSession session){
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//        HttpSession session1 = attributes.getRequest().getSession();
//        Object userId = session1.getAttribute("userId");
//        Object role = session1.getAttribute("role");
//        model.addAttribute("role", role);
//        return "header";
//    }

    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable int id, Model model){
        try {
            User userById = userService.getUserById(id);
            model.addAttribute("user", userById);
        } catch (RuntimeException e){
            e.getMessage();
            return "404";
        }
        return "cabinet";
    }

    @GetMapping("/sendResume")
    public String sendResume(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession();
        Object itemId = session.getAttribute("itemId");
        Item item = productService.getItemById((Integer) itemId);
        int userId = item.getUserId();
    }


    @PostMapping("/user/save")
    public RedirectView registrationUser(@RequestParam String firstname,
                                   @RequestParam String lastname,
                                   @RequestParam MultipartFile avatar,
                                   @RequestParam String email,
                                   @RequestParam String password,
                                   @RequestParam Role role,
                                         Model model){
        User user = new User(firstname,lastname,email,password,role);
        RedirectView redirectView = new RedirectView();
        String redirect = "/registration?error";
        if (avatar.isEmpty()){
            try {
                user.setAvatar("defaultimage.png");
                userService.save(user);
                model.addAttribute("user",user);
                bucketService.create(user.getId());
                redirectView.setUrl("/");
                return redirectView;
            } catch (RuntimeException e){
                redirectView.setUrl(redirect);
                return redirectView;
            }
        }
        String file = userService.transferFile(avatar);
        user.setAvatar(file);
        try {
            User save = userService.save(user);
        } catch (RuntimeException e){
            e.getMessage();
            redirectView.setUrl(redirect);
            return redirectView;
        }
        bucketService.create(user.getId());
        model.addAttribute("user",user);
        redirectView.setUrl("/login");
        return redirectView;
    }





    @GetMapping("/")
    RedirectView getRedirect(){
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/index");
        return redirectView;
    }


}
