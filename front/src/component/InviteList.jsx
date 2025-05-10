import React, { useContext, useState } from "react";
import axios from "axios";
function InviteList({id, name, isSelected, toggleSelect}) {
    
    

    return (
       <>
            <div class = "requestlist-container">
                <div class = "request-name">{name}</div>
                <div
                className={`request-o ${isSelected ? "selected" : ""}`}
                onClick={() => toggleSelect(id)}
            >
                {isSelected ? "선택됨" : "선택"}
            </div>
            </div>
      </>
    );
}

export default InviteList;
