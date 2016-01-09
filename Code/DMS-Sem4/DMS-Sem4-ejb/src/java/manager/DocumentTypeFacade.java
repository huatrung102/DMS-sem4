/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import entity.Department;
import entity.DocumentType;
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
public class DocumentTypeFacade extends AbstractFacade<DocumentType> implements DocumentTypeFacadeLocal {
    @PersistenceContext(unitName = "DMS-Sem4-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DocumentTypeFacade() {
        super(DocumentType.class);
    }
    @Override
    public List<DocumentType> getAll(){
        Query q = em.createQuery("SELECT d from DocumentType d ORDER BY d.docTypeName");
        return  (List<DocumentType>) q.getResultList();
    }
    
}
