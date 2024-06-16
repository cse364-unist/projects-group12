import React from 'react';
import './loadingicon.css'; // Make sure to style your loading icon

const LoadingIcon = () => {
  return (
    <div className="loading-icon">
      <div className="spinner"></div>
      <p>Loading...</p>
    </div>
  );
};

export default LoadingIcon;
