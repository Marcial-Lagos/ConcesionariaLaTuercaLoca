import java.util.*;

public abstract class Vehiculo {
    protected String codigo;
    protected String marca;
    protected String modelo;
    protected double precioBase;
    protected int anioFabricacion;

    public Vehiculo(String codigo, String marca, String modelo, double precioBase, int anioFabricacion) {
        if (codigo == null || codigo.trim().isEmpty())
            throw new IllegalArgumentException("El código no puede estar vacío.");
        if (marca == null || marca.trim().isEmpty()) 
            throw new IllegalArgumentException("La marca no puede estar vacía.");
        if (modelo == null || modelo.trim().isEmpty())
            throw new IllegalArgumentException("El modelo no puede estar vacío.");
        if (precioBase <= 0) 
            throw new IllegalArgumentException("El precio base debe ser mayor que cero.");
        if (anioFabricacion < 1900 || anioFabricacion > 2100) 
            throw new IllegalArgumentException("El año de fabricación no es válido.");

        this.codigo = codigo.trim();
        this.marca = marca.trim();
        this.modelo = modelo.trim();
        this.precioBase = precioBase;
        this.anioFabricacion = anioFabricacion;
    }

    public String getCodigo() { return codigo; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public double getPrecioBase() { return precioBase; }
    public int getAnioFabricacion() { return anioFabricacion; }

    public void setPrecioBase(double precioBase) {
        if (precioBase <= 0)
            throw new IllegalArgumentException("El precio base debe ser mayor que cero.");
        this.precioBase = precioBase;
    }

    public abstract void aplicarImpuesto();
    public abstract void ajustePrecio();

    @Override
    public String toString() {
        return "Código: " + codigo + ", Marca: " + marca + ", Modelo: " + modelo +
                ", Precio Base: $" + String.format("%.2f", precioBase) + ", Año: " + anioFabricacion;
    }
}