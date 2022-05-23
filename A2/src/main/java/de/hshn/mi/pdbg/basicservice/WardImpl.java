package de.hshn.mi.pdbg.basicservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Implements {@link Ward}.
 */
public class WardImpl extends PersistentJDBCObject implements Ward {

    private String name;
    private int numberOfBeds;
    private PreparedStatement preparedStatementWardUpdate;
    private PreparedStatement preparedStatementWardInsert;

    /**
     * Default constructor initializes the following values.
     * @ param service
     * @ param id
     * @ param name
     * @ param numberOfBeds
     */
    public WardImpl(BasicDBService service, long id, String name, int numberOfBeds) {
        super(service, id);
        this.name = name;
        this.numberOfBeds = numberOfBeds;
    }


    @Override
    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    @Override
    public void setNumberOfBeds(int number) {
        assert number > 0;
        this.numberOfBeds = number;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        assert name != null;
        assert !name.isBlank();
        this.name = name;

    }

    @Override
    public long store(Connection connection) throws SQLException {
        assert connection != null;

        if (!this.isPersistent()) {
            this.setObjectID(this.generateID(connection, "wardIDSequence"));
            preparedStatementWardInsert = connection.prepareStatement("INSERT INTO ward(id, name, numberofbeds)"
                                                                      + " VALUES (?,?,?)");
            preparedStatementWardInsert.setLong(1, this.getObjectID());
            preparedStatementWardInsert.setString(2, this.getName());
            preparedStatementWardInsert.setInt(3, this.getNumberOfBeds());
            preparedStatementWardInsert.executeUpdate();
            preparedStatementWardInsert.close();
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
            preparedStatementWardUpdate.close();
        }
        return getObjectID();
    }

}
