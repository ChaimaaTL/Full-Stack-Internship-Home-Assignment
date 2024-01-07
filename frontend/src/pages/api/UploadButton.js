// UploadButton.js
import React from 'react';

const UploadButton = ({ onFileChange }) => {
  const handleFileChange = (event) => {
    const selectedFile = event.target.files[0];

    if (selectedFile) {
      onFileChange(selectedFile);
    }
  };

  return (
    <div>
      <label htmlFor="uploadInput">Sélectionner un fichier CSV :</label>
      <input
        type="file"
        id="uploadInput"
        accept=".csv"
        onChange={handleFileChange}
      />
    </div>
  );
};

export default UploadButton;
