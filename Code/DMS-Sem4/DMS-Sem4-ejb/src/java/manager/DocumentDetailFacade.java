/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import entity.DocumentDetail;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author TrungHTH
 */
@Stateless
public class DocumentDetailFacade extends AbstractFacade<DocumentDetail> implements DocumentDetailFacadeLocal {
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
        return new DocumentDetail(1);
    }
    public boolean createDocument(DocumentDetail docDetail){
        try {
             em.persist(docDetail);
             return true;
        } catch (Exception e) {
            
        }
       return false;       
        
    }
    
}
