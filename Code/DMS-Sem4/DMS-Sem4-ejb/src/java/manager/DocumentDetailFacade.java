/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import entity.DocumentDetail;
import java.util.Iterator;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.apache.log4j.Logger;

/**
 *
 * @author TrungHTH
 */
@Stateless
public class DocumentDetailFacade extends AbstractFacade<DocumentDetail> implements DocumentDetailFacadeLocal {
    Logger logger = Logger.getLogger(getClass().getName());
    @PersistenceContext(unitName = "DMS-Sem4-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DocumentDetailFacade() {
        super(DocumentDetail.class);
    }

    @Override
    public DocumentDetail getById(String docDetailId) {
        Query q = em.createQuery("SELECT d FROM DocumentDetail d WHERE d.docDetailId=:docDetailId")
                .setParameter("docDetailId", docDetailId);
        return  (DocumentDetail) q.getSingleResult();
    }
    public DocumentDetail getDefault(){
        try {
            return new DocumentDetail(1);
        } catch (Exception e) {
        }
        return null;
    }
    

    public boolean createDocument(DocumentDetail docDetail){
        try {
            if (!constraintValidationsDetected(docDetail)) {                
              //  em.persist(docDetail);
             //em.merge(docDetail);
                
             em.persist(docDetail);
                em.flush();
            }else{
                return false;  
            }
        } catch (Exception e) {
        return false;     
        }
          return true;
    }
    
}
