/*
	8 stations.
	Each station can have any number of trains inside and any number of cargos to be delivered to
	some other station.
	
	Index is N      - 0..7
	Position is 2N  - 0..14
*/

import java.util.Arrays;
import java.util.ArrayList;

public class Station extends APlace {
	private ArrayList<Train> trains_here; // array of Trains currently here
	private ArrayList<Cargo> cargos;      // array of Cargos parked here and to be picked up
	private              int index;       // 0..7
	protected         Object depotLock = new Object() ; // I can synchronize on this when accessing the Depot
	
	// a station is born with no trains there
	public Station(int label) {
		index = label;
		trains_here = new ArrayList<Train>();
		cargos      = new ArrayList<Cargo>();
	}
	
	public synchronized void addTrain(Train newTrain) {
		//if (train_here != null) {
		//	throw new Exception("this train is already present in this station: " + train_here);
		//}
		trains_here.add(newTrain); // OK
	}
	
	/**
	 * ArrayList only supports removing INT (index of object). Strangely enough, Eclipse didnt correct me!
	 */
	public synchronized void removeTrain(Train train) {
		// Maybe in the future check/test exception
		//if (train_here != null) {
		//	throw new Exception("this train wasnt in this station before!");
		//}
		System.out.println("DEB: trains_here = " + trains_here);
		trains_here.remove(trains_here.indexOf(train)); // seems to crash here, apparently "remove" wants an index 
	}
	
	public boolean isEmpty() {
		return trains_here.isEmpty();
	}
	public String toString(boolean verbose) {
		if (verbose) 
			return "S"+index+"(C:"+ cargos.size() +", Ts:"+trains_here+")";
		else {
			//cant afford to print all Train status here..
			int occurrences[]; 
			occurrences = new int[ trains_here.size() ];
			for (int i=0; i< trains_here.size(); i++) {
				occurrences[i] = trains_here.get(i).occurrence;
			}
			return "S"+index+"(C:"+ cargos.size() +", Ts:"+Arrays.toString(occurrences)+")";
		}
	}
	public String toString() {
		return toString(true); 
	}
	public String toStringMini() {
		return toString(false);
	}
	public int getPosition() {
		// 2N for stations and 2N+1 for railways
		return 2 * index;
	}
	public Position absolutePosition2() {
		// 2N for stations and 2N+1 for railways
		return new Position(2 * index);
	}
	
	// adds cargo from close by city to this station
	public synchronized void addCargo(Cargo c) {
		cargos.add(c) ;
	}

	public synchronized ArrayList<Cargo> getCargos() {
		return cargos;
	}

	public synchronized void removeCargo(int cargoIndex) {
		this.cargos.remove(cargoIndex);
	}

	public int getIndex() {
		return index;
	}

	/**
	 * his methods basically does a 'pop' frpom the array. For the
	 * exercise, it's not important which cargo is taken, so for simplicity
	 * we'll get the first one.
	 * 
	 * The important thing is that this code is synchronized so any
	 * access to the array should be locked to one thread at a time.
	 * 
	 * Actually 'remove' does exactly what we want
	 * 
	 * @return returns the 'popped' cargo.
	 * 
	 */
	public synchronized Cargo removeAndGetCargo() {
		return cargos.remove(0);
	}

}