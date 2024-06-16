import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from "../api/axiosConfig";
import MovieCard from './MovieCard';
import './mainpage.css';
import SecondHeader from './SecondHeader';
import LoadingIcon from './LoadingIcon';

const MainPage = () => {
  const navigate = useNavigate();

  const [movies, setMovies] = useState([]);
  const [curRec, setCurRec] = useState({});
  const [rec, setRec] = useState(false);
  const [loading, setLoading] = useState(false); 

  useEffect(() => {
    setLoading(true);
    axios.get('/movies/some')
      .then(response => {
        setMovies(response.data);
        setLoading(false);
      })
      .catch(error => {
        console.error('There was an error fetching the movies!', error);
        setLoading(false);
      });
  }, []);

  const recommendTypeOne = async (data) => {
    setLoading(true);
    try {
      const response = await axios.post('/recommendation/type1', {
        gender: data.gender,
        age: data.age,
        occupation: data.occupation,
        genre: data.genre
      });
      setMovies(response.data);
      setCurRec(data);
      setRec(true);
      setLoading(false);
    } catch (error) {
      console.log("SOMETHING WENT WRONG WHEN RECOM: ", error);
      setLoading(false);
    }
    console.log(data);
  }

  const recommendTypeTwo = async (data) => {
    setLoading(true);
    try {
      const response = await axios.get(`/recommendation/type2/${data}`);
      setMovies(response.data);
      // setCurRec(data);
      // setRec(true);
      setLoading(false);
    } catch (error) {
      console.log("SOMETHING WENT WRONG WHEN RECOM: ", error);
      setLoading(false);
    }
    console.log(data);
  }

  const getRandom = async () => {
    setLoading(true);
    try {
      const response = await axios.get(`/movies/some`);
      setMovies(response.data);
      setLoading(false);
    } catch (error) {
      console.log("SOMETHING WENT WRONG WHEN RECOM: ", error);
      setLoading(false);
    }
  }

  return (
    <div>
      <SecondHeader recommend1={recommendTypeOne} recommend2={recommendTypeTwo} onRandom={getRandom}/>
      <div className="main-container">
        <h1>HEY HERE ARE SOME MOVIES</h1>
        {rec && <h2>Current recommendation based on: {curRec.gender} {curRec.age} {curRec.occupation} {curRec.genre}</h2>}
        {loading ? (
          <LoadingIcon /> 
        ) : (
          <div className="movies-grid">
            {movies.map(movie => (
              <MovieCard key={movie.movieId} movie={movie} />
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default MainPage;