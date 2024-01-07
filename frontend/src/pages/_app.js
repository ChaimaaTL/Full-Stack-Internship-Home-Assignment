import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import UploadButton from './api/UploadButton';
import ProcessButton from './api/ProcessButton';
import EmployeeTable from './api/EmployeeTable';
import JobSummaryTable from './api/JobSummaryTable';
import './styles.css';
// Styles
const containerStyle = {
  textAlign: 'center',
  marginTop: '20px',
};

const processingStyle = {
  fontStyle: 'italic',
  color: '#888',
  marginTop: '10px',
};

// App Component : Gère l'état global de l'application
const App = () => {
  const [selectedFile, setSelectedFile] = useState(null);
  const [employees, setEmployees] = useState([]);
  const [jobSummary, setJobSummary] = useState({});
  const [processing, setProcessing] = useState(false);
  const [step, setStep] = useState(1);
  const navigate = useNavigate();

  const handleFileChange = (file) => {
    setSelectedFile(file);
    setStep(2);
  };

  const handleProcessClick = async () => {
    if (selectedFile) {
      setProcessing(true);

      // Utiliser la vraie URL de votre backend ici
      const backendURL = 'http://localhost:8080/api/employees/upload'; // Remplacez par votre URL réelle

      try {
        // Envoyer le fichier au backend
        const formData = new FormData();
        formData.append('file', selectedFile);

        const uploadResponse = await fetch(backendURL, {
          method: 'POST',
          body: formData,
        });

        if (uploadResponse.ok) {
          // Si le téléchargement est réussi, demandez les données mises à jour au backend
          const dataResponse = await fetch('http://localhost:8080/api/employees');
          const newData = await dataResponse.json();
          setEmployees(newData);

          // Exemple: Mise à jour du résumé des salaires (à remplacer par votre propre logique)
          const jobSummaryData = await fetch('http://localhost:8080/api/employees/average-salary');
          const newJobSummary = await jobSummaryData.json();
          setJobSummary(newJobSummary);

          // Rafraîchir la page après le traitement réussi
          window.location.reload();
        } else {
          console.error('Erreur lors du téléchargement du fichier', uploadResponse.statusText);
        }
      } catch (error) {
        console.error('Erreur lors de la requête au backend:', error);
      }

      setProcessing(false);
      setStep(3); // Passer à l'étape suivante
    }
  };

  // Utiliser navigate pour naviguer vers une nouvelle route
  const navigateToNextStep = () => {
    navigate('/next-route'); // Remplacez '/next-route' par le chemin de votre prochaine interface
  };

  return (
    <div className="container">
      <h1 className="header">Mon Application</h1>

      {step === 1 && <UploadButton onFileChange={handleFileChange} />}
      {step === 2 && <ProcessButton onClick={() => { handleProcessClick(); navigateToNextStep(); }} isFileSelected={selectedFile !== null} />}
      {processing && <p className="processing">Traitement en cours...</p>}
    {step === 3 && (
        <div>
          <EmployeeTable employees={employees} />
          <JobSummaryTable jobSummary={jobSummary} />
        </div>
      )}
    </div>
  );
};

export default App;
