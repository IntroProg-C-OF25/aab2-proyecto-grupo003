import java.text.SimpleDateFormat; //SimpleDateFormat se usa para formatear y convertir fechas en String con diferentes formatos.
import java.util.Calendar; //Calendar permite manipular fechas y horas
import java.util.Date; //Se usa en combinación con SimpleDateFormat para convertir fechas a String.
import java.util.Scanner;

public class CinemasIn {

    // Variables globales para almacenar los  datos
    static String cartelera[][] = {
        {"Avengers", "14:00", "Sala 1", "8.00"},
        {"Spiderman", "16:00", "Sala 2", "7.50"},
        {"Batman", "18:00", "Sala 3", "8.50"},
        {"Doctor Strange", "20:00", "Sala 4", "9.00"},
        {"Thor: Ragnarok", "13:00", "Sala 5", "7.75"},
        {"Black Panther", "15:30", "Sala 6", "8.25"},
        {"InterEstelar", "17:00", "Sala 7", "9.50"},
        {"Mufasa", "19:30", "Sala 8", "8.75"},
        {"Iron Man", "12:30", "Sala 9", "7.25"},
        {"Hombre Lobo", "14:30", "Sala 10", "9.00"},
        {"Superman Returns", "16:30", "Sala 11", "7.80"},
        {"Deadpool", "18:30", "Sala 12", "8.90"},
        {"The Flash", "20:30", "Sala 13", "7.95"},
        {"Hulk", "21:00", "Sala 14", "8.10"},
        {"Shazam!", "13:30", "Sala 15", "7.60"},
        {"Aquaman", "15:00", "Sala 16", "8.40"},
        {"Joker", "17:30", "Sala 17", "9.20"},
        {"X-Men", "19:00", "Sala 18", "8.30"},
        {"Venom", "21:30", "Sala 19", "8.00"},
        {"Ant-Man", "22:00", "Sala 20", "7.70"}};

    static String snacks[][] = {
        {"Canguil Grande", "5.00", "50"},
        {"Gaseosa", "2.50", "100"},
        {"Nachos", "4.00", "30"},
        {"Hot Dog", "3.50", "40"},
        {"Chocolates", "2.75", "60"}};

    static String[][] ventas = new String[20][6]; // [película, horario, cantidad boletos, snacks, total, fecha]
    static int ventasCount = 0;
    static Scanner tcl = new Scanner(System.in);

    // Main
    public static void main(String[] args) {

        boolean continuar = true;
        int opcion;

        while (continuar) {
            System.out.println("\n=== SISTEMA CINEMAS-IN===");
            System.out.println("1. Ver Cartelera");
            System.out.println("2. Comprar Boletos");
            System.out.println("3. Comprar Snacks");
            System.out.println("4. Ver Registro de Ventas");
            System.out.println("5. Salir");

            opcion = tcl.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("\n=== CARTELERA CINEMAS IN===");
                    System.out.printf("%-20s %-10s %-10s %-10s\n", "PELICULA", "HORARIO", "SALA", "PRECIO");
                    mostrarCartelera();
                    break;
                case 2:
                    comprarBoletos();
                    break;
                case 3:
                    comprarSnacks();
                    break;
                case 4:
                    System.out.println("\n=== REGISTRO DE VENTAS ===");
                    System.out.printf("%-20s %-10s %-15s %-30s %-10s %-10s\n", "PELÍCULA", "HORARIO", "CANT. BOLETOS", "SNACKS", "TOTAL", "FECHA");
                    mostrarVentas();
                    break;
                case 5:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción incorrecta inserte nuevamente");
            }
        }
    }

    public static void mostrarCartelera() {
        for (int i = 0; i < cartelera.length; i++) {
            if (cartelera[i] != null) {  
                System.out.printf("%-20s %-10s %-10s $%-10s\n", cartelera[i][0], cartelera[i][1], cartelera[i][2], cartelera[i][3]);
            }
        }
    }

    public static void comprarBoletos() {
        int peliculaOpcion, cantidadBoletos;
        double precioBase, total;
        mostrarCartelera(); //Llamamos el metodo mostrarCartelera
        System.out.println("\nSeleccione el numero de pelicula(o numero de la Sala):");
        peliculaOpcion = tcl.nextInt() - 1;

        System.out.println("Ingrese cantidad de boletos:");
        cantidadBoletos = tcl.nextInt();

        precioBase = Double.valueOf(cartelera[peliculaOpcion][3]); // Se trasforma de String a Double para hacer la operacion respectiva
        total = calcularTotalBoletos(precioBase, cantidadBoletos); //Llamamos a laa funcion calcularTotalBoletos

        registrarVenta(cartelera[peliculaOpcion][0], cartelera[peliculaOpcion][1], cantidadBoletos, "", total);  //Se llama a metodo registrarVenta para guardar la información de la compra.
        System.out.printf("Total a pagar: $%.2f\n", total);
    }

    public static double calcularTotalBoletos(double precioBase, int cantidad) {
        double total = 0;
        int dia;
        int cantidadHombres = 0, cantidadMujeres = 0;

        Calendar calendar = Calendar.getInstance();//Obtiene una instancia del calendario del sistema,  obtiene la fecha y hora actuales
        dia = calendar.get(Calendar.DAY_OF_WEEK); //Obtiene el día de la semana en el que estamos y la guarda en la variable dia

        if (dia == Calendar.MONDAY || dia == Calendar.THURSDAY) {
            System.out.println("Ingrese la cantidad de hombres:");
            cantidadHombres = tcl.nextInt();

            System.out.println("Ingrese la cantidad de mujeres:");
            cantidadMujeres = tcl.nextInt();

            int totalPersonas = cantidadHombres + cantidadMujeres;

            if (totalPersonas != cantidad) {
                System.out.println("La suma de hombres y mujeres no coincide con la cantidad total de boletos. Intente nuevamente.");
                return 0;
            }
        }

        switch (dia) {
            case Calendar.MONDAY:
                total = (cantidadHombres * 3.25) + (cantidadMujeres * precioBase); // Lunes de Caballeros
                break;
            case Calendar.TUESDAY:
                total = (cantidad / 2 + cantidad % 2) * precioBase; // 2x1
                break;
            case Calendar.WEDNESDAY:
                total = precioBase * cantidad * 0.5; // 50% en cualquier película
                break;
            case Calendar.THURSDAY:
                total = (cantidadMujeres * 3.25) + (cantidadHombres * precioBase); // Jueves de Damas
                break;
            case Calendar.FRIDAY:
                total = 3.00 * cantidad; // Precio especial $3.00 para todas las películas
                break;
            default:
                total = precioBase * cantidad; // Sábado y Domingo precio normal
                break;
        }

        return total;
    }

    public static void comprarSnacks() {
        boolean seguirComprando = true;
        double totalSnacks = 0;
        int productoIndex, cantidad, stockActual;
        double precio;
        String snacksComprados = "";

        while (seguirComprando) {
            System.out.println("\n=== SNACKS DISPONIBLES ===");
            System.out.printf("%-20s %-10s %-10s\n", "PRODUCTO", "PRECIO", "STOCK");

            for (int i = 0; i < snacks.length; i++) {
                if (snacks[i] != null) {
                    System.out.printf("%d. %-20s $%-10s %-10s\n", i + 1, snacks[i][0], snacks[i][1], snacks[i][2]);
                }
            }

            System.out.println("\nSeleccione el numero de producto (0 para terminar):");
            productoIndex = tcl.nextInt() - 1;

            if (productoIndex == -1) {
                seguirComprando = false;
                continue;  // Se salta todas las linea que quedan dentro del while y regresa al inicio del while
            }

            if (productoIndex < 0 || productoIndex >= snacks.length || snacks[productoIndex] == null) {
                System.out.println("Seleccion invalida. Intente nuevamente Porfavor.");
                continue;
            }

            System.out.println("Ingrese cantidad:");
            cantidad = tcl.nextInt();

            stockActual = Integer.parseInt(snacks[productoIndex][2]);
            if (cantidad > stockActual) {
                System.out.println("Cantidad solicitada excede el stock disponible. Intente nuevamente.");
                continue;
            }
            precio = Double.parseDouble(snacks[productoIndex][1]);
            totalSnacks += precio * cantidad;  // Si se quita el mas se sobre escribe el precio y no se acumula para el valor final a pagar 
            snacksComprados += snacks[productoIndex][0] + " x" + cantidad + ", ";
            snacks[productoIndex][2] = String.valueOf(stockActual - cantidad);
        }

        if (totalSnacks > 0) {
            registrarVenta("", "", 0, snacksComprados, totalSnacks);
            System.out.printf("Total snacks: $%.2f\n", totalSnacks);
        }
    }

    public static void registrarVenta(String pelicula, String horario, int cantidadBoletos, String snacksVendidos, double total) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //Crea un formato de fecha personalizado en el formato dd/MM/yyyy.
        Date fecha = new Date(); //new Date() crea un objeto de tipo Date con la fecha y hora actual del sistema.

        ventas[ventasCount] = new String[]{pelicula, horario, String.valueOf(cantidadBoletos), snacksVendidos, String.valueOf(total), formatter.format(fecha)};//  formatter convierte la fecha en un texto con el formato deseado.

        ventasCount++; // Se incrementa ventasCount para que la siguiente venta no sobrescriba la anterior
    }

    public static void mostrarVentas() {
        for (int i = 0; i < ventas.length; i++) {
            if (ventas[i] != null && (ventas[i][0] != null && !ventas[i][0].isEmpty() || ventas[i][3] != null && !ventas[i][3].isEmpty())) { // Evalua que tenga almenos un dato en la matriz

                System.out.printf("%-20s %-10s %-15s %-30s $%-10s %-10s\n",
                        ventas[i][0] != null ? ventas[i][0] : "-",
                        ventas[i][1] != null ? ventas[i][1] : "-",
                        ventas[i][2] != null ? ventas[i][2] : "-",
                        ventas[i][3] != null ? ventas[i][3] : "-",
                        ventas[i][4] != null ? ventas[i][4] : "0.00",
                        ventas[i][5] != null ? ventas[i][5] : "-");
            }
        }
    }
}
/*
run:

=== SISTEMA CINEMAS-IN===
1. Ver Cartelera
2. Comprar Boletos
3. Comprar Snacks
4. Ver Registro de Ventas
5. Salir
1

=== CARTELERA CINEMAS IN===
PELICULA             HORARIO    SALA       PRECIO    
Avengers             14:00      Sala 1     $8.00      
Spiderman            16:00      Sala 2     $7.50      
Batman               18:00      Sala 3     $8.50      
Doctor Strange       20:00      Sala 4     $9.00      
Thor: Ragnarok       13:00      Sala 5     $7.75      
Black Panther        15:30      Sala 6     $8.25      
InterEstelar         17:00      Sala 7     $9.50      
Mufasa               19:30      Sala 8     $8.75      
Iron Man             12:30      Sala 9     $7.25      
Hombre Lobo          14:30      Sala 10    $9.00      
Superman Returns     16:30      Sala 11    $7.80      
Deadpool             18:30      Sala 12    $8.90      
The Flash            20:30      Sala 13    $7.95      
Hulk                 21:00      Sala 14    $8.10      
Shazam!              13:30      Sala 15    $7.60      
Aquaman              15:00      Sala 16    $8.40      
Joker                17:30      Sala 17    $9.20      
X-Men                19:00      Sala 18    $8.30      
Venom                21:30      Sala 19    $8.00      
Ant-Man              22:00      Sala 20    $7.70      

=== SISTEMA CINEMAS-IN===
1. Ver Cartelera
2. Comprar Boletos
3. Comprar Snacks
4. Ver Registro de Ventas
5. Salir
2
Avengers             14:00      Sala 1     $8.00      
Spiderman            16:00      Sala 2     $7.50      
Batman               18:00      Sala 3     $8.50      
Doctor Strange       20:00      Sala 4     $9.00      
Thor: Ragnarok       13:00      Sala 5     $7.75      
Black Panther        15:30      Sala 6     $8.25      
InterEstelar         17:00      Sala 7     $9.50      
Mufasa               19:30      Sala 8     $8.75      
Iron Man             12:30      Sala 9     $7.25      
Hombre Lobo          14:30      Sala 10    $9.00      
Superman Returns     16:30      Sala 11    $7.80      
Deadpool             18:30      Sala 12    $8.90      
The Flash            20:30      Sala 13    $7.95      
Hulk                 21:00      Sala 14    $8.10      
Shazam!              13:30      Sala 15    $7.60      
Aquaman              15:00      Sala 16    $8.40      
Joker                17:30      Sala 17    $9.20      
X-Men                19:00      Sala 18    $8.30      
Venom                21:30      Sala 19    $8.00      
Ant-Man              22:00      Sala 20    $7.70      

Seleccione el numero de pelicula(o numero de la Sala):
5
Ingrese cantidad de boletos:
4
Total a pagar: $15.50

=== SISTEMA CINEMAS-IN===
1. Ver Cartelera
2. Comprar Boletos
3. Comprar Snacks
4. Ver Registro de Ventas
5. Salir
3

=== SNACKS DISPONIBLES ===
PRODUCTO             PRECIO     STOCK     
1. Canguil Grande       $5.00       50        
2. Gaseosa              $2.50       100       
3. Nachos               $4.00       30        
4. Hot Dog              $3.50       40        
5. Chocolates           $2.75       60        

Seleccione el numero de producto (0 para terminar):
-1
Seleccion invalida. Intente nuevamente Porfavor.

=== SNACKS DISPONIBLES ===
PRODUCTO             PRECIO     STOCK     
1. Canguil Grande       $5.00       50        
2. Gaseosa              $2.50       100       
3. Nachos               $4.00       30        
4. Hot Dog              $3.50       40        
5. Chocolates           $2.75       60        

Seleccione el numero de producto (0 para terminar):
6
Seleccion invalida. Intente nuevamente Porfavor.

=== SNACKS DISPONIBLES ===
PRODUCTO             PRECIO     STOCK     
1. Canguil Grande       $5.00       50        
2. Gaseosa              $2.50       100       
3. Nachos               $4.00       30        
4. Hot Dog              $3.50       40        
5. Chocolates           $2.75       60        

Seleccione el numero de producto (0 para terminar):
1
Ingrese cantidad:
5

=== SNACKS DISPONIBLES ===
PRODUCTO             PRECIO     STOCK     
1. Canguil Grande       $5.00       45        
2. Gaseosa              $2.50       100       
3. Nachos               $4.00       30        
4. Hot Dog              $3.50       40        
5. Chocolates           $2.75       60        

Seleccione el numero de producto (0 para terminar):
2
Ingrese cantidad:
5

=== SNACKS DISPONIBLES ===
PRODUCTO             PRECIO     STOCK     
1. Canguil Grande       $5.00       45        
2. Gaseosa              $2.50       95        
3. Nachos               $4.00       30        
4. Hot Dog              $3.50       40        
5. Chocolates           $2.75       60        

Seleccione el numero de producto (0 para terminar):
5
Ingrese cantidad:
5

=== SNACKS DISPONIBLES ===
PRODUCTO             PRECIO     STOCK     
1. Canguil Grande       $5.00       45        
2. Gaseosa              $2.50       95        
3. Nachos               $4.00       30        
4. Hot Dog              $3.50       40        
5. Chocolates           $2.75       55        

Seleccione el numero de producto (0 para terminar):
3
Ingrese cantidad:
31
Cantidad solicitada excede el stock disponible. Intente nuevamente.

=== SNACKS DISPONIBLES ===
PRODUCTO             PRECIO     STOCK     
1. Canguil Grande       $5.00       45        
2. Gaseosa              $2.50       95        
3. Nachos               $4.00       30        
4. Hot Dog              $3.50       40        
5. Chocolates           $2.75       55        

Seleccione el numero de producto (0 para terminar):
0
Total snacks: $51.25

=== SISTEMA CINEMAS-IN===
1. Ver Cartelera
2. Comprar Boletos
3. Comprar Snacks
4. Ver Registro de Ventas
5. Salir
4

=== REGISTRO DE VENTAS ===
PEL�CULA             HORARIO    CANT. BOLETOS   SNACKS                         TOTAL      FECHA     
Thor: Ragnarok       13:00      4                                              $15.5       05/02/2025
                                0               Canguil Grande x5, Gaseosa x5, Chocolates x5,  $51.25      05/02/2025

=== SISTEMA CINEMAS-IN===
1. Ver Cartelera
2. Comprar Boletos
3. Comprar Snacks
4. Ver Registro de Ventas
5. Salir
5
BUILD SUCCESSFUL (total time: 1 minute 1 second)
*/