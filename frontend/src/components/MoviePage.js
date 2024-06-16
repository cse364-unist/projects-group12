// MoviePage.js
import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from '../api/axiosConfig';
import './mainpage.css';

const MoviePage = () => {
  const { movieId } = useParams();
  const [movie, setMovie] = useState(null);
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState('');
  const [rating, setRating] = useState(0);
  const [hoverRating, setHoverRating] = useState(0);
  const [error, setError] = useState('');
  const [notification, setNotification] = useState('');

  const navigate = useNavigate();

  useEffect(() => {
    axios.get(`/movies/${movieId}`)
      .then(response => {
        setMovie(response.data);
        setComments(response.data.comments || []);
      })
      .catch(error => {
        console.error('There was an error fetching the movie!', error);
      });
  }, [movieId]);

  const handleCommentSubmit = () => {
    if (rating === 0) {
      setError('Please select a rating before submitting your comment.');
      return;
    }
    const comment = {
      text: newComment,
      date: new Date().toISOString().split('T')[0],
      time: new Date().toLocaleTimeString(),
      rating: rating,
      
    };
    setComments([...comments, comment]);
    setNewComment('');
    setRating(0);
    setHoverRating(0);
    setError('');
  };

  const handleAddToWishlist = async () => {
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
  };
  
  if (!movie) return <div>Loading...</div>;

  return (
    <div className="movie-page">
      <div className="movie-details">
        <img src={movie.posterLink} alt={movie.title} className="movie-poster" />
        <div className="details">
          <h1>{movie.title}</h1>
          <p>Genres: {movie.genres.join(', ')}</p>
          <p>Rating: ⭐ {movie.rate[0].toFixed(1)}/5</p>
          <button className="watch-button">Watch</button>
          <button className="wishlist-button" onClick={handleAddToWishlist}>Add to Wishlist</button>
          {notification && <p className="notification">{notification}</p>}
        </div>
      </div>
      <div className="comments-section">
        <h2>Comments</h2>
        <textarea
          value={newComment}
          onChange={(e) => setNewComment(e.target.value)}
          placeholder="Add your Comment"
        />
        <div className="rating">
          <p>Rate:</p>
          {[1, 2, 3, 4, 5].map(star => (
            <span
              key={star}
              onClick={() => setRating(star)}
              onMouseEnter={() => setHoverRating(star)}
              onMouseLeave={() => setHoverRating(0)}
              className="star"
              style={{
                color: star <= (hoverRating || rating) ? 'gold' : 'gray',
                cursor: 'pointer'
              }}
            >
              {star <= (hoverRating || rating) ? '★' : '☆'}
            </span>
          ))}
        </div>
        {error && <p className="error-message">{error}</p>}
        <button onClick={handleCommentSubmit} className="submit-button">Submit</button>
        <div className="comments-list">
          {comments.map((comment, index) => (
            <div key={index} className="comment">
              <p>{comment.time} {comment.date}</p>
              <p>{comment.text}</p>
              <p>Rating:  {'⭐'.repeat(comment.rating)}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default MoviePage;
