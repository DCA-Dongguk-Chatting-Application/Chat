import React , { useState, useRef, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom"; 
import "./style.css";
import RequestList from "./component/RequestList"; //친구신청목록 컴포넌트
//import {friendRequests} from "./Testdata/testdata_friendrequest"; // 친신 목록
import { GetUserInfo }  from "./component/UserInfo"
import { GetUserProfile} from "./component/UserProfile"
import { GetRequest} from "./component/UserFriendRequests"
import axios from 'axios'



export const Setting = () => {
  const [requestId, setRequestId] = useState("")//친구신청 창에서, 입력한 ID값
  const [userInfo, setUserInfo] = useState(null);//회원가입시 개인정보
  const [userProfile, setUserProfile] = useState(null);//프로필 정보
  const [loading, setLoadingComplete] = useState(false);//로딩 여부 검ㅅㅏ
  const [friendReqlist, setFriendReqList] = useState([]);//친구요청 목록
  const [editPhone, setEditPhone] = useState("");//폰
  const [editEmail, setEditEmail] = useState("");//멜
  const token = localStorage.getItem("accessToken");
  const userId = localStorage.getItem("userId");
  const serverUrl = 'http://59.24.237.51:8080';
  // ✅ 1. 유저 정보, 유저 프로필 가져오기
  const fetchUserInfo = async () => {
    try {
        const user = await GetUserInfo(token);
        const user_profile = await GetUserProfile(token);
        setUserInfo(user);
        setUserProfile(user_profile);
        
        setEditPhone(user.phoneNumber);
        setEditEmail(user.email);
        setLoadingComplete(true);
        console.log("로딩끝")
        console.log("[setting_user]유저 정보:", user);
        console.log("[setting_user_profile]유저 프로필", user_profile);
    } catch (err) {
        alert("유저 정보를 불러오지 못했습니다.");
    }
};
  
  useEffect(() => {
  
      fetchUserInfo();
  }, []);


//친구 요청 목록을 불러오기
const fetchFriendReq = async () => {
        try {
          const data = await GetRequest(); 
          setFriendReqList(data); 
          console.log("친구 요청 목록:", data); 
        } catch (err) {
            alert("친구요청 정보를 불러오지 못했습니다.");
        }
    };
useEffect(() => {
  
    
    fetchFriendReq();
}, []);

 
//프로필수정
  const [showModal, setShowModal] = useState(false);
      const toggleModal = () => {
          setShowModal(!showModal);
      };
//친구수락
  const [showFriendModal, setShowFriendModal] = useState(false);
  const  toggleFriendModal = () => {
          setShowFriendModal(!showFriendModal);
      };
//친구걸기
  const [showAddFriendModal, setAddShowFriendModal] = useState(false);
  const  toggleAddFriendModal = () => {
          setAddShowFriendModal(!showAddFriendModal);
      };

 
  const navigate = useNavigate();
  const goBack = () => {
    navigate("/main"); 
  };

  const logout = () => {
    navigate("/"); 
  };

  const sendRequest = async () => {///친구 신청 버튼 누르면 작동
    const token = localStorage.getItem("accessToken");
  
    if (!requestId) {
      alert("닉네임을 입력해주세요.");
      return;
    }
  
    if (!userInfo || !userInfo.id) {
      alert("유저 정보를 불러오지 못했습니다.");
      return;
    }

    if (userInfo.id == requestId) {
      alert("자기 자신에게는 걸 수 없습니다");
      return;
    }
    try {
      const response = await axios.post(
        "/api/friends/request", 
        {
          requesterId: userInfo.id,
          receiverNickName: requestId
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
          }
        }
      );
      alert("친구신청을 보냈습니다");
      console.log("친구 요청 응답:", response.data);
    } catch (error) {
      console.error("친구신청 중 에러:", error);
      alert("실패하였습니다. 존재하는 닉네임이 맞는지 확인하세요");
    }
  };
  //프로필 수정
  const handleSubmitProfileEdit = async () => {
    try {
      const payload = {
        username: userInfo.username,
        phoneNumber: editPhone.trim() ? editPhone : userInfo.phoneNumber,
        email: editEmail.trim() ? editEmail : userInfo.email,
      };
  
      const response = await axios.put('/api/user', payload, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });
  
      if (response.status === 200) {
        console.log('프로필 수정 성공:', response.data);
        alert('프로필이 성공적으로 수정되었습니다!');
        setShowModal(false);//창닫기
        fetchUserInfo();//새로고침
      } else {
        console.error('프로필 수정 실패:', response);
        alert('프로필 수정에 실패했습니다.');
      }
    } catch (error) {
      console.error('프로필 수정 중 오류 발생:', error);
      alert('서버와 통신 중 오류가 발생했습니다.');
    }
  };

  const IdClick = () => {
    alert("ID는 바꿀 수 없습니다");
  }

  const handleAccept = (acceptedId) => {
    // 수락된 요청을 상태에서 제거
    fetchFriendReq();
  };
  

    return (


      
      <div class = "background">
        
          <div class = "setting-profile-container">
              <div class = "logout" onClick={logout}>Logout</div>
              <div class = "setting-profile-picture" >
              {loading==false ? (
                "Loading..."
              ) : userProfile.imageUrl ? (
                <img
                  className="setting-profile-picture-real"
                  src={`${serverUrl}${userProfile.imageUrl}`}
                  alt="User profile"
                />
              ) : (
                <img
                  className="setting-profile-picture-none"
                  src={require('./assets/profile-user.png')}
                  alt="기본 프로필 이미지"
                />
              )}

              </div>
              <h3 class = "setting-profile-info-text">프로필 정보</h3>
              <h1 class = "setting-nick">{loading ? userProfile.nickname : "Loading..."}</h1>
              
              <div class = "setting-info-container">
                <div class = "setting-name-zone">
                  <h1 class = "setting-name-tag">ID</h1>
                  <h1 class = "setting-name-user">{loading ? userInfo.username : "Loading..."}</h1>
                </div>
              
                <div class = "setting-phone-zone">
                  <h1 class = "setting-phone-tag">전화번호</h1>
                  <h1 class = "setting-phone-user">{loading ? userInfo.phoneNumber : "Loading..."}</h1>
                </div>
              
                <div class = "setting-mail-zone">
                  <h1 class = "setting-mail-tag">E-mail</h1>
                  <h1 class = "setting-mail-user">{loading ? userInfo.email : "Loading..."}</h1>
                </div>
              </div>
              
              <div class = "setting-edit-button" onClick = {toggleModal}>
                <img
                src={require(`./assets/edit.png`)}
                alt="edit icon"
                className="edit-icon"
               />  
              </div>
              
              <div class = "setting-friend-add-button" onClick = {toggleAddFriendModal}>
                <img
                src={require(`./assets/add-friend.png`)}
                alt="previous icon"
                className="friend-add-icon"
                /> 
              </div>

              <div class = "setting-back-button" onClick = {goBack}>
                <img
                src={require(`./assets/previous.png`)}
                alt="previous icon"
                className="previous-icon"
                /> 
              </div>

              <div class = "setting-friend-button" onClick = {toggleFriendModal}>
                <img
                src={require(`./assets/friends.png`)}
                alt="previous icon"
                className="friend-icon"
                /> 
              </div>
          
          </div>

          {showModal && (
            <div class = "modal-overlay">
              <div class = "setting-modal-container">
                <div class = "setting-modal-close-button" onClick={toggleModal}>
                  <img
                    src={require(`./assets/close.png`)}
                    alt="close icon"
                    className="close-icon"
                  />
                </div>
                <div class = "setting-modal-text-info">개인정보 수정</div>

                <div class = "setting-modal-text-1">ID</div>
                <div class = "setting-modal-text-2">전화번호</div>
                <div class = "setting-modal-text-3">이메일</div>
                <div class = "setting-modal-text-4"></div>

                <input class = "setting-modal-textbox-1" value = {userInfo.username} onClick={IdClick}></input>
                <input class = "setting-modal-textbox-2"  placeholder = {userInfo.phoneNumber} onChange={(e) => setEditPhone(e.target.value)}></input>
                <input class = "setting-modal-textbox-3"  placeholder = {userInfo.email} onChange={(e) => setEditEmail(e.target.value)}></input>
                

                <div class = "setting-modal-ok" onClick={handleSubmitProfileEdit}>확인</div>
              </div>
            
            </div>
          )}
          
          {showFriendModal && (
              <div className="modal-overlay">
                <div className="modal-setting-friend-container">
                  <div className="modal-setting-friend-close" onClick={toggleFriendModal}>
                    <img
                      src={require(`./assets/close.png`)}
                      alt="close icon"
                      className="close-icon"
                    />
                  </div>
                  <div>
                    <div className="setting-mode-1-text">받은 친구신청</div>

                    <div className="setting-mode-requested-container">
                      {friendReqlist.length === 0 ? (
                        <div className="no-friend-requests">친구신청이 없습니다</div>
                      ) : (
                        friendReqlist.map((req, index) => (
                          <RequestList
                            key={index}
                            id={req.userId}
                            myId={userId}
                            name={req.nickname}
                            onAccept={handleAccept}
                          />
                        ))
                      )}
                    </div>
                  </div>
                </div>
              </div>
            )}


        {showAddFriendModal && (
          <div class = "modal-overlay">
            <div class = "modal-setting-friend-container-2">
              <div class = "modal-setting-friend-close" onClick={toggleAddFriendModal}>
                <img
                  src={require(`./assets/close.png`)}
                  alt="close icon"
                  className="close-icon"
                  />
              </div>
              <div>
                  <div class = "setting-mode-1-text">친구 신청하기</div>
                  <input class = "setting-mode-searchbox" placeholder = "닉네임 입력" onChange={(e) => setRequestId(e.target.value)}/>
                  <div class = "setting-searched-name">닉네임 입력후 확인을 누르세요</div>
                  <div class = "setting-mode-confirm-ok" onClick = {sendRequest}>친구신청 보내기</div>
                </div>

            </div>
          </div>
        )}

      </div>
    );
};