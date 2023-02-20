package procesamiento;

import java.util.ArrayList;
import java.util.List;

public class ProductoAjustado implements Producto {

	private List<Ingrediente> agregados;
	private List<Ingrediente> eliminados;
	private ProductoMenu base;

	public ProductoAjustado(ProductoMenu base) {
		this.base = base;
		this.agregados = new ArrayList<>();
		this.eliminados = new ArrayList<>();
//		

	}

	public void agregarProductos(ArrayList<Ingrediente> IA, ArrayList<Ingrediente> IE) {
		// Agregar ingredientes a la lista de agregados del ProductoAjustado actual
		for (Ingrediente ingredienteA : IA) {
			this.agregar(ingredienteA);
		}
		// Eliminar ingredientes de la lista de eliminados del ProductoAjustado actual
		for (Ingrediente ingredienteE : IE) {
			this.eliminados(ingredienteE);
		}

	}

	private void agregar(Ingrediente modificacion) {
		if (agregados == null) {
			agregados = new ArrayList<Ingrediente>();
		}
		agregados.add(modificacion);
	}

	private void eliminados(Ingrediente modificacion) {
		if (eliminados == null) {
			eliminados = new ArrayList<Ingrediente>();
		}
		eliminados.add(modificacion);
	}

	@Override
	public String getNombre() {
		return base.getNombre();

	}

	@Override
	public int getPrecio() {
		int precio = base.getPrecio();
		if (agregados != null) {
			for (Ingrediente ingrediente : agregados) {
				precio += ingrediente.getCostoAdicional();
			}
		}
		return precio;
	}

	@Override
	public String generarTextoFactura() {
		StringBuilder factura = new StringBuilder();
		factura.append(String.format("%s - $%d\n", this.getNombre(), this.getPrecio()));
		if (eliminados != null && eliminados.size() > 0) {
			factura.append("Ingredientes eliminados: ");
			for (Ingrediente ingrediente : eliminados) {
				factura.append(ingrediente.getNombre() + ", ");
			}
		}
		factura.delete(factura.length() - 2, factura.length());
		factura.append("\n");
		factura.append("Ingredientes agregados: ");
		if (agregados != null && agregados.size() > 0) {
			for (Ingrediente ingrediente : agregados) {

				String precios = String.format("\nPrecio Adicional: $%d\n", ingrediente.getCostoAdicional());
				factura.append(ingrediente.getNombre() + " - " + precios);
			}
		}
		factura.delete(factura.length() - 2, factura.length());
		factura.append("\n");
		return factura.toString();
	}

}
