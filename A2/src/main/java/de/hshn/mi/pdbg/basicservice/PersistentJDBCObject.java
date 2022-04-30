package de.hshn.mi.pdbg.basicservice;

import de.hshn.mi.pdbg.basicservice.jdbc.AbstractPersistentJDBCObject;

import java.sql.*;


/**
 * Klasse.
 */
public abstract class PersistentJDBCObject extends AbstractPersistentJDBCObject {

    protected PersistentJDBCObject(BasicDBService service, long id) {
        super(service, id);
    }

    public abstract long store(Connection connection) throws SQLException;

    protected long generateLongID(Connection connection, String sequence) throws SQLException {
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
