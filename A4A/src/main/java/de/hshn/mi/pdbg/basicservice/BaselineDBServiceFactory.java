package de.hshn.mi.pdbg.basicservice;

import de.hshn.mi.pdbg.basicservice.jdbc.PostgresSQLBaselineDBFactory;

/**
 * {@link BaselineDBServiceFactory} defines a static factory method in order to create an instance of a
 * {@link BasicDBService} object.
 *
 * @version 1.0
 */
public class BaselineDBServiceFactory {

    /**
     * Main Method.
     * @ param args
     */
    public static void main(String[] args) {
        new de.hshn.mi.pdbg.basicservice.jdbc.PostgresSQLBaselineDBConstructor().construct(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres", "password");
    }

    /**
     * Factory method in order to create a instance of a baseline BasicDBService object.
     *
     * @return instance of a baseline BasicDBService object.
     */
    public static BasicDBService createBaselineDBService() {
        try {
            //Use "jdbc:postgresql://postgres/pdbg-baseline", "postgres", "postgres" for jdbc url,
            // login and password in CI/CD context
            Class.forName(org.postgresql.Driver.class.getName());
            return PostgresSQLBaselineDBFactory.createBaselineDBService("jdbc:postgresql://localhost:5432/postgres",
                    "postgres", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}