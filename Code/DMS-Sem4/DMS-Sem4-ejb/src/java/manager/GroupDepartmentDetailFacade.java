/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import entity.GroupDepartmentDetail;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author TrungHTH
 */
@Stateless
public class GroupDepartmentDetailFacade extends AbstractFacade<GroupDepartmentDetail> implements GroupDepartmentDetailFacadeLocal {
    @PersistenceContext(unitName = "DMS-Sem4-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GroupDepartmentDetailFacade() {
        super(GroupDepartmentDetail.class);
    }
    
}