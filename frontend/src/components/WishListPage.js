import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './Wishlist.css';
import axios from '../api/axiosConfig';

const Wishlist = ({ movie, unlocked, onBuy }) => {
  const [wishlist, setWishlist] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchWishlist = async () => {
      const username = localStorage.getItem('username');
      if (username) {
        try {
          const response = await axios.get(`/users/${username}/wishList`);
          setWishlist(response.data);
        } catch (error) {
          console.log("SOME ERROR WHILE FETCHING WISH LIST", error);
        }
      } else {
        navigate('/auth');
      }
    };
    fetchWishlist();
  }, [navigate]);

  const handleDelete = async (movieId) => {
    const updatedWishlist = wishlist.filter(movie => movie.movieId !== movieId);
    setWishlist(updatedWishlist);
    const username = localStorage.getItem('username');
    try {
      await axios.post(`/users/${username}/wishList/delete?movieId=${movieId}`);
      console.log("SUCCESS deleting from WL");
    } catch (error) {
      console.log("SOMETHING WENT WRONG WHILE deleting movie from WL", error);
    }
  };

  const handleBuy = async (movieId) => {
    const username = localStorage.getItem('username');
    if (username) {
      let curScore = localStorage.getItem('score');
      if (curScore < 10) {
        alert("You can't buy this movie, try to solve More Quizzes!");
        return;
      }
      try {
        await axios.post(`/users/${username}/unlockedMovies/add?movieId=${movieId}`);
        console.log("SUCCESS unlocking movie");
        localStorage.setItem('score', curScore - 10);
        setWishlist(prevWishlist => prevWishlist.map(movie => 
          movie.movieId === movieId ? { ...movie, unlocked: true } : movie
        ));
        onBuy();
      } catch (error) {
        console.log("SOMETHING WENT WRONG WHILE unlocking movie", error);
      }
    } else {
      navigate('/auth');
    }
  };

  const handleClick = (movieId) => {
    navigate(`/movie/${movieId}`);
  };

  return (
    <div className="wishlist-container">
      <h1>Wishlist</h1>
      <div className="wishlist-grid">
        {wishlist.map(movie => (
          <div key={movie.movieId} className="wishlist-card">
            <img src={movie.posterLink} alt={movie.title} className="wishlist-image" />
            <h2>{movie.title}</h2>
            {!movie.unlocked ? (
              <button onClick={() => handleBuy(movie.movieId)}>10c BUY</button>
            ) : (
              <button onClick={() => handleClick(movie.movieId)}>View Details</button>
            )}
            <button onClick={() => handleDelete(movie.movieId)} className="delete-button">Delete</button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Wishlist;
