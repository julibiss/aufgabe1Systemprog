package de.hshn.mi.pdbg.basicservice;

import java.sql.SQLException;

/**
 * {@link SystemUnderTestDBServiceFactory} define a static factory method in order to create an instance of a
 * {@link BasicDBService} object.
 * @ version 1.0
 */
public class SystemUnderTestDBServiceFactory {

    /**
     * main method.
     *
     * @param args optional arguments
     * @throws Exception if input arguments are invalid
     */
    public static void main(String[] args) throws Exception { // Erzeugen und Nutzen des Datenbankgenerators :
        // Erzeugen Sie Ihren Dienst so, dass er sich
        // mit der passenden PostgreSQL−Datenbank verbindet .
        // In der Datenbank muessen bereits die entsprechenden
        // ( leeren ) Tabellen erzeugt sein .
        new de.hshn.mi.pdbg.basicservice.dbgen.DBGenerator(SystemUnderTestDBServiceFactory
                .createSystemUnderTestDBService())
                .generateDB(100);
    }

    /**
     * Test.
     * @ return
     */
    public static BasicDBService createSystemUnderTestDBService() {
        try {
            return new BasicDBServiceImpl("jdbc:postgresql://postgres/pdbg-baseline",
                    "postgres", "password");
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}