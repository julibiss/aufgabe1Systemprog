package de.hshn.mi.pdbg.basicservice;

import de.hshn.mi.pdbg.basicservice.jdbc.AbstractPersistentJDBCObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;


/**
 * Klasse.
 */
public abstract class PersistentJDBCObject extends AbstractPersistentJDBCObject {

    protected PersistentJDBCObject(BasicDBService service, long id) {
        super(service, id);
    }

    public abstract long store(Connection connection) throws SQLException;

    protected long generateLongID(Connection connection) throws SQLException {
        long i;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT nextval('sequence');");
        rs.next();
        i = rs.getLong("nextval");
        statement.close();
        rs.close();
        return i;
    }



}
