// ProcessButton.js
import React from 'react';

const ProcessButton = ({ onClick, isFileSelected }) => {
  const handleProcessClick = () => {
    onClick();
  };

  return (
    <div>
      {isFileSelected ? (
        <button onClick={handleProcessClick}>Traiter le fichier</button>
      ) : (
        <p>Sélectionnez d&apos;abord un fichier CSV.</p>
      )}
    </div>
  );
};

export default ProcessButton;
