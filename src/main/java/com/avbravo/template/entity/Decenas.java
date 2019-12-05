/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.template.entity;

/**
 *
 * @author avbravo
 */
public class Decenas {
    private Integer decena;
    private Integer contador;

    public Decenas() {
    }

    public Decenas(Integer decena, Integer contador) {
        this.decena = decena;
        this.contador = contador;
    }

    public Integer getDecena() {
        return decena;
    }

    public void setDecena(Integer decena) {
        this.decena = decena;
    }

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }
    
    
    
}
