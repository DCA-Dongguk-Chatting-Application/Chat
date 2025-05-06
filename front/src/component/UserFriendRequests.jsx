import React, { useEffect, useState } from "react";
import axios from "axios";


//친구요청 목록을 불러온다.
export const GetRequest = async () => {
    const userId = localStorage.getItem("userId");
    try {
        const response = await axios.get(`/api/friends/requests/${userId}`, {
        });
        return response.data; // 유저 정보 반환
    } catch (error) {
        console.error("친구신청요청 불러오기 실패", error);
        throw error; // 에러를 호출한 쪽에서 처리할 수 있도록 throw
    }
};

export default GetRequest;