import React , { useState, useRef, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom"; 
import "./style.css";
import RequestList from "./component/RequestList"; //친구신청목록 컴포넌트
import {friendRequests} from "./Testdata/testdata_friendrequest"; // 친신 목록
import { GetUserInfo }  from "./component/UserInfo"
import { GetUserProfile} from "./component/UserProfile"
import axios from 'axios'



export const Setting = () => {
  const [requestId, setRequestId] = useState("")//친구신청 창에서, 입력한 ID값
  const [userInfo, setUserInfo] = useState(null);//회원가입시 개인정보
  const [userProfile, setUserProfile] = useState(null);//프로필 정보
  const [loading, setLoadingComplete] = useState(false);//로딩 여부 검ㅅㅏ
  const [newImageFile, setNewImageFile] = useState(null);//프사 
  const [editNickname, setEditNickname] = useState("");//닉
  const [editPhone, setEditPhone] = useState("");//폰
  const [editEmail, setEditEmail] = useState("");//멜
  const token = localStorage.getItem("accessToken");
  const serverUrl = 'http://59.24.237.51:8080';
  // ✅ 1. 유저 정보, 유저 프로필 가져오기
  useEffect(() => {
      
      const fetchUserInfo = async () => {
          try {
              const user = await GetUserInfo(token);
              const user_profile = await GetUserProfile(token);
              setUserInfo(user);
              setUserProfile(user_profile);
              setEditNickname(user_profile.nickname);
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
      fetchUserInfo();
  }, []);






  
  ///이미지
 
//프로필수정
  const [showModal, setShowModal] = useState(false);
      const toggleModal = () => {
          setShowModal(!showModal);
      };
//친구관리
  const [showFriendModal, setShowFriendModal] = useState(false);
  const  toggleFriendModal = () => {
          setShowFriendModal(!showFriendModal);
      };
//친추 받는창/거는창 전환
  const [showFriendModalSwitch, setShowFriendModalSwitch] = useState(false);
  const  toggleFriendModalSwitch = () => {
              setShowFriendModalSwitch(!showFriendModalSwitch);
      };
 
  const navigate = useNavigate();
  const goBack = () => {
    navigate("/main"); 
  };

  const sendRequest = async () => {///친구 신청 버튼 누르면 작동
    const token = localStorage.getItem("accessToken");
  
    if (!requestId) {
      alert("ID를 입력해주세요.");
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
          receiverId: requestId
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
      alert("실패하였습니다. 존재하는 ID가 맞는지 확인하세요");
    }
  };
  //프로필 수정
  const handleSubmitProfileEdit = async () => {
    try {
      const payload = {
        username: editNickname,
        phoneNumber: editPhone,
        email: editEmail,
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
  
        
       
  
        setUserProfile(false);
      } else {
        console.error('프로필 수정 실패:', response);
        alert('프로필 수정에 실패했습니다.');
      }
    } catch (error) {
      console.error('프로필 수정 중 오류 발생:', error);
      alert('서버와 통신 중 오류가 발생했습니다.');
    }
  };
  

    return (


      
      <div class = "background">
          <div class = "setting-profile-container">
              <div class = "setting-profile-picture" >
              {loading ? (
    <img  class = "setting-profile-picture-real" src= {`${serverUrl}${userProfile.imageUrl}`} alt="이미지를 설정하지 않음"/>
        ) : ("Loading...")}
              </div>
              <h3 class = "setting-profile-info-text">프로필 정보</h3>
              <h1 class = "setting-nick">{loading ? userProfile.nickname : "Loading..."}</h1>
              
              <div class = "setting-info-container">
                <div class = "setting-name-zone">
                  <h1 class = "setting-name-tag">이름</h1>
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
                <div class = "setting-modal-text-info">정보 수정</div>

                <div class = "setting-modal-text-1">이름</div>
                <div class = "setting-modal-text-2">전화번호</div>
                <div class = "setting-modal-text-3">이메일</div>
                <div class = "setting-modal-text-4"></div>

                <input class = "setting-modal-textbox-1"  onChange={(e) => setEditNickname(e.target.value)}></input>
                <input class = "setting-modal-textbox-2" value = {userInfo.phoneNumber}  onChange={(e) => setEditPhone(e.target.value)}></input>
                <input class = "setting-modal-textbox-3" value = {userInfo.email} onChange={(e) => setEditEmail(e.target.value)}></input>
                

                <div class = "setting-modal-ok" onClick={handleSubmitProfileEdit}>확인</div>
              </div>
            
            </div>
          )}
          
        {showFriendModal && (
          <div class = "modal-overlay">
            <div class = "modal-setting-friend-container">
              <div class = "modal-setting-friend-close" onClick={toggleFriendModal}>
                <img
                  src={require(`./assets/close.png`)}
                  alt="close icon"
                  className="close-icon"
                  />
              </div>
              {showFriendModalSwitch? (
                <div>
                  <div class = "setting-mode-1-text">친구 신청하기</div>
                  <div class = "setting-mode-switch-button" onClick={toggleFriendModalSwitch}>친구신청받기</div>
                  <input class = "setting-mode-searchbox" placeholder = "userID 입력" onChange={(e) => setRequestId(e.target.value)}/>
                  {/*<div class = "setting-mode-confirm">검색</div>*/}
                  <div class = "setting-searched-name">아이디 입력후 확인을 누르세요</div>
                  <div class = "setting-mode-confirm-ok" onClick = {sendRequest}>친구신청 보내기</div>
                </div>
                ) : (
                <div>
                  <div class = "setting-mode-1-text">받은 친구신청</div>
                  <div class = "setting-mode-switch-button" onClick={toggleFriendModalSwitch}>친구신청하기</div>
                  <div class = "setting-mode-requested-container">
                  {friendRequests.map((req, index) => (
                    <RequestList
                    key = {index}
                    id = {req.id}
                    name = {req.name}
                    />
                ))}
                  </div>
                </div>)}

            </div>
          </div>
        )}

      </div>
    );
};