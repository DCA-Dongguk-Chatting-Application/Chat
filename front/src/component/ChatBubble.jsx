import React, { useContext, useState } from "react";


function ChatBubble({sender, message, time, showDate}) {
   
    const userId = localStorage.getItem("userId");
    const dater = time.slice(0, 10);
    return (
        
    <div class = "chat-single-section">
        {showDate && <div className="chat-time">{dater}</div>}
        
        {userId == sender? (
            <div class = "chat-section-me">
                <div class = "chat-bubble-zone-me">
                    <div class = "chat-bubble-me" style={{ marginTop: showDate ? '40px' : '4px' }}>{message}</div>
                </div>
                
            </div>
            
            ):(
            <div class = "chat-section-opponent" style={{ marginTop: showDate ? '40px' : '4px' }}>
                <div class = "info-zone-opponent">
                    <div class = "profile-picture-opponent"></div>
                    <div class = "name-opponent">{sender}</div>
                </div>
                
                <div class = "chat-bubble-zone-opponent" >
                    <div class = "chat-bubble-opponent" >{message}</div>
                </div>
            </div>
            
            )}
    </div>

    
    );
}

export default ChatBubble;
