import java.util.*;
import java.text.*;

public class Concesionaria {

    private ArrayList<Vehiculo> vehiculos;

    public Concesionaria() {
        vehiculos = new ArrayList<>();
    }

    public boolean ingresarVehiculo(Vehiculo v) {
        if (v == null)
            throw new IllegalArgumentException("El vehículo no puede ser nulo.");
        for (Vehiculo ve : vehiculos) {
            if (ve.getCodigo().equalsIgnoreCase(v.getCodigo()))
                throw new IllegalArgumentException("El vehículo con código " + v.getCodigo() + " ya existe.");
        }
        vehiculos.add(v);
        return true;
    }

    public Vehiculo buscarVehiculo(String codigo) {
        for (Vehiculo v : vehiculos) {
            if (v.getCodigo().equalsIgnoreCase(codigo)) return v;
        }
        return null;
    }

    public void mostrarVehiculo(String codigo) {
        Vehiculo v = buscarVehiculo(codigo);
        if (v == null) {
            System.out.println("Vehículo con código " + codigo + " no encontrado.");
        } else {
            System.out.println(v.toString());
        }
    }

    //MÉTODO: Formatear a pesos chilenos| lo saque de aqui video ->  https://youtu.be/b0NHh8RNWK4?si=zgH6ClhlAvMvqPI-&t=6225

    private String formatearPesos(double monto) {
        Locale chile = new Locale("es", "CL");
        NumberFormat formato = NumberFormat.getCurrencyInstance(chile);
        formato.setMaximumFractionDigits(0);
        formato.setMinimumFractionDigits(0);
        return formato.format(monto);
    }

    public int aplicarAjusteATodos() {
        int cont = 0;

        System.out.println("\n=== AJUSTE DE PRECIOS APLICADO ===");

        for (Vehiculo v : vehiculos) {
            double precioAntes = v.getPrecioBase();
            v.ajustePrecio();
            double precioDespues = v.getPrecioBase();

            if (precioDespues != precioAntes) {
                cont++;
                System.out.println("- Se ajustó el precio del vehículo con código [" + v.getCodigo() + "]: "
                        + formatearPesos(precioAntes) + " → " + formatearPesos(precioDespues));
            }
        }

        if (cont == 0) {
            System.out.println("No se realizaron ajustes de precio.");
        }

        return cont;
    }

    public double calcularDescuentoTotal() {
        double total = 0;
        for (Vehiculo v : vehiculos) {
            if (v instanceof PromEspecial) {
                total += ((PromEspecial) v).descuentoFeriado();
            }
        }
        return total;
    }
}
