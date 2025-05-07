import React, { useContext, useState } from "react";


function MateList({id, name, image, loading}) {
    const [showModal, setShowModal] = useState(false);
    const serverUrl = 'http://59.24.237.51:8080';
    const toggleModal = () => {
        setShowModal(!showModal);
    };


    return (
    <>
    <div class = "friend-single-section" onClick={toggleModal}>
        <div class = "right-banner-friend-profile-picture">
        {loading==false ? (
                            <img
                            className="profile-icon-friend"
                            src={require('./assets/profile-user.png')}
                            alt="기본 프로필 이미지"
                            />
                        ) : image? (
                            <img
                            className="profile-icon-friend-real"
                            src={`${serverUrl}${image}`}
                            alt="User profile"
                            />
                        ) : (
                            <img
                            className="profile-icon-friend"
                            src={require('./assets/profile-user.png')}
                            alt="기본 프로필 이미지"
                            />
                    )}
        </div>
        <div class = "right-banner-freind-name-container">#{id} {name}</div>
        <div class = "right-banner-online-indicator"></div>

    </div>
    
    {showModal && (
        <div class = "modal-overlay">
            <div class = "friend-modal-container">
                <div class = "friend-modal-text">{name}</div>
                <div class = "friend-modal-close" onClick={toggleModal}>
                        <img
                            src={require(`./assets/close.png`)}
                            alt="close icon"
                            className="close-icon"
                         />
                </div>
                <div class = "friend-modal-profile-pic">프사</div>
                <div class = "friend-modal-desc">안녕하세요 반갑습니다</div>
                <div class = "friend-modal-phone-num">010-1010-0101</div>
                <div class = "friend-modal-email">aaaaa@bbb.com</div>
            </div>
        </div>

    )}

    </> 
    );
}

export default MateList;
