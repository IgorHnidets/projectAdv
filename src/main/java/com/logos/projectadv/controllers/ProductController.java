package com.logos.projectadv.controllers;

import com.logos.projectadv.models.Item;
import com.logos.projectadv.models.Role;
import com.logos.projectadv.models.User;
import com.logos.projectadv.repository.UserRepository;
import com.logos.projectadv.service.ProductService;
import com.logos.projectadv.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final UserService userService;
    private final UserRepository userRepository;


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @GetMapping("/index")
    public String getall(Model model, Principal principal){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession();
        User user = userRepository.findByEmailOrderByEmail(principal.getName());
        List<Item> items = productService.getAll();
        System.out.println(items);
        session.setAttribute("userId",user.getId());
        model.addAttribute("user", user);
        model.addAttribute("items", items);
        return "index";
    }

    @GetMapping("/product/delete")
    RedirectView deleteProduct(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession();
        Object itemId = session.getAttribute("itemId");
        productService.delete((Integer) itemId);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/index");
        return redirectView;
    }

    @GetMapping("/product/{itemId}")
    public String getProdById(@PathVariable int itemId, Model model){
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attributes.getRequest().getSession();
            Item itemById = productService.getItemById(itemId);
            session.setAttribute("itemId",itemById.getItemId());
            Object userId = session.getAttribute("userId");
            User user = userService.getUserById((Integer) userId);
            model.addAttribute("user",user);
            model.addAttribute("item", itemById);
            return "product_details";
        }catch (RuntimeException e){
            e.getMessage();
        }
        return "404";
    }

    @RequestMapping(value = "/createproduct", method = RequestMethod.GET)
    @GetMapping("/createproduct")
    String getUser(Model model, Principal p){
        User user = userRepository.findByEmailOrderByEmail(p.getName());
        model.addAttribute("user",user);
        return "createproduct";
    }


    @PostMapping("/product/save")
    public RedirectView saveItem(@RequestParam String name,
                                    @RequestParam String description,
                                    @RequestParam double salary,
                                    @RequestParam String phoneNumber,
                                    @RequestParam MultipartFile image,
                                    @RequestParam int userId,
                                     Model model){
        Item item = new Item(name,description,phoneNumber,salary,userId);
        model.addAttribute("item", item);
        RedirectView redirectView = new RedirectView();
        if (image.isEmpty()){
            item.setImage("defaultimage.png");
            productService.save(item);
            redirectView.setUrl("/index");
            return redirectView;
        }
        String file = productService.transferFile(image);
        item.setImage(file);
        productService.save(item);
        redirectView.setUrl("/index");
        return redirectView;
    }

}
