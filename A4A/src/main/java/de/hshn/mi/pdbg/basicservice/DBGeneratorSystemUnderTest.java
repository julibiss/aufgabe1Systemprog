package de.hshn.mi.pdbg.basicservice;

/**
 * Fills the normal database with generated data.
 */
public class DBGeneratorSystemUnderTest {

    public static void main(String[] args) throws Exception {
        new de.hshn.mi.pdbg.basicservice.dbgen.DBGenerator(
                SystemUnderTestDBServiceFactory.createSystemUnderTestDBService()).generateDB(100);
    }
}
