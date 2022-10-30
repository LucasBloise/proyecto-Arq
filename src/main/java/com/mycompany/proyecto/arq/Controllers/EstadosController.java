package com.mycompany.proyecto.arq.Controllers;
import com.mycompany.proyecto.arq.Data.Estado;
import com.mycompany.proyecto.arq.Data.Proceso;

public class EstadosController {

    public static boolean hayProcesoEn(Estado setState) {
        boolean hayProceso = false;
        for (Proceso p : ProcesoController.procesosPorEjecutar) {
            if (p.getEstado() == setState) {
                hayProceso = true;
                break;
            }
        }
        return hayProceso;
    }

    public static Proceso getPrimerProcesoEn(Estado forSetState) {
        Proceso proceso = null;

        for (Proceso p : ProcesoController.procesosPorEjecutar)
            if (p.getEstado() == forSetState) {
                proceso = p;
                break;
            }
        return proceso;
    }


    public static Proceso getProcesoMasCorto() {
        Proceso proceso = null;
        int menorTiempoDeRafagaActual = Integer.MAX_VALUE;

        for (Proceso p : ProcesoController.procesosPorEjecutar) {
            if (p.getEstado() != Estado.LISTO)
                continue;
            if (p.getRafagaActual() < menorTiempoDeRafagaActual) {
                menorTiempoDeRafagaActual = p.getRafagaActual();
            }

        }

        for (Proceso p : ProcesoController.procesosPorEjecutar) {
            if (p.getEstado() != Estado.LISTO)
                continue;
            if (menorTiempoDeRafagaActual == p.getRafagaActual()) {
                proceso = p;
                break;
            }
        }

        return proceso;

    }

    public static int getProcesosTerminados() {

        int cantidadAux = 0;

        for (Proceso p : ProcesoController.procesosPorEjecutar) {
            if (p.getEstado() == Estado.TERMINADO)
                cantidadAux = cantidadAux + 1;
        }

        return cantidadAux;

    }
}
