package tareaSO;

import java.util.ArrayList;
//Clase que ejecuta el código de cada proceso
public class Proceso extends Thread {
	int numFunciones,resultado=0;
	String operacion,nombre,funcion,funcionOriginal;
	ArrayList<Proceso> hilos;
	//constructor que guarda todos los datos necesarios de cada proceso: su nombre, la lista de todos los procesos, la función, el número de procesos que hay y la operación que se le pide hacer.
	public Proceso(String nombre, ArrayList<Proceso> hilos, String funcion,int numero,String operacion) {
		this.nombre=nombre;
		this.hilos=hilos;
		this.funcion=funcion;
		this.funcionOriginal=funcion;
		this.numFunciones=numero;
		this.operacion=operacion;
	}
	//Método que pone a trabajar el proceso: el proceso muere cuando este método termina
	public void run() {
		ejecutar();
	}
	//En este método se realiza la evaluación de la función y la operación, llamando a los procesos hijos correspondientes si es que hay operaciones anidadas.
	public int ejecutar() {
		//this.resultado=0;
		int flag=0;
		for(int i=0;i<numFunciones;i++) {
			if(funcion.contains(hilos.get(i).getNombre()) ) {
				//System.out.println("soy la funcion de flag=111 : "+hilos.get(i).getNombre().substring(0,1)+"\([0-9]+\)");
				flag=1;
				funcion=funcion.replaceAll(hilos.get(i).getNombre().substring(0,1)+"\\(x\\)", Integer.toString(hilos.get(i).ejecutar() ));

				//System.out.println("soy la funcion de flag=1 : "+funcion);
				//resultado=(int)eval(funcion);
				//System.out.println("El resultado es:" + resultado);
			}
		}
		if(flag==0) {
			funcion=funcion.replace("x",operacion.substring(2,3));
			//System.out.println("soy la funcion de flag =0 :"+funcion);
			//System.out.println((int) eval(funcion));
			//return (int) eval(funcion);
		}
		this.resultado = (int) evaluar(funcion);
		//funcion=this.funcionOriginal;
		return resultado;
	}
	//Genera un parse tree para evaluar operaciones matemáticas en string, devolviendo su valor final
	public static double evaluar(final String str) {
	    return new Object() {
	        int pos = -1, ch;

	        void nextChar() {
	            ch = (++pos < str.length()) ? str.charAt(pos) : -1;
	        }

	        boolean mueve(int charToEat) {
	            while (ch == ' ') nextChar();
	            if (ch == charToEat) {
	                nextChar();
	                return true;
	            }
	            return false;
	        }

	        double parse() {
	            nextChar();
	            double x = parseExpression();
	            if (pos < str.length()) throw new RuntimeException("Falta: " + (char)ch);
	            return x;
	        }
	        double parseExpression() {
	            double x = parseTerm();
	            for (;;) {
	                if      (mueve('+')) x += parseTerm(); //suma
	                else if (mueve('-')) x -= parseTerm(); //resta
	                else return x;
	            }
	        }

	        double parseTerm() {
	            double x = parseFactor();
	            for (;;) {
	                if      (mueve('*')) x *= parseFactor(); // multiplicacion
	                else if (mueve('/')) x /= parseFactor(); // division
	                else return x;
	            }
	        }

	        double parseFactor() {
	            if (mueve('+')) return parseFactor();
	            if (mueve('-')) return -parseFactor();

	            double x;
	            int startPos = this.pos;
	            if (mueve('(')) { // para evaluar parentesis
	                x = parseExpression();
	                mueve(')');
	            } else if ((ch >= '0' && ch <= '9') || ch == '.') { // números
	                while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
	                x = Double.parseDouble(str.substring(startPos, this.pos));
	            }  else {
	                throw new RuntimeException("Falta: " + (char)ch);
	            }



	            return x;
	        }
	    }.parse();
	}
	//getter y setters para reiniciar el valor de las variables y obtener otras
	public String getNombre() {
		return this.nombre;
	}
	public int getResultado() {
		return this.resultado;
	}
	public void setResultado() {
		this.resultado=0;
	}
	public void setFuncion() {
		this.funcion=funcionOriginal;
	}
	public void setOperacion(String operacion) {
		this.operacion=operacion;

	}

}
