package de.unistuttgart.iste.pe2.examples;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import de.unistuttgart.iste.pe2.model.Department;

public class ORMExamples {
    private static Logger LOGGER = Logger.getLogger(ORMExamples.class.getName());
    private static int CONTROLLING_DEPARTMENT_ID = 20;
    private static int MARKETING_DEPARTMENT_ID = 21;

    private ConnectionSource connectionSource;
    private Dao<Department, Integer> departmentDao;

    /*
     * Runs code demonstrating the use of ORMLite.
     */
    public void runDemonstration() {
        // creates connection to a MariaDB installed on localhost
        boolean connected = this.connectToDB("jdbc:mariadb://bilbao.informatik.uni-stuttgart.de/pe2-db-a1", "pe2-nutzer", "esJLtFm6ksCT4mCyOS");

        if (connected) {
            try {
                // instantiate the Database Access Object (DAO) for handling DB operations
                this.departmentDao = DaoManager.createDao(this.connectionSource, Department.class);

                // demonstrates operations using TableUtils class
                this.performTableOperations();

                // fill a create table with demo data
                this.fillTable();

                // retrieves and logs the content od the table
                this.retrieveTableContent();

                // updates a table entry
                this.updateTableContent();

                // deletes a table entry
                this.deleteTableContent();

                // closes connection to the database
                this.closeConnectionToDB();

            } catch (SQLException exception) {
                this.logSQLException(exception);
            }
        }
    }

    /*
     * Connects to a database
     */
    private boolean connectToDB(String connectionString, String user, String password) {
        try {
            this.connectionSource = new JdbcConnectionSource(connectionString, user, password);
            return true;
        } catch (SQLException exception) {
            this.logSQLException(exception);
        }
        return false;
    }

    /*
     * Closes connection to the database
     */
    private void closeConnectionToDB() {
        try {
            this.connectionSource.close();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error message: " + exception.getMessage());
        }
    }

    /*
     * Creates a table. It also demostrate how to clear and drop a table.
     */
    private void performTableOperations() {
        try {
            // clear table for demonstration purposes
            TableUtils.clearTable(this.connectionSource, Department.class);

            // drop table for demonstration purposes
            TableUtils.dropTable(this.connectionSource, Department.class, true);
        } catch (SQLException exception) {
            this.logSQLException(exception);
        }

        try {
            // create table
            TableUtils.createTableIfNotExists(this.connectionSource, Department.class);
        } catch (SQLException exception) {
            this.logSQLException(exception);
        }
    }

    /*
     * Fills table with examplary data.
     */
    private void fillTable() {
        try {
            Department controllingDepartment =
                    new Department(CONTROLLING_DEPARTMENT_ID, "Controlling", 41573);
            this.departmentDao.create(controllingDepartment);

            Department marketingDepartment =
                    new Department(MARKETING_DEPARTMENT_ID, "Marketing", 69547);
            this.departmentDao.create(marketingDepartment);

        } catch (SQLException exception) {
            this.logSQLException(exception);
        }
    }

    /*
     * Shows examples retrieving the content of the table.
     */
    private void retrieveTableContent() {
        try {
            // get all entries of the table
            List<Department> departmentsList = departmentDao.queryForAll();
            for (Department department : departmentsList) {
                String departmentName = department.getDepartmentName();
                LOGGER.log(Level.INFO, "Department name: " + departmentName);
            }

            // get entries that match the given equality condition
            List<Department> searchedDepartments =
                    departmentDao.queryForEq("departmentName", "Controlling");
            for (Department department : searchedDepartments) {
                Integer departmentId = department.getDepartmentId();
                LOGGER.log(Level.INFO, "Department ID: " + departmentId);
            }

            // get entry by Id
            Department searchedDepartmentById = departmentDao.queryForId(20);
            if (searchedDepartmentById != null) {
                String departmentName = searchedDepartmentById.getDepartmentName();
                LOGGER.log(Level.INFO, "Department name: " + departmentName);
            }
        } catch (SQLException exception) {
            this.logSQLException(exception);
        }
    }

    /*
     * Updates the content of the table.
     */
    private void updateTableContent() {
        try {
            Department controllingDepartment = departmentDao.queryForId(CONTROLLING_DEPARTMENT_ID);
            if (controllingDepartment != null) {

                // for demonstration, refresh object to update in with possible changes in the DB
                departmentDao.refresh(controllingDepartment);

                // update data in table
                controllingDepartment.setDepartmentName("Human Resources");
                int numberOfUpdatedRows = departmentDao.update(controllingDepartment);
                LOGGER.log(Level.INFO, "Number of updated rows: " + numberOfUpdatedRows);
            }
        } catch (SQLException exception) {
            this.logSQLException(exception);
        }
    }

    private void deleteTableContent() {
        try {
            Department marketingDepartment = departmentDao.queryForId(MARKETING_DEPARTMENT_ID);
            if (marketingDepartment != null) {
                // delete row
                int numberOfUpdatedRows = departmentDao.delete(marketingDepartment);

                // alternative delete method by Id:
                // numberOfUpdatedRows =
                // departmentDao.deleteById(marketingDepartment.getDepartmentNr());

                LOGGER.log(Level.INFO, "Number of deleted rows: " + numberOfUpdatedRows);
            }
        } catch (SQLException exception) {
            this.logSQLException(exception);
        }
    }

    private void logSQLException(SQLException exception) {
        LOGGER.log(Level.SEVERE, "Error code: " + exception.getErrorCode());
        LOGGER.log(Level.SEVERE, "Error message: " + exception.getMessage());
    }
}
