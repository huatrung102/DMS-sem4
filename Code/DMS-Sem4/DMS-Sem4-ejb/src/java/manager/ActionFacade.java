/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import entity.Action;
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
public class ActionFacade extends AbstractFacade<Action> implements ActionFacadeLocal {
    @PersistenceContext(unitName = "DMS-Sem4-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    

    public ActionFacade() {
        super(Action.class);
    }

    @Override
    public List<Action> getAll() {
          Query q = em.createQuery("SELECT a FROM Action a ");
        return  (List<Action>) q.getResultList();
    }
    
}
