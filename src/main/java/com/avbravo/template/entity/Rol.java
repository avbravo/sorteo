package com.avbravo.template.entity;


import java.util.List;

public class Rol {

    private String idrol;
    private String rol;
    private String activo;
    
    public Rol() {
    }

    public Rol(String idrol, String rol, String activo) {
        this.idrol = idrol;
        this.rol = rol;
        this.activo = activo;
    }

    

    @Override
    public String toString() {
        return "Rol{" + "idrol=" + idrol + ", rol=" + rol + '}';
    }

    public String getIdrol() {
        return idrol;
    }

    public void setIdrol(String idrol) {
        this.idrol = idrol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    
    

}
