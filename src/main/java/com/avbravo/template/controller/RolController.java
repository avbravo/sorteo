/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.template.controller;

// <editor-fold defaultstate="collapsed" desc="imports">
import com.avbravo.jmoordb.configuration.JmoordbContext;
import com.avbravo.jmoordb.configuration.JmoordbControllerEnvironment;
import com.avbravo.jmoordb.interfaces.IController;
import com.avbravo.jmoordb.metafactory.JmoordbIntrospection;
import com.avbravo.jmoordb.mongodb.history.services.AutoincrementableServices;
import com.avbravo.jmoordbutils.printer.Printer;

import com.avbravo.jmoordb.mongodb.history.services.ErrorInfoServices;
import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.jmoordb.pojos.JmoordbNotifications;
import com.avbravo.jmoordb.profiles.repository.JmoordbNotificationsRepository;
import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.jmoordbutils.DateUtil;
import com.avbravo.jmoordbutils.JmoordbResourcesFiles;
import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.template.datamodel.RolDataModel;
import com.avbravo.template.entity.Rol;
import com.avbravo.template.entity.Usuario;
import com.avbravo.template.repository.RolRepository;
import com.avbravo.template.services.RolServices;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.primefaces.event.SelectEvent;
// </editor-fold>

/**
 *
 * @authoravbravo
 */
@Named
@ViewScoped
@Getter
@Setter
public class RolController implements Serializable {

// <editor-fold defaultstate="collapsed" desc="fields">  
    private static final long serialVersionUID = 1L;

    private Boolean writable = false;
    //DataModel
    private RolDataModel rolDataModel;

    Integer page = 1;
    Integer rowPage = 25;
    List<Integer> pages = new ArrayList<>();

    //Entity
    Rol rol = new Rol();
    Rol rolSelected;
    Rol rolSearch = new Rol();
    String sql = "";
    //List
    List<Rol> rolList = new ArrayList<>();

    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="reposisitory">
    //Repository
    @Inject
    RolRepository rolRepository;

    @Inject
    JmoordbResourcesFiles rf;
    @Inject
    Printer printer;
    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="services">

    //Notification
    @Inject
    RolServices rolServices;

    //List of Relations
    //Repository of Relations
    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="getter/setter">
    // <editor-fold defaultstate="collapsed" desc="getter/setter">
    public List<Integer> getPages() {

        return rolRepository.listOfPage(rowPage, sql);
    }

    // </editor-fold>
    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="constructor">
    public RolController() {
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="init">
    @PostConstruct
    public void init() {
        try {

            /*
            configurar el ambiente del controller
             */
            HashMap parameters = new HashMap();
            Usuario jmoordb_user = (Usuario) JmoordbContext.get("jmoordb_user");
            //    parameters.put("P_EMPRESA", jmoordb_user.getEmpresa().getDescripcion());

//            String action = "gonew";
//            if (getAction() != null) {
//                action = getAction();
//            }
        } catch (Exception e) {
            JsfUtil.errorDialog("init()", e.getLocalizedMessage());
        }
    }// </editor-fold>

    public String clear() {
        try {
            rolList = rolRepository.findAll();
            rolDataModel = new RolDataModel(rolList);
        } catch (Exception e) {
            JsfUtil.errorDialog("clear()", e.getLocalizedMessage());
        }
        return "";
    }
    
    public String prepareGoList(){
        return "/faces/pages/rol/list";
    }
    
   public String first() {
        try {
            Integer page = 1;
            move(page);
        } catch (Exception e) {
            JmoordbUtil.errorMessage( e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold>
   public String last() {
        try {
            //indicar ultima pagina
            move(page);
        } catch (Exception e) {
            JmoordbUtil.errorMessage( e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="next">
    public String next() {
        try {
            

            move(page);
        } catch (Exception e) {
            JmoordbUtil.errorMessage( e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="back">
    public String back() {
        try {
          
            if (page > 1) {
                page--;
            }
            move(page);
        } catch (Exception e) {
            JmoordbUtil.errorMessage( e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold>

    public void move(Integer page){
        
    }
    
    public String prepareNew(){
        rol = new Rol();
        return "";
    }
    public String prepareGoNew(){
        
        return "/faces/pages/rol/new";
    }
    public String prepareView(){
        
        return "/faces/pages/rol/view";
    }

}
