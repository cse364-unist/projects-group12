import React from 'react';

const MainPage = ({ onLogout }) => {
  return (
    <div className="main-container">
      <h1>Welcome to the Movie App</h1>
      <ul className="movie-list">
        <li>Movie 1</li>
        <li>Movie 2</li>
        <li>Movie 3</li>
        <li>Movie 4</li>
      </ul>
      <button className="logout-button" onClick={onLogout}>Logout</button>
    </div>
  );
};

export default MainPage;
