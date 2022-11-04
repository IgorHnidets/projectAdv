package com.logos.projectadv;

import com.logos.projectadv.models.Role;
import com.logos.projectadv.models.User;
import com.logos.projectadv.repository.BucketRepository;
import com.logos.projectadv.service.BucketService;
import com.logos.projectadv.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProjectAdvApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ProjectAdvApplication.class, args);
        BucketRepository bean = context.getBean(BucketRepository.class);
        BucketService bean1 = context.getBean(BucketService.class);
//        bean1.insertProduct(14,7);
//        System.out.println(bean1.getAllItems(14));
//        bean1.create(3);
//        bean.save(new User("Igor","Karpyuk","abatar","igorK","1234", Role.USER));
    }

}
