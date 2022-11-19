package com.logos.projectadv.controllers;

import com.logos.projectadv.models.Item;
import com.logos.projectadv.models.Role;
import com.logos.projectadv.models.User;
import com.logos.projectadv.repository.ProductRepository;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final BucketService bucketService;
    @Autowired
    public UserController(UserService userService, ProductRepository productRepository, ProductService productService, BucketService bucketService) {
        this.userService = userService;
        this.productRepository = productRepository;
        this.productService = productService;
        this.bucketService = bucketService;
    }




    @RequestMapping(value = "/cabinet", method = RequestMethod.GET)
    @GetMapping("/cabinet")
    public String getAll(Principal principal, Model model){
        User user = userService.getUserFromPrincipal(principal);
        System.out.println(user);
        model.addAttribute("user", user);
        return "cabinet";
    }


    @RequestMapping(value = "/candidates", method = RequestMethod.GET)
    @GetMapping("/candidates")
    public String getCandidates(Principal principal,Model model){
        Item item = productService.getItemFromSession();
        List<User> users = item.getUsers();
        User user = userService.getUserFromPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("users" , users);
        return "candidates";
    }

    @GetMapping("/sendResume")
    public RedirectView sendResume(){
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/index");
        User user = userService.getUserFromSession();
        Item item = productService.getItemFromSession();
        int itemID = item.getItemId();
        int userID = user.getId();

        List<User> users = item.getUsers();
        if (!users.isEmpty()){
            for (int c = 0; c< users.size(); c++){
                int id = users.get(c).getId();
                if (id == userID){
                    return redirectView;
                }
            }
        }
        productRepository.addResume(userID,itemID);
        System.out.println("RESUME SEND");
        return redirectView;
    }

    @GetMapping("/rejectCV/{userId}")
    public RedirectView rejectCV(@PathVariable int userId){
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/candidates");
        Item item = productService.getItemFromSession();
        int itemId = item.getItemId();
        productRepository.delResume(userId,itemId);
        return redirectView;
    }


    @PostMapping("/user/save")
    public RedirectView registrationUser(@RequestParam String firstname,
                                   @RequestParam String lastname,
                                   @RequestParam String description,
                                   @RequestParam String phoneNumber,
                                   @RequestParam MultipartFile avatar,
                                   @RequestParam String email,
                                   @RequestParam String password,
                                   @RequestParam Role role,
                                         Model model){
        User user = new User(firstname,lastname,description,phoneNumber,email,password,role);
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
            userService.save(user);
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

    @GetMapping ("/index/home")
    String header(Model model){
        List<Item> all = productService.getAll();
        model.addAttribute("items",all);
        return "header";
    }


}
