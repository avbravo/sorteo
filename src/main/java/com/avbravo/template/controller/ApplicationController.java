/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.template.controller;

import com.avbravo.jmoordbutils.JsfUtil;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author avbravo
 */
@Named
@ApplicationScoped
public class ApplicationController implements Serializable {

    private Integer maximoparticipantes = 25;
    private Integer maximopremios = 4;
  

   

    
    
    
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
    
    
   

}
