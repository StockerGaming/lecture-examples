package de.unistuttgart.iste.pe2.Assignment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import de.unistuttgart.iste.pe2.examples.ORMExamples;
import de.unistuttgart.iste.pe2.model.Department;

public class ORMAssignment {
    private ConnectionSource connectionSource;
    private Dao<Letters, Integer> lettersDao;
    private static Logger LOGGER = Logger.getLogger(ORMAssignment.class.getName());

    public Optional<String> getLetters() {
        boolean connected = connectToDB();
        Optional<String> word = Optional.empty();
        if (connected) {
            List<Integer> arrayIndexes = new ArrayList<>(Arrays.asList(
                    20, 44, 50, 13, 17, 33, 41,
                    68, 77, 44, 29, 72, 48, 71,
                    37, 48, 11, 69, 5, 65, 65));

            word = Optional.of("");
            for (Integer id : arrayIndexes) {
                try {
                    String letter = lettersDao.queryForId(id).getLetter();
                    word = Optional.of(word.get().concat(letter));
                } catch (SQLException exception) {
                    logSQLException(exception);
                }
            }
            closeConnectionToDB();
        }
        return word;
    }

    private boolean connectToDB() {
        try {
            connectionSource = new JdbcConnectionSource(
                    "jdbc:mariadb://bilbao.informatik.uni-stuttgart.de/pe2-db-a1",
                    "pe2-nutzer", "esJLtFm6ksCT4mCyOS");
            lettersDao = //
                    DaoManager.createDao(connectionSource, Letters.class);
            return true;
        } catch (SQLException exception) {
            logSQLException(exception);
        }
        return false;
    }

    private void closeConnectionToDB() {
        try {
            connectionSource.close();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error message: " + exception.getMessage());
        }
    }

    private void logSQLException(SQLException exception) {
        LOGGER.log(Level.SEVERE, "Error code: " + exception.getErrorCode());
        LOGGER.log(Level.SEVERE, "Error message: " + exception.getMessage());
    }
}
