import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './mainpage.css';
import axios from "../api/axiosConfig";

const MovieCard = ({ movie }) => {

  const navigate = useNavigate();
  const [notification, setNotification] = useState('');

  const handleClick = () => {
    navigate(`/movie/${movie.movieId}`);
  };

  // const handleAddToWishlist = () => {
  //   let wishlist = JSON.parse(localStorage.getItem('wishlist')) || [];
  //   if (!wishlist.some(m => m.movieId === movie.movieId)) {
  //     wishlist.push(movie);
  //     localStorage.setItem('wishlist', JSON.stringify(wishlist));
  //     setNotification('Movie successfully added to wishlist!');
  //     setTimeout(() => {
  //       setNotification('');
  //     }, 3000); 
  //   } else {
  //     setNotification('Movie is already in the wishlist.');
  //     setTimeout(() => {
  //       setNotification('');
  //     }, 3000);
  //   }
  // };

  const handleWL = async () => {
    const username = localStorage.getItem('username');
    if(username){
      try{
        await axios.post(`/users/${username}/wishList/add?movieId=${movie.movieId}`);
        console.log("SUCCESS adding to WL");
        setNotification('Movie successfully added to wishlist!');
        setTimeout(() => {
          setNotification('');
        }, 3000); 
      } catch(error){
        console.log("SOMETHING WENT WRONG WHILE ADDING movie TO WL", error);
      }
    } else {
      navigate('/auth');
    }
  }

  return (
    <div className="movie-card">
      <img src={movie.posterLink} alt={movie.title} className="movie-image" />
      <h2>{movie.title}</h2>
      <p>{movie.genres.join(', ')}</p>
      <div className='buttonc'>
        <button onClick={handleWL}>+ Add to WL</button>
        <button onClick={handleClick}>View Details</button>
      </div>
    </div>
  );
};

export default MovieCard;
