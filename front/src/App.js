import React, { useEffect, useState, useRef  }  from 'react';

import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Login } from "./Login";
import { Main } from "./Main";
import { Setting } from "./Setting";

function App() {

  

  return (
    
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/main" element = {<Main/>}/> 
        <Route path="/setting" element = {<Setting/>}/> 
      </Routes>
    </Router>
    
  );
}
//
export default App;
