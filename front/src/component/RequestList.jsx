import React, { useContext, useState } from "react";
import axios from "axios";
function RequestList({id, name, myId, onAccept}) {
    const handleAccept = async () => {
        try {
          // 친구 요청 수락 API 호출
          await axios.post("api/friends/accept", {
            requesterId: id,
            receiverId: myId,
          });
    
          // 성공 시 부모 컴포넌트에 알림
          onAccept(id);
          console.log(`#${id}(${name})와 #${myId}(당신)은 이제 친구입니다. Congrats!`)
        } catch (error) {
          console.error("친구 요청 수락 실패:", error);
        }
      };
    return (
       <>
            <div class = "requestlist-container">
                <div class = "request-name">#{id}/{name}</div>
                <div class = "request-o" onClick = {handleAccept}>수락</div>
            </div>
      </>
    );
}

export default RequestList;
