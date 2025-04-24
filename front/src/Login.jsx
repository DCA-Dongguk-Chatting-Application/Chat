import React , { useState, useRef, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom"; 
import "./style.css";


export const Login = () => {
    const navigate = useNavigate();
    const [isRight, setIsRight] = useState(false);
    const [idLogin, setIdLogin] = useState("");
    const [pwLogin, setPwLogin] = useState("");
    const [idRegister, setIdRegister] = useState("");
    const [pwRegister, setPwRegister] = useState("");
    const [nameRegister, setNameRegister] = useState("");
    
    
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
                <button class = "login-confirm" onClick={goToMain}>확인</button>
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
                     onChange={(e) => setNameRegister(e.target.value)}>
                </input>
                <button class = "register-confirm" onClick={goToLogin}>확인</button>
            </div>
        
        
        </div>
    </div>
    );
};