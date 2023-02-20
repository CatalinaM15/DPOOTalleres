package procesamiento;

import java.util.ArrayList;
import java.util.List;

public class Combo implements Producto {

	private double descuento;
	private String nombreCombo;
	private List<ProductoMenu> itemsCombo;

	public Combo(String nombreCombo, double descuento) {
		this.nombreCombo = nombreCombo;
		this.descuento = descuento;
		itemsCombo = new ArrayList<>();

	}

	@Override
	public String getNombre() {
		return nombreCombo;
	}

	public void agregarItemACombo(Producto itemCombo) {
		itemsCombo.add((ProductoMenu) itemCombo);
	}

	@Override
	public int getPrecio() {
		int PrecioNeto;
		PrecioNeto = 0;
		for (ProductoMenu itemActual : itemsCombo) {
			PrecioNeto = PrecioNeto + itemActual.getPrecio();
		}
		double descuentoComplemento = 1 - descuento;
		int precioConDescuento = (int) (PrecioNeto * descuentoComplemento);
		return precioConDescuento;
	}

	@Override
	public String generarTextoFactura() {
		return String.format("%s - $%d\n", this.getNombre(), this.getPrecio());
	}
}
