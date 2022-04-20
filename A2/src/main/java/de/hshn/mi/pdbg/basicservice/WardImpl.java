package de.hshn.mi.pdbg.basicservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Klasse.
 */
public class WardImpl extends PersistentJDBCObject implements Ward {
    protected WardImpl(BasicDBService service, long id) {
        super(service, id);
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
        return 0;
    }
}
