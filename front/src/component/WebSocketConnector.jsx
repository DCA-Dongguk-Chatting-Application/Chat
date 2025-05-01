// src/component/WebSocketConnector.jsx
import { useEffect } from "react";
import { Client } from "@stomp/stompjs";

const WebSocketConnector = ({ token, roomId, setChatLog, setClient }) => {
  useEffect(() => {
    const client = new Client({
      brokerURL: "ws://59.24.237.51:8080/portfolio", 
      connectHeaders: {
        token: token,
      },
      onConnect: () => {
        console.log("✅ STOMP 연결 성공");
        console.log('웹소켓 연결됨');
        if (roomId) {
          client.subscribe(`/topic/chatroom/${roomId}`, (message) => {
            const newMessage = JSON.parse(message.body);
            setChatLog(prev => [...prev, newMessage]);
          });
        }
      },
      onStompError: (frame) => {
        console.error("❌ STOMP 에러 발생", frame);
      },
    });
    setClient(client);
    client.activate();

    return () => {
      client.deactivate();
      console.log("🔌 STOMP 연결 해제");
    };
  }, [token, roomId, setChatLog]);  // roomId와 setChatLog가 변경될 때마다 구독을 새로 설정

  return null;
};

export default WebSocketConnector;
