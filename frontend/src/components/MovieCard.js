import React from 'react';
import { useNavigate } from 'react-router-dom';
import './MoviePage.css'; 

const MovieCard = ({ movie }) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/movie/${movie.id}`);
  };

  return (
    <div className="movie-card">
      <img src={movie.image} alt={movie.title} className="movie-image" />
      <h2>{movie.title}</h2>
      <p>{movie.genres.join(', ')}</p>
      <button onClick={handleClick}>View Details</button>
    </div>
  );
};

export default MovieCard;
