import React, { useEffect, useState } from "react";
import axios from "axios";


// accessToken을 받아서 유저 정보를 반환하는 함수
export const GetUserProfile = async (accessToken) => {
    try {
        const response = await axios.get("/api/profile", {
            headers: {
                Authorization: `Bearer ${accessToken}`,
            },
        });
        return response.data; // 유저 정보 반환
    } catch (error) {
        console.error("유저 프로필 불러오기 실패", error);
        throw error; // 에러를 호출한 쪽에서 처리할 수 있도록 throw
    }
};

export default GetUserProfile;