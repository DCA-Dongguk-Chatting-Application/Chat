import React, { useContext, useState } from "react";
import axios from "axios";
function InviteList({id, name, myId, roomId, roomName, existingParticipants, onInviteSuccess}) {
    const [isInvited, setIsInvited] = useState(false);
    const handleInvite = () => {
        const newUserIds = [...existingParticipants, id];

        axios.post(`/api/chatroom/upgrade`, {
            originalRoomId: roomId,
            newUserIds: newUserIds,
            roomName: roomName
        })
            .then((response) => {
                setIsInvited(true);
                alert("초대 성공!");

                
                onInviteSuccess();
            })
            .catch((error) => {
                console.error("초대 실패", error);
                alert("초대에 실패했습니다.");
            });
    };

    return (
       <>
            <div class = "requestlist-container">{roomId}
                <div class = "request-name">#{id}/{name}</div>
                <div class = "request-o" onClick={handleInvite}>초대</div>
            </div>
      </>
    );
}

export default InviteList;
