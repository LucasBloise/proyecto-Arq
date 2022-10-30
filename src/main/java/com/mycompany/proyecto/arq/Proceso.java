/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.arq;

import java.util.ArrayList;

import com.mycompany.proyecto.arq.Controllers.InfoGlobal;

/**
 *
 * @author lucasbloise
 */
public class Proceso {

    private static int cantidadDeProcesos = 0;
    private int cantidadRafagas;
    private int tiempoDeLlegada;
    private int nombreProceso;
    private int tiempoRequerido = 0;
    private int tiempoEmpleado = 0;
    private int tiempoBloqueado = 0;
    private int momentoDeFinalizacion;
    public ArrayList<Integer> ciclosParaEjecutar = new ArrayList<Integer>();
    private Estado estado = Estado.NUEVO;

    public Proceso(int cantidadRafagas, int tiempoDeLlegada, int nombreProceso, int tiempoRequerido, int tiempoEmpleado,
            int tiempoBloqueado, ArrayList<Integer> ciclosParaEjecutar, Estado estado) {
        this.cantidadRafagas = cantidadRafagas;
        this.tiempoDeLlegada = tiempoDeLlegada;
        this.nombreProceso = nombreProceso;
        this.tiempoRequerido = tiempoRequerido;
        this.tiempoEmpleado = tiempoEmpleado;
        this.tiempoBloqueado = tiempoBloqueado;
        this.ciclosParaEjecutar = ciclosParaEjecutar;
    }

    public boolean haEntrado(int tiempo) {
        return this.tiempoDeLlegada <= tiempo;
    }

    public boolean deboDesbloquear() {
        return this.tiempoBloqueado >= InfoGlobal.getTiempoEntradaSalida();
    }

    public void incrementarTiempoBloqueado() {
        this.tiempoBloqueado += 1;
    }

    public void reiniciarTiempoBloqueado() {
        this.tiempoBloqueado = 0;
    }

    public void reiniciarTiempoEjecuccion() {
        this.tiempoEmpleado = 0;
    }

    public boolean deboTerminar() {
        return this.ciclosParaEjecutar.size() == 0;
    }

    public void reducirRafagaProcesamiento() {
        this.ciclosParaEjecutar.remove(0);
    }

    public int getRafagaActual() {
        return this.ciclosParaEjecutar.get(0);
    }

    public void incrementarTiempoEmpleado() {

        this.tiempoEmpleado += 1;
    }

    public static int getCantidadDeProcesos() {
        return cantidadDeProcesos;
    }

    public boolean deboBloquear() {
        return this.estado == Estado.EJECUCCION && this.tiempoEmpleado >= this.ciclosParaEjecutar.get(0);
    }

    public static void setCantidadDeProcesos(int cantidadDeProcesos) {
        Proceso.cantidadDeProcesos = cantidadDeProcesos;
    }

    public int getCantidadRafagas() {
        return cantidadRafagas;
    }

    public void setCantidadRafagas(int cantidadRafagas) {
        this.cantidadRafagas = cantidadRafagas;
    }

    public void reiniciarTiempoEnEjecucion() {
        this.tiempoEmpleado = 0;
    }

    public int getTiempoDeLlegada() {
        return tiempoDeLlegada;
    }

    public void setTiempoDeLlegada(int tiempoDeLlegada) {
        this.tiempoDeLlegada = tiempoDeLlegada;
    }

    public int getTiempoRequerido() {
        return tiempoRequerido;
    }

    public void setTiempoRequerido(int tiempoRequerido) {
        this.tiempoRequerido = tiempoRequerido;
    }

    public int getTiempoEmpleado() {
        return tiempoEmpleado;
    }

    public int getTiempoBloqueado() {
        return tiempoBloqueado;
    }

    public void setTiempoBloqueado(int tiempoBloqueado) {
        this.tiempoBloqueado = tiempoBloqueado;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getCantidadCiclos() {
        return cantidadRafagas;
    }

    public void setMomentoDeFinalizacion(int momentoDeFinalizacion) {
        this.momentoDeFinalizacion = momentoDeFinalizacion;
    }

    public int getMomentoDeFinalizacion() {
        return momentoDeFinalizacion;
    }

    public void setCantidadCiclos(int CantidadCiclos) {
        this.cantidadRafagas = CantidadCiclos;
    }

    public int getNombreProceso() {
        return nombreProceso;
    }

    public void setNombreProceso(int nombreProceso) {
        this.nombreProceso = nombreProceso;
    }

    public void agregarTiempoProcesamiento(int p) {
        this.ciclosParaEjecutar.add(p);
    }

    public void incrementarTiempo(int p) {
        this.tiempoRequerido += p;
    }

    public Proceso crearAux() {
        Proceso p = new Proceso(this.cantidadRafagas, this.tiempoDeLlegada, this.nombreProceso, this.tiempoRequerido,
                this.tiempoEmpleado, this.tiempoBloqueado, new ArrayList<Integer>(this.ciclosParaEjecutar),
                Estado.NUEVO);
        return p;
    }

    public void imprimir() {
        System.out.println("");
        System.out.println("Proceso: " + this.nombreProceso);
        System.out.println("Tiempo De Llegada: " + this.tiempoDeLlegada);
        System.out.println("Cantidad de Rafagas: " + this.cantidadRafagas);
        for (int i = 0; i < this.ciclosParaEjecutar.size(); i++) {
            System.out.println("Rafaga: " + (i + 1) + " Valor: " + this.ciclosParaEjecutar.get(i));
        }
    }

    @Override
    public String toString() {
        return "{\n" + " nombre:  " + this.nombreProceso + "\n tiempoBloqueado: " + this.tiempoBloqueado
                + "\n rafagas: " + this.ciclosParaEjecutar + "\n tiempoDeEjecucion: " + this.tiempoEmpleado
                + "\n estado: " + this.estado + "\n}";
    }

}