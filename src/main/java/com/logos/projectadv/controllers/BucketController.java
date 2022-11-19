package com.logos.projectadv.controllers;

import com.logos.projectadv.models.Item;
import com.logos.projectadv.models.User;
import com.logos.projectadv.service.BucketService;
import com.logos.projectadv.service.ProductService;
import com.logos.projectadv.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class BucketController {

    private final BucketService bucketService;
    private final ProductService productService;
    private final UserService userService;




    @RequestMapping(value = "/bucket/save/{itemId}", method = RequestMethod.GET)
    @ResponseBody
    RedirectView addItemtoBucket(@PathVariable int itemId, Principal principal){
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/index");
        Item item = productService.getItemById(itemId);
        User user = userService.getUserFromPrincipal(principal);
        int itemId2 = item.getItemId();
        int userId = user.getId();
        List<Item> items = bucketService.getAllItems(userId);
        if (!items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                int itemIdInBucket = items.get(i).getItemId();
                if (itemId2 == itemIdInBucket) {
                    return redirectView;
                }
            }
        }

        bucketService.insertProduct(userId,itemId2);
        return redirectView;
    }

    @RequestMapping(value = "/getItems", method = RequestMethod.GET)
    @GetMapping("/getItems")
    String getItems(Principal principal, Model model){
        User user = userService.getUserFromPrincipal(principal);
        List<Item> allItems = bucketService.getAllItems(user.getId());
        System.out.println("ITEMS -------------------------------- \n " + allItems);
        model.addAttribute("user", user);
        model.addAttribute("items", allItems);
        return "bucket";
    }


    @RequestMapping(value = "/deleteItem/{id}",method = RequestMethod.GET)
    RedirectView deleteItem(@PathVariable int id,Principal principal){
        User user = userService.getUserFromPrincipal(principal);
        int id1 = user.getId();
        bucketService.removeProduct(id1,id);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/getItems");
        return redirectView;
    }


}
