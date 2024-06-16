import React, { useState } from 'react';
import axios from "../api/axiosConfig"

const LoginForm = ({ onLogin }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async (e) => {
    if (username.trim() === '' || password.trim() === '') {
        alert('Please fill in all fields before registering.');
        return;
      }
      e.preventDefault();
      try {
        await axios.post('/users/login', { username, password });
        onLogin(username);
        localStorage.setItem('username', username);
      } catch (error) {
        alert("Login failed")
        setPassword('');
        console.error('Login failed', error);
        return;
      }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Login</h2>
      <div>
        <label>Username:</label>
        <input
          type="username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />
      </div>
      <div>
        <label>Password:</label>
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
      </div>
      <button type="submit">Login</button>
    </form>
  );
};

export default LoginForm;
