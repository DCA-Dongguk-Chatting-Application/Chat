import React , { useState, useRef, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom"; 
import "./style.css";
import RequestList from "./component/RequestList"; //친구신청목록 컴포넌트
import {friendRequests} from "./Testdata/testdata_friendrequest"; // 친신 목록
import axios from 'axios'



export const Setting = () => {
  const fileInputRef = useRef(null);

  const handleProfilePicUpload = (e) => {
    const file = e.target.files[0];
    if (file) {
      alert(`선택한파일: ${file.name}`);
    
    }
  };
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

//친구정보 불러오기
const userId = 4;///임시시
const [friendRequests, setFriendRequests] = useState([]);
useEffect(() => {
  axios.get(`/api/requests/${userId}`)
      .then((response) => {
          setFriendRequests(response.data);
          console.log("친구신청목록 부름");
      })
      .catch((error) => {
          console.error("친구신청목록 불러오기 실패", error);
      });
}, []);  
  
    

    return (
      <div class = "background">
          <div class = "setting-profile-container">
              <div class = "setting-profile-picture" onClick={() => fileInputRef.current.click()}>클릭하여 업로드</div>
              <h3 class = "setting-profile-info-text">프로필 정보</h3>
              <h1 class = "setting-nick">아아아ㄴ</h1>
              
              <div class = "setting-info-container">
                <div class = "setting-name-zone">
                  <h1 class = "setting-name-tag">이름</h1>
                  <h1 class = "setting-name-user">땡땡떙</h1>
                </div>
              
                <div class = "setting-phone-zone">
                  <h1 class = "setting-phone-tag">전화번호</h1>
                  <h1 class = "setting-phone-user">010-1577-1577</h1>
                </div>
              
                <div class = "setting-mail-zone">
                  <h1 class = "setting-mail-tag">E-mail</h1>
                  <h1 class = "setting-mail-user">parrot@email.com</h1>
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

                <div class = "setting-modal-text-1">설명</div>
                <div class = "setting-modal-text-2">이름</div>
                <div class = "setting-modal-text-3">전화번호</div>
                <div class = "setting-modal-text-4">E-mail</div>

                <input class = "setting-modal-textbox-1"></input>
                <input class = "setting-modal-textbox-2"></input>
                <input class = "setting-modal-textbox-3"></input>
                <input class = "setting-modal-textbox-4"></input>

                <div class = "setting-modal-ok">확인</div>
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
                  <input class = "setting-mode-searchbox" placeholder = "userID 입력"/>
                  <div class = "setting-mode-confirm">검색</div>
                  <div class = "setting-searched-name">여기에 검색된 이름표시</div>
                  <div class = "setting-mode-confirm-ok">친구신청 보내기</div>
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

          <input
            type="file"
            accept="image/*"
            ref={fileInputRef}
            style={{ display: "none" }}
            onChange={handleProfilePicUpload}
          />

      </div>
    );
};