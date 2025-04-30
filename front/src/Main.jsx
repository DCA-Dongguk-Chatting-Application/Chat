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
import axios from 'axios'
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import { GetUserInfo }  from "./component/UserInfo"



export const Main = () => {
    
    const [isHovered, setIsHovered] = useState(false)
    const [ismymenuModalOpened, setmymenuModalOpened] = useState(false)
    const [isRoomAddModalOpened, setRoomAddModalOpened] = useState(false)
    const [isExitModalOpened, setExitModalOpened] = useState(false)
    const [friendlistswitched, setfriendlistswitched] = useState(true)
    const [inviteList, setInviteList] = useState([]); //방 생성시초대목록
    const [searchTerm, setSearchTerm] = useState("");//방 생성시 친구검색색필터
    const [textbox, setTextbox] = useState("");//채팅칸 입력내용 받음
    const [chatlog, setChatLog] = useState([]);//채팅내역 배열열
    const [rooms, setRooms] = useState([]);//방목록
    const [friendlist, setFriendList] = useState([]);//친구목록
    const [roomates, setRoomatesList] = useState([]);//참여자목록
    const [roomName, setRoomName] = useState("방을 선택하세요")//방 제목
    const fileInputRef = useRef(null);
    const clientRef = useRef(null);
    const chatContainerRef = useRef(null);
    const [roomId, setRoomId] = useState("");//임시
    const [userInfo, setUserInfo] = useState(null);


//파일 업로드  
    const handleFileUpload = (e) => {
        const file = e.target.files[0];
        if (file) {
            alert(`선택된 파일: ${file.name}`);
            // 여기에 서버 연결 api 추가가
        }
    };

// ✅ 1. 유저 정보 가져오기
useEffect(() => {
    const token = localStorage.getItem("accessToken");
    const fetchUserInfo = async () => {
        try {
            const user = await GetUserInfo(token);
            setUserInfo(user);
            console.log("유저 정보:", user);
        } catch (err) {
            alert("유저 정보를 불러오지 못했습니다.");
        }
    };
    fetchUserInfo();
}, []);

// ✅ 2. 유저 정보를 기준으로 방 목록, 친구 목록
useEffect(() => {
    if (!userInfo) return;

    // 방 목록 가져오기
    axios.get(`/api/chatroom/list/${userInfo.id}`)
        .then((res) => {
            setRooms(res.data);
        })
        .catch((err) => console.error("방 목록 불러오기 실패", err));

    // 친구 목록 가져오기
    axios.get(`/api/friends/${userInfo.id}`)
        .then((res) => {
            setFriendList(res.data);
            console.log("친구목록 부름");
        })
        .catch((err) => console.error("친구 목록 불러오기 실패", err));

}, [userInfo]);

// ✅ 3. roomId가 선택된 후 참여자 목록과 채팅 기록 가져오기
useEffect(() => {
    if (!roomId) return;

    // 참여자 목록
    axios.get(`/api/chatroom/${roomId}/participants`)
        .then((res) => {
            setRoomatesList(res.data);
            console.log("참여자목록 부름");
        })
        .catch((err) => console.error("참여자목록 불러오기 실패", err));

    // 과거 채팅기록
    axios.get(`/api/chatroom/${roomId}/messages`)
        .then((res) => {
            setChatLog(res.data);
        })
        .catch((err) => console.error("채팅 기록 불러오기 실패", err));

}, [roomId]);

// ✅ 4. 웹소켓 연결
useEffect(() => {
    if (!roomId) return;

    const socket = new SockJS('/api/portfolio');
    const client = new Client({
        webSocketFactory: () => socket,
        debug: (str) => console.log(str),
        onConnect: () => {
            console.log('웹소켓 연결됨');
            client.subscribe(`/topic/chatroom/${roomId}`, (message) => {
                const newMessage = JSON.parse(message.body);
                setChatLog(prev => [...prev, newMessage]);
            });
        },
        onStompError: (frame) => {
            console.error('STOMP 오류', frame);
        },
    });

    client.activate();
    clientRef.current = client;

    return () => {
        client.deactivate();
    };
}, [roomId]);

//메시지 전송 버튼 동작
function sendMessage() {
    
    const client = clientRef.current;
    if (!client || !client.connected) {
        alert("STOMP에 연결되지 않았습니다. '채팅 연결' 버튼을 눌러주세요.");
        return;
    }

    const payload = {
        sender: userInfo.id,
        roomId: roomId,
        content: textbox
    };

    client.publish({
        destination: "/app/chat.send",
        body: JSON.stringify(payload)
    });

    setTextbox("");
}
//채팅 오면 자동으로 스크롤 아래로
useEffect(() => {
    if (chatContainerRef.current) {
      chatContainerRef.current.scrollTop = chatContainerRef.current.scrollHeight;
    }
  }, [chatlog]);
//방 생성시 친구초대
    const toggleInviteFriend = (friend) => {//방 생성시 친구초대
        setInviteList((prev) => {
          const exists = prev.find((f) => f.id === friend.id);
          return exists
            ? prev.filter((f) => f.id !== friend.id) // 이미 있으면 제거
            : [...prev, friend]; // 없으면 추가
        });
      };
    //각 팝업창 on/off관리
    const ToggleMyMenu = () => {//내 메뉴
        setmymenuModalOpened(!ismymenuModalOpened);
    };
    const ToggleAddRoom = () => {//방 추가시 안내창
        setRoomAddModalOpened(!isRoomAddModalOpened);
    };
    const ToggleExitRoom = () => {//나가기 버튼 클릭 후 팝업
        setExitModalOpened(!isExitModalOpened);
    };
    const ToggleFriendListSwitch = () => {//누르면 친구목록, 참여자목록 전환
        setfriendlistswitched(!friendlistswitched);
    };
    
    const navigate = useNavigate();
      const goSetting = () => {
        navigate("/setting"); 
      };
//방 클릭 시, 방의 아이디 이름 저장
    const handleRoomClick = (id, name) => {
        setRoomId(id);
        setRoomName(name);
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
                    name = {room.roomName}
                    onClick={() => handleRoomClick(room.id, room.roomName)}
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
                            {friendlist
                            .filter((friend) =>friend.nickname?.toLowerCase().includes(searchTerm.toLowerCase()))
                            .map((friend) => (
                                <div
                                    key={friend.userId}
                                    className="friend-list-item"
                                    onClick={() => toggleInviteFriend(friend)}
                                >
                                {friend.userId}
                            </div>
                            ))}
                            </div>

                            <img src={require(`./assets/right.png`)}alt="arrow icon" className="arrow-icon"/> 
                            
                            <div class = "left-banner-room-add-invitelist-container">
                            {inviteList.map((friend) => (
                                <div
                                    key={friend.userId}
                                    className="invite-list-item"
                                    onClick={() => toggleInviteFriend(friend)}
                                >
                                    {friend.userId}
                                </div>
                                ))}
                            </div>

                            <input class = "left-banner-room-add-searchbox" placeholder="검색" value = {searchTerm} onChange = {(e)=>setSearchTerm(e.target.value)}/>
                            <div class = "left-banner-room-ok">확인</div>
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
                value = {textbox}
                onChange={(e) => setTextbox(e.target.value)}
                onKeyDown={(e) => {
                    if (e.key === "Enter") {
                      e.preventDefault(); // enter키로 전송
                      sendMessage();
                    }
                  }}></input>
            <button class = "message-send-button" onClick={sendMessage}>전송</button>
            
            <button className="file-button" onClick={() => fileInputRef.current.click()}>
                <img className="icon" src={file_icon} alt="로고" style={{ width: "30px" }} />
            </button>

            <input
                type="file"
                ref={fileInputRef}
                style={{ display: "none" }}
                onChange={handleFileUpload}
            />
            
               
        </div>
       {roomId && (
        <div class = "center-banner" ref = {chatContainerRef}>
            <div class = "center-banner-top">
                <div class = "center-banner-top-text">{roomName}</div>
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
                    message = {chat.content}
                    isMe = {chat.isMe}
                />
            ))}
        </div>
       )}
        
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
                    <FriendList key={index} id={friend.userId} name={friend.userId} />
                ))
                : roomates.map((mate, index) => (
                    <MateList key={index} id={mate.user.id} name={mate.user.id} />
                ))}
            </div>
            
        </div>
    
        
    </div>
    );
};