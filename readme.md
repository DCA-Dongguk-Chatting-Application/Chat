# Chat
# 💬 Chat Application

React, Spring Boot, Docker, MySQL, MongoDB를 활용한 실시간 채팅 서비스

---

## 📌 소개

이 프로젝트는 실시간 채팅 기능을 중심으로 하는 웹 애플리케이션입니다.  
프론트엔드는 **React**, 백엔드는 **Spring Boot**, 데이터베이스는 **MySQL**과 **MongoDB**를 상황에 따라 적절히 분리하여 사용합니다.  
**Docker**를 이용해 전체 애플리케이션을 컨테이너화하여 손쉽게 배포하고 관리할 수 있습니다.

---

## 🧱 기술 스택

| 역할 | 기술 |
|------|------|
| 프론트엔드 | React, HTML, CSS, JavaScript (TypeScript 선택 가능) |
| 백엔드 | Spring Boot, WebSocket |
| 데이터베이스 | MySQL (회원, 메시지 메타 정보 등)<br>MongoDB (채팅 로그 등 비정형 데이터) |
| 배포 및 인프라 | Docker, Docker Compose |
| 기타 | REST API, WebSocket 통신, JWT 인증 등 |

---


## ⚙️ 주요 기능

- ✅ 회원가입 / 로그인 (JWT 인증)
- ✅ 실시간 채팅 (WebSocket 기반)
- ✅ 1:1 채팅 및 그룹 채팅
- ✅ 채팅방 생성, 입장, 퇴장 기능
- ✅ 채팅 로그 저장 (MongoDB)
- ✅ 사용자 정보 및 채팅방 관리 (MySQL)
- ✅ Docker를 통한 로컬 및 클라우드 배포 지원