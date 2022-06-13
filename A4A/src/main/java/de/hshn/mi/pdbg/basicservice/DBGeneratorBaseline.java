package de.hshn.mi.pdbg.basicservice;

/**
 * Class that is being used to create and fill the BaseLine database.
 */
public class DBGeneratorBaseline {

    /**
     * Main method.
     */
    public static void main(String[] args) {
        BaselineDBServiceFactory.createBaselineDBService();
        new de.hshn.mi.pdbg.basicservice
                .jdbc.PostgresSQLBaselineDBConstructor().construct(
                "jdbc:postgresql://localhost:5432/dbOpt",
                "postgres",
                "password");
    }
}
