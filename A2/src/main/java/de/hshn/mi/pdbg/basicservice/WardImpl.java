package de.hshn.mi.pdbg.basicservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Klasse.
 */
public class WardImpl extends PersistentJDBCObject implements Ward {

    private String name;
    private int numberOfBeds;
    private PreparedStatement preparedStatementWardUpdate;
    private PreparedStatement preparedStatementWardInsert;

    public WardImpl(BasicDBService service, long id, String name, int numberOfBeds) {
        super(service, id);
        this.name = name;
        this.numberOfBeds = numberOfBeds;
    }

    @Override
    public void setCluster(PreparedStatement cluster) {

    }

    @Override
    public int getNumberOfBeds() {
        return 0;
    }

    @Override
    public void setNumberOfBeds(int number) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public long store(Connection connection) throws SQLException {
        if (!this.isPersistent()) {
            this.setObjectID(this.generateID(connection));
            String sql = """
                   INSERT INTO ward(id, name, numberofbeds) 
                   VALUES (?, ?, ?)
                    """;
            preparedStatementWardInsert = connection.prepareStatement(sql);
            preparedStatementWardInsert.setLong(1, this.getObjectID());
            preparedStatementWardInsert.setString(2, this.getName());
            preparedStatementWardInsert.setInt(3, this.getNumberOfBeds());
            preparedStatementWardInsert.execute();
        } else {
            String sql = """
                    UPDATE ward
                    SET name = ?,
                    numberofbeds = ?
                    WHERE id = ?
                    """;
            preparedStatementWardUpdate = connection.prepareStatement(sql);
            preparedStatementWardUpdate.setString(1, this.getName());
            preparedStatementWardUpdate.setInt(2, this.getNumberOfBeds());
            preparedStatementWardUpdate.setLong(3, this.getObjectID());
            preparedStatementWardUpdate.execute();
        }
        return getObjectID();
    }

}
