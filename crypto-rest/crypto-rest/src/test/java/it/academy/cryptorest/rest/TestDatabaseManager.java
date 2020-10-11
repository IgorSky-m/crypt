package it.academy.cryptorest.rest;

import com.mysql.cj.jdbc.MysqlDataSource;
import it.academy.cryptorest.exception.DatabaseManagerException;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;

import java.sql.SQLException;

public interface TestDatabaseManager {

    void cleanInsertData();

    default void cleanInsertData(String url, String username, String password){
       try {

           MysqlDataSource dataSource = new MysqlDataSource();
           dataSource.setURL(url);
           dataSource.setUser(username);
           dataSource.setPassword(password);

           IDataSet dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                   .getResourceAsStream("data.xml"));

           IDatabaseConnection connection = new DatabaseConnection(dataSource.getConnection(), "crypto_test");
           connection.getConfig()
                   .setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());

           DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
       } catch (DatabaseUnitException | SQLException e) {
           throw new DatabaseManagerException(e);
       }
    }

    void deleteData();

    default void deleteData(String url, String username, String password){
        try {

            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setURL(url);
            dataSource.setUser(username);
            dataSource.setPassword(password);

            IDataSet dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                    .getResourceAsStream("data.xml"));

            IDatabaseConnection connection = new DatabaseConnection(dataSource.getConnection(), "crypto_test");
            connection.getConfig()
                    .setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());

            DatabaseOperation.DELETE.execute(connection, dataSet);
        } catch (DatabaseUnitException | SQLException e) {
            throw new DatabaseManagerException(e);
        }
    }
}
