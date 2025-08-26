import React, { useState } from 'react';
import axios from "../api/axiosConfig"

const LoginForm = ({ onLogin }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  // const handleSubmit = async (e) => {
  //   if (username.trim() === '' || password.trim() === '') {
  //       alert('Please fill in all fields before registering.');
  //       return;
  //     }
  //     e.preventDefault();
  //     try {
  //       await axios.post('/users/login', { username, password });
  //       onLogin(username);
  //     } catch (error) {
  //       alert("Login failed")
  //       setPassword('');
  //       console.error('Login failed', error);
  //       return;
  //     }
  // };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (username.trim() === '' || password.trim() === '') {
      alert('Please fill in all fields.');
      return;
    }
    try {
      const response = await axios.post('/users/login', { username, password });
      const token = response.data.token;
      if (token) {
        localStorage.setItem('jwtToken', token);
        onLogin(username); // Or pass token here, as needed
      }
    } catch (error) {
      alert("Login failed");
      setPassword('');
      console.error('Login failed', error);
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
