/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import entity.WorkFlow;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author TrungHTH
 */
@Stateless
public class WorkFlowFacade extends AbstractFacade<WorkFlow> implements WorkFlowFacadeLocal {
    @PersistenceContext(unitName = "DMS-Sem4-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WorkFlowFacade() {
        super(WorkFlow.class);
    }
    @Override
    public WorkFlow getObjectByStep(WorkFlow workFlow,int nextStep){
        int step;
        step = workFlow.getWorkFlowStep() + nextStep;
        Query q = em.createQuery("SELECT w FROM WorkFlow w where w.appId.appId =:appId and w.workFlowStep=:nextStep")
                .setParameter("appId", workFlow.getAppId().getAppId())
                .setParameter("nextStep", step);
        return (WorkFlow) q.getSingleResult();
    }
    
    @Override
    public WorkFlow getObjectById(String workFlowId){
        Query q = em.createQuery("SELECT w FROM WorkFlow w where w.workFlowId =:workFlowId")
                .setParameter("workFlowId", workFlowId);
        return (WorkFlow) q.getSingleResult();
    }
    
    @Override
    public WorkFlow getObjectByAppId(String appId,double workFlowStep){
        
        Query q = em.createQuery("SELECT w FROM WorkFlow w where w.appId.appId =:appId and w.workFlowStep=:workFlowStep")
                .setParameter("appId", appId )
                .setParameter("workFlowStep", workFlowStep);
        return (WorkFlow) q.getSingleResult();
    }
}
