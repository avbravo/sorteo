/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.template.controller;

import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.template.entity.Decenas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author avbravo
 */
@Named
@ApplicationScoped
public class ApplicationController implements Serializable {

    private Integer numerosPendientes = 0;
    private Boolean iniciado = false;
    private Integer maximoparticipantes = 65;
    private Integer maximopremios = 4;
    private List<Integer> disponiblesList = new ArrayList<>();
    private List<Integer> ganadoresList = new ArrayList<>();
    private List<Decenas> decenasList = new ArrayList<>();
    private List<Integer> aleatoriosList = new ArrayList<>();
    private Integer numeroDecenas = 0;
    Integer numeroGenerado = 0;

    public Integer getNumerosPendientes() {
        return pendientes();
        //return numerosPendientes;
    }

    public void setNumerosPendientes(Integer numerosPendientes) {
        this.numerosPendientes = numerosPendientes;
    }

    public List<Decenas> getDecenasList() {
        return decenasList;
    }

    public void setDecenasList(List<Decenas> decenasList) {
        this.decenasList = decenasList;
    }

    public Integer getNumeroDecenas() {
        return numeroDecenas;
    }

    public void setNumeroDecenas(Integer numeroDecenas) {
        this.numeroDecenas = numeroDecenas;
    }

    public Integer getNumeroGenerado() {
        return numeroGenerado;
    }

    public void setNumeroGenerado(Integer numeroGenerado) {
        this.numeroGenerado = numeroGenerado;
    }

    public Boolean getIniciado() {
        return iniciado;
    }

    public void setIniciado(Boolean iniciado) {
        this.iniciado = iniciado;
    }

    public List<Integer> getDisponiblesList() {
        return disponiblesList;
    }

    public void setDisponiblesList(List<Integer> disponiblesList) {
        this.disponiblesList = disponiblesList;
    }

    public List<Integer> getGanadoresList() {
        return ganadoresList;
    }

    public void setGanadoresList(List<Integer> ganadoresList) {
        this.ganadoresList = ganadoresList;
    }

    /**
     * *
     * Genera la lista de numeros
     *
     * @return
     */
    public String iniciar() {
        numeroGenerado = 0;
        disponiblesList = new ArrayList<>();
        ganadoresList = new ArrayList<>();
        decenasList = new ArrayList<>();
        aleatoriosList = new ArrayList<>();
        try {

            for (int i = 1; i <= maximoparticipantes; i++) {
                disponiblesList.add(new Integer(i));

            }
            numeroDecenas = JsfUtil.decenaDeUnEntero(maximoparticipantes);
            for (int i = 0; i <= numeroDecenas; i++) {
                decenasList.add(new Decenas(i, 0));
            }
            Integer numeroAleatorio = 0;
            Boolean seguir = true;
            Boolean found=false;
            for (int i = 1; i <= maximoparticipantes; i++) {
                seguir=true;
                while (seguir) {
                    numeroAleatorio = JsfUtil.getRandomNumber(1, maximoparticipantes);
                    if (aleatoriosList == null || aleatoriosList.isEmpty()) {
                        aleatoriosList.add(numeroAleatorio);
                        seguir=false;
                    } else {
                        found=false;
                        for (Integer n : aleatoriosList) {
                             if(n.equals(numeroAleatorio)){
                                 found=true;
                             }
                        }
                        if(!found){
                             aleatoriosList.add(numeroAleatorio);
                             seguir=false;
                        }
                    }
                }

            }
            System.out.println("=============aleatorios==========");
            for(Integer i:aleatoriosList){
                System.out.print( i+ " ");
            }

            JsfUtil.infoDialog("Mensaje", "Se preparo el entorno para jugar");
            iniciado = true;
        } catch (Exception e) {
            JsfUtil.errorDialog("iniciar()", e.getLocalizedMessage());
        }
        return "";
//        return "/faces/pages/index";
    }
    // </editor-fold>

    public String goIniciar() {
        return "/faces/pages/configuracion/iniciar";
    }

    public String rechazar() {
        try {
            if (ganadoresList == null || ganadoresList.isEmpty()) {
                JsfUtil.warningDialog("Advertencia", "La lista de ganadores esta vacia");
                return "";
            }
            ganadoresList.forEach((n) -> {
                numeroGenerado = n;
            });

            ganadoresList.remove(numeroGenerado);

            Integer gen = JsfUtil.getRandomNumber(1, maximoparticipantes);

            numeroGenerado = 0;

            JsfUtil.infoDialog("Removido", "Se removio el ultimo premio jugado");
            return "";
        } catch (Exception e) {
            JsfUtil.errorDialog("rechazar()", e.getLocalizedMessage());
        }
        return "";
    }

    public String jugar() {

        Integer number = 0;
        Boolean continuar = true;
        Boolean found = false;
        Integer paseDecena = 0;
        Integer decenaActual = 0;
        Integer gen = 0;
        if (ganadoresList.size() == maximopremios) {
            JsfUtil.warningDialog("jugar", "El juego ya finalizo");
            return "";
        }
        try {
            while (continuar) {

                gen = JsfUtil.getRandomNumber(1, maximoparticipantes);
                if (ganadoresList.isEmpty()) {
                    number = gen;
                    continuar = false;
                } else {
                    //No esta repetido
                    found = false;
                    for (Integer d : ganadoresList) {
                        if (d.equals(gen)) {
                            found = true;
                        }
                    }
                    if (!found) {
                        number = gen;
                        continuar = false;
                    }
                }

//                decenaActual = JsfUtil.decenaDeUnEntero(gen);
//                Integer decenaAnterior = JsfUtil.decenaDeUnEntero(numeroGenerado);
////                System.out.println("----------------------------------------------");
//                System.out.println("Gen (" + gen + ") decena actual (" + decenaActual + ") dec. anterior (" + decenaAnterior + ")");
//                if (paseDecena == 4) {
//                    System.out.println("-------->3 pase");
//                    continuar = false;
//                    //Incrementa el contador de decenas
//
//                } else {
//                    if (!decenaActual.equals(decenasList.get(JsfUtil.getRandomNumber(1, numeroDecenas)).getDecena())) {
//                        System.out.println("..............|a.--> reserved");
//                        continuar = false;
//                    } else {
//                        System.out.println("..............|b.continuar=true |........................");
//                        continuar = true;
//                        paseDecena++;
//                    }
//
//                }
//                //Verifica si se puede asignar a una de las decenas menor
//                if (decenaActual.equals(decenaAnterior)) {
//                    if (paseDecena == 3) {
//                        System.out.println("-------->3 pase");
//                        continuar = false;
//                        //Incrementa el contador de decenas
//
//                    } else {
////                        decenasList.sort(Comparator.comparingInt(Decenas::getContador)
////                        );
//                        if (decenaActual.equals(decenasList.get(JsfUtil.getRandomNumber(1, numeroDecenas)).getDecena())) {
//                            System.out.println("..............|a.--> reserved");
//                            continuar = false;
//                        } else {
//                            System.out.println("..............|b.continuar=true |........................");
//                            continuar = true;
//                            paseDecena++;
//                        }
//
//                    }
//
//                } else {
//                    //ordena inversamente por contador
//                    decenasList.sort(Comparator.comparingInt(Decenas::getContador)
//                    );
//                    if (decenaActual.equals(decenasList.get(0).getDecena())) {
//                        System.out.println("-------> (*) continuar=false......");
//                        continuar = false;
//                    } else {
//                        System.out.println("-------->(*) continuar=true");
//                        continuar = true;
//                        if (paseDecena == 3) {
//                            System.out.println("-------->(*) pase=3");
//                            continuar = false;
//                            //Incrementa el contador de decenas
//
//                        }
//                    }
//
//                }
//                decenasList.sort(Comparator.comparingInt(Decenas::getContador)
//                        .reversed());
//                if (decenaActual.equals(decenasList.get(0).getDecena())) {
//                    continuar = false;
//                    paseDecena++;
//                } else {
//                    if (paseDecena == 4) {
//                        System.out.println("-------->3 pase");
//                        continuar = false;
//
//                    }
//                }
//                }
            }
            numeroGenerado = gen;

            //Verificar las decenas
            ganadoresList.add(gen);
            //Ordena el contador
            decenasList.sort(Comparator.comparingInt(Decenas::getDecena));

            decenasList.get(decenaActual).setContador(decenasList.get(decenaActual).getContador() + 1);
            System.out.println("=====================DECENAS=========================");
            for (Decenas d : decenasList) {
                System.out.println(d.getDecena() + " " + d.getContador());
            }

            if (ganadoresList.size() == maximopremios) {
                JsfUtil.warningDialog("jugar", "Se termino el juego");
                // iniciado = false;
            } else {
                JsfUtil.successMessage("Numero " + gen);
            }
        } catch (Exception e) {
            JsfUtil.errorMessage("jugar()" + e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold>

    public Integer pendientes() {
        try {
            if (ganadoresList == null || ganadoresList.isEmpty()) {
                return maximopremios;
            }
            return maximopremios - ganadoresList.size();
        } catch (Exception e) {
        }
        return 0;
    }

    public String resetear() {
        try {
            ganadoresList = new ArrayList<>();
            iniciado = false;
            iniciar();
        } catch (Exception e) {
            JsfUtil.errorDialog("resetear()", e.getLocalizedMessage());
        }
        return "";
    }

    public Integer getMaximoparticipantes() {
        return maximoparticipantes;
    }

    public void setMaximoparticipantes(Integer maximoparticipantes) {

        this.maximoparticipantes = maximoparticipantes;
    }

    public Integer getMaximopremios() {
        return maximopremios;
    }

    public void setMaximopremios(Integer maximopremios) {
        try {

            this.maximopremios = maximopremios;
        } catch (Exception e) {
            JsfUtil.errorDialog("setMaximopremios", e.getLocalizedMessage());
        }

    }

    public String columnColor(Integer number) {
        String color = "green";
        try {
            for (Integer n : ganadoresList) {
                if (number.equals(n)) {
                    color = "brown";
                }
            }

        } catch (Exception e) {
            JsfUtil.errorDialog("columnColor", e.getLocalizedMessage());
        }
        return color;
    }

    public Boolean isSeguirJugando() {
        return ganadoresList.size() == maximopremios;
    }

    public String reiniciarParticipantes(Integer sizeOld) {

        try {

            for (int i = sizeOld + 1; i <= maximoparticipantes; i++) {
                disponiblesList.add(new Integer(i));

            }
            JsfUtil.infoDialog("Mensaje", "Se preparo el entorno para jugar");

        } catch (Exception e) {
            JsfUtil.errorDialog("reiniciarParticipantes()", e.getLocalizedMessage());
        }
        return "";
    }
}
