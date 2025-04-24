import React, { useContext, useState } from "react";


function MateList({id, name}) {
    const [showModal, setShowModal] = useState(false);
    const toggleModal = () => {
        setShowModal(!showModal);
    };


    return (
    <>
    <div class = "friend-single-section" onClick={toggleModal}>
        <div class = "right-banner-friend-profile-picture"></div>
        <div class = "right-banner-freind-name-container">{name}</div>
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
