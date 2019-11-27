/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.template.controller;

import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.jmoordbutils.JsfUtil;
import com.lowagie.text.pdf.ArabicLigaturizer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author avbravo
 */
@Named
@ApplicationScoped
public class ApplicationController implements Serializable {

    private Boolean iniciado = false;
    private Integer maximoparticipantes = 65;
    private Integer maximopremios = 4;
    private List<Integer> disponiblesList = new ArrayList<>();
    private List<Integer> ganadoresList = new ArrayList<>();
    Integer numeroGenerado = 0;

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
        try {

            for (int i = 1; i <= maximoparticipantes; i++) {
                disponiblesList.add(new Integer(i));

            }
            JsfUtil.infoDialog("Mensaje", "Se preparo el entorno para jugar");
            iniciado = true;
        } catch (Exception e) {
            JsfUtil.errorDialog("generarListaNumeros()", e.getLocalizedMessage());
        }
        return "/faces/pages/index";
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
            System.out.println("size() "+ganadoresList.size());
            ganadoresList.remove(numeroGenerado);
            System.out.println("remove-->size() "+ganadoresList.size());
            numeroGenerado=0;
            JsfUtil.infoDialog("Removido", "Se removio el ultimo premio jugado");
                return "";
        } catch (Exception e) {
            JsfUtil.errorDialog("rechazar()" , e.getLocalizedMessage());
        }
        return "";
    }

    public String jugar() {

        Integer number = 0;
        Boolean continuar = true;
        Boolean found = false;
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

            }
            numeroGenerado = gen;
            // disponiblesList.remove(gen);
            ganadoresList.add(gen);
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
