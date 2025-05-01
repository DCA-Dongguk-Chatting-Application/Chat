// src/component/WebSocketConnector.jsx
import { useEffect } from "react";
import { Client } from "@stomp/stompjs";

const WebSocketConnector = ({ token }) => {
  useEffect(() => {
    const client = new Client({
      brokerURL: "ws://59.24.237.51:8080/portfolio", // 실제 ws 주소로 변경 필요
      connectHeaders: {
        token: token,
      },
      onConnect: () => {
        console.log("✅ STOMP 연결 성공");
      },
      onStompError: (frame) => {
        console.error("❌ STOMP 에러 발생", frame);
      },
    });

    client.activate();

    return () => {
      client.deactivate();
      console.log("🔌 STOMP 연결 해제");
    };
  }, [token]);

  return null;
};

export default WebSocketConnector;
