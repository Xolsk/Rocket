package rockets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Rocket {

	public String name;
	public List<Propeller> propellers = new ArrayList<Propeller>();
	public int currentSpeed;
	public double totalPower;
	private List<Thread> threadList= new ArrayList <Thread>();

	public Rocket(String name, List<Propeller> propellers) {

		this.name = name;
		this.propellers = propellers;
		this.currentSpeed = 0;
		this.totalPower = getTotalPower(propellers);


	}

	synchronized public void run(int desiredSpeed) {
		threadList.clear();
		double desiredPower;
		System.out.println("Asignando potencia a " + this.name);
		desiredPower = calculatePower(desiredSpeed);
		if (desiredPower > totalPower) {
			System.out.println("Velocidad fuera de potencia con la velocidad actual.");
		} else {
			allocatePower(desiredPower);
			for (int i = 0; i < propellers.size(); i++) {
				if (propellers.get(i).desiredPower>0) {
				System.out.println(
						"Potencia objetivo para " + propellers.get(i).name + " " + propellers.get(i).desiredPower);
						new Thread (propellers.get(i)).start();
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}		
			}
		}
				
				this.currentSpeed = desiredSpeed;
				System.out.println("Orden exitosa. Nueva velocidad de " + this.currentSpeed);	
	}

	private double calculatePower(double desiredSpeed) {

		double desiredPower = Math.pow((desiredSpeed - currentSpeed) / 100, 2);

		System.out.println("Potencia total necesaria: " + desiredPower + " de " + this.totalPower);
		return desiredPower;
	}

	private double getTotalPower(List<Propeller> propellers) {

		double totalPower = 0;
		for (int i = 0; i < propellers.size(); i++) {

			totalPower = totalPower + propellers.get(i).maxPower;
		}
		return totalPower;
	}

	private void allocatePower(double desiredPower) {

		double leftPower = desiredPower;
		double optimalPower;
		double stressPower;
		double underPower;
		double marginPower;
		double optimisePower;
		Collections.sort(propellers, Collections.reverseOrder()); // Es importante tener los cohetes ordenados por
																	// potencia maxima.
		// PRIMERA ASIGNACION BUSCANDO RENDMIENTO OPTIMO del 60%;

		for (int i = 0; i < propellers.size(); i++) {
			optimalPower = (propellers.get(i).maxPower / 100) * 60;
			if (optimalPower >= leftPower) {
				propellers.get(i).desiredPower = leftPower;
				for (int e = i + 1; e < propellers.size(); e++) {
					propellers.get(e).desiredPower = 0;
				}
				leftPower = 0;
				break;
			} else {
				propellers.get(i).desiredPower = optimalPower;

				leftPower = leftPower - optimalPower;
			}
		}
		// SECOND ROUND AL 80% ALARGANDO POTENCIA.

		if (leftPower > 0) {
			for (int i = 0; i < propellers.size(); i++) {
				optimalPower = (propellers.get(i).maxPower / 100) * 60;
				stressPower = (propellers.get(i).maxPower / 100) * 80;
				marginPower = stressPower - optimalPower;
				if (marginPower < leftPower) {
					propellers.get(i).desiredPower = propellers.get(i).desiredPower + marginPower;
					leftPower = leftPower - marginPower;
				} else {
					propellers.get(i).desiredPower = propellers.get(i).desiredPower + leftPower;
					leftPower = 0;
					break;
				}
			}
		}
		// TERCER ROUND PARA DEJAR MOTORES AL 100%;

		if (leftPower > 0) {
			for (int i = 0; i < propellers.size(); i++) {
				stressPower = (propellers.get(i).maxPower / 100) * 80;
				marginPower = propellers.get(i).maxPower - stressPower;
				if (marginPower < leftPower) {
					propellers.get(i).desiredPower = propellers.get(i).maxPower;
					leftPower = leftPower - marginPower;
				} else {
					propellers.get(i).desiredPower = propellers.get(i).desiredPower + leftPower;
					leftPower = 0;
					break;
				}
			}
		}

		// OPTIMIZAMOS POR SI HAY ALGUN MOTOR UNDERPOWERED
		for (int i = propellers.size(); i > 1; i--) {
			underPower = (propellers.get(i - 1).maxPower / 100) * 40;
			if (propellers.get(i - 1).desiredPower < underPower && propellers.get(i - 1).desiredPower != 0) {
				optimisePower = propellers.get(i - 1).desiredPower;
				for (int e = 0; e < propellers.size(); e++) {
					stressPower = (propellers.get(e).maxPower / 100) * 80;
					if (propellers.get(e).desiredPower + leftPower <= stressPower) {
						propellers.get(e).desiredPower = propellers.get(e).desiredPower + optimisePower;
						propellers.get(i - 1).desiredPower = 0;
						break;
					}
				}
			}

		}

	}
}
