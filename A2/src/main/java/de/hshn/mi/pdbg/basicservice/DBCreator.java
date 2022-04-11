package de.hshn.mi.pdbg.basicservice;

import de.hshn.mi.pdbg.schema.SchemaGenerator;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCreator implements SchemaGenerator {

    protected static final String [] SQL_DDL_STATEMENTS = {

    }

    public static void main(String[] args) throws Exception{
        String url = "jdbc.hsqldb:file:myDBSchemaName";
        new DBCreator().createDatabase(url, "sa", "");
    }

    public boolean createDatabase(String jdbcUrl, String user, String password){

    }

    protected Connection createConnection(String jdbcURL, String user, String password) throws ClassNotFoundException, SQLException{

    }

}
