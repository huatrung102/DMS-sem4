/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import entity.Users;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author TrungHTH
 */
@Local
public interface UsersFacadeLocal {

    void create(Users users);

    void edit(Users users);

    void remove(Users users);
    Users login(String username,String password);
    Users find(Object id);
    Users getUserById(String userId);
    
    List<Users> findAll();

    List<Users> getListByDepartment(String depId);

    int count();
    
}
