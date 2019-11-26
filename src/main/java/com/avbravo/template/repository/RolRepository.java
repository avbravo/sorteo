package com.avbravo.template.repository;

import com.avbravo.jmoordb.mongodb.repository.Repository;
import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.template.entity.Rol;
import com.avbravo.template.entity.Usuario;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
@Stateless
public class RolRepository {

    public RolRepository() {
        super();
    }

    public List<Rol> findAll() {
        List<Rol> rolList = new ArrayList<>();
        try {

            rolList.add(new Rol("ADMINISTRADOR", "ADMINISTRADOR", "si"));
          

        } catch (Exception e) {
            JsfUtil.errorDialog("findAll()", e.getLocalizedMessage());
        }
        return rolList;
    }

    public Optional<Rol> findById(Rol rol) {
        try {
            return Optional.of(rol);
        } catch (Exception e) {
            System.out.println("findById() " + e.getLocalizedMessage());
        }
        return Optional.empty();
    }

    public List<Integer> listOfPage(Integer rowsForPage, String sql) {
        List<Integer> pages = new ArrayList<>();
        try {

            Integer size = sizeOfPage(rowsForPage,sql);
            for (int i = 1; i <= size; i++) {
                pages.add(new Integer(i));

            }
            return pages;

        } catch (Exception e) {
            Logger.getLogger(Repository.class.getName() + "listOfPage()").log(Level.SEVERE, null, e);
            
        }
        return pages;

    }

    public Integer sizeOfPage(Integer rowsForPage,String sql) {
        Integer size = 0;
        Integer mod = 0;
        try {
            size = count(sql) / rowsForPage;
            mod = count(sql) % rowsForPage;
            if (mod > 0) {
                size++;
            }
        } catch (Exception e) {

        }
        return size;
    }// </editor-fold>

    public Integer count(String sql) {
        Integer contador = 0;
        try {
//ejecuta el sql ycuenta el numero de registros con esa condicion
        } catch (Exception e) {

        }
        return contador;
    }// </editor-fold>
}
