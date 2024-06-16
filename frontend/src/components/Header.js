// Header.js
import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import '../index.css';

const Header = ({ isLoggedIn, onLogout }) => {
  const navigate = useNavigate();

  return (
    <header className="header">
      <nav>
        <ul className="nav-links">
          <li><Link to="/main">Main</Link></li>
          {isLoggedIn && <li><button className="nav-button" onClick={() => navigate('/wishlist')}>Wishlist</button></li>}
        </ul>
      </nav>
      <Link to="/quiz" className="quiz-button">Take Quiz</Link>
      {isLoggedIn ? (
        <button className="logout-button" onClick={onLogout}>Logout</button>
      ) : (
        <button className="logout-button" onClick={() => navigate('/auth')}>Login</button>
      )}
    </header>
  );
};

export default Header;
