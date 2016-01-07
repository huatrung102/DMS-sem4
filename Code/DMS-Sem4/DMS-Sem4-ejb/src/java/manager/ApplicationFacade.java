/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import additional.DMSException;
import entity.Application;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author TrungHTH
 */
@Stateless
public class ApplicationFacade extends AbstractFacade<Application> implements ApplicationFacadeLocal {
    @PersistenceContext(unitName = "DMS-Sem4-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ApplicationFacade() {
        super(Application.class);
    }
    public Application getApplicationById(String uid) throws DMSException{
         if(uid == null)
             throw new DMSException("ApplicationId Null");
         Application app = find(uid);
         if(app == null)
             throw new DMSException("Application not found");
         return app;
     }
     
     public List<Application> getAll(){
        return findAll();
    }
}
