package com.logos.projectadv.service.Impl;

import com.logos.projectadv.models.Chat;
import com.logos.projectadv.repository.ChatRepository;
import com.logos.projectadv.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    ChatRepository chatRepository;

    @Override
    public Chat save(Chat chat) {
        return chatRepository.save(chat);
    }

    @Override
    public List<Chat> getAll(int senderId, int getterId) {
        return chatRepository.getMessage(senderId,getterId);
    }
}
