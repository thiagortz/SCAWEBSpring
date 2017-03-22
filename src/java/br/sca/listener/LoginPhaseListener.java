/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.sca.listener;

import br.sca.beans.LoginBean;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 *
 * @author Paulo
 */
public class LoginPhaseListener implements PhaseListener{

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext context = event.getFacesContext();

        if("/index.xhtml".equals(context.getViewRoot().getViewId())){
            return;
        }

        LoginBean loginBean = context.getApplication().
                evaluateExpressionGet(context, "#{loginBean}", LoginBean.class);

        if(!loginBean.isUsuarioLogado()){
            NavigationHandler handler = context.getApplication().getNavigationHandler();
            handler.handleNavigation(context, null, "index");

            context.renderResponse();
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {

    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

}
