package com.mycompany.proyecto.arq;

import com.mycompany.proyecto.arq.Controllers.MenuController;

/**
 *
 * @author lucasbloise
 */
public class ProyectoArq {

    public static void main(String[] args) {
        do {
            MenuController.mostrarMenu();
            MenuController.seleccionarOpcion();
        } while (true);
    }

/**
 *TODO:
 3- Tabla excel opcional
 2- Fix bug de opcion 7,6,3
 *
 */

}
