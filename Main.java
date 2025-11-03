import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Concesionaria concesionaria = new Concesionaria();
        int opcion = 0;

        do {
            try {
                System.out.println("\n===  LA TUERCA LOCA  ===");
                System.out.println("1. Ingresar Vehículo");
                System.out.println("2. Mostrar Información");
                System.out.println("3. Aplicar Ajuste de Precio");
                System.out.println("4. Aplicar Descuento Feriado");
                System.out.println("5. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = sc.nextInt();
                sc.nextLine(); 

                switch (opcion) {
                    case 1:
                    System.out.print("¿Desea ingresar (A)utomóvil o (M)otocicleta? ");
                    String tipo = sc.nextLine();

                    if (!tipo.equalsIgnoreCase("A") && !tipo.equalsIgnoreCase("M")) {
                        throw new IllegalArgumentException("Solo se permiten Automóviles (A) o Motocicletas (M).");
                    }

                    System.out.print("Código: ");
                    String codigo = sc.nextLine();
                    System.out.print("Marca: ");
                    String marca = sc.nextLine();
                    System.out.print("Modelo: ");
                    String modelo = sc.nextLine();
                    System.out.print("Precio base: ");
                    double precio = sc.nextDouble();
                    System.out.print("Año de fabricación: ");
                    int anio = sc.nextInt();
                    sc.nextLine();

                    if (tipo.equalsIgnoreCase("A")) {
                        System.out.print("Tipo de carrocería (sedán/SUV): ");
                        String carroceria = sc.nextLine();
                        System.out.print("Cantidad de puertas: ");
                        int puertas = sc.nextInt();
                        sc.nextLine();

                        concesionaria.ingresarVehiculo(
                            new Automovil(codigo, marca, modelo, precio, anio, carroceria, puertas)
                        );
                    } 

                    else {
                        System.out.print("Tipo (deportiva/clásica): ");
                        String tipoMoto = sc.nextLine();
                        System.out.print("Cilindrada (cc): ");
                        int cilindrada = sc.nextInt();
                        sc.nextLine();

                        concesionaria.ingresarVehiculo(
                            new Motocicleta(codigo, marca, modelo, precio, anio, tipoMoto, cilindrada)
                        );
                    }
                    break;

                    case 2:
                        System.out.print("Ingrese el código del vehículo: ");
                        String codigoBuscar = sc.nextLine();
                        concesionaria.mostrarVehiculo(codigoBuscar);
                        break;

                    case 3:
                        int totalAjustados = concesionaria.aplicarAjusteATodos();
                        System.out.println("Se aplicó ajuste a " + totalAjustados + " vehículos.");
                        break;

                    case 4:
                        double totalDescuento = concesionaria.calcularDescuentoTotal();
                        System.out.println("Descuento total aplicado: $" + String.format("%.2f", totalDescuento));
                        break;

                    case 5:
                        System.out.println("Saliendo del sistema. ¡Gracias por usar el programa La Tuerca Loca!");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Intente nuevamente.");
                sc.nextLine();
            }

        } while (opcion != 5);

        sc.close();
    }
}
