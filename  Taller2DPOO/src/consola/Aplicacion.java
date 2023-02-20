package consola;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import procesamiento.Restaurante;

public class Aplicacion {
	private Restaurante restaurante;

	public void mostrarMenu() {
		System.out.println("\nOpciones de Pedido\n");
		System.out.println("1. Pedido en Combo\n");
		System.out.println("2. Individual\n");

	}

	public void pedido() {
		String nombre = input("Ingrese su nombre: ");
		String direccion = input("Ingrese su direccion:");
		this.restaurante.iniciarPedido(nombre, direccion);

	}

	public void adicionarAPedido() {
		try {
			String Modificar = "";
			int AgregaCombo = 100;
			int AgregaMenu = 100;
			mostrarMenu();
			int opcion_seleccionada = Integer.parseInt(input("Seleccione una opción"));
			if (opcion_seleccionada == 1) {
				this.restaurante.generarMenuCombo();
				AgregaCombo = Integer.parseInt(input("Seleccione una opción:"));

			} else if (opcion_seleccionada == 2) {
				this.restaurante.generarMenuBase();
				AgregaMenu = Integer.parseInt(input("Seleccione una opción"));
				System.out.println("1. SI\n");
				System.out.println("2. NO\n");
				int modify = Integer.parseInt(input("Desea Modificar su producto?"));

				if (modify == 1) {
					System.out.println("Estos son los ingredientes existentes ");
					this.restaurante.generarMenuItems();
					Modificar = input("Ingrese lo que quiere modificar: ");
				} else {
					System.out.println("Por favor seleccione una opción válida.");
				}

			}
			this.restaurante.paraAgregar(AgregaCombo, AgregaMenu, Modificar);

		} catch (NumberFormatException e) {
			System.out.println("Debe seleccionar uno de los números de las opciones.");
		}

	}

	public void funciones() {

		boolean continuar = true;
		while (continuar) {
			try {
				mostrarFunciones();
				int opcion_seleccionada = Integer.parseInt(input("Seleccione una opción"));
				if (opcion_seleccionada == 1) {
					this.restaurante.generarMenuBase();
				} else if (opcion_seleccionada == 2) {
					// Se iniciar un pedido
					this.pedido();
				} else if (opcion_seleccionada == 3) {
					// Se debe agregar un elemento al pedido
					this.adicionarAPedido();

				} else if (opcion_seleccionada == 4) {
					// Cerrar y guardar factura
					this.restaurante.cerrarYGuardarPedido0();

					System.out.println("Su pedido se ha guardado exitosamente");
				} else if (opcion_seleccionada == 5) {
					// Consulta por ID
					int ID = Integer.parseInt(input("Que ID desea consultar?"));
					this.restaurante.getPedidoPorID(ID);

				} else if (opcion_seleccionada == 0) {
					System.out.println("Saliendo de la aplicación ...");
					continuar = false;
				} else {
					System.out.println("Por favor seleccione una opción válida.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Debe seleccionar uno de los números de las opciones.");
			}
		}
	}

	

	public void mostrarFunciones() {
		System.out.println("\nSeleccione una Función:\n");
		System.out.println("1. Mostrar el menú\n");
		System.out.println("2. Iniciar un nuevo pedido\n");
		System.out.println("3. Agregar un elemento a un pedidoo\n");
		System.out.println("4. Cerrar un pedido y guardar la factura\n");
		System.out.println("5. Consultar la información de un pedido dado su id\n");
		System.out.println("0. Fin\n");

	}

	public String input(String mensaje) {
		try {
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		} catch (IOException e) {
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		Aplicacion app = new Aplicacion();
		app.restaurante = new Restaurante();
		app.restaurante.cargarinformacionRestaurante();
		app.funciones();
	}
}
