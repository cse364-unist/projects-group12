import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import LoginForm from './components/LoginForm';
import RegisterForm from './components/RegisterForm';
import MainPage from './components/MainPage';
import Header from './components/Header';
import QuizPage from './components/QuizPage'; // Import the new QuizPage component
import MoviePage from './components/MoviePage';
import WishlistPage from './components/WishListPage';
import axios from "./api/axiosConfig"

import './index.css';

const App = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [score, setScore] = useState(localStorage.getItem('score'));
  const [username, setUsername] = useState(localStorage.getItem('username'));

  useEffect(() => {
    const loggedInStatus = localStorage.getItem('isLoggedIn') === 'true';
    setIsLoggedIn(loggedInStatus);
  }, []);

  const handleLogin = (data) => {
    console.log("Login data:", data);
    setIsLoggedIn(true);
    localStorage.setItem('username', data);
    setTheScore(data);
    setUsername(data);
    localStorage.setItem('isLoggedIn', 'true');
  };

  const handleRegister = (data) => {
    console.log("Register data:", data);
    localStorage.setItem('username', data);
    setTheScore(data);
    setUsername(data);
    setIsLoggedIn(true);
    localStorage.setItem('isLoggedIn', 'true');
  };

  const handleLogout = () => {
    setIsLoggedIn(false);
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('username');
    localStorage.removeItem('score');
  };

  const setTheScore = async (username) => {
    try{
      const response = await axios.get(`/users/${username}/getPoints`);
      console.log("CURRENT SCORE: ", response.data);
      localStorage.setItem('score', response.data);
      setScore(response.data);
    } catch(error){
      console.log("SOMETHING WRONG WHEN FETCHING SCORE", error);
    }
  }

  const onBuy = () => {
    setScore(score - 10);
  }

  return (
    <Router>
      <Header isLoggedIn={isLoggedIn} onLogout={handleLogout} curScore={score} username={username}/>
      <Routes>
        <Route path="/main" element={<MainPage updateScore={onBuy}/>} />
        <Route path="/quiz" element={<QuizPage />} /> {/* Add route for QuizPage */}
        <Route 
          path="/auth" 
          element={isLoggedIn ? <Navigate to="/main" /> : (
            <div className="App">
              <h1>Welcome Bratishka</h1>
              {isLogin ? (
                <LoginForm onLogin={handleLogin} />
              ) : (
                <RegisterForm onRegister={handleRegister} />
              )}
              <button className="toggle-button" onClick={() => setIsLogin(!isLogin)}>
                {isLogin ? "Don't have an account? Register" : "Already have an account? Login"}
              </button>
            </div>
          )} 
        />
        <Route path="/movie/:movieId" element={<MoviePage />} />
         <Route path="/wishlist" element={<WishlistPage />} />

      </Routes>
    </Router>
  );
};

export default App;
