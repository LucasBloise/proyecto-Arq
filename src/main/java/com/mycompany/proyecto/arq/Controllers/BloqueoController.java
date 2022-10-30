package com.mycompany.proyecto.arq.Controllers;
import com.mycompany.proyecto.arq.Data.Estado;
import com.mycompany.proyecto.arq.Data.Proceso;

public class BloqueoController {
    public static void incrementarTiempoBloqueado() {
        for (Proceso p : ProcesoController.procesosPorEjecutar) {
            if (p.getEstado() == Estado.BLOQUEADO) {
                p.incrementarTiempoBloqueado();
            }
        }
    }

    public static Proceso getPrimerProcesoQueRequieraSerDesbloqueado() {
        Proceso proceso = null;

        for (Proceso p : ProcesoController.procesosPorEjecutar) {
            if (p.getEstado() != Estado.BLOQUEADO)
                continue;
            if (p.deboDesbloquear()) {
                proceso = p;
                break;
            }
        }
        return proceso;
    }

    public static boolean hayProcesosQueRequirenSerDesbloqueados() {
        return getPrimerProcesoQueRequieraSerDesbloqueado() != null;
    }
}
