import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../api/axiosConfig';
import './QuizPage.css';

const QuizPage = ({curScore, onSolve}) => {

    const navigate = useNavigate();
    const [username, setUsername] = useState(localStorage.getItem('username'));
    
    useEffect(() => {
        const userName = localStorage.getItem('username');
        if(userName){
            setUsername(userName);
        }else {
            navigate('/auth');
        }
    }, [])

    const quizCompletionKey = `${username}_completedQuiz`;

    const [quiz, setQuiz] = useState(null);
    const [selectedAnswer, setSelectedAnswer] = useState('');
    const [result, setResult] = useState(null);
    const [coins, setCoins] = useState(curScore);

    const [completed, setCompleted] = useState(() => {
        return localStorage.getItem(quizCompletionKey) === 'true';
    });

    useEffect(() => {
        const lastQuizDate = localStorage.getItem('lastQuizDate');
        const today = new Date().toISOString().split('T')[0]; // Get today's date in YYYY-MM-DD format

        if (!lastQuizDate || lastQuizDate !== today || localStorage.getItem(quizCompletionKey) !== 'true') {
            fetchQuiz();
            localStorage.setItem('lastQuizDate', today); // Update the date after fetching the quiz
            setCompleted(false); // Reset completed status if it's a new day
            localStorage.removeItem(quizCompletionKey); // Reset completion flag in local storage
        } else {
            const savedQuiz = localStorage.getItem('quiz'); // Retrieve saved quiz
            if (savedQuiz) {
                setQuiz(JSON.parse(savedQuiz));
            }
        }
    }, [quizCompletionKey]);

    const fetchQuiz = async () => {
        try {
            const response = await axios.get('/quiz');
            console.log('Quiz fetched:', response.data); // Debug log
            setQuiz(response.data);
            localStorage.setItem('quiz', JSON.stringify(response.data)); // Save quiz to localStorage
        } catch (error) {
            console.error('Error fetching quiz:', error);
        }
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        const isCorrect = selectedAnswer === quiz.answer;
        setResult(isCorrect ? 'Correct!' : 'Incorrect.');

        if (isCorrect) {
            const newScore = coins + 10;
            setCoins(newScore);
            localStorage.setItem('score', newScore);
            axios.post(`/users/${username}/addPoints`)
                .then(response => {
                    console.log("SUCCESS: ", response.data);
                })
                .catch(error => {
                    console.log("ERROR while adding points: ", error);
                })
            onSolve();
        }

        setSelectedAnswer('');
        setCompleted(true);
        if (quizCompletionKey) {
            localStorage.setItem(quizCompletionKey, 'true'); // Mark the quiz as completed for the user
        }
    };

    if (!quiz) return <div>Loading...</div>;

    return (
        <div className="quiz-container">
            <div className="quiz-header">
                <h2>Quiz</h2>
                <div className="score">Coins: {curScore}</div>
            </div>
            {!completed ? (
                <>
                    <div className="question">{quiz.question}</div>
                    <form onSubmit={handleSubmit}>
                        {quiz.answers.map((answer, index) => (
                            <div key={index} className="answer-option">
                                <label>
                                    <input
                                        type="radio"
                                        name="answer"
                                        value={answer}
                                        checked={selectedAnswer === answer}
                                        onChange={() => setSelectedAnswer(answer)}
                                    />
                                    {answer}
                                </label>
                            </div>
                        ))}
                        <button type="submit" className="submit-button">Submit</button>
                    </form>
                    {result && <div className={`result ${result === 'Correct!' ? 'correct-answer' : 'incorrect-answer'}`}>{result}</div>}
                </>
            ) : (
                <div className="come-back-tomorrow">
                    <h3>Great job!</h3>
                    <p>Thanks for participating in today's quiz. Come back tomorrow for another question! 🎉</p>
                    <p>Meanwhile, why not sharpen your wits with some fun facts or puzzles?</p>
                </div>
            )}
        </div>
    );
};

export default QuizPage;
