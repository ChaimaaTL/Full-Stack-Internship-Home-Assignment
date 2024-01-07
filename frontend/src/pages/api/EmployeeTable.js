import React from 'react';

const EmployeeTable = ({ employees }) => {
  return (
    <div style={styles.employeeTableContainer}>
      <h2>Informations sur les employés</h2>
      {employees.length > 0 ? (
        <table style={styles.employeeTable}>
          <thead>
            <tr>
              <th>Nom</th>
              <th>Poste</th>
              <th>Salaire</th>
            </tr>
          </thead>
          <tbody>
            {employees.map((employee, index) => (
              <tr key={index}>
                <td>{employee.employeeName}</td>
                <td>{employee.jobTitle}</td>
                <td>{employee.salary}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p style={styles.noDataMessage}>Aucune donnée d'employé à afficher.</p>
      )}
    </div>
  );
};

const styles = {
  employeeTableContainer: {
    margin: '20px',
  },
  employeeTable: {
    width: '100%',
    borderCollapse: 'collapse',
    marginTop: '10px',
  },
  tableCell: {
    border: '1px solid #ddd',
    padding: '8px',
    textAlign: 'left',
  },
  tableHeader: {
    backgroundColor: '#f2f2f2',
  },
  noDataMessage: {
    marginTop: '10px',
    fontStyle: 'italic',
    color: '#888',
  },
};

export default EmployeeTable;
