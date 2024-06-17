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
  const [username, setUsername] = useState('');
  const [curRate, setCurRate] = useState();

  const navigate = useNavigate();

  useEffect(() => {
    const username = localStorage.getItem('username');
      
    if(username){
      setUsername(username);
      axios.get(`/users/${username}/unlockedMovies`)
        .then(respponse => {
          const unlocked_movies = respponse.data || [];
          console.log(unlocked_movies, movieId)
          if(!unlocked_movies.includes(Number(movieId))) {
            navigate('/main');
            alert('NICE TRY! Buy it first');
          }
        })
        .catch(error => {
          console.log('some error while taking UNLOCKED movies', error)
        });
      } else {
        navigate('/auth')
      }

    axios.get(`/movies/${movieId}`)
      .then(response => {
        setMovie(response.data);
        console.log('COMMENTS: ', response.data.comments);
        setComments(response.data.comments || []);
        setCurRate(response.data.rate);
      })
      .catch(error => {
        console.error('There was an error fetching the movie!', error);
      });
  }, [movieId]);

  useEffect(() => {
    const username = localStorage.getItem('username');
  }, [])

  const handleCommentSubmit = () => {
    if (rating === 0) {
      setError('Please select a rating before submitting your comment.');
      return;
    }
    const comment = {
      username: username,
      body: newComment,
      timestamp: new Date(),
      rate: rating,
    };
    setCurRate([(curRate[0]*curRate[1] + rating)/(curRate[1] + 1), curRate[1] + 1]);
    submitComment(comment);
    setComments([...comments, comment]);
    setNewComment('');
    setRating(0);
    setHoverRating(0);
    setError('');
  };

  const submitComment = async (comment) =>{
    try{
      await axios.post(`/movies/${movieId}/addComment`, {
        username: username,
        rate: comment.rate,
        body: comment.body,
      });
      console.log("SUCCES ADDING COMMENT")

    } catch(error){
      console.log("ERROR WHILE ADDING COMMENT", error);
    }
  }

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
          <p>Rating: ⭐ {curRate[0].toFixed(1)}/5</p>
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
        <button onClick={handleCommentSubmit} className="submit-button" style={{marginTop: '5px'}}>Submit</button>
        <div className="comments-list">
          {comments.map((comment, index) => (
            <div key={index} className="comment">
              <h4 style={{margin: '0px'}}>{comment.username} {comment.timestamp.toString().split('T')[0]}</h4>
              <p>{comment.body}</p>
              <p>Rating:  {'⭐'.repeat(comment.rate)}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default MoviePage;
