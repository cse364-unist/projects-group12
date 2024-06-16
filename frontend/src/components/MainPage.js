import React, { useEffect, useState } from 'react';
import axios from "../api/axiosConfig"
import MovieCard from './MovieCard';
import './mainpage.css'; 
import SecondHeader from './SecondHeader';

const MainPage = () => {
  const [movies, setMovies] = useState([]);
  const [curRec, setCurRec] = useState({});
  const [rec, setRec] = useState(false);

  useEffect(() => {
    axios.get('/movies/some')
      .then(response => {
        setMovies(response.data);
      })
      .catch(error => {
        console.error('There was an error fetching the movies!', error);
      });
  }, []);

  const recommendTypeOne = async (data) => {
    
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
    } catch(error){
      console.log("SOMETHING WENT WRONG WHEN RECOM: ", error);
    }
    console.log(data);
  }


  return (
    <div>
      <SecondHeader recommend={recommendTypeOne}/>
      <div className="main-container">
        {rec && <h1>current recommendation based on: {curRec.gender} {curRec.age} {curRec.occupation} {curRec.genre}</h1>}
        <div className="movies-grid">
          {movies.map(movie => (
            <MovieCard key={movie.movieId} movie={movie} />
          ))}
        </div>
      </div>
    </div>
  );
};

export default MainPage;
