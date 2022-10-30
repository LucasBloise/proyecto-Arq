package com.mycompany.proyecto.arq.Controllers;

import java.util.ArrayList;

import com.mycompany.proyecto.arq.Data.Proceso;
import com.mycompany.proyecto.arq.Data.Tiempo;

public class ComparativaController {
    public static boolean imprimirGrafica = true;
    public static int tiempoJSF = 0;
    public static int tiempoJSFD = 0;
    public static ArrayList<Proceso> procesosModificados = new ArrayList<Proceso>();
    public static ArrayList<Proceso> procesosOriginales = new ArrayList<Proceso>();

    public static void mostrarComparativa() {
        System.out.print("\033[H\033[2J");
        System.out.println("");

        RutinasController.ejecutarProcesos(false);
        for (Proceso proceso : procesosOriginales) {
            proceso.imprimir();
        }
        System.out.println(" ");
        System.out.println("- - - USANDO JSF - - -");
        estadisticasRendimiento();
        System.out.println("Tiempo de ejecucion total: " + (tiempoJSF - 1));
        limpiarMemoria();
        RutinasController.ejecutarProcesos(true);
        System.out.println(" ");
        System.out.println("- - - USANDO JSDF - - -");
        estadisticasRendimiento();
        System.out.println("Tiempo de ejecucion total: " + (tiempoJSFD - 1));
        limpiarMemoria();

    }

    public static void limpiarMemoria() {
        ProcesoController.procesosPorEjecutar.clear();
        GraficoController.grafico = new String[7][1000];
        Tiempo.tiempo = 0;
    }

    public static void estadisticasRendimiento() {
        float promedio = 0;
        for (Proceso p : procesosModificados) {
            int tiempoTotal = p.getMomentoDeFinalizacion() - p.getTiempoDeLlegada();
            promedio += tiempoTotal;
            System.out.println("El proceso numero " + p.getNombreProceso() + " entro en: "
                    + p.getTiempoDeLlegada() + " unidades de tiempo");
            System.out.println("El proceso numero " + p.getNombreProceso() + " termino su ejecucion en: "
                    + p.getMomentoDeFinalizacion() + " unidades de tiempo");
            System.out.println("El proceso numero " + p.getNombreProceso() + " demora un total de: "
                    + tiempoTotal + " unidades de tiempo");
            System.out.println(" ");
        }

        System.out.println("El tiempo de ejecucion promedio es de: " + promedio / 3);
        System.out.println("");
    }
}
