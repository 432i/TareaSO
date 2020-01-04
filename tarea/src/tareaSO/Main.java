package tareaSO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//Funcion y clase Main: se encarga de procesar el archivo .txt entregado, guardando y creando los procesos en una lista de tipo Thread, para luego pedir la información
//por pantalla
public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		File archivo = new File("funciones.txt");
		Scanner scan = new Scanner(archivo);
		Scanner entrada= new Scanner(System.in);
		String operacion;
		int numFunciones = Integer.parseInt(scan.nextLine());
		ArrayList<String> funciones=new ArrayList<String>(); //almacena la linea de string completa
		ArrayList<Proceso> hilos=new ArrayList<Proceso>(); //almacena los hilos con los nombres de las funciones (f,g,h)

		System.out.println("Funciones ingresadas!\n");
		System.out.println("Ingrese una operacion (ingrese F para salir del programa):");
		operacion = entrada.nextLine();
		for(int i=0;i<numFunciones;i++) {
			funciones.add(scan.nextLine());
			 hilos.add(new Proceso (funciones.get(i).substring(0,4),hilos,funciones.get(i).substring(5),numFunciones,operacion));

		}
		int flag=0;
		while(flag==0) {
			if(operacion.equals("F")) flag=1;
			else {
				for(int i=0;i<numFunciones;i++) {
					if(operacion.substring(0,1).equals(hilos.get(i).getNombre().substring(0,1))) {
						hilos.get(i).run();
						System.out.println("El resultado es: "+hilos.get(i).getResultado());
					}
				}
				System.out.println("Ingrese otra operacion (ingrese F para salir del programa):");
				operacion = entrada.nextLine();
				for(int j=0;j<numFunciones;j++) {
					hilos.get(j).setFuncion();
					hilos.get(j).setResultado();
					hilos.get(j).setOperacion(operacion);
				}

			}
		}
		System.out.println("Adios nwn!!");


}


}
