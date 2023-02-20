package procesamiento;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Restaurante {
	private List<Ingrediente> ingredientes;
	private List<ProductoMenu> menuBase;
	private Pedido pedidoEnCurso;
	private List<Pedido> pedidos;
	private List<Combo> combos;

	public Restaurante() {
		ingredientes = new ArrayList<Ingrediente>();
		menuBase = new ArrayList<ProductoMenu>();
		pedidos = new ArrayList<Pedido>();
		combos = new ArrayList<Combo>();
	}

	public void iniciarPedido(String nombreCliente, String direccionCliente) {
		if (pedidoEnCurso != null) {
			pedidos.add(pedidoEnCurso);
		}

		pedidoEnCurso = new Pedido(nombreCliente, direccionCliente);

	}

	public void cerrarYGuardarPedido0() {
		pedidoEnCurso.guardarFactura();
	}

	public Pedido getPedidoEnCurso0() {
		return pedidoEnCurso;

	}

	public List<ProductoMenu> getMenuBase0() {
		return menuBase;
	}

	public List<Ingrediente> getingredientes0() {
		return ingredientes;
	}

	public Pedido getPedidoPorID(int id) {
		for (Pedido pedido : pedidos) {
			if (pedido.getidPedido0() == id) {
				return pedido;
			}
		}
		return null;
	}

	public ProductoMenu seleccionarProductoMenu(int opcion) {
		if (opcion >= 1 && opcion <= menuBase.size()) {
			return menuBase.get(opcion - 1);
		} else {
			return null;
		}
	}

	public void cargarinformacionRestaurante() {
		cargaringredientes();
		cargarMenu();
		cargarCombos();

	}

	public void generarMenuCombo() {
		int opcion = 1;
		System.out.println("Combos disponibles:");
		for (Combo combo : combos) {
			System.out.println(opcion + ". " + combo.getNombre() + " - $" + combo.getPrecio());
			opcion++;
		}
	}

	public void generarMenuBase() {
		int opcion = 1;
		System.out.println("Menú Base:");
		for (ProductoMenu menu : menuBase) {
			System.out.println(opcion + ". " + menu.getNombre() + " - $" + menu.getPrecio());
			opcion++;
		}
	}

	public void generarMenuItems() {
		int opcion = 1;
		System.out.println("Ingredientes disponibles:");
		for (Ingrediente item : ingredientes) {
			System.out.println(opcion + ". " + item.getNombre() + "  + $" + item.getCostoAdicional());
			opcion++;
		}
	}

	private void cargaringredientes() {

		try (BufferedReader ir = new BufferedReader(new FileReader("data/ingredientes.txt"))) {
			String linea;
			while ((linea = ir.readLine()) != null) {
				String[] partes = linea.split(";");
				String nombre = partes[0];
				int valor = Integer.parseInt(partes[1]);

				Ingrediente item = new Ingrediente(nombre, valor);
				ingredientes.add(item);
			}
		} catch (IOException e) {
			System.out.println("Error al leer el archivo: " + e.getMessage());
		}
	}

	private void cargarMenu() {

		try (BufferedReader br = new BufferedReader(new FileReader("data/menu.txt"))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] partes = linea.split(";");
				String nombre = partes[0];
				int precio = Integer.parseInt(partes[1]);

				ProductoMenu ProductoM = new ProductoMenu(nombre, precio);
				menuBase.add(ProductoM);
			}
		} catch (IOException e) {
			System.out.println("Error al leer el archivo: " + e.getMessage());
		}
	}

	public ProductoMenu buscarPorNombre(String nombre) {
		for (ProductoMenu producto : menuBase) {
			if (producto.getNombre().equals(nombre)) {
				return producto;
			}
		}
		return null;
	}

	public void paraAgregar(int AgregaCombo, int AgregaMenu, String Modificar) {
		if (Modificar.isEmpty() != true) {

			ProductoMenu modificado = this.seleccionarProductoMenu(AgregaMenu);
			this.Categorizar(Modificar, modificado);

		} else if (AgregaCombo != 100)
			pedidoEnCurso.agregarProducto(this.seleccionarCombo(AgregaCombo));
		else if (AgregaMenu != 100)
			pedidoEnCurso.agregarProducto(this.seleccionarProductoMenu(AgregaMenu));
	}

	public Combo seleccionarCombo(int opcion) {
		if (opcion >= 1 && opcion <= combos.size()) {
			return combos.get(opcion - 1);
		} else {
			return null;
		}
	}

	public void Categorizar(String Modificar, ProductoMenu Base) {
		String[] modificaciones = Modificar.split(" ");
		ArrayList<Ingrediente> IngredientesA = new ArrayList<Ingrediente>();
		ArrayList<Ingrediente> IngredientesE = new ArrayList<Ingrediente>();
		for (int i = 0; i < modificaciones.length; i++) {
			if (modificaciones[i].equals("con")) {

				String nombreIngredienteA = modificaciones[i + 1];
				if (i + 2 < modificaciones.length && !modificaciones[i + 2].equals("sin")
						&& !modificaciones[i + 2].equals("con") && !modificaciones[i + 2].equals("y")) {

					nombreIngredienteA = modificaciones[i + 1] + " " + modificaciones[i + 2];
					Ingrediente IngredienteA = buscarIngredientePorNombre(nombreIngredienteA);
					IngredientesA.add(IngredienteA);

				} else {
					Ingrediente IngredienteA = buscarIngredientePorNombre(nombreIngredienteA);
					IngredientesA.add(IngredienteA);
				}

			} else if (modificaciones[i].equals("sin")) {

				String nombreIngredienteE = modificaciones[i + 1];
				if (i + 2 < modificaciones.length && !modificaciones[i + 2].equals("sin") && !modificaciones[i + 2].equals("sin")
						&& !modificaciones[i + 2].equals("y")) {
					nombreIngredienteE = modificaciones[i + 1] + " " + modificaciones[i + 2];
					Ingrediente IngredienteE = buscarIngredientePorNombre(nombreIngredienteE);
					IngredientesE.add(IngredienteE);

				} else {
					Ingrediente IngredienteE = buscarIngredientePorNombre(nombreIngredienteE);
					IngredientesE.add(IngredienteE);
				}

			}
		}
		pedidoEnCurso.agregarModificado(Base, IngredientesA, IngredientesE);
	}

	public Ingrediente buscarIngredientePorNombre(String nombre) {
		for (Ingrediente ingrediente : ingredientes) {
			if (ingrediente.getNombre().equals(nombre)) {
				return ingrediente;
			}
		}
		return null;
	}

	private void cargarCombos() {

		try (BufferedReader cr = new BufferedReader(new FileReader("data/combos.txt"))) {
			String linea;
			while ((linea = cr.readLine()) != null) {
				String[] partes = linea.split(";");
				String nombre = partes[0];
				double descuento = Double.parseDouble(partes[1].replace("%", "")) / 100;

				Combo combo = new Combo(nombre, descuento);
				ProductoMenu PartedeCombo;
				for (int i = 2; i < partes.length; i++) {
					// Buscar producto menu con el nombre para poder sacar el precio
					PartedeCombo = this.buscarPorNombre(partes[i]);
					combo.agregarItemACombo(PartedeCombo);

				}

				combos.add(combo);
			}
		} catch (IOException e) {
			System.out.println("Error al leer el archivo: " + e.getMessage());
		}
	}

}
