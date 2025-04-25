import React, { useContext, useState } from "react";


function RequestList({id, name}) {
    
   

    return (
       <>
            <div class = "requestlist-container">
                <div class = "request-name">{name}</div>
                <div class = "request-o">O</div>
                <div class = "request-x">X</div>
            </div>

            
            
        </>
    );
}

export default RequestList;
