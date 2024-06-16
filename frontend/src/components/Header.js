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
      {isLoggedIn && (
        <div className="header-buttons">
          <Link to="/quiz" className="quiz-button">Take Quiz</Link>
          <button className="logout-button" onClick={onLogout}>Logout</button>
        </div>
      )}
      {!isLoggedIn && (
        <div className="header-buttons">
          <button className="logout-button" onClick={() => navigate('/auth')}>Login</button>
        </div>
      )}
    </header>
  );
};

export default Header;
