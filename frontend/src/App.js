import React, { useState } from 'react';
import LoginForm from './components/LoginForm';
import RegisterForm from './components/RegisterForm';
import './index.css';

const App = () => {
  const [isLogin, setIsLogin] = useState(true);

  const handleLogin = (data) => {
    console.log("Login data:", data);
    // Add your login logic here
  };

  const handleRegister = (data) => {
    console.log("Register data:", data);
    // Add your registration logic here
  };

  return (
    <div className="App">
      <h1>Welcome Bratishka</h1>
      {isLogin ? (
        <LoginForm onLogin={handleLogin} />
      ) : (
        <RegisterForm onRegister={handleRegister} />
      )}
      <button className="toggle-button" onClick={() => setIsLogin(!isLogin)}>
        {isLogin ? "go to sign up" : "go to login"}
      </button>
    </div>
  );
};

export default App;
