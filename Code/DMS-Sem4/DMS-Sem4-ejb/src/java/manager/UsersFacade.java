/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import entity.Users;
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
public class UsersFacade extends AbstractFacade<Users> implements UsersFacadeLocal {
    @PersistenceContext(unitName = "DMS-Sem4-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }

    @Override
    public Users login(String username,String password) {
        Query q = null;
        try {
            q = em.createQuery("SELECT u from Users u where u.userName=:username AND u.userPassword =:userpassword")
            .setParameter("username", username)
            .setParameter("userpassword", password);
            return (Users) q.getSingleResult();
        } catch (Exception e) {
            
        }
        return null;
    }

    @Override
    public List<Users> getListByDepartment(String depId) {
        Query q = em.createQuery("SELECT u from Users u where u.depId =:depId")
                .setParameter("depId", depId);
        return (List<Users>) q.getResultList();
    }
    
    @Override
    public Users getUserById(String userId){
        Query q = em.createQuery("SELECT u FROM Users u where u.userId =:userId");
        return (Users) q.getSingleResult();
    }
    
    
}
