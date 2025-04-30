import React , { useState, useRef, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom"; 
import "./style.css";
import axios from 'axios'


export const Login = () => {
    const navigate = useNavigate();
    const [isRight, setIsRight] = useState(false);
    const [idLogin, setIdLogin] = useState("");
    const [pwLogin, setPwLogin] = useState("");
    const [idRegister, setIdRegister] = useState("");
    const [pwRegister, setPwRegister] = useState("");
    const [nameRegister, setNameRegister] = useState("");
    const [phoneRegister, setPhoneRegister] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    

    const handleRegister = async () => {//회원가입
        try {
            const response = await axios.post("/api/user", {
            
            username: idRegister,
            email: nameRegister,
            password: pwRegister,
            phoneNumber : phoneRegister
            });
            alert(idRegister + "로 회원가입");
            console.log(response.data);
            navigate("/");
        } catch (error) {
          setErrorMessage(error.response?.data || "회원가입 실패");
        }
      };

    const handleLogin = async () => {//로그인
        try {
            const response = await axios.post("/api/auth/login", {
                userLoginId: idLogin,
                userPassword: pwLogin,
            });
            const accessToken = response.data.accessToken;
            if (response.status === 200 && accessToken) {
                
                
                alert("로그인 성공!");
                navigate("/main");
            }
            
        } catch (error) {
            setErrorMessage(error.response?.data || "로그인 실패");
        }
    };
    
    const togglePanel = () => {
        setIsRight(prev => !prev);
    };

    const goToMain = () => {
       navigate("/main"); 
    };

    const goToLogin = () => {
        navigate("/"); 
     };
    
  

//**본 메인 화면**//
    return (
    <div class = "background">
        
        <div class = "overall-container">
            <div class = "login-panel" style={{
                left: isRight ? '25%' : '75%',
            }}>
                <h1 class = "login-panel-text">Welcome!</h1>
                <button class = "login-panel-switch-button" onClick = {togglePanel}>
                {isRight ? '로그인' : '회원가입'}
                </button>
            </div>
        
            <div class = "login-zone">
                <h3 class = "login-text">로그인</h3>
                <input class = "login-id-textbox"
                    placeholder = "아이디"
                    onChange={(e) => setIdLogin(e.target.value)}></input>
                <input class = "login-pw-textbox" 
                    placeholder = "PW"
                    onChange={(e) => setPwLogin(e.target.value)}></input>
                <button class = "login-confirm" onClick={handleLogin}>확인</button>
             </div>

            <div class = "register-zone">
                <h3 class = "register-text">회원가입</h3>
                <input class = "register-id-textbox" 
                    placeholder = "아이디"
                    onChange={(e) => setIdRegister(e.target.value)}></input>
                <input class = "register-pw-textbox" 
                    placeholder = "PW"
                    onChange={(e) => setPwRegister(e.target.value)}></input>
                <input class = "register-name-textbox"
                    placeholder = "이름"
                     onChange={(e) => setNameRegister(e.target.value)}></input>
                <input class = "register-phone-textbox"
                    placeholder = "전화번호"
                     onChange={(e) => setPhoneRegister(e.target.value)}></input>
                
                <button class = "register-confirm" onClick={handleRegister}>확인</button>
            </div>
        
        
        </div>
    </div>
    );
};