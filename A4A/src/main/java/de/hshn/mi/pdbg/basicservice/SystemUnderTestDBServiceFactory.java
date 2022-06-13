package de.hshn.mi.pdbg.basicservice;

import java.sql.SQLException;

/**
 * {@link SystemUnderTestDBServiceFactory} define a static factory method in order to create an instance of a
 * {@link BasicDBService} object.
 * @ version 1.0
 */
public class SystemUnderTestDBServiceFactory {

    /**
     * Factory method in order to create a instance of your implementation of the BasicDBService.
     * @ return instance of a system under test BasicDBService object.
     */

    public static BasicDBService createSystemUnderTestDBService() {
        try {
            return new BasicDBServiceImpl("jdbc:postgresql://postgres/pdbg-a4a",
                    "postgres",
                    "postgres");
        } catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}