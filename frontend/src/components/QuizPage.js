import React, { useState, useEffect } from 'react';
import axios from '../api/axiosConfig';
import './QuizPage.css'; // Import your custom CSS for QuizPage styling

const QuizPage = () => {
    const [quiz, setQuiz] = useState(null);
    const [selectedAnswer, setSelectedAnswer] = useState('');
    const [result, setResult] = useState(null);
    const [score, setScore] = useState(() => {
        // Initialize score from localStorage, or default to 0
        const savedScore = localStorage.getItem('quizScore');
        return savedScore ? parseInt(savedScore, 10) : 0;
    });
    const username = localStorage.getItem('username'); // Assuming you store the username in local storage

    useEffect(() => {
        fetchQuiz();
    }, []);

    const fetchQuiz = async () => {
        try {
            const response = await axios.get('/quiz');
            console.log('Quiz fetched:', response.data); // Debug log
            setQuiz(response.data);
        } catch (error) {
            console.error('Error fetching quiz:', error);
        }
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        // Check if selected answer is correct
        const isCorrect = selectedAnswer === quiz.answer;

        // Display result
        setResult(isCorrect ? 'Correct!' : 'Incorrect.');

        // Update score
        if (isCorrect) {
            const newScore = score + 10;
            setScore(newScore); // Increment score by 10
            localStorage.setItem('quizScore', newScore); // Save score to localStorage
        }

        // Fetch next quiz
        fetchQuiz();
        setSelectedAnswer('');
    };

    if (!quiz) return <div>Loading...</div>;

    return (
        <div className="quiz-container">
            <div className="quiz-header">
                <h2>Quiz</h2>
                <div className="score">Score: {score}</div>
            </div>
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
        </div>
    );
};

export default QuizPage;
