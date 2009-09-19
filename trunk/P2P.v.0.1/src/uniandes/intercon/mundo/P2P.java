package uniandes.intercon.mundo;
/**
 * 
 */

/**
 * @author Juan Pablo
 *
 */
public class P2P {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ThreadServidor ts = new ThreadServidor();
		ts.start();
		
		
		ThreadCliente tc= new ThreadCliente();
		tc.start();

                //Test

	}

        public String test (String a)
        {
            return a;

        }

}
