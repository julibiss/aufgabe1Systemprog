package de.hshn.mi.pdbg.basicservice;

/**
 * .
 */
public class DBGeneratorSystemUnderTest {

    /**
     * .
     * @ param args
     * @ throws Exception
     */
    public static void main(String[] args) throws Exception {
        BaselineDBServiceFactory.createBaselineDBService();
        new de.hshn.mi.pdbg.basicservice.dbgen.DBGenerator(
                SystemUnderTestDBServiceFactory.createSystemUnderTestDBService()).generateDB(100);
    }
}
