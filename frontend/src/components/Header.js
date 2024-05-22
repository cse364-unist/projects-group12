import React from 'react';
import { Link } from 'react-router-dom';
import '../index.css';

const Header = ({ isLoggedIn, onLogout }) => {
  return (
    <header className="header">
      <nav>
        <ul className="nav-links">
          {/* <li><Link to="/">Home</Link></li> */}
          {isLoggedIn && <li><Link to="/main">Main</Link></li>}
        </ul>
      </nav>
      {isLoggedIn && (
        <button className="logout-button" onClick={onLogout}>Logout</button>
      )}
    </header>
  );
};

export default Header;
