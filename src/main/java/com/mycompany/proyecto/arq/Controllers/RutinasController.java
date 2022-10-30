package com.mycompany.proyecto.arq.Controllers;

import com.mycompany.proyecto.arq.Data.Estado;
import com.mycompany.proyecto.arq.Data.Proceso;
import com.mycompany.proyecto.arq.Data.Tiempo;


public abstract class RutinasController {

  public static void ejecutarProcesos(boolean esJSFD) {
    ProcesoController.reiniciarProcesos();

    for (Tiempo.tiempo = 0; EstadosController.getProcesosTerminados() <= 2; Tiempo.tiempo++) {

      if (Tiempo.tiempo == GraficoController.grafico[0].length)
        break;

      BloqueoController.incrementarTiempoBloqueado();

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

      if (esJSFD &&
          EstadosController.hayProcesoEn(Estado.NUEVO) &&
          EjecuccionController.hayProcesoEnEjecucion() &&
          EstadosController.getPrimerProcesoEn(Estado.NUEVO).getRafagaActual() < EjecuccionController
              .getProcesoEnEjecucion().getTiempoRequerido()) {
        GraficoController.grafico[EjecuccionController.getProcesoEnEjecucion().getNombreProceso()
            + 1][Tiempo.tiempo] = " X ";
        EjecuccionController.getProcesoEnEjecucion().incrementarTiempoEmpleado();
        Tiempo.tiempo++;
        GraficoController.grafico[5][Tiempo.tiempo] = "5P"
            + EjecuccionController.getProcesoEnEjecucion().getNombreProceso();
        EjecuccionController.getProcesoEnEjecucion().setEstado(Estado.LISTO);
        continue;
      }

      if (EstadosController.hayProcesoEn(Estado.EJECUCCION)
          && EstadosController.getPrimerProcesoEn(Estado.EJECUCCION).deboTerminar()) {
        GraficoController.grafico[5][Tiempo.tiempo] = "6P"
            + EstadosController.getPrimerProcesoEn(Estado.EJECUCCION).getNombreProceso();
        EstadosController.getPrimerProcesoEn(Estado.EJECUCCION).setMomentoDeFinalizacion(Tiempo.tiempo);
        EstadosController.getPrimerProcesoEn(Estado.EJECUCCION).setEstado(Estado.TERMINADO);
        continue;
      }

      if (EstadosController.hayProcesoEn(Estado.EJECUCCION)
          && EstadosController.getPrimerProcesoEn(Estado.EJECUCCION).deboBloquear()) {
        Proceso p = EstadosController.getPrimerProcesoEn(Estado.EJECUCCION);
        p.reducirRafagaProcesamiento();
        if (p.ciclosParaEjecutar.isEmpty()) {
          GraficoController.grafico[5][Tiempo.tiempo] = "6P" + p.getNombreProceso();
          EstadosController.getPrimerProcesoEn(Estado.EJECUCCION).setMomentoDeFinalizacion(Tiempo.tiempo);
          p.setEstado(Estado.TERMINADO);
          continue;
        } else if (!p.ciclosParaEjecutar.isEmpty()) {

          GraficoController.grafico[5][Tiempo.tiempo] = "3P" + p.getNombreProceso();
          p.reiniciarTiempoEjecuccion();
          p.setEstado(Estado.BLOQUEADO);
          continue;

        }

      }

      if (EjecuccionController.hayProcesoEnEjecucion()) {
        GraficoController.grafico[EjecuccionController.getProcesoEnEjecucion().getNombreProceso() +
            1][Tiempo.tiempo] = " X ";
        EjecuccionController.getProcesoEnEjecucion().incrementarTiempoEmpleado();
        continue;
      }

      if (EstadosController.hayProcesoEn(Estado.NUEVO)
          && EstadosController.getPrimerProcesoEn(Estado.NUEVO).getTiempoDeLlegada() <= Tiempo.tiempo) {
        GraficoController.grafico[5][Tiempo.tiempo] = "1P"
            + EstadosController.getPrimerProcesoEn(Estado.NUEVO).getNombreProceso();
        EstadosController.getPrimerProcesoEn(Estado.NUEVO).setEstado(Estado.LISTO);
        continue;
      }

      if (BloqueoController.getPrimerProcesoQueRequieraSerDesbloqueado() != null) {
        Proceso p = BloqueoController.getPrimerProcesoQueRequieraSerDesbloqueado();
        if (p == null)
          return;
        ProcesoController.procesosPorEjecutar.remove(p);
        ProcesoController.procesosPorEjecutar.add(p);
        GraficoController.grafico[5][Tiempo.tiempo] = "4P" + p.getNombreProceso();
        if (p.ciclosParaEjecutar.isEmpty()) {
          GraficoController.grafico[5][Tiempo.tiempo] = "6P" + p.getNombreProceso();
          EstadosController.getPrimerProcesoEn(Estado.EJECUCCION).setMomentoDeFinalizacion(Tiempo.tiempo);
          p.setEstado(Estado.TERMINADO);
        }
        p.reiniciarTiempoBloqueado();
        p.setEstado(Estado.LISTO);
        continue;
      }

      if (EstadosController.hayProcesoEn(Estado.LISTO) || EstadosController.getPrimerProcesoEn(Estado.LISTO) != null) {
        Proceso proceso = null;
        for (Proceso p : ProcesoController.procesosPorEjecutar) {
          if (p.getEstado() == Estado.TERMINADO)
            continue;
          if (p.getEstado() != Estado.LISTO)
            continue;
          if (p.getEstado() != Estado.TERMINADO && p.getEstado() == Estado.LISTO) {
            proceso = EstadosController.getProcesoMasCorto();
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
      // GraficoController.imprimirTabla2();
    }

    if (esJSFD) {
      ComparativaController.tiempoJSFD = Tiempo.tiempo;
    } else {
      ComparativaController.tiempoJSF = Tiempo.tiempo;
    }
    ComparativaController.procesosModificados = ProcesoController.procesosPorEjecutar;
    ComparativaController.procesosOriginales = ProcesoController.procesos;
    Tiempo.tiempo = 0;
    ProcesoController.reiniciarProcesos();
  }
}
