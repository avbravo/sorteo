/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.template.controller;

// <editor-fold defaultstate="collapsed" desc="imports">
import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.jmoordbutils.JmoordbResourcesFiles;
import com.avbravo.template.repository.RolRepository;
import com.avbravo.template.repository.UsuarioRepository;
import com.avbravo.template.security.LoginController;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.chart.PieChartModel;

// </editor-fold>
/**
 *
 * @author avbravo
 */
@Named
@ViewScoped
public class DashboardIndexController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="fields">  
    private static final long serialVersionUID = 1L;
 Integer totalSolicitado;
    Integer totalAprobado;
    Integer totalRechazado;
    Integer totalCancelado;
    Integer totales;
    Integer totalVehiculos;
    Integer totalVehiculosActivos;
    Integer totalVehiculosInActivos;
    Integer totalVehiculosEnReparacion;

    Integer totalVistoBuenoPendiente;
    Integer totalVistoBuenoAprobado;
    Integer totalVistoBuenoCancelado;
    private PieChartModel pieModelSolicitud;
    private PieChartModel pieModelVistoBueno;
    // </editor-fold>  
// <editor-fold defaultstate="collapsed" desc="repository">
    
    @Inject
    RolRepository rolRepository;
    UsuarioRepository usuarioRepository;

    @Inject
    LoginController loginController;
    @Inject
    JmoordbResourcesFiles rf;

   
    // </editor-fold>

    public PieChartModel getPieModelSolicitud() {
        return pieModelSolicitud;
    }

    public void setPieModelSolicitud(PieChartModel pieModelSolicitud) {
        this.pieModelSolicitud = pieModelSolicitud;
    }

    public PieChartModel getPieModelVistoBueno() {
        return pieModelVistoBueno;
    }

    public void setPieModelVistoBueno(PieChartModel pieModelVistoBueno) {
        this.pieModelVistoBueno = pieModelVistoBueno;
    }

    

    public Integer getTotalVistoBuenoPendiente() {
        return totalVistoBuenoPendiente;
    }

    public void setTotalVistoBuenoPendiente(Integer totalVistoBuenoPendiente) {
        this.totalVistoBuenoPendiente = totalVistoBuenoPendiente;
    }

    public Integer getTotalVistoBuenoAprobado() {
        return totalVistoBuenoAprobado;
    }

    public void setTotalVistoBuenoAprobado(Integer totalVistoBuenoAprobado) {
        this.totalVistoBuenoAprobado = totalVistoBuenoAprobado;
    }

    public Integer getTotalVistoBuenoCancelado() {
        return totalVistoBuenoCancelado;
    }

    public void setTotalVistoBuenoCancelado(Integer totalVistoBuenoCancelado) {
        this.totalVistoBuenoCancelado = totalVistoBuenoCancelado;
    }

    public Integer getTotalCancelado() {
        return totalCancelado;
    }

    public void setTotalCancelado(Integer totalCancelado) {
        this.totalCancelado = totalCancelado;
    }

    public Integer getTotalVehiculos() {
        return totalVehiculos;
    }

    public void setTotalVehiculos(Integer totalVehiculos) {
        this.totalVehiculos = totalVehiculos;
    }

    public Integer getTotalVehiculosActivos() {
        return totalVehiculosActivos;
    }

    public Integer getTotalVehiculosInActivos() {
        return totalVehiculosInActivos;
    }

    public void setTotalVehiculosInActivos(Integer totalVehiculosInActivos) {
        this.totalVehiculosInActivos = totalVehiculosInActivos;
    }

    public void setTotalVehiculosActivos(Integer totalVehiculosActivos) {
        this.totalVehiculosActivos = totalVehiculosActivos;
    }

    public Integer getTotalVehiculosEnReparacion() {
        return totalVehiculosEnReparacion;
    }

    public void setTotalVehiculosEnReparacion(Integer totalVehiculosEnReparacion) {
        this.totalVehiculosEnReparacion = totalVehiculosEnReparacion;
    }

    public Integer getTotales() {
        return totales;
    }

    public void setTotales(Integer totales) {
        this.totales = totales;
    }

    public Integer getTotalSolicitado() {
        return totalSolicitado;
    }

    public void setTotalSolicitado(Integer totalSolicitado) {
        this.totalSolicitado = totalSolicitado;
    }

    public Integer getTotalAprobado() {
        return totalAprobado;
    }

    public void setTotalAprobado(Integer totalAprobado) {
        this.totalAprobado = totalAprobado;
    }

    public Integer getTotalRechazado() {
        return totalRechazado;
    }

    public void setTotalRechazado(Integer totalRechazado) {
        this.totalRechazado = totalRechazado;
    }

    /**
     * Creates a new instance of DashboardController
     */
    public DashboardIndexController() {
    }

    // <editor-fold defaultstate="collapsed" desc="init">
    @PostConstruct
    public void init() {

        calcularTotales();
    } // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="calcularTotales()">
    public void calcularTotales() {
        try {
            pieModelSolicitud = new PieChartModel();
            pieModelVistoBueno = new PieChartModel();
            switch (loginController.getRol().getIdrol()) {
                case "ADMINISTRADOR":
                case "SECRETARIA":
                    totalSolicitado = rolRepository.findAll().size();
                    totalAprobado =  rolRepository.findAll().size() +5;
                    totalRechazado =  rolRepository.findAll().size() +15;
                    totalCancelado =  rolRepository.findAll().size() +25;

                    break;
                case "SUBDIRECTORADMINISTRATIVO":
                    totalSolicitado = rolRepository.findAll().size();
                    totalAprobado =  rolRepository.findAll().size() +5;
                    totalRechazado =  rolRepository.findAll().size() +15;
                    totalCancelado =  rolRepository.findAll().size() +25;

                    break;
                case "COORDINADOR":
                     totalSolicitado = rolRepository.findAll().size();
                    totalAprobado =  rolRepository.findAll().size() +5;
                    totalRechazado =  rolRepository.findAll().size() +15;
                    totalCancelado =  rolRepository.findAll().size() +25;

                        totalVistoBuenoAprobado = 5;
                        totalVistoBuenoCancelado = 15;
                        totalVistoBuenoPendiente = 20;

                  

                    break;

                default:

                    totalSolicitado = rolRepository.findAll().size();
                    totalAprobado =  rolRepository.findAll().size() +5;
                    totalRechazado =  rolRepository.findAll().size() +15;
                    totalCancelado =  rolRepository.findAll().size() +25;

            }
            totales = totalAprobado + totalCancelado + totalRechazado + totalSolicitado;

            //Grafica de solicitudes
            pieModelSolicitud.set("Solicitado", totalSolicitado);
            pieModelSolicitud.set("Aprobado", totalAprobado);
            pieModelSolicitud.set("Rechazado", totalRechazado);
            pieModelSolicitud.set("Cancelado", totalCancelado);

            pieModelSolicitud.setTitle("Solicitudes");
            pieModelSolicitud.setLegendPosition("w");
            pieModelSolicitud.setShowDatatip(true);
            //    pieModelSolicitud.setFill(false);
            pieModelSolicitud.setShowDataLabels(true);
            //  pieModelSolicitud.setDiameter(150);
            pieModelSolicitud.setShadow(false);

            //Grafica de Visto Bueno
            pieModelVistoBueno.set("Pendiente", totalVistoBuenoPendiente);
            pieModelVistoBueno.set("Aprobado", totalVistoBuenoAprobado);
            pieModelVistoBueno.set("Cancelado", totalVistoBuenoCancelado);

            pieModelVistoBueno.setTitle("Visto Bueno");
            pieModelVistoBueno.setLegendPosition("w");
            pieModelVistoBueno.setShowDatatip(true);
            //    pieModelSolicitud.setFill(false);
            pieModelVistoBueno.setShowDataLabels(true);
            //  pieModelSolicitud.setDiameter(150);
            pieModelVistoBueno.setShadow(false);

            //Vehiculos
            totalVehiculos = usuarioRepository.findAll().size();
            totalVehiculosActivos = usuarioRepository.findAll() .size() +15;
            totalVehiculosInActivos = usuarioRepository.findAll() .size() +20;
            totalVehiculosEnReparacion =usuarioRepository.findAll() .size() +30;
            totalVehiculosActivos -= totalVehiculosEnReparacion;

        } catch (Exception e) {
   
          JsfUtil.errorDialog("calcularTotales()", e.getLocalizedMessage());
        }
    }
    // </editor-fold>

}
