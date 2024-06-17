import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../api/axiosConfig';
import './mainpage.css';

const UnlockedMovies = () => {
    const [unlocked, setUnlocked] = useState([]);
    const navigate = useNavigate();
    const [notification, setNotification] = useState('');

    useEffect(() => {
        const fetchUnlockedMovies = async () => {
            const username = localStorage.getItem('username');
            if (username) {
                try {
                    const response = await axios.get(`/users/${username}/unlockedMoviesList`); 
                    setUnlocked(response.data);
                } catch (error) {
                    console.log("SOME ERROR WHILE FETCHING UNLOCKED MOVIES LIST", error);
                }
            } else {
                navigate('/auth');
            }
        };
        fetchUnlockedMovies();
    }, [navigate]);
    

    const handleWatch = (movieId) => {
        navigate(`/movie/${movieId}`);
    };

    const handleWL = async (movie) => {
        const username = localStorage.getItem('username');
        if (username) {
            try {
                await axios.post(`/users/${username}/wishList/add?movieId=${movie.movieId}`);
                console.log("SUCCESS adding to WL");
                setNotification('Movie successfully added to wishlist!');
                setTimeout(() => {
                    setNotification('');
                }, 3000);
            } catch (error) {
                console.log("SOMETHING WENT WRONG WHILE ADDING movie TO WL", error);
            }
        } else {
            navigate('/auth');
        }
    };

    return (
        <div className="unlockedmovies-container">
            <h1>Unlocked Movies</h1>
            {notification && <div className="notification">{notification}</div>}
            <div className="unlockedmovies-grid">
                {unlocked.map(movie => (
                    <div key={movie.movieId} className="unlockedmovies-card">
                        <img src={movie.posterLink} alt={movie.title} className="unlockedmovies-image" />
                        <h2>{movie.title}</h2>
                        <button onClick={() => handleWatch(movie.movieId)} className="watch1-button">Watch</button>
                        <button onClick={() => handleWL(movie)}>+ Add to WL</button>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default UnlockedMovies;
