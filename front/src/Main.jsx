import React , { useState, useRef, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom"; 
import "./style.css";
import file_icon from "./assets/attach_file_icon.png";
import ChatBubble from "./component/ChatBubble";
import FriendList from "./component/FriendList";
import RoomList from "./component/RoomList";
import {chatlog} from "./Testdata/testdata_chat";
import {friendlist} from "./Testdata/testdata_friendlist";
import {rooms} from "./Testdata/testdata_roomlist";





export const Main = () => {
    const [isHovered, setIsHovered] = useState(false)
    const [ismymenuModalOpened, setmymenuModalOpened] = useState(false)
    const [isRoomAddModalOpened, setRoomAddModalOpened] = useState(false)
    const [textbox, setTextbox] = useState("");
    const ToggleMyMenu = () => {
        setmymenuModalOpened(!ismymenuModalOpened);
    };
    const ToggleAddRoom = () => {
        setRoomAddModalOpened(!isRoomAddModalOpened);
    };

    const navigate = useNavigate();
      const goSetting = () => {
        navigate("/setting"); 
      };
 
  

//**본 메인 화면**//
    return (
    <div class = "background">
        <div class = "left-banner">
            <div class = "left-banner-zone">
                {rooms.map((room, index) => (
                <RoomList
                    key = {index}
                    id = {room.id}
                    name = {room.name}
                />
                ))}
                <button class = "left-banner-room-add-button" onClick = {ToggleAddRoom}>ADD</button>
                {isRoomAddModalOpened && (
                    <div class = "modal-overlay">
                        <div class = "left-banner-room-add-modal-container">
                            <div class = "left-banner-room-add-modal-text">방을 추가할까요</div>
                            <div class = "left-banner-room-add-modal-close" onClick = {ToggleAddRoom}><img
                                src={require(`./assets/close.png`)}
                                alt="close icon"
                                className="close-icon"
                            /> </div>
                        </div>
                    </div>
                )}

            </div>
            <div class = "left-banner-profile-container">
                 <div class = "left-banner-my-profile" onClick = {ToggleMyMenu}>내프사</div>
            </div>

            {ismymenuModalOpened && (
                <div class = "modal-overlay">
                    <div class = "main-menu-my-profile-modal-container">
                        <div class = "main-menu-my-profile-modal-text">내모달</div>
                        <div class = "main-menu-my-profile-modal-close"  onClick = {ToggleMyMenu}>
                        
                        <img
                            src={require(`./assets/close.png`)}
                            alt="close icon"
                            className="close-icon"
                         /> 
                         
                        </div>
                        <div class = "main-menu-my-profile-modal-profile-button" onClick = {goSetting}>프로필로</div>
                    </div>
                </div>
            )}
           

            
            
        </div>

        <div class = "bottom-banner">
            <input class = "chat-textbox"
                placeholder= "메시지 입력"
                onChange={(e) => setTextbox(e.target.value)}></input>
            <button class = "message-send-button">전송</button>
            <button class = "file-button">
            <img  class = "icon" src={file_icon} alt="로고" style={{ width: "30px" }} />
            </button>
        </div>
        <div class = "center-banner">
            {chatlog.map((chat, index) => (
                <ChatBubble
                    key = {index}
                    sender = {chat.sender}
                    message = {chat.message}
                    isMe = {chat.isMe}
                />
            ))}
        </div>
        <div class = "right-banner">
            <div class = "title-friend-list-container">
                <h3 class = "title-friend-list">친구목록</h3>
            </div>
            
            <div class = "right-banner-friend-list-zone">
                {friendlist.map((friendlist, index) => (
                    <FriendList
                        key = {index}
                        id = {friendlist.id}
                        name = {friendlist.name}
                    />
                ))}
            </div>
            
        </div>
    
        
    </div>
    );
};