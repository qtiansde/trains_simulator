/* 
	Thread that manages the Cargo creation

	Manages the 8 stations and creates at random times cargos for the cities.
	Ideally, it should produce 8 stocastic independent events with poissonian
	distribution with an Average of 'µ' (Greek mu).
	
	Ideally, I should use 8 threads and have each a poissonian generator (that's what we
	used in Operation Research to simulate stocastic events). For the purposes of this
	exercise, we think a single linear generator will do.
	
*/
import java.util.Random;
	
class CargoManager extends AVerboseThread {
	public static Random random = new Random();

	// Uses Country.RandomCargoPeriodSecs 
	public void run() {
		vlog("started");
		try {
			while (true) {
				// TODO put 100 to 1000 :)
				Thread.sleep(random.nextInt(100 * Country.RandomCargoPeriodSecs )); 
				Cargo recurrent = Cargo.getRandomCargo();
				dlog("Generating a cargo: "+ recurrent );
				//Country.getInstance().stations[ recurrent.getSource() ].setCargo
				Country.getInstance().deliverCargoToAppropriateStation(recurrent);
			}
		} catch (InterruptedException e) {
			log("Something wrong happened");
		}
	}
	
	
}