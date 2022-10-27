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
    private int tiempoIngreso;
    private int nombreProceso;
    private int tiempoRequerido = 0;
    private int tiempoEmpleado = 0;
    private int tiempoBloqueado = 0;
    public ArrayList<Integer> ciclosParaEjecutar = new ArrayList<Integer>();
    private Estado estado = Estado.NUEVO;

    public Proceso(int cantidadRafagas, int tiempoIngreso, int nombreProceso, int tiempoRequerido, int tiempoEmpleado,
            int tiempoBloqueado, ArrayList<Integer> ciclosParaEjecutar, Estado estado) {
        this.cantidadRafagas = cantidadRafagas;
        this.tiempoIngreso = tiempoIngreso;
        this.nombreProceso = nombreProceso;
        this.tiempoRequerido = tiempoRequerido;
        this.tiempoEmpleado = tiempoEmpleado;
        this.tiempoBloqueado = tiempoBloqueado;
        this.ciclosParaEjecutar = ciclosParaEjecutar;
    }

    public boolean deboDesbloquear() {
        return this.tiempoBloqueado >= 3; //Tiempo E/S por defecto
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
        return this.ciclosParaEjecutar.size() == 0 ;
    }

    public void reducirRafagaProcesamiento() {
        this.ciclosParaEjecutar.remove(0); //Borro el primer item de la lista de ciclosParaEjecutar
    }

    public int getRafagaActual() {
        return this.ciclosParaEjecutar.get(0); //Traigo el valor del primer item de la lista
    }

    public void incrementarTiempoEmpleado() {
        this.tiempoEmpleado += 1;
    }

    public boolean deboBloquear(){
        return this.estado == Estado.EJECUCCION && this.tiempoEmpleado >= this.ciclosParaEjecutar.get(0);
    }

    public int getTiempoIngreso() {
        return tiempoIngreso;
    }

    public int getTiempoRequerido() {
        return tiempoRequerido;
    }

    public int getTiempoBloqueado() {
        return tiempoBloqueado;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getNombreProceso() {
        return nombreProceso;
    }

    public void incrementarTiempo(int p) {
        this.tiempoRequerido += p;
    }

    public Proceso crearAux (){
        Proceso p = new Proceso(this.cantidadRafagas, this.tiempoIngreso, this.nombreProceso, this.tiempoRequerido, this.tiempoEmpleado, this.tiempoBloqueado, new ArrayList<Integer>(this.ciclosParaEjecutar), Estado.NUEVO);
        return p;
    }

    public void imprimir() {
        System.out.println("Proceso" + this.nombreProceso);
        System.out.println("tiempoIngreso" + this.tiempoIngreso);
        System.out.println("CantidadRafagas" + this.cantidadRafagas);
    }

    @Override
    public String toString(){
        return "{\n" + " nombre:  "  + this.nombreProceso + "\n tiempoBloqueado: " + this.tiempoBloqueado +  "\n rafagas: " + this.ciclosParaEjecutar + "\n tiempoDeEjecucion: " + this.tiempoEmpleado + "\n estado: " + this.estado + "\n}";
    }

}
