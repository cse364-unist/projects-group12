import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './mainpage.css';

const MovieCard = ({ movie }) => {
  const navigate = useNavigate();
  const [notification, setNotification] = useState('');

  const handleClick = () => {
    navigate(`/movie/${movie.movieId}`);
  };

  const handleAddToWishlist = () => {
    let wishlist = JSON.parse(localStorage.getItem('wishlist')) || [];
    if (!wishlist.some(m => m.movieId === movie.movieId)) {
      wishlist.push(movie);
      localStorage.setItem('wishlist', JSON.stringify(wishlist));
      setNotification('Movie successfully added to wishlist!');
      setTimeout(() => {
        setNotification('');
      }, 3000); 
    } else {
      setNotification('Movie is already in the wishlist.');
      setTimeout(() => {
        setNotification('');
      }, 3000);
    }
  };

  return (
    <div className="movie-card">
      <img src={movie.posterLink} alt={movie.title} className="movie-image" />
      <h2>{movie.title}</h2>
      <p>{movie.genres.join(', ')}</p>
      <button onClick={handleClick}>View Details</button>
      <button onClick={handleAddToWishlist}>Add to Wishlist</button>
      {notification && <p className="notification">{notification}</p>}
    </div>
  );
};

export default MovieCard;
