package com.logos.projectadv.controllers;

import com.logos.projectadv.models.Item;
import com.logos.projectadv.models.User;
import com.logos.projectadv.service.ProductService;
import com.logos.projectadv.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final UserService userService;


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @GetMapping("/index")
    public String getall(Model model, Principal principal) {
        User user = userService.getUserFromPrincipal(principal);
        int userId = user.getId();
        List<Item> items = productService.getAll();
        userService.setIdInSession("userId",userId);
        model.addAttribute("user", user);
        model.addAttribute("items", items);
        return "index";
    }

    @GetMapping("/product/delete")
    RedirectView deleteProduct() {
        Item item = productService.getItemFromSession();
        int itemId = item.getItemId();
        productService.delete((Integer) itemId);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/index");
        return redirectView;
    }

    @GetMapping("/product/{itemId}")
    public String getProdById(@PathVariable int itemId, Model model) {
        try {
            User user = userService.getUserFromSession();
            Item itemById = productService.getItemById(itemId);
            userService.setIdInSession("itemId", itemId );
            model.addAttribute("user", user);
            model.addAttribute("item", itemById);
            return "product_details";
        } catch (RuntimeException e) {
            e.getMessage();
        }
        return "404";
    }

    @RequestMapping(value = "/createproduct", method = RequestMethod.GET)
    @GetMapping("/createproduct")
    String getUser(Model model, Principal principal) {
        User user = userService.getUserFromPrincipal(principal);
        model.addAttribute("user", user);
        return "createproduct";
    }


    @PostMapping("/product/save")
    public RedirectView saveItem(@RequestParam String name,
                                 @RequestParam String description,
                                 @RequestParam double salary,
                                 @RequestParam String phoneNumber,
                                 @RequestParam MultipartFile image,
                                 @RequestParam int userId,
                                 Model model) {
        Item item = new Item(name, description, phoneNumber, salary, userId);
        model.addAttribute("item", item);
        RedirectView redirectView = new RedirectView();
        if (image.isEmpty()) {
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
