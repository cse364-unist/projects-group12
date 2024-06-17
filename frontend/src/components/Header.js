import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import '../index.css';

const Header = ({ isLoggedIn, onLogout, curScore, username }) => {
  const navigate = useNavigate();

  return (
    <header className="header">
      <nav className="nav-container">
        <li className='main-page'><Link to="/main">rest2night</Link></li>
        <ul className="nav-buttons">
          {isLoggedIn && <li><button className="nav-button" onClick={() => navigate('/wishlist')}>Wishlist</button></li>}
          {isLoggedIn && (
            <li className="header-buttons">
              <Link to="/quiz" className="quiz-button">Take Quiz</Link>
              <button className="unlocked-movies" onClick={() => navigate('/unlockedMovies')}>Unlocked Movies</button>
              <button className="logout-button" onClick={onLogout}>Logout</button>
              <button className='nav-button'>{username} {curScore} c</button>
            </li>
          )}
          {!isLoggedIn && (
            <li className="header-buttons">
              <button className="logout-button" onClick={() => navigate('/auth')}>Login</button>
            </li>
          )}
        </ul>
      </nav>
    </header>
  );
};

export default Header;
