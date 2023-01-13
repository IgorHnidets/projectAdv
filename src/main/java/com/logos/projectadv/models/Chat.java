package com.logos.projectadv.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;
    private String message;
    private int getterId;
    private String timeOfSend;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    public Chat(String message, int getterId, String timeOfSend, User user) {
        this.message = message;
        this.getterId = getterId;
        this.timeOfSend = timeOfSend;
        this.user = user;
    }

}
