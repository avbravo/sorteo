package com.avbravo.template.services;



import com.avbravo.jmoordb.util.JmoordbUtil;
import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.template.entity.Rol;
import com.avbravo.template.repository.RolRepository;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.Document;

/**
 *
 * @author avbravo
 */
@Stateless
public class RolServices  {
List<Rol> rolList = new ArrayList<>();
@Inject
RolRepository repository;
     public List<Rol> getRolList() {
        try {
            rolList = repository.findAll();
        } catch (Exception e) {
           JsfUtil.errorDialog("getRolList()", e.getLocalizedMessage());
        }
        return rolList;
    }// </editor-fold>

   

}
