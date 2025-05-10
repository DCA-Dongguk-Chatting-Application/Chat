import React, { useContext, useState } from "react";


function MateList({id, name, image, loading}) {
   
    const serverUrl = 'http://59.24.237.51:8080';
  


    return (
    <>
    <div class = "friend-single-section">
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
        <div class = "right-banner-freind-name-container">{name}</div>
        <div class = "right-banner-online-indicator"></div>

    </div>
    
    

    </> 
    );
}

export default MateList;
