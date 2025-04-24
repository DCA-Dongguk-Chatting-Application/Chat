import React , { useState, useRef, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom"; 
import "./style.css";




export const Setting = () => {
  const [showModal, setShowModal] = useState(false);
      const toggleModal = () => {
          setShowModal(!showModal);
      };
 
  const navigate = useNavigate();
  const goBack = () => {
    navigate("/main"); 
  };

    return (
      <div class = "background">
          <div class = "setting-profile-container">
              <div class = "setting-profile-picture"></div>
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
          
          
      </div>
    );
};