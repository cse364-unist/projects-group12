import React, { useState, useRef, useEffect } from 'react';
import './mainpage.css';
import "./secondHeader.css";

const genres = ["Action", "Comedy", "Drama", "Fantasy", "Horror", "Mystery", "Romance", "Thriller"];

const occupationList = [
    "other or not specified",
    "academic/educator",
    "artist",
    "clerical/admin",
    "college/grad student",
    "customer service",
    "doctor/health care",
    "executive/managerial",
    "farmer",
    "homemaker",
    "K-12 student",
    "lawyer",
    "programmer",
    "retired",
    "sales/marketing",
    "scientist",
    "self-employed",
    "technician/engineer",
    "tradesman/craftsman",
    "unemployed",
    "writer"
];

const ageList = [
    "0-17",
    "18-24",
    "25-34",
    "35-44",
    "45-49",
    "50-55",
    "56+"
]

const ageMap = {
    "": "",
    "0-17" : "1",
    "18-24": "18",
    "25-34": "25",
    "35-44": "35",
    "45-49": "45",
    "50-55": "50",
    "56+" : "56"
}

const genders = ["Male", "Female"];

const genMap = {"": "", "Male" : "M", "Female" : "F"}


const SecondHeader = ({recommend}) => {
  const [dropdownVisibleGenres, setDropdownVisibleGenres] = useState(false);
  const [dropdownVisibleOccup, setDropdownVisibleOccup] = useState(false);
  const [dropdownVisibleGender, setDropdownVisibleGender] = useState(false);
  const [dropdownVisibleAge, setDropdownVisibleAge] = useState(false);

  const [selectedGenres, setSelectedGenres] = useState([]);
  const [occup, setOccup] = useState('');
  const [gender, setGender] = useState('');
  const [age, setAge] = useState('');
  
  const dropdownRef = useRef(null);

  const toggleDropdownGenres = (e) => {
    e.preventDefault();
    setDropdownVisibleGenres(!dropdownVisibleGenres);
  };

  const toggleDropdownOccup = (e) => {
    e.preventDefault();
    setDropdownVisibleOccup(!dropdownVisibleOccup);
  }

  const toggleDropdownGender = (e) => {
    e.preventDefault();
    setDropdownVisibleGender(!dropdownVisibleGender);
  }

  const toggleDropdownAge = (e) => {
    e.preventDefault();
    setDropdownVisibleAge(!dropdownVisibleAge);
  }

  const handleCheckboxChange = (option) => {
    setSelectedGenres((prevSelectedOptions) =>
      prevSelectedOptions.includes(option)
        ? prevSelectedOptions.filter((o) => o !== option)
        : [...prevSelectedOptions, option]
    );
  };

  const handleChangeOccup = (option) => {
    setOccup(option === occup ? "" : option);
  }

  const handleGenderChange = (option) => {
    setGender(option === gender ? "" : option);
  }

  const handleAgeChange = (option) => {
    setAge(option === age ? "" : option);
  }

//   useEffect(() => {
//     const handleClickOutside = (event) => {
//       if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
//         setDropdownVisible(false);
//       }
//     };

//     document.addEventListener('mousedown', handleClickOutside);
//     return () => {
//       document.removeEventListener('mousedown', handleClickOutside);
//     };
//   }, []);

  const recommendTypeOne = (e) => {
    e.preventDefault();
    if(selectedGenres.length < 1) {
        alert("Please choose at least one genre");
        return;
    }
    const data = {
        gender: genMap[gender],
        age: ageMap[age],
        occupation: occup,
        genre: selectedGenres.join('|')
    }
    recommend(data);
    setAge('');
    setGender('');
    setOccup('');
    setSelectedGenres([]);
  }

  return (
    <div className="second-header">
        <form onSubmit={recommendTypeOne}>
        <div>
            <button onClick={toggleDropdownGender} id="dropdownButton">
                Gender
            </button>
            {dropdownVisibleGender && (
                <div className="dropdown-content" ref={dropdownRef} >
                {genders.map((option) => (
                    <label key={option}>
                    <input
                        type="checkbox"
                        value={genMap[option]}
                        checked={option === gender}
                        onChange={() => handleGenderChange(option)}
                    />
                    {option}
                    </label>
                ))}
                </div>
            )}
        </div>

        <div>
            <button onClick={toggleDropdownAge} id="dropdownButton">
                Age
            </button>
            {dropdownVisibleAge && (
                <div className="dropdown-content" ref={dropdownRef} >
                {ageList.map((option) => (
                    <label key={option}>
                    <input
                        type="checkbox"
                        value={ageMap[option]}
                        checked={option === age}
                        onChange={() => handleAgeChange(option)}
                    />
                    {option}
                    </label>
                ))}
                </div>
            )}
        </div>

          <div>
        <button onClick={toggleDropdownOccup} id="dropdownButton">
            Occupation
            </button>
            {dropdownVisibleOccup && (
                <div className="dropdown-content" ref={dropdownRef} >
                {occupationList.map((option) => (
                    <label key={option}>
                    <input
                        type="checkbox"
                        value={option}
                        checked={option === occup}
                        onChange={() => handleChangeOccup(option)}
                    />
                    {option}
                    </label>
                ))}
                </div>
            )}
        </div>
        
        <div>
        <button onClick={toggleDropdownGenres} id="dropdownButton">
            Genres
        </button>
        {dropdownVisibleGenres && (
            <div className="dropdown-content" ref={dropdownRef} >
            {genres.map((option) => (
                <label key={option}>
                <input
                    type="checkbox"
                    value={option}
                    checked={selectedGenres.includes(option)}
                    onChange={() => handleCheckboxChange(option)}
                />
                {option}
                </label>
            ))}
            </div>
        )}
        </div>
        <div>
            <button type="submit">Recommend</button>
        </div>
      </form>
    </div>
  );
}

export default SecondHeader;
