/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import entity.Application;
import entity.Department;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    
     @Override
    public List<Application> getAll(){
        Query q = em.createQuery("SELECT a FROM Application a ORDER BY a.appName");
        return  (List<Application>) q.getResultList();
    }

    
}
