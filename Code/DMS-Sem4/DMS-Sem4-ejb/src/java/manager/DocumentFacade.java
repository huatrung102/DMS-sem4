/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import entity.Document;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 *
 * @author TrungHTH
 */
@Stateless
public class DocumentFacade extends AbstractFacade<Document> implements DocumentFacadeLocal {
    @PersistenceContext(unitName = "DMS-Sem4-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DocumentFacade() {
        super(Document.class);
    }

    @Override
    public boolean createDocument(Document document) {
         try {
            if (!constraintValidationsDetected(document)) {                
                em.persist(document);
                em.flush();
            }
        } catch (Exception e) {
        return false;     
        }
          return true;
    }
    
}
