package com.logos.projectadv.service;

import com.logos.projectadv.models.Chat;

import java.util.List;

public interface ChatService {

    Chat save(Chat chat);

    List<Chat> getAll(int senderId, int getterId);

    String removeLast(String time);
}
