public class Automovil extends Vehiculo implements PromEspecial {

    private String tipoCarroceria;
    private int cantidadPuertas;

    public Automovil(String codigo, String marca, String modelo, double precioBase, int anioFabricacion,
                     String tipoCarroceria, int cantidadPuertas) {
        super(codigo, marca, modelo, precioBase, anioFabricacion);

        if (tipoCarroceria == null || tipoCarroceria.trim().isEmpty())
            throw new IllegalArgumentException("El tipo de carrocería no puede estar vacío.");
        if (!tipoCarroceria.equalsIgnoreCase("sedán") && !tipoCarroceria.equalsIgnoreCase("sedan") && !tipoCarroceria.equalsIgnoreCase("SUV"))
            throw new IllegalArgumentException("Tipo de carrocería inválido. Debe ser 'sedán' o 'SUV'.");
        if (cantidadPuertas < 2 || cantidadPuertas > 5)
            throw new IllegalArgumentException("La cantidad de puertas debe estar entre 2 y 5.");

        this.tipoCarroceria = tipoCarroceria.trim();
        this.cantidadPuertas = cantidadPuertas;
    }

    @Override
    public void aplicarImpuesto() {
        if (tipoCarroceria.equalsIgnoreCase("SUV")) {
            precioBase += precioBase * 0.1;
        }
    }

    @Override
    public void ajustePrecio() {
        precioBase += precioBase * 0.05; 
    }

    @Override
    public double descuentoFeriado() {
        if (marca.equalsIgnoreCase("Ford") && tipoCarroceria.equalsIgnoreCase("SUV")) {
            double descuento = precioBase * DTO;
            precioBase -= descuento;
            return descuento;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Automóvil -> " + super.toString() +
                ", Carrocería: " + tipoCarroceria +
                ", Puertas: " + cantidadPuertas;
    }

    public String getTipoCarroceria() { return tipoCarroceria; }
    public int getCantidadPuertas() { return cantidadPuertas; }
}
