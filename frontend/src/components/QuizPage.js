import React, { useState, useEffect } from 'react';
import axios from '../api/axiosConfig';
import './QuizPage.css'; // Import your custom CSS for QuizPage styling

const QuizPage = () => {
    const [quiz, setQuiz] = useState(null);
    const [selectedAnswer, setSelectedAnswer] = useState('');
    const [result, setResult] = useState(null);
    const [score, setScore] = useState(() => {
        const savedScore = localStorage.getItem('quizScore');
        return savedScore ? parseInt(savedScore, 10) : 0;
    });
    const [completed, setCompleted] = useState(false);
    const username = localStorage.getItem('username'); // Assuming you store the username in local storage

    useEffect(() => {
        const lastQuizDate = localStorage.getItem('lastQuizDate');
        const today = new Date().toISOString().split('T')[0]; // Get today's date in YYYY-MM-DD format

        if (lastQuizDate !== today) {
            fetchQuiz();
            localStorage.setItem('lastQuizDate', today); // Update the date after fetching the quiz
        } else {
            const savedQuiz = localStorage.getItem('quiz'); // adding some comment
            if (savedQuiz) {
                setQuiz(JSON.parse(savedQuiz));
            }
        }
    }, []);

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
            const newScore = score + 10;
            setScore(newScore); // Increment score by 10
            localStorage.setItem('quizScore', newScore); // Save score to localStorage
        }

        setSelectedAnswer('');
        setCompleted(true);
    };

    if (!quiz) return <div>Loading...</div>;

    return (
        <div className="quiz-container">
            <div className="quiz-header">
                <h2>Quiz</h2>
                <div className="score">Score: {score}</div>
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
