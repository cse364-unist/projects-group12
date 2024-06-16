
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './Wishlist.css';
import axios from '../api/axiosConfig';

const Wishlist = () => {
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


  const handleWatch = (movieId) => {
    navigate(`/movie/${movieId}`);
  };

  const handleDelete = async (movieId) => {
    const updatedWishlist = wishlist.filter(movie => movie.movieId !== movieId);
    setWishlist(updatedWishlist);
    const username = localStorage.getItem('username');
    try{
      await axios.post(`/users/${username}/wishList/delete?movieId=${movieId}`);
      console.log("SUCCESS deleting from WL");
    } catch(error){
      console.log("SOMETHING WENT WRONG WHILE deleting movie from WL", error);
    }
  
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
