// src/components/Wishlist.js
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './Wishlist.css';

const Wishlist = () => {
  const [wishlist, setWishlist] = useState([]);

  useEffect(() => {
    // Fetch wishlist from local storage or server
    const storedWishlist = JSON.parse(localStorage.getItem('wishlist')) || [];
    setWishlist(storedWishlist);
  }, []);

  const navigate = useNavigate();

  const handleWatch = (movieId) => {
    navigate(`/movie/${movieId}`);
  };

  const handleDelete = (movieId) => {
    const updatedWishlist = wishlist.filter(movie => movie.movieId !== movieId);
    setWishlist(updatedWishlist);
    localStorage.setItem('wishlist', JSON.stringify(updatedWishlist));
  };

  return (
    <div className="wishlist-container">
      <h1>Wishlist</h1>
      <div className="wishlist-grid">
        {wishlist.map(movie => (
          <div key={movie.movieId} className="wishlist-card">
            <img src={movie.posterLink} alt={movie.title} className="wishlist-image" />
            <h2>{movie.title}</h2>
            <button onClick={() => handleWatch(movie.movieId)} className="watch1-button">Watch</button>
            <button onClick={() => handleDelete(movie.movieId)} className="delete-button">Delete</button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Wishlist;
