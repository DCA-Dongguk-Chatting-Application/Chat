package com.dongguk.chat.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatViewController {
        @GetMapping("/chat")
        public String chatPage() {
            System.out.println("요청 들어옴");
            return "chat.html"; // resources/templates/chat.html 로 연결됨
        }
}

