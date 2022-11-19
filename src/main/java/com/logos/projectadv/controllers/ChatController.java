package com.logos.projectadv.controllers;


import com.logos.projectadv.models.Chat;
import com.logos.projectadv.models.Item;
import com.logos.projectadv.models.User;
import com.logos.projectadv.service.ChatService;
import com.logos.projectadv.service.ProductService;
import com.logos.projectadv.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

@Controller
@AllArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/chatWith/{getterId}")
    String chat(@PathVariable int getterId, Principal principal, Model model){
        userService.setIdInSession("getterId", getterId);
        User sender = userService.getUserFromPrincipal(principal);
        List<Chat> all = chatService.getAll(sender.getId(), getterId);
        model.addAttribute("chat", all);
        return "chat";
    }

    @GetMapping("/userChat")
    String userChat(Principal principal, Model model){
        User user = userService.getUserFromPrincipal(principal);
        int userId = user.getId();
        Item item = productService.getItemFromSession();
        int adminId = item.getUserId();
        List<Chat> all = chatService.getAll(adminId, userId);
        model.addAttribute("chat",all);
        return "chat";
    }

    @PostMapping("/messageSend")
    RedirectView sendMessage(@RequestParam String message, Principal principal){
        RedirectView redirectView = new RedirectView();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession();
        User sender = userService.getUserFromPrincipal(principal);
        Object getterId = session.getAttribute("getterId");
        if (getterId != null) {
            User getter = userService.getUserById((Integer) getterId);
            Chat chat = new Chat(message, getter.getId(), new Timestamp(System.currentTimeMillis()), sender);
            chatService.save(chat);
            String id = Integer.toString(getter.getId());
            redirectView.setUrl("/chatWith/" + id);
            return redirectView;
        }
        Item item = productService.getItemFromSession();
        User admin = userService.getUserById(item.getUserId());
        Chat chat = new Chat(message,sender.getId(),new Timestamp(System.currentTimeMillis()),admin);
        chatService.save(chat);
        redirectView.setUrl("/userChat");
        return redirectView;
    }

}
