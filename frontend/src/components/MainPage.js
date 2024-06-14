import React, { useEffect, useState } from 'react';
import axios from "../api/axiosConfig"
import MovieCard from './MovieCard';
import './MoviePage.css'; 

const MainPage = () => {
  const [movies, setMovies] = useState([]);

  useEffect(() => {
    axios.get('/movies/')
      .then(response => {
        setMovies(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the movies!', error);
      });
  }, []);

  return (
    <div className="main-container">
      <h1>Lets rest tonight</h1>
      <div className="movies-grid">
        {movies.map(movie => (
          <MovieCard key={movie.movieId} movie={movie} />
        ))}
      </div>
    </div>
  );
};

export default MainPage;
