package model;

import DAO.DAOException;
import DAO.impl.DAOManagerImpl;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.SQLException;

public class ServicioDeDatos {
    private static DAOManagerImpl daoManager;

    static {
        Dotenv dotenv = Dotenv.load();

        String host = dotenv.get("DB_HOST");
        String database = dotenv.get("DB_NAME");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        try {
            daoManager = new DAOManagerImpl(host, user, password, database);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);

        }
    }

    public static DAOManagerImpl getDaoManager (){
        return daoManager;
    }

}
