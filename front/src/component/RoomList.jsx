import React, { useContext, useState } from "react";


function RoomList({id, name, onClick}) {
    const [showModal, setShowModal] = useState(false);
        const toggleModal = () => {
            setShowModal(!showModal);
            onClick(id,name);
        };
    
   

    return (
       <>
            <div class = "left-banner-room-icon" onClick={toggleModal}></div>

            {showModal && (
            <div class = "modal-overlay">
                <div class = "room-list-modal-container">
                    <div class = "room-list-modal-text">{name}</div>
                    <div class = "room-list-modal-close" onClick={toggleModal}>
                        <img
                            src={require(`./assets/close.png`)}
                            alt="close icon"
                            className="close-icon"
                         /> 
                    </div>
                </div>
            </div>
            )}
            
        </>
    );
}

export default RoomList;
