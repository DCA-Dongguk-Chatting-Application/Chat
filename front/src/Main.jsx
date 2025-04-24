import React , { useState, useRef, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom"; 
import "./style.css";
import file_icon from "./assets/attach_file_icon.png";
import ChatBubble from "./component/ChatBubble"; //말풍선 컴포넌트
import FriendList from "./component/FriendList"; // 친구목록 컴포넌트
import MateList from "./component/MateList"; // 참여자 목록 보여주는 컴포넌트
import RoomList from "./component/RoomList"; // 방 목록 보여주는 컴포넌트
import {chatlog} from "./Testdata/testdata_chat"; //대화목록
import {friendlist} from "./Testdata/testdata_friendlist"; //친구목록
import {roomates} from "./Testdata/testdata_roomates"; // 참여자 목록
import {rooms} from "./Testdata/testdata_roomlist"; // 방 목록





export const Main = () => {
    const [isHovered, setIsHovered] = useState(false)
    const [ismymenuModalOpened, setmymenuModalOpened] = useState(false)
    const [isRoomAddModalOpened, setRoomAddModalOpened] = useState(false)
    const [isExitModalOpened, setExitModalOpened] = useState(false)
    const [friendlistswitched, setfriendlistswitched] = useState(true)
    const [inviteList, setInviteList] = useState([]); //방 생성시초대목록
    const [textbox, setTextbox] = useState("");
    const toggleInviteFriend = (friend) => {
        setInviteList((prev) => {
          const exists = prev.find((f) => f.id === friend.id);
          return exists
            ? prev.filter((f) => f.id !== friend.id) // 이미 있으면 제거
            : [...prev, friend]; // 없으면 추가
        });
      };
    //각 팝업창 on/off관리
    const ToggleMyMenu = () => {
        setmymenuModalOpened(!ismymenuModalOpened);
    };
    const ToggleAddRoom = () => {
        setRoomAddModalOpened(!isRoomAddModalOpened);
    };
    const ToggleExitRoom = () => {
        setExitModalOpened(!isExitModalOpened);
    };
    const ToggleFriendListSwitch = () => {
        setfriendlistswitched(!friendlistswitched);
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
                <button class = "left-banner-room-add-button" onClick = {ToggleAddRoom}>추가</button>
                {isRoomAddModalOpened && (
                    <div class = "modal-overlay">
                        <div class = "left-banner-room-add-modal-container">
                            <div class = "left-banner-room-add-modal-text">채팅방 추가하기</div>
                            <div class = "left-banner-room-add-modal-close" onClick = {ToggleAddRoom}>
                                <img
                                src={require(`./assets/close.png`)}
                                alt="close icon"
                                className="close-icon"
                            /> </div>
                            <div class = "left-banner-room-add-friendlist-container">
                            {friendlist.map((friend) => (
                                <div
                                    key={friend.id}
                                    className="friend-list-item"
                                    onClick={() => toggleInviteFriend(friend)}
                                >
                                {friend.name}
                            </div>
                            ))}
                            </div>

                            <img src={require(`./assets/right.png`)}alt="arrow icon" className="arrow-icon"/> 
                            
                            <div class = "left-banner-room-add-invitelist-container">
                            {inviteList.map((friend) => (
                                <div
                                    key={friend.id}
                                    className="invite-list-item"
                                    onClick={() => toggleInviteFriend(friend)}
                                >
                                    {friend.name}
                                </div>
                                ))}
                            </div>
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
            <div class = "center-banner-top">
                <div class = "center-banner-top-text">채팅방제목</div>
                <div class = "center-banner-top-room-exit-button" onClick={ToggleExitRoom}>
                    <img
                        src={require(`./assets/door.png`)}
                        alt="close icon"
                        className="close-icon"
                    />
                </div>
            </div>
            
            {chatlog.map((chat, index) => (
                <ChatBubble
                    key = {index}
                    sender = {chat.sender}
                    message = {chat.message}
                    isMe = {chat.isMe}
                />
            ))}
        </div>
        {isExitModalOpened && (
                <div class = "modal-overlay">
                    <div class = "exit-modal-container">
                        <div class = "exit-modal-container-text">방에서 나가시겠습니까?</div>
                        <div class = "exit-modal-button-1">네</div>
                        <div class = "exit-modal-button-2" onClick = {ToggleExitRoom}>아니오</div>
                    </div>
                </div>
        )}


        <div class = "right-banner">
            <div class = "title-friend-list-container">
                <h3 class = "title-friend-list">{friendlistswitched? ("친구목록"):("참가자")}</h3>
                <div class = "title-switch-button" onClick = {ToggleFriendListSwitch}>
                    <img
                        src={require(`./assets/switch.png`)}
                        alt="close icon"
                        className="close-icon"
                    />
                </div>
            </div>
            
            <div className="right-banner-friend-list-zone">
             {friendlistswitched
                ? friendlist.map((friend, index) => (
                    <FriendList key={index} id={friend.id} name={friend.name} />
                ))
                : roomates.map((mate, index) => (
                    <MateList key={index} id={mate.id} name={mate.name} />
                ))}
            </div>
            
        </div>
    
        
    </div>
    );
};