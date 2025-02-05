from datetime import datetime, date
import calendar

class CinemasIn:
    def __init__(self):

        # Inicializar las variables como atributos de instancia usando self
        self._cartelera = [
            ["Avengers", "14:00", "Sala 1", "8.00"],
            ["Spiderman", "16:00", "Sala 2", "7.50"],
            ["Batman", "18:00", "Sala 3", "8.50"],
            ["Doctor Strange", "20:00", "Sala 4", "9.00"],
            ["Thor: Ragnarok", "13:00", "Sala 5", "7.75"],
            ["Black Panther", "15:30", "Sala 6", "8.25"],
            ["InterEstelar", "17:00", "Sala 7", "9.50"],
            ["Mufasa", "19:30", "Sala 8", "8.75"],
            ["Iron Man", "12:30", "Sala 9", "7.25"],
            ["Hombre Lobo", "14:30", "Sala 10", "9.00"],
            ["Superman Returns", "16:30", "Sala 11", "7.80"],
            ["Deadpool", "18:30", "Sala 12", "8.90"],
            ["The Flash", "20:30", "Sala 13", "7.95"],
            ["Hulk", "21:00", "Sala 14", "8.10"],
            ["Shazam!", "13:30", "Sala 15", "7.60"],
            ["Aquaman", "15:00", "Sala 16", "8.40"],
            ["Joker", "17:30", "Sala 17", "9.20"],
            ["X-Men", "19:00", "Sala 18", "8.30"],
            ["Venom", "21:30", "Sala 19", "8.00"],
            ["Ant-Man", "22:00", "Sala 20", "7.70"]
        ]

        self._snacks = [
            ["Canguil Grande", "5.00", "50"],
            ["Gaseosa", "2.50", "100"],
            ["Nachos", "4.00", "30"],
            ["Hot Dog", "3.50", "40"],
            ["Chocolates", "2.75", "60"]
        ]

        self._ventas = []

    def mostrar_cartelera(self):
        print("\n")
        print(f"{'PELICULA':<20} {'HORARIO':<10} {'SALA':<10} {'PRECIO':<10}")
        print("-" * 50)
        for pelicula in self._cartelera:  # Usar self._cartelera en lugar de self.cartelera
            print(f"{pelicula[0]:<20} {pelicula[1]:<10} {pelicula[2]:<10} ${pelicula[3]:<10}")

    def calcular_total_boletos(self, precio_base, cantidad):
        total = 0
        dia_semana = date.today().weekday()
        cantidad_hombres = cantidad_mujeres = 0

        if dia_semana in [0, 3]:
            while True:
                print("\nPara días especiales, necesitamos saber la distribución:")
                try:
                    cantidad_hombres = int(input("Ingrese la cantidad de hombres: "))
                    cantidad_mujeres = int(input("Ingrese la cantidad de mujeres: "))
                    if cantidad_hombres + cantidad_mujeres != cantidad:
                        print(
                            "La suma de hombres y mujeres no coincide con la cantidad total de boletos. Intente nuevamente.")
                        continue
                    break
                except ValueError:
                    print("Por favor ingrese números válidos.")
        else:
            cantidad_hombres = cantidad
            cantidad_mujeres = 0

        precio_base = float(precio_base)

        if dia_semana == 0:  # Lunes
            total = (cantidad_hombres * 3.25) + (cantidad_mujeres * precio_base)
        elif dia_semana == 1:  # Martes
            total = (cantidad // 2 + cantidad % 2) * precio_base
        elif dia_semana == 2:  # Miércoles
            total = precio_base * cantidad * 0.5
        elif dia_semana == 3:  # Jueves
            total = (cantidad_mujeres * 3.25) + (cantidad_hombres * precio_base)
        elif dia_semana == 4:  # Viernes
            total = 3.00 * cantidad
        else:  # Fin de semana
            total = precio_base * cantidad

        return round(total, 2)

    def comprar_boletos(self):
        self.mostrar_cartelera()

        while True:
            try:
                pelicula_opcion = int(input("\nSeleccione el número de película (1-20): ")) - 1
                if 0 <= pelicula_opcion < len(self._cartelera):
                    break
                print("Opción inválida. Por favor seleccione un número entre 1 y 20.")
            except ValueError:
                print("Por favor ingrese un número válido.")

        while True:
            try:
                cantidad_boletos = int(input("Ingrese cantidad de boletos: "))
                if cantidad_boletos > 0:
                    break
                print("Por favor ingrese una cantidad válida mayor a 0.")
            except ValueError:
                print("Por favor ingrese un número válido.")

        precio_base = self._cartelera[pelicula_opcion][3]
        total = self.calcular_total_boletos(precio_base, cantidad_boletos)

        print("\n=== RESUMEN DE COMPRA ===")
        print(f"Película: {self._cartelera[pelicula_opcion][0]}")
        print(f"Horario: {self._cartelera[pelicula_opcion][1]}")
        print(f"Sala: {self._cartelera[pelicula_opcion][2]}")
        print(f"Cantidad de boletos: {cantidad_boletos}")
        print(f"Total a pagar: ${total:.2f}")

        self.registrar_venta(
            self._cartelera[pelicula_opcion][0],
            self._cartelera[pelicula_opcion][1],
            cantidad_boletos,
            "",
            total
        )

    def comprar_snacks(self):
        seguir_comprando = True
        total_snacks = 0
        snacks_comprados = ""

        while seguir_comprando:
            print("\n=== SNACKS DISPONIBLES ===")
            print(f"{'#':<3} {'PRODUCTO':<20} {'PRECIO':<10} {'STOCK':<10}")
            print("-" * 45)

            for i, snack in enumerate(self._snacks, 1):
                print(f"{i:<3} {snack[0]:<20} ${snack[1]:<10} {snack[2]:<10}")

            try:
                opcion = input("\nSeleccione el número de producto (0 para terminar): ")
                if opcion == "0":
                    break

                producto_index = int(opcion) - 1

                if not (0 <= producto_index < len(self._snacks)):
                    print("Selección inválida. Intente nuevamente por favor.")
                    continue

                cantidad = int(input("Ingrese cantidad: "))
                if cantidad <= 0:
                    print("Por favor ingrese una cantidad válida mayor a 0.")
                    continue

                stock_actual = int(self._snacks[producto_index][2])

                if cantidad > stock_actual:
                    print(f"Cantidad solicitada excede el stock disponible ({stock_actual}). Intente nuevamente.")
                    continue

                precio = float(self._snacks[producto_index][1])
                subtotal = precio * cantidad
                total_snacks += subtotal
                snacks_comprados += f"{self._snacks[producto_index][0]} x{cantidad}, "
                self._snacks[producto_index][2] = str(stock_actual - cantidad)

                print(f"\nSubtotal actual: ${total_snacks:.2f}")

            except ValueError:
                print("Por favor ingrese un número válido.")
                continue

        if total_snacks > 0:
            snacks_comprados = snacks_comprados.rstrip(", ")
            self.registrar_venta("", "", 0, snacks_comprados, total_snacks)
            print(f"\nTotal snacks: ${total_snacks:.2f}")

    def registrar_venta(self, pelicula, horario, cantidad_boletos, snacks_vendidos, total):
        fecha = datetime.now().strftime("%d/%m/%Y")
        self._ventas.append([
            pelicula if pelicula else "-",
            horario if horario else "-",
            str(cantidad_boletos) if cantidad_boletos else "-",
            snacks_vendidos if snacks_vendidos else "-",
            f"{total:.2f}",
            fecha
        ])

    def mostrar_ventas(self):
        if not self._ventas:
            print("\nNo hay ventas registradas.")
            return

        print(f"\n{'PELÍCULA':<20} {'HORARIO':<10} {'CANT. BOLETOS':<15} {'SNACKS':<30} {'TOTAL':<10} {'FECHA':<10}")
        print("-" * 95)
        for venta in self._ventas:
            print(f"{venta[0]:<20} {venta[1]:<10} {venta[2]:<15} {venta[3]:<30} ${venta[4]:<10} {venta[5]:<10}")

    def run(self):
        while True:
            print("\n=== SISTEMA CINEMAS-IN ===")
            print("1. Ver Cartelera")
            print("2. Comprar Boletos")
            print("3. Comprar Snacks")
            print("4. Ver Registro de Ventas")
            print("5. Salir")
            print("=" * 25)

            try:
                opcion = input("Seleccione una opción: ")
                if opcion == "1":
                    print("\n=== CARTELERA CINEMAS IN ===")
                    self.mostrar_cartelera()
                elif opcion == "2":
                    self.comprar_boletos()
                elif opcion == "3":
                    self.comprar_snacks()
                elif opcion == "4":
                    print("\n=== REGISTRO DE VENTAS ===")
                    self.mostrar_ventas()
                elif opcion == "5":
                    print("\n¡Gracias por usar CINEMAS-IN! ¡Hasta pronto!")
                    break
                else:
                    print("\nOpción incorrecta. Por favor, seleccione una opción válida (1-5).")
            except ValueError:
                print("\nPor favor ingrese un número válido.")



if __name__ == "__main__":
    cinema = CinemasIn()
    cinema.run()
"""
=== SISTEMA CINEMAS-IN ===
1. Ver Cartelera
2. Comprar Boletos
3. Comprar Snacks
4. Ver Registro de Ventas
5. Salir
=========================
Seleccione una opción: 1

=== CARTELERA CINEMAS IN ===


PELICULA             HORARIO    SALA       PRECIO
--------------------------------------------------
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

=== SISTEMA CINEMAS-IN ===
1. Ver Cartelera
2. Comprar Boletos
3. Comprar Snacks
4. Ver Registro de Ventas
5. Salir
=========================
Seleccione una opción: 2


PELICULA             HORARIO    SALA       PRECIO
--------------------------------------------------
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

Seleccione el número de película (1-20): 3
Ingrese cantidad de boletos: 3

=== RESUMEN DE COMPRA ===
Película: Batman
Horario: 18:00
Sala: Sala 3
Cantidad de boletos: 3
Total a pagar: $12.75

=== SISTEMA CINEMAS-IN ===
1. Ver Cartelera
2. Comprar Boletos
3. Comprar Snacks
4. Ver Registro de Ventas
5. Salir
=========================
Seleccione una opción: 3

=== SNACKS DISPONIBLES ===
#   PRODUCTO             PRECIO     STOCK
---------------------------------------------
1   Canguil Grande       $5.00       50
2   Gaseosa              $2.50       100
3   Nachos               $4.00       30
4   Hot Dog              $3.50       40
5   Chocolates           $2.75       60

Seleccione el número de producto (0 para terminar): -1
Selección inválida. Intente nuevamente por favor.

=== SNACKS DISPONIBLES ===
#   PRODUCTO             PRECIO     STOCK
---------------------------------------------
1   Canguil Grande       $5.00       50
2   Gaseosa              $2.50       100
3   Nachos               $4.00       30
4   Hot Dog              $3.50       40
5   Chocolates           $2.75       60

Seleccione el número de producto (0 para terminar): 6
Selección inválida. Intente nuevamente por favor.

=== SNACKS DISPONIBLES ===
#   PRODUCTO             PRECIO     STOCK
---------------------------------------------
1   Canguil Grande       $5.00       50
2   Gaseosa              $2.50       100
3   Nachos               $4.00       30
4   Hot Dog              $3.50       40
5   Chocolates           $2.75       60

Seleccione el número de producto (0 para terminar): 1
Ingrese cantidad: 51
Cantidad solicitada excede el stock disponible (50). Intente nuevamente.

=== SNACKS DISPONIBLES ===
#   PRODUCTO             PRECIO     STOCK
---------------------------------------------
1   Canguil Grande       $5.00       50
2   Gaseosa              $2.50       100
3   Nachos               $4.00       30
4   Hot Dog              $3.50       40
5   Chocolates           $2.75       60

Seleccione el número de producto (0 para terminar): 1
Ingrese cantidad: 5

Subtotal actual: $25.00

=== SNACKS DISPONIBLES ===
#   PRODUCTO             PRECIO     STOCK
---------------------------------------------
1   Canguil Grande       $5.00       45
2   Gaseosa              $2.50       100
3   Nachos               $4.00       30
4   Hot Dog              $3.50       40
5   Chocolates           $2.75       60

Seleccione el número de producto (0 para terminar): 2
Ingrese cantidad: 3

Subtotal actual: $32.50

=== SNACKS DISPONIBLES ===
#   PRODUCTO             PRECIO     STOCK
---------------------------------------------
1   Canguil Grande       $5.00       45
2   Gaseosa              $2.50       97
3   Nachos               $4.00       30
4   Hot Dog              $3.50       40
5   Chocolates           $2.75       60

Seleccione el número de producto (0 para terminar): 0

Total snacks: $32.50

=== SISTEMA CINEMAS-IN ===
1. Ver Cartelera
2. Comprar Boletos
3. Comprar Snacks
4. Ver Registro de Ventas
5. Salir
=========================
Seleccione una opción: 4

=== REGISTRO DE VENTAS ===

PELÍCULA             HORARIO    CANT. BOLETOS   SNACKS                         TOTAL      FECHA
-----------------------------------------------------------------------------------------------
Batman               18:00      3               -                              $12.75      05/02/2025
-                    -          -               Canguil Grande x5, Gaseosa x3  $32.50      05/02/2025

=== SISTEMA CINEMAS-IN ===
1. Ver Cartelera
2. Comprar Boletos
3. Comprar Snacks
4. Ver Registro de Ventas
5. Salir
=========================
Seleccione una opción: 5

¡Gracias por usar CINEMAS-IN! ¡Hasta pronto!

Process finished with exit code 0.
"""