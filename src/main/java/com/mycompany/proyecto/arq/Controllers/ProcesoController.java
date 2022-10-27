/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.arq.Controllers;

import java.util.ArrayList;
import java.util.Scanner;

import com.mycompany.proyecto.arq.Estado;
import com.mycompany.proyecto.arq.Proceso;

/**
 *
 * @author lucasbloise
 */
public class ProcesoController {
  static ArrayList<Proceso> procesos = new ArrayList<Proceso>();
  static ArrayList<Proceso> procesosPorEjecutar = new ArrayList<Proceso>();
  private static Scanner sc = new Scanner(System.in);

  public static void cargarProcesos() {

    procesos = new ArrayList<Proceso>();

    for (int i = 0; i < 3; i++) {

      System.out.println("Proceso: " + (i + 1));
      int nombreProceso = i + 1;
      System.out.println("Cantidad de Rafagas de procesamiento");
      int CantidadRafagas = sc.nextInt();
      System.out.println("Tiempo de llegada del proceso");
      int tiempoDeLlegada = sc.nextInt();

      ArrayList<Integer> rafagas = new ArrayList<Integer>();
      int tiempoTotal = 0;

      for (int j = 0; j < CantidadRafagas; j++) {
        System.out.println("Valor de Rafaga N" + (j + 1));

        int tiempoRafaga = sc.nextInt();
        rafagas.add(tiempoRafaga);
        tiempoTotal += tiempoRafaga;
      }

      Proceso p = new Proceso(CantidadRafagas, tiempoDeLlegada, nombreProceso, tiempoTotal, 0, 0, rafagas,
          Estado.NUEVO);


      procesos.add(p);
    }
  }

  public static void reiniciarProcesos(){
    
    procesosPorEjecutar = new ArrayList<Proceso>();
    for(Proceso p : procesos){
      procesosPorEjecutar.add(p.crearAux());
    }
  };
  

  public static void cargaAutomatica() {

    procesos = new ArrayList<Proceso>();

    for (int i = 0; i < 3; i++) {

      System.out.println("Proceso: " + (i + 1));
      int nombreProceso = i + 1;
      int CantidadRafagas = 2;

      int tiempoDeLlegada = 2;

      ArrayList<Integer> rafagas = new ArrayList<Integer>();
      int tiempoTotal = 0;

      for (int j = 0; j < CantidadRafagas; j++) {

        int tiempoRafaga = 2;
        rafagas.add(tiempoRafaga);
        tiempoTotal += tiempoRafaga;
      }

      Proceso p = new Proceso(CantidadRafagas, tiempoDeLlegada, nombreProceso, tiempoTotal, 0, 0, rafagas,
          Estado.NUEVO);

            
      procesos.add(p);
    }
  }

  public static void cargarProcesosPorEjecutar() {
    procesosPorEjecutar = procesos;
  }

}
