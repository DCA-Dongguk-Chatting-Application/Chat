import React, { useContext, useState } from "react";


function ChatBubble({sender, message, time, showDate, avatarList, loading}) { //sender는 숫자 uid
   
    const userId = localStorage.getItem("userId");
    const dater = time.slice(0, 10);
    const avatar = avatarList?.find((a) => a.userId === sender);
    const serverUrl = 'http://59.24.237.51:8080';
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
                    <div class = "profile-picture-opponent">
                    {loading==false ? (
                            <img
                            className="profile-icon-friend-chat"
                            src={require('./assets/profile-user.png')}
                            alt="기본 프로필 이미지"
                            />
                        ) : avatar.imageUrl? (
                            <img
                            className="profile-icon-friend-real-chat"
                            src={`${serverUrl}${avatar.imageUrl}`}
                            alt="User profile"
                            />
                        ) : (
                            <img
                            className="profile-icon-friend-chat"
                            src={require('./assets/profile-user.png')}
                            alt="기본 프로필 이미지"
                            />
                    )}
                    </div>
                    <div class = "name-opponent">{sender} / {avatar?.nickname || "이름없음"}</div>
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
