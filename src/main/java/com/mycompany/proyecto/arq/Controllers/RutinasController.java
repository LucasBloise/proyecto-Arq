package com.mycompany.proyecto.arq.Controllers;

import com.mycompany.proyecto.arq.Estado;
import com.mycompany.proyecto.arq.Proceso;

import java.util.ArrayList;

public abstract class RutinasController {

  public static void ejecutarProcesos(boolean esJSFD) {
    ProcesoController.reiniciarProcesos();

    for (Tiempo.tiempo = 0; getProcesosTerminados() <= 2; Tiempo.tiempo++) {
      // Para evitar desbordamientos
      if (Tiempo.tiempo == GraficoController.grafico[0].length)
        break;
      /*
       * if(Tiempo.tiempo == 16){
       * .println(getPrimerProcesoEn(Estado.BLOQUEADO).deboDesbloquear());
       * .println(getPrimerProcesoEn(Estado.BLOQUEADO).getTiempoBloqueado());
       * }
       */

      incrementarTiempoBloqueado();

      // Cargamos procesos a nuevo

      // Grabar datos previos en la tabla
      // GraficoController.grabarEnTabla(); TODO:

      String celda = "";
      for (Proceso p : ProcesoController.procesosPorEjecutar) {
        if (p.getEstado() == Estado.LISTO) {
          celda += p.getNombreProceso();
          GraficoController.grafico[0][Tiempo.tiempo] = celda;
        }
      }
      celda = "";
      for (Proceso p : ProcesoController.procesosPorEjecutar) {
        if (p.getEstado() == Estado.BLOQUEADO) {
          celda += p.getNombreProceso();
          GraficoController.grafico[1][Tiempo.tiempo] = celda;
        }
      }

      // Incrementar tiempo en bloqueo

      if (esJSFD &&
          hayProcesoEn(Estado.NUEVO) &&
          hayProcesoEnEjecucion() &&
          getPrimerProcesoEn(Estado.NUEVO).getRafagaActual() < getProcesoEnEjecucion().getTiempoRequerido()) {
        GraficoController.grafico[getProcesoEnEjecucion().getNombreProceso() + 1][Tiempo.tiempo] = " X ";
        getProcesoEnEjecucion().incrementarTiempoEmpleado();
        Tiempo.tiempo++;
        GraficoController.grafico[5][Tiempo.tiempo] = "5P" + getProcesoEnEjecucion().getNombreProceso();
        getProcesoEnEjecucion().setEstado(Estado.LISTO);
        continue;
      }

      // Preguntamos si debemos bloquear el proceso en ejecucion

      if (hayProcesoEn(Estado.EJECUCCION) && getPrimerProcesoEn(Estado.EJECUCCION).deboTerminar()) {
        GraficoController.grafico[5][Tiempo.tiempo] = "6P" + getPrimerProcesoEn(Estado.EJECUCCION).getNombreProceso();
        getPrimerProcesoEn(Estado.EJECUCCION).setEstado(Estado.TERMINADO);
        continue;
      }

      if (hayProcesoEn(Estado.EJECUCCION) && getPrimerProcesoEn(Estado.EJECUCCION).deboBloquear()) {
        Proceso p = getPrimerProcesoEn(Estado.EJECUCCION);
        p.reducirRafagaProcesamiento();
        if (p.ciclosParaEjecutar.isEmpty()) {
          GraficoController.grafico[5][Tiempo.tiempo] = "6P" + p.getNombreProceso();
          p.setEstado(Estado.TERMINADO);
          continue;
        } else if (!p.ciclosParaEjecutar.isEmpty()) {

          GraficoController.grafico[5][Tiempo.tiempo] = "3P" + p.getNombreProceso();
          p.reiniciarTiempoEjecuccion();
          p.setEstado(Estado.BLOQUEADO);
          continue;

        }

      }

      // Ejecutar mi proceso si no require ser bloqueado
      if (hayProcesoEnEjecucion()) {
        GraficoController.grafico[getProcesoEnEjecucion().getNombreProceso() +
            1][Tiempo.tiempo] = " X ";
        getProcesoEnEjecucion().incrementarTiempoEmpleado();
        continue;
      }

      // Cargar procesos
      if (hayProcesoEn(Estado.NUEVO) && getPrimerProcesoEn(Estado.NUEVO).getTiempoDeLlegada() <= Tiempo.tiempo) {
        GraficoController.grafico[5][Tiempo.tiempo] = "1P" + getPrimerProcesoEn(Estado.NUEVO).getNombreProceso();
        getPrimerProcesoEn(Estado.NUEVO).setEstado(Estado.LISTO);
        continue;
      }

      if (getPrimerProcesoQueRequieraSerDesbloqueado() != null) {
        Proceso p = getPrimerProcesoQueRequieraSerDesbloqueado();
        if (p == null)
          return;
        ProcesoController.procesosPorEjecutar.remove(p);
        ProcesoController.procesosPorEjecutar.add(p);
        GraficoController.grafico[5][Tiempo.tiempo] = "4P" + p.getNombreProceso();
        if (p.ciclosParaEjecutar.isEmpty()) {
          GraficoController.grafico[5][Tiempo.tiempo] = "6P" + p.getNombreProceso();
          p.setEstado(Estado.TERMINADO);
        }
        p.reiniciarTiempoBloqueado();
        p.setEstado(Estado.LISTO);
        continue;
      }

      // Mandar procesos de listo a ejecucion
      if (hayProcesoEn(Estado.LISTO) || getPrimerProcesoEn(Estado.LISTO) != null) {
        Proceso proceso = null;
        for (Proceso p : ProcesoController.procesosPorEjecutar) {
          if (p.getEstado() == Estado.TERMINADO)
            continue;
          if (p.getEstado() != Estado.LISTO)
            continue;
          if (p.getEstado() != Estado.TERMINADO && p.getEstado() == Estado.LISTO) {
            proceso = getProcesoMasCorto();
            break;
          }
        }
        GraficoController.grafico[5][Tiempo.tiempo] = "2P" + proceso.getNombreProceso();
        proceso.setEstado(Estado.EJECUCCION);
        continue;
      }
    }
    if (ComparativaController.imprimirGrafica) {
      GraficoController.imprimirTabla();
    }

    if (esJSFD) {
      ComparativaController.tiempoJSFD = Tiempo.tiempo;
    } else {
      ComparativaController.tiempoJSF = Tiempo.tiempo;
    }

    Tiempo.tiempo = 0;
    ProcesoController.reiniciarProcesos();
  }

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

  public static void incrementarTiempoBloqueado() {
    for (Proceso p : ProcesoController.procesosPorEjecutar) {
      if (p.getEstado() == Estado.BLOQUEADO) {
        p.incrementarTiempoBloqueado();
      }
    }
  }

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
