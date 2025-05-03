import React, { useState } from "react";
import ReactDOM from "react-dom";

function RoomList({ id, name, onClick }) {
    const [showModal, setShowModal] = useState(false);
    const [showTooltip, setShowTooltip] = useState(false);
    const [tooltipPosition, setTooltipPosition] = useState({ left: 0, top: 0 });

    const toggleModal = () => {
        setShowModal(!showModal);
        onClick(id, name);
    };

    const new_name = name.slice(0, 3);

    const handleMouseEnter = (e) => {
        const rect = e.target.getBoundingClientRect();
        setTooltipPosition({
            left: rect.left + window.scrollX + rect.width / 2, // Tooltip이 왼쪽에서 중앙에 위치하도록
            top: rect.top + window.scrollY - 30, // Tooltip이 방 아이콘 위에 표시되도록
        });
        setShowTooltip(true);
    };

    const handleMouseLeave = () => {
        setShowTooltip(false);
    };

    return (
        <>
            <div
                className="left-banner-room-icon"
                onClick={toggleModal}
                onMouseEnter={handleMouseEnter}
                onMouseLeave={handleMouseLeave}
            >
                <div className="left-banner-room-icon-text">{new_name}</div>
                <div style={{ color: 'red' }}>#{id}</div>
            </div>

            {/* React Portal을 사용하여 Tooltip을 body 태그에 렌더링 */}
            {showTooltip &&
                ReactDOM.createPortal(
                    <div
                        className="left-banner-tooltip-container"
                        style={{
                            position: "absolute",
                            left: tooltipPosition.left,
                            top: tooltipPosition.top,
                            zIndex: 1000,
                            backgroundColor: "rgba(0, 0, 0, 0.7)",
                            color: "white",
                            padding: "5px",
                            borderRadius: "3px",
                        }}
                    >
                        {name}
                    </div>,
                    document.body
                )}

            {showModal && (
                <div className="modal-overlay">
                    <div className="room-list-modal-container">
                        <div className="room-list-modal-text">{name}</div>
                        <div className="room-list-modal-close" onClick={toggleModal}>
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
