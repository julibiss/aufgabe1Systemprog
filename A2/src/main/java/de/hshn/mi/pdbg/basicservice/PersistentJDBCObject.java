package de.hshn.mi.pdbg.basicservice;

import de.hshn.mi.pdbg.basicservice.jdbc.AbstractPersistentJDBCObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Klasse.
 */
public abstract class PersistentJDBCObject extends AbstractPersistentJDBCObject {

    protected PersistentJDBCObject(BasicDBService service, long id) {
        super(service, id);
    }

    protected long generateID(Connection connection) throws SQLException {
        long i;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT nextval('sequence_1');");
        rs.next();
        i = rs.getLong("nextval");
        statement.close();
        rs.close();
        return i;
    }

    public abstract void setCluster(Statement cluster);

    public abstract long store(Connection connection)throws SQLException;
}
