package de.hshn.mi.pdbg.basicservice;

import de.hshn.mi.pdbg.basicservice.jdbc.AbstractPersistentJDBCObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Extends {@link AbstractPersistentJDBCObject}.
 * Class that is used to make Objects persistent.
 */
public abstract class PersistentJDBCObject extends AbstractPersistentJDBCObject {

    protected PersistentJDBCObject(BasicDBService service, long id) {
        super(service, id);
    }

    public abstract long store(Connection connection) throws SQLException;

    /**
     * Returns a generated ID based on the current sequence.
     * @ param connection
     * @ param sequence
     * @ return
     * @ throws SQLException
     */
    protected long generateID(Connection connection, String sequence) throws SQLException {
        long i;
        PreparedStatement statement = connection.prepareStatement("SELECT  nextval(?);");
        statement.setString(1, sequence);
        ResultSet rs = statement.executeQuery();
        rs.next();
        i = rs.getLong("nextval");
        statement.close();
        rs.close();
        return i;
    }

}
