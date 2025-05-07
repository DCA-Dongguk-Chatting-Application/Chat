import React , { useState, useRef, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom"; 
import "./style.css";
import file_icon from "./assets/attach_file_icon.png";
import ChatBubble from "./component/ChatBubble"; //말풍선 컴포넌트
import FriendList from "./component/FriendList"; // 친구목록 컴포넌트
import MateList from "./component/MateList"; // 참여자 목록 보여주는 컴포넌트
import RoomList from "./component/RoomList"; // 방 목록 보여주는 컴포넌트
import WebSocketConnector from "./component/WebSocketConnector";
import {chatlog} from "./Testdata/testdata_chat"; //대화목록
import {friendlist} from "./Testdata/testdata_friendlist"; //친구목록
import {roomates} from "./Testdata/testdata_roomates"; // 참여자 목록
import {rooms} from "./Testdata/testdata_roomlist"; // 방 목록
import axios from 'axios'

import { Client } from "@stomp/stompjs";
import { GetUserInfo }  from "./component/UserInfo"
import { GetUserProfile} from "./component/UserProfile"



export const Main = () => {
    const [errorMessage, setErrorMessage] = useState("");
    const [isHovered, setIsHovered] = useState(false)
    const [ismymenuModalOpened, setmymenuModalOpened] = useState(false)
    const [isRoomAddModalOpened, setRoomAddModalOpened] = useState(false)//방 추가버튼 누를시 모달창
    const [isExitModalOpened, setExitModalOpened] = useState(false)//방 나가기 클릭시 모달
    const [isProfileModalOpened, setProfileModalOpened] = useState(true)//로그인 후, 프로필 정보 쓰는 모달이 나온다
    const [friendlistswitched, setfriendlistswitched] = useState(true)
    const [inviteList, setInviteList] = useState([]); //방 생성시초대목록
    const [newRoomname, setNewRoomname] = useState(""); //방 생성시 제목
    const [searchTerm, setSearchTerm] = useState("");//방 생성시 친구검색색필터
    const [textbox, setTextbox] = useState("");//채팅칸 입력내용 받음
    const [chatlog, setChatLog] = useState([]);//채팅내역 배열열
    const [rooms, setRooms] = useState([]);//방목록
    const [friendlist, setFriendList] = useState([]);//친구 목록 (1초마다 자동 갱신됨)
    const [displayFriendList, setDisplayFriendList] = useState([]);//친구 초대시에만 임시로 쓰는 부분. 갱신 버그를 방지하기 위해 친구 목록을 복사해 저장
    const [roomates, setRoomatesList] = useState([]);//참여자목록
    const [avatarList, setAvatarList] = useState([]);//참여자목록서 userId + imageUrl만 (채팅창 프사 표시용)
    const [roomName, setRoomName] = useState("방을 선택하세요")//방 제목
    const [profileNick, setProFileNick] = useState("");//프로필 생성- 닉네임
    const [userProfile, setUserProfile] = useState(null);//최초 접속 시, 프로필이 있는지 검사용
    const [loading, setLoadingComplete] = useState(false);//내 프로필로딩 여부 검ㅅㅏ
    const [loading_friend, setLoadingComplete_friend] = useState(false)//친구정보 로딩 여부 검사
    const [loading_mate, setLoadingComplete_mate] = useState(false)//참여자정보 로딩 여부 검사
    const [loading_chat, setLoadingComplete_chat] = useState(false)//채팅 로딩 여부 검사
    const [loading_complete, setLoadingComplete_complete] = useState(false)//채팅, 참여자 모두 로딩 되었는지 여부 검사
   //프로필 생성- 프사
    const profileImageRef = useRef(null);
    const fileInputRef = useRef(null);
    const serverUrl = 'http://59.24.237.51:8080';
    const [client, setClient] = useState(null);
    const chatContainerRef = useRef(null);
    const [roomId, setRoomId] = useState("");//임시
    const [userInfo, setUserInfo] = useState(null);
    const userId = localStorage.getItem("userId");
    const token = localStorage.getItem("accessToken");
   

    const profileClose = () => {
        setProfileModalOpened(false);
    }
//파일 업로드  (채팅버튼)
    const handleFileUpload = (e) => {
       
        const file = e.target.files[0];
        if (file) {
            alert(`선택된 파일: ${file.name}`);
            // 여기에 서버 연결 api 추가가
        }
        
    };
//프사 설정 (프로필 생성)
const handleImageChange = (e) => {
    profileImageRef.current = e.target.files[0];
    if(profileImageRef.current){
        alert(`선택한 파일 : ${profileImageRef.current.name}`)
    }
 
};
//프로필을 불러와서, 이미 있다면 팝업창 false
  const fetchUserInfo = async () => {
    try {
        const user_profile = await GetUserProfile(token);
        setUserProfile(user_profile);
        console.log("[setting_user_profile]유저 프로필", user_profile);
        setLoadingComplete(true);//로딩 검사
        if (user_profile.nickname) {
            setProfileModalOpened(false);
          } else {
            setProfileModalOpened(true);
          }
    } catch (err) {
        alert("확인된 프로필 정보가 없습니다. 프로필을 생성해 주세요");
    }

};
useEffect(() => {fetchUserInfo();}, []);
//프로필 업로드 확인 버튼 
const handleProfileConfirm = async (e) => {
    if(!profileNick){
        alert("이름은 필수입니다!");
        return;
    }

   
    const formData = new FormData();
    
    if(profileImageRef.current){
        
        formData.append('image', profileImageRef.current);
    }
    // MultipartFile
    formData.append('nickname', profileNick);     // 일반 문자열
    formData.append('userId', userId);  
    console.log('전송할 이미지:', profileImageRef.current);       // Long 타입, 문자열로 보내도 됨

    try {
      const response = await axios.post('/api/profile', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          
        },
        
        
      }
    );
      console.log('프로필 생성 성공:', response.data);
      setProfileModalOpened(false);
      alert("프로필을 생성했습니다!");
      navigate(0);
    } catch (error) {
      console.error('프로필 생성 실패:', error);
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

 
    }, [userInfo]);

        // 친구 목록 불러오기 및 polling (1초마다 갱신)
useEffect(() => {
    if (!userInfo) return;

    const fetchFriendList = () => {
        axios.get(`/api/friends/${userInfo.id}`)
            .then((res) => {
                setFriendList(res.data);
                setLoadingComplete_friend(true);
                console.log("친구목록 갱신");
            })
            .catch((err) => console.error("친구 목록 갱신 실패", err));
    };

    const silenceFriendList = () => {
        axios.get(`/api/friends/${userInfo.id}`)
            .then((res) => {
                setDisplayFriendList(res.data);
                console.log("친추용 친구목록 받아옴");
            })
            .catch((err) => console.error("친구 목록 갱신 실패", err));
    };

    // 최초 실행
    fetchFriendList();
    silenceFriendList();//친구초대 기능용 리스트. 초대 기능 작동중 갱신되지 않게 조치
    // 1초마다 polling, 온/오프라인 상태 실시간 반영을 위함
    const intervalId = setInterval(fetchFriendList, 1000);


    // 컴포넌트 언마운트 시 clear
    return () => clearInterval(intervalId);

}, [userInfo]);


// ✅ 3. roomId가 선택된 후 참여자 목록과 채팅 기록 가져오기
useEffect(() => {
    if (!roomId) return;
            setLoadingComplete_mate(false);
            setLoadingComplete_chat(false);
            setLoadingComplete_complete(false);
    // 참여자 목록
    axios.get(`/api/chatroom/${roomId}/participants`)
        .then((res) => {
            setRoomatesList(res.data);
            console.log("참여자목록 부름");
            // imageUrl + userId만 추출한 리스트 저장
            const avatars = res.data.map((entry) => ({
                userId: entry.profile.userId,
                imageUrl: entry.profile.imageUrl,
                nickname : entry.profile.nickname
            }));
            setAvatarList(avatars);
            console.log("AvatarList:", avatarList);
            setLoadingComplete_mate(true);
        })
        .catch((err) => console.error("참여자목록 불러오기 실패", err));

    // 과거 채팅기록
    axios.get(`/api/chatroom/${roomId}/messages`)
        .then((res) => {
            setChatLog(res.data);
            setLoadingComplete_chat(true);
        })
        .catch((err) => console.error("채팅 기록 불러오기 실패", err));

}, [roomId]);

// 참여자, 채팅 로딩 모두 완료되면 최종 로딩 완료 처리
useEffect(() => {
    if (loading_mate && loading_chat) {
        setLoadingComplete_complete(true);
    }
}, [loading_mate, loading_chat]);


//메시지 전송 버튼 동작
function sendMessage() {
    
    if (!textbox || textbox.trim() === "") {
        return;
    }

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
const handleInvite = (friend) => {
    setDisplayFriendList(displayFriendList.filter(f => f.userId !== friend.userId));
    setInviteList([...inviteList, friend]);
  };

  const handleRemoveInvite = (friend) => {
    setInviteList(inviteList.filter(f => f.userId !== friend.userId));
    setDisplayFriendList([...displayFriendList, friend]);
  };
    //각 팝업창 on/off관리
    const ToggleMyMenu = () => {//내 메뉴
        // setmymenuModalOpened(!ismymenuModalOpened);
    };
    const ToggleAddRoom = () => {//방 추가시 안내창
        setRoomAddModalOpened(!isRoomAddModalOpened);
    };
    const ToggleExitRoom = () => {//나가기 버튼 클릭 후 팝업
        setRoomId(null);
        setfriendlistswitched(true);
    };

    
    const navigate = useNavigate();
      const goSetting = () => {
        navigate("/setting"); 
      };
//방 클릭 시, 방의 아이디 이름 저장
    const handleRoomClick = (id, name) => {
        setRoomId(id);
        setRoomName(name);
        setfriendlistswitched(false);
    };

//채팅방 생성기능 구현 (아직 1대1 채팅만 구현)
const handleCreateRoom = async () => {
    const invitedUserIds = inviteList.map(friend => friend.userId);//초대한 인원들의 ID을 모두 inviteList에 담는다
    const roomName = newRoomname; // 예: "chat_123_456"
    if (invitedUserIds.length == 0) {//0명일 경우 생성 불가
      alert('아무도 초대하지 않았습니다');
      return;
    }

    else if (invitedUserIds.length == 1){ // 1대1기능
        try {
            const response = await axios.post('/api/chatroom/create', {
            roomName: roomName,
            myId: userId,
            partnerId: invitedUserIds[0]
      });
  
      // 성공 시 처리
            console.log('1대1 채팅방 생성 성공:', response.data);
            const roomRes = await axios.get(`/api/chatroom/list/${userId}`);//방 생성후 리스트 재 로딩딩
            setRooms(roomRes.data);

            setRoomAddModalOpened(!isRoomAddModalOpened);
        } catch (error) {
            console.error('방 생성 실패:', error);
            alert('방 생성에 실패했습니다.');
        }
    }

    else if (invitedUserIds.length > 1){  // 1대n 기능
        try {
            invitedUserIds.push(userId);
            const response = await axios.post('/api/chatroom/group', {
            roomName: roomName,
            userIds : invitedUserIds,
      });
  
      // 성공 시 처리
            console.log('1대n 채팅방 생성 성공:', response.data);
            const roomRes = await axios.get(`/api/chatroom/list/${userId}`);
            setRooms(roomRes.data);

            setRoomAddModalOpened(!isRoomAddModalOpened);
        } catch (error) {
            console.error('방 생성 실패:', error);
            alert('방 생성에 실패했습니다.');
        }
    }
    
  };
 
  

//**본 메인 화면**//
    return (
    <div class = "background">
        <WebSocketConnector token = {token} roomId = {roomId} setChatLog={setChatLog} setClient = {setClient}/>
        {isProfileModalOpened && (
            <div class = "modal-overlay">
                <div class = "profile-modal-container">
                    <div class = "profile-modal-instruction">환영합니다!</div>
                    <div class = "profile-modal-sub-intro">진행하시려면 정보를 입력해주세요</div>
                    <div class = "profile-modal-name-guide" >닉네임</div>
                    <div className="profile-modal-image-guide">프로필 이미지</div>
                    <input 
                         type="file" 
                        accept="image/*" 
                        className="profile-modal-image-upload"
                        onChange={handleImageChange}
                    />
                    <input class = "profile-modal-name-textbox" placeholder="닉네임 입력" onChange={(e) => setProFileNick(e.target.value)}/>
                    <div class = "profile-modal-confirm-button" onClick = {handleProfileConfirm}>확인</div>
                    {/* <div class = "profile-modal-close" onClick={profileClose}>닫기</div> */}
                </div>
            </div>
        )}
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
                <button class = "left-banner-room-add-button" onClick = {ToggleAddRoom}>
                    <img
                        className="room-add-icon"
                        src={require('./assets/add.png')}
                        alt="기본 프로필 이미지"
                    />
                </button>
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
                            {displayFriendList
                            .filter((friend) =>String(friend.userId)?.toLowerCase().includes(searchTerm.toLowerCase()))
                            .map((friend) => (
                                <div
                                    key={friend.userId}
                                    className="friend-list-item"
                                    onClick={() => handleInvite(friend)}
                                >
                                {friend.userId}-{friend.nickname}
                            </div>
                            ))}
                            </div>
                            <div class = "left-banner-room-add-friendlist-container-text">친구 목록</div>

                            <img src={require(`./assets/right.png`)}alt="arrow icon" className="arrow-icon"/> 
                            
                            <div class = "left-banner-room-add-invitelist-container">
                            {inviteList.map((friend) => (
                                <div
                                    key={friend.userId}
                                    className="invite-list-item"
                                    onClick={() => handleRemoveInvite(friend)}
                                >
                                    {friend.userId}-{friend.nickname}
                                </div>
                                ))}
                            </div>
                            <div class = "left-banner-room-add-invitelist-container-text">초대 목록</div>
                            <input class = "left-banner-room-add-searchbox" placeholder="검색" value = {searchTerm} onChange = {(e)=>setSearchTerm(e.target.value)}/>
                            <input class = "left-banner-room-add-room-title-textbox" placeholder = "방 제목은?"  onChange={(e) => setNewRoomname(e.target.value)}/>
                            <div class = "left-banner-room-ok" onClick={handleCreateRoom}>확인</div>
                        </div>
                    </div>
                )}

            </div>
            <div class = "left-banner-profile-container">
                 <div class = "left-banner-my-profile" onClick = {goSetting}>
                

                    {loading==false ? (
                            <img
                            className="profile-icon-main"
                            src={require('./assets/profile-user.png')}
                            alt="기본 프로필 이미지"
                            />
                        ) : userProfile.imageUrl ? (
                            <img
                            className="profile-icon-main-real"
                            src={`${serverUrl}${userProfile.imageUrl}`}
                            alt="User profile"
                            />
                        ) : (
                            <img
                            className="profile-icon-main"
                            src={require('./assets/profile-user.png')}
                            alt="기본 프로필 이미지"
                            />
                    )}
                 </div>
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

            {loading? (<div class = "main-left-my-name">{userProfile.nickname}</div>) : ("Loading")}
            
         
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
       {roomId ? (
        <div class = "center-banner" ref = {chatContainerRef}>
            <div class = "center-banner-top">
                <div class = "center-banner-top-text">{roomName}</div>
                <div class = "center-banner-top-room-exit-button" onClick={ToggleExitRoom}채>
                    <img
                        src={require(`./assets/door.png`)}
                        alt="close icon"
                        className="close-icon"
                    />
                </div>
            </div>
            
            {chatlog.map((chat, index) => {
                const currentDate = new Date(chat.sentAt).toDateString();
                const prevDate =
                    index > 0 ? new Date(chatlog[index - 1].sentAt).toDateString() : null;

                const showDate = index === 0 || currentDate !== prevDate;

                return (
                    <ChatBubble
                        key={index}
                        sender={chat.sender}
                        message={chat.content}
                        time={chat.sentAt}
                        showDate={showDate}
                        avatarList = {avatarList}
                        loading = {loading_complete}
                    />
                );
            })}
        </div>
       ) : (
        <div class = "center-banner">
            <div class = "center-banner-guide-1">현재 선택한 채팅방이 없습니다</div>
            <div class = "center-banner-guide-2">좌측에서 방을 만들거나 선택하세요</div>
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
               
            </div>
            
            <div className="right-banner-friend-list-zone">
             {friendlistswitched
                ? friendlist.map((friend, index) => (
                    <FriendList key={index} id={friend.userId} name={friend.nickname} isOnline = {friend.online} image = {friend.imageUrl} loading = {loading_friend} />
                ))
                : roomates.map((mate, index) => (
                    <MateList key={index} id={mate.user.id} name={mate.profile?.nickname || "!!NO_DATA!!"} image = {mate.profile.imageUrl} loading = {loading_mate}/>
                ))}
            </div>
            
        </div>
    
        
    </div>
    );
};