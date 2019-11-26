/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package com.avbravo.template.repository;


import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.template.entity.Rol;
import com.avbravo.template.entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;

/**
 *
 * @author avbravo
 */
@Stateless
public class UsuarioRepository  {

       public UsuarioRepository(){
//        super(Usuario.class,"transporte","usuario");
        super();
    }
       
       public Optional<Usuario> findById(Usuario usuario){
           try {
               usuario.setActivo("si");
               usuario.setCargo("--");
               usuario.setCedula("7-7");
               usuario.setCelular("99");
               usuario.setEmail("avbra");
               usuario.setNombre("user1");
               usuario.setPassword("6T5Lv/MrRKEBbhpvjgZk9Q==");
               List<Rol> rolList = new ArrayList<>();
               rolList.add(new Rol("ADMINISTRADOR","ADMINISTRADOR","si")) ;
               rolList.add(new Rol("DOCENTE","DOCENTE","si")) ;
               usuario.setRol(rolList);
               usuario.setUsername("avbravo");
               return Optional.of(usuario);
           } catch (Exception e) {
               JsfUtil.errorDialog("findById()", e.getLocalizedMessage());
           }
           return Optional.empty();
       }
   
 public List<Usuario> findAll() {
        List<Usuario> usuarioList = new ArrayList<>();
        try {

           usuarioList.add(new Usuario());
           usuarioList.add(new Usuario());
           usuarioList.add(new Usuario());

        } catch (Exception e) {
            JsfUtil.errorDialog("findAll()", e.getLocalizedMessage());
        }
        return usuarioList;
    }
}
