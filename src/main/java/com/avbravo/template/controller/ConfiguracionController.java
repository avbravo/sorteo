/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.template.controller;

import com.avbravo.jmoordb.configuration.JmoordbContext;
import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.template.entity.Usuario;
import java.io.Serializable;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author avbravo
 */
@Named
@RequestScoped
public class ConfiguracionController implements Serializable {
    @Inject
    ApplicationController applicationController;
      private Integer varmaximoparticipantes = 25;
    private Integer varmaximopremios = 4;
     public Integer getVarmaximoparticipantes() {
        return varmaximoparticipantes;
    }

    public void setVarmaximoparticipantes(Integer varmaximoparticipantes) {
        this.varmaximoparticipantes = varmaximoparticipantes;
    }

    public Integer getVarmaximopremios() {
        return varmaximopremios;
    }

    public void setVarmaximopremios(Integer varmaximopremios) {
        this.varmaximopremios = varmaximopremios;
    }
    
    // <editor-fold defaultstate="collapsed" desc="init">
    @PostConstruct
    public void init() {
        try {

         varmaximoparticipantes = applicationController.getMaximoparticipantes();
         varmaximopremios = applicationController.getMaximopremios();
        } catch (Exception e) {
            JsfUtil.errorDialog("init()", e.getLocalizedMessage());
        }
    }// </editor-fold>

    
     public String saveConfiguration(){
        try {
            if (varmaximopremios > varmaximoparticipantes) {
                JsfUtil.warningDialog("Advertencia", "Maximo de premios es mayor que los participantes");
               
                return "";
            }
            if (varmaximopremios < 0) {
                JsfUtil.warningDialog("Advertencia", "Maximo de premios es negativo");
                  
                return "";
            }
            
             if (varmaximoparticipantes < varmaximopremios ) {
                JsfUtil.warningDialog("Advertencia", "Maximo de premios es mayor que los participantes");
                  
                return "";
            }
            if (varmaximoparticipantes < 0) {
                JsfUtil.warningDialog("Advertencia", "Maximo de participantes es negativo");
          
                return "";
            }
            applicationController.setMaximopremios(varmaximopremios);
             applicationController.setMaximoparticipantes(varmaximoparticipantes );
             JsfUtil.infoDialog("Guardado", "Se guardo la configuracion");
        } catch (Exception e) {
               JsfUtil.errorDialog("saveConfiguracion()", e.getLocalizedMessage());
        }
        return "";
    }
}
