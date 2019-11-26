/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.template.security;



import com.avbravo.template.repository.RolRepository;
import com.avbravo.jmoordbsecurity.SecurityInterface;
import com.avbravo.jmoordbutils.JsfUtil;
import com.avbravo.jmoordbutils.email.ManagerEmail;
import com.avbravo.jmoordb.configuration.JmoordbContext;
import com.avbravo.jmoordbutils.JmoordbResourcesFiles;
import com.avbravo.template.entity.Rol;
import com.avbravo.template.entity.Usuario;
import com.avbravo.template.repository.UsuarioRepository;
import com.avbravo.template.services.RolServices;

import java.io.Serializable;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Named
@SessionScoped
public class LoginController implements Serializable, SecurityInterface {
// <editor-fold defaultstate="collapsed" desc="fields">

    @Inject
    private SecurityContext securityContext;
    @Inject
    private ExternalContext externalContext;
    @Inject
    private FacesContext facesContext;

@Inject
RolServices rolServices;

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());
    private HashMap<String, String> parameters = new HashMap<>();

    private String passwordold;
    private String passwordnew;
    private String passwordnewrepeat;

    @Inject
    JmoordbResourcesFiles rf;

    Boolean loggedIn = false;
    private String username;
    private String password;
    private String foto;
    private String id;
    private String key;
    String usernameSelected;
    Boolean recoverSession = false;
    Boolean userwasLoged = false;
    Boolean tokenwassend = false;
    String usernameRecover = "";
    String myemail = "@gmail.com";
    String mytoken = "";
    //Repository
    //Notificaciones
   
    @Inject
    UsuarioRepository usuarioRepository;
    Usuario usuario = new Usuario();
    @Inject
    RolRepository rolRepository;
    Rol rol = new Rol();
    



    public RolServices getRolServices() {
        return rolServices;
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="getter/setter">
    public void setRolServices(RolServices rolServices) {
        this.rolServices = rolServices;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getPasswordold() {
        return passwordold;
    }

    public void setPasswordold(String passwordold) {
        this.passwordold = passwordold;
    }

    public String getPasswordnew() {
        return passwordnew;
    }

    public void setPasswordnew(String passwordnew) {
        this.passwordnew = passwordnew;
    }

    public String getPasswordnewrepeat() {
        return passwordnewrepeat;
    }

    public void setPasswordnewrepeat(String passwordnewrepeat) {
        this.passwordnewrepeat = passwordnewrepeat;
    }

    public String getMyemail() {
        return myemail;
    }

    public void setMyemail(String myemail) {
        this.myemail = myemail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Boolean getTokenwassend() {
        return tokenwassend;
    }

    public void setTokenwassend(Boolean tokenwassend) {
        this.tokenwassend = tokenwassend;
    }

    public String getMytoken() {
        return mytoken;
    }

    public void setMytoken(String mytoken) {
        this.mytoken = mytoken;
    }

    public String getUsernameSelected() {
        return usernameSelected;
    }

    public void setUsernameSelected(String usernameSelected) {
        this.usernameSelected = usernameSelected;
    }

    public Boolean getUserwasLoged() {
        return userwasLoged;
    }

    public void setUserwasLoged(Boolean userwasLoged) {
        this.userwasLoged = userwasLoged;
    }
    // </editor-fold>

// <editor-fold defaultstate="collapsed" desc="init">
    @PostConstruct
    public void init() {
        loggedIn = false;
        recoverSession = false;
        userwasLoged = false;
        tokenwassend = false;
      

        //Configuracion de la base de datos
       
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="destroy">
    @PreDestroy
    public void destroy() {
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="constructor">
    public LoginController() {
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="irLogin">
    public String irLogin() {
//        return "/faces/login";
        return "/login";
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="doLogin">
    public String doLogin() {
        try {

            System.out.println("sorteo"+JsfUtil.encriptar("sorteo"));
            System.out.println("demo"+JsfUtil.encriptar("demo"));
            tokenwassend = false;
            userwasLoged = false;
            loggedIn = true;
            usuario = new Usuario();
         
            if (username == null || password == null) {
                JsfUtil.warningMessage(rf.getAppMessage("login.usernamenotvalid"));
                return null;
            }
  
            usernameRecover = usernameRecoveryOfSession();
            recoverSession = !usernameRecover.equals("");
            if (recoverSession) {
                invalidateCurrentSession();
                //  RequestContext.getCurrentInstance().execute("PF('sessionDialog').show();");
                JsfUtil.warningMessage(rf.getAppMessage("session.procederacerrar"));
                return "";
            }
            if (recoverSession && usernameRecover.equals(username)) {
            } else {
                if (isUserLogged(username)) {
                    userwasLoged = true;
                    JsfUtil.warningMessage(rf.getAppMessage("login.alreadylogged"));
                    if (destroyByUsername(username)) {

                    }
                    return "";
                }

            }
       
            if (!isValidSession(username)) {
                return "";
            }
      
            /**
             * Cargando la configuracion
             */
           

            //----------------------------------------------
//Agregar al context

            JmoordbContext.put("jmoordb_user", usuario);
         
            JmoordbContext.put("jmoordb_rol", rol);
          
//---Injectarlo en el Session
            switch (continueAuthentication()) {
                case SEND_CONTINUE:
                    facesContext.responseComplete();
                    break;
                case SEND_FAILURE:
                    facesContext.addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", null));
                    break;
                case SUCCESS:
                    foto = "img/me.jpg";
                    loggedIn = true;
                    usuario = (Usuario) JmoordbContext.get("jmoordb_user");

                    saveUserInSession(username, 2100);
                 
                    loggedIn = true;
                    JsfUtil.successMessage(rf.getAppMessage("login.welcome") + " " + usuario.getNombre());

                    //Notificaciones que tiene
                 
                   // validadorRoles.validarRoles(rol.getIdrol());
                    System.out.println("=>>> rol.getIdrol"+rol.getIdrol());
                    switch (rol.getIdrol()) {
                        case "DOCENTE":
                            return "/faces/pages/solicituddocente/new.xhtml?faces-redirect=true";
                        case "ADMINISTRATIVO":
                            return "/faces/pages/solicitudadministrativo/new.xhtml?faces-redirect=true";
                        case "COORDINADOR":
                            return "/faces/pages/coordinador/list.xhtml?faces-redirect=true";
                        case "ADMINISTRADOR":
                        case "SUBDIRECTORADMINISTRATIVO":
                        case "SECRETARIA":
             
                        case "CONDUCTOR":
                            System.out.println("voy al index....");
                            return "/faces/pages/index.xhtml?faces-redirect=true";
                        default:
                           JsfUtil.warningDialog(rf.getAppMessage("warning.view"), rf.getMessage("warning.rolnovalidadoenelmenu"));
                    }

                case NOT_DONE:
            }

            //-----------------------------
            //              return "/dashboard.xhtml?faces-redirect=true";
        } catch (Exception e) {
            JsfUtil.errorDialog("doLogin()",e.getLocalizedMessage());
            
        }
        return "";
    }

    // </editor-fold>
    private AuthenticationStatus continueAuthentication() {
        return securityContext.authenticate(
                (HttpServletRequest) externalContext.getRequest(),
                (HttpServletResponse) externalContext.getResponse(),
                AuthenticationParameters.withParams()
                        .credential(new UsernamePasswordCredential(username, password))
        );
    }

   

    // <editor-fold defaultstate="collapsed" desc="sendToken()"> 
    public String sendToken() {
        try {


            ManagerEmail managerEmail = new ManagerEmail();
            String token = tokenOfUsername(username);
            if (!token.equals("")) {

                String texto = rf.getAppMessage("token.forinitsession") + " " + token + rf.getAppMessage("token.forinvalidate ");
                if (managerEmail.send(myemail, rf.getAppMessage("token.tokenofsecurity"), texto, "adminemail@gmail.com", "adminpasswordemail")) {
                    JsfUtil.successMessage(rf.getAppMessage("token.wassendtoemail"));
                    tokenwassend = true;
                } else {
                    JsfUtil.warningMessage(rf.getAppMessage("token.errortosendemail"));
                }
            } else {
                JsfUtil.warningMessage(rf.getAppMessage("token.asiganedtouser"));
            }

        } catch (Exception e) {
            JsfUtil.errorDialog("sendToken()",e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="destroyByUser()"> 

    public String destroyByUser() {
        try {

            userwasLoged = !destroyByUsername(username);
            if (!userwasLoged) {
                JsfUtil.successMessage(rf.getAppMessage("session.destroyedloginagain"));
            } else {
                JsfUtil.successMessage(rf.getAppMessage("session.notdestroyed"));
            }

        } catch (Exception e) {
           JsfUtil.errorDialog("destroyByUser()",e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="destroyWithToken()">

    public String destroyByToken() {
        try {

            userwasLoged = !destroyByToken(username, mytoken);

        } catch (Exception e) {
            JsfUtil.warningMessage(rf.getAppMessage("warning.usernotvalid"));
        }
        return "";
    }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="invalidateCurrentSession"> 

    public String invalidateCurrentSession() {
        try {
            if (invalidateMySession()) {
                JsfUtil.successMessage(rf.getAppMessage("sesion.invalidate"));
            } else {
                JsfUtil.warningMessage(rf.getAppMessage("sesion.errortoinvalidate"));
            }

        } catch (Exception e) {
            JsfUtil.successMessage("invalidateCurrentSession() " + e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="doLogout">

    public String doLogout() {
        return logout("/sorteo/faces/login.xhtml?faces-redirect=true");
    }

    // </editor-fold>


}
