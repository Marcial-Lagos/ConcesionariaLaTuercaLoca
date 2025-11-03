public class Motocicleta extends Vehiculo {

    private String tipo; 
    private int cilindrada; 

    public Motocicleta(String codigo, String marca, String modelo, double precioBase, int anioFabricacion,
                       String tipo, int cilindrada) {
        super(codigo, marca, modelo, precioBase, anioFabricacion);

        if (tipo == null || tipo.trim().isEmpty())
            throw new IllegalArgumentException("El tipo de motocicleta no puede estar vacío.");
        if (!tipo.equalsIgnoreCase("deportiva") && !tipo.equalsIgnoreCase("clásica") && !tipo.equalsIgnoreCase("clasica"))
            throw new IllegalArgumentException("Tipo de motocicleta inválido. Debe ser 'deportiva' o 'clásica'.");
        if (cilindrada <= 0)
            throw new IllegalArgumentException("La cilindrada debe ser mayor a cero.");

        this.tipo = tipo.trim();
        this.cilindrada = cilindrada;
    }

    @Override
    public void aplicarImpuesto() {
        if (cilindrada > 600) {
            precioBase += precioBase * 0.08; // 8% impuesto
        }
    }

    @Override
    public void ajustePrecio() {
        if (tipo.equalsIgnoreCase("clásica")) {
            precioBase -= precioBase * 0.15;
        }
    }

    @Override
    public String toString() {
        return "Motocicleta -> " + super.toString() +
                ", Tipo: " + tipo +
                ", Cilindrada: " + cilindrada + "cc";
    }
}
