package it.academy.cryptorest.rest;

import com.mysql.cj.jdbc.MysqlDataSource;
import it.academy.cryptorest.exception.DatabaseManagerException;
import lombok.Getter;
import lombok.Setter;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Value;

import java.sql.SQLException;
@Getter
@Setter
public abstract class TestDatabaseDBUnitManagerImpl implements TestDatabaseDBUnitManager {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private IDatabaseConnection connection;

    private IDataSet dataSet;



    public void cleanInsertData(){
        try {
            prepareStatement();

            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);

        } catch (DatabaseUnitException | SQLException e) {
            throw new DatabaseManagerException(e);
        }
    }

    public void deleteData(){
        try {
            prepareStatement();

            DatabaseOperation.DELETE.execute(connection, dataSet);
        } catch (DatabaseUnitException | SQLException e) {
            throw new DatabaseManagerException(e);
        }
    }

    private void prepareStatement() throws SQLException, DatabaseUnitException {

            MysqlDataSource dataSource;
            dataSource = new MysqlDataSource();
            dataSource.setURL(url);
            dataSource.setUser(username);
            dataSource.setPassword(password);

            dataSet = new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                    .getResourceAsStream("data.xml"));

            connection = new DatabaseConnection(dataSource.getConnection(), "crypto_test");

            connection.getConfig()
                    .setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
            connection.getConfig()
                    .setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());

    }

}

