package de.hshn.mi.pdbg.basicservice;

/**
 * .
 */
public class DBGeneratorBaseline {
    /**
     * Main Method.
     * @ param args
     * @ throws Exception
     */
    public static void main(String[] args) throws Exception {
        BaselineDBServiceFactory.createBaselineDBService();
        new de.hshn.mi.pdbg.basicservice
                .jdbc.PostgresSQLBaselineDBConstructor().construct(
                "jdbc:postgresql://localhost:5432/dbOpt",
                "postgres",
                "password"
        );
    }
}
