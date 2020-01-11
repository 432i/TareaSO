import java.util.Scanner;


public class ThreadSort extends Thread {
	int array[];
	int inicio, fin;
	
	
	public ThreadSort (int[] array, int inicio, int fin) {
		this.array = array;
		this.inicio = inicio;
		this.fin = fin;
	}
	
	public void run() {
		Sort(array, inicio, fin);
	}
	

	
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
		
		for(int k=0; k<n; k++) {
			System.out.println(array[k]);
		}
	}
}
