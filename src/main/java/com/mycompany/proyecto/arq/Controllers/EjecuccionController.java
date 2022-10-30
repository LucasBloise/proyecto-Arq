package com.mycompany.proyecto.arq.Controllers;

import com.mycompany.proyecto.arq.Data.Estado;
import com.mycompany.proyecto.arq.Data.Proceso;

public class EjecuccionController {
    public static boolean hayProcesoEnEjecucion() {
        return getProcesoEnEjecucion() != null;
    }

    public static Proceso getProcesoEnEjecucion() {
        Proceso proceso = null;
        for (Proceso p : ProcesoController.procesosPorEjecutar) {
            if (p.getEstado() == Estado.EJECUCCION)
                proceso = p;
        }
        return proceso;
    }

}
