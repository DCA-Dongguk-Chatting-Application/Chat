import React, { useContext, useState } from "react";


function ChatBubble({sender, message, isMe}) {
   
    const userId = localStorage.getItem("userId");
    return (
        
    <div class = "chat-single-section">
        
        {userId == sender? (
            <div class = "chat-section-me">
                <div class = "chat-bubble-zone-me">
                    <div class = "chat-bubble-me">{message}</div>
                </div>
                
            </div>
            
            ):(
            <div class = "chat-section-opponent">
                <div class = "info-zone-opponent">
                    <div class = "profile-picture-opponent"></div>
                    <div class = "name-opponent">{sender}</div>
                </div>
                
                <div class = "chat-bubble-zone-opponent">
                    <div class = "chat-bubble-opponent">{message}</div>
                </div>
            </div>
            
            )}
    </div>

    
    );
}

export default ChatBubble;
