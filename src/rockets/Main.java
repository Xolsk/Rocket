package rockets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

	private static final List<Rocket> rocketList = new ArrayList<Rocket>();

	public static void main(String[] args) throws Exception {

		Propeller r1p1 = new Propeller("r1p1", 10);
		Propeller r1p2 = new Propeller("r1p2", 10);
		Propeller r1p3 = new Propeller("r1p3", 20);
		Boolean exit=false;
		List <Propeller> r1Props = new ArrayList<Propeller>(Arrays.asList(r1p1,r1p2,r1p3));
		Rocket r1 = new Rocket("X23", r1Props);
		rocketList.add(r1);
		String order;
		System.out.print(r1.name + ":  ");
		for (int i = 0; i < r1Props.size(); i++) {
			System.out.print( "Motor "+ i + " " + r1Props.get(i).maxPower + "  ");
		}

		Scanner sc = new Scanner(System.in);
		while (exit==false) {
		System.out.println("Escoja su cohete y la velocidad deseada. Atencion, los nombres de cohete son case sensitive. Para salir, teclee 'exit' ");
		order = sc.nextLine();
		switch (order) {
		case "exit": {System.out.println("Chau");
					exit=true;
					break;}
		default: validateData(order);
		}
		}

	}

	private static void validateData(String order) throws Exception {
		String[] orderArr = order.split(" ");
		int desiredSpeed = Integer.parseInt(orderArr[1]);
		if (orderArr.length > 2) {
			throw new Exception();
		}
		for (int i = 0; i < rocketList.size(); i++) {
			if (orderArr[0].matches(rocketList.get(i).name)) {
				System.out.println("Cohete localizado,ejecutando orden.");
				rocketList.get(i).run(desiredSpeed);
			}
		}
		
	}
}
