import java.util.Scanner;

//Clase capaz de ejecutar el c�digo de cada proceso
public class ThreadSort extends Thread {
	int array[];
	int inicio, fin;
	
	//Constructor de la clase que guarda el arreglo de n�meros, posici�n inicial y posici�n final
	public ThreadSort (int[] array, int inicio, int fin) {
		this.array = array;
		this.inicio = inicio;
		this.fin = fin;
	}
	
	//M�todo que permite ejecutar la hebra
	public void run() {
		Sort(array, inicio, fin);
	}
	

	//M�todo que parte el arreglo en 2 y cambia valores, orden�ndolos
	public static int particion(int[] array, int inicio, int fin) {
		int pivote = array[fin];
		int i = inicio -1;
		for(int j=inicio; j<= fin; j++) {
			if(array[j] > pivote) {
				i++;
				int temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
		}
		int temp = array[i+1];
		array[i+1] = array[fin];
		array[fin] = temp;
		return i+1;
	}
	
	
	//M�todo que invoca a las hebras para ser ejecutadas en cada parte del arreglo
	public static void Sort(int[] array, int inicio, int fin) {
		if(inicio < fin) {
			int p = particion(array, inicio, fin);
			Thread hilo1 = new Thread(new ThreadSort(array, inicio, p-1));
			Thread hilo2 = new Thread(new ThreadSort(array, p+1, fin));
			hilo1.start();
			hilo2.start();
			try {
				hilo1.join();
				hilo2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//M�todo main para la ejecuci�n del programa completo, solicita la cantidad total de n�meros a ingresar y luego solicita ingresar dichos n�meros
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Cuantos numeros ingresara?: ");
		int n = input.nextInt();
		int[] array = new int[n];
		Scanner elem = new Scanner(System.in);
		System.out.println("Ingrese elemento(s): ");
		for(int i=0; i<n;i++) {
			array[i] = elem.nextInt();
		}
		
		ThreadSort.Sort(array, 0, n-1);
		System.out.println("Tu arreglo se ordeno de la siguiente manera: ");
		
		for(int k=0; k<n; k++) {
			System.out.println(array[k]);
		}
	}
}
