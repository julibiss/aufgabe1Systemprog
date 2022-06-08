package de.hshn.mi.pdbg.basicservice;

import java.sql.SQLException;

/**
 * {@link SystemUnderTestDBServiceFactory} define a static factory method in order to create an instance of a
 * {@link BasicDBService} object.
 * @ version 1.0
 */
public class SystemUnderTestDBServiceFactory {

    /**
     * Test.
     * @ return
     */
    public static BasicDBService createSystemUnderTestDBService() {
        try {
            return new BasicDBServiceImpl("jdbc:postgresql://postgres/pdbg-baseline",
                    "postgres", "postgres");
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}