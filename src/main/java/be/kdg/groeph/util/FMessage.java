package be.kdg.groeph.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Created with IntelliJ IDEA.
 * User: Vincent
 * Date: 6/03/13
 * Time: 22:51
 * To change this template use File | Settings | File Templates.
 */
public final class FMessage {
    public static void makeFacesMessage(String message, String type) {

            FacesMessage facesMsg = null;
            if (type.equals("info")) {
                facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);
            } else if (type.equals("error")) {
                facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
            }
            if(FacesContext.getCurrentInstance() != null){
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, facesMsg);
            }

        }
}
