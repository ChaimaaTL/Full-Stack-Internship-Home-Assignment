import React from 'react';

const JobSummaryTable = ({ jobSummary }) => {
  return (
    <div>
      <h2>Résumé des salaires par poste</h2>
      {Object.keys(jobSummary).length > 0 ? (
        <table>
          <thead>
            <tr>
              <th>Poste</th>
              <th>Salaire moyen</th>
            </tr>
          </thead>
          <tbody>
            {Object.entries(jobSummary).map(([jobTitle, averageSalary], index) => (
              <tr key={index}>
                <td>{jobTitle}</td>
                <td>{averageSalary}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>Aucun résumé de salaire à afficher.</p>
      )}
    </div>
  );
};

export default JobSummaryTable;
