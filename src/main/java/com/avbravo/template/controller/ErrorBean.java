/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.template.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author avbravo
 */
@RequestScoped
@Named
public class ErrorBean {
     public void throwError(){
        throw new RuntimeException("throwing new error");
    }
}
