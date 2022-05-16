package de.hshn.mi.pdbg.basicservice;

/**
 * {@link SystemUnderTestDBServiceFactory} define a static factory method in order to create an instance of a
 * {@link BasicDBService} object.
 * @version 1.0
 */
public class SystemUnderTestDBServiceFactory
{
	/**
	 * Factory method in order to create a instance of your implementation of the BasicDBService.
	 * @return instance of a system under test BasicDBService object.
	 */
	public static BasicDBService createSystemUnderTestDBService()
	{
		//The code needed to instantiate an implementation of a BasicDBService. Use "jdbc:postgresql://postgres/pdbg-a4a", "postgres", "postgres" for jdbc url, login and password in CI/CD context
		throw new UnsupportedOperationException();
    }
}
		
			
			
		
