/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import entity.Action;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author TrungHTH
 */
@Local
public interface ActionFacadeLocal {

    void create(Action action);

    void edit(Action action);

    void remove(Action action);

    Action find(Object id);

    List<Action> findAll();
    List<Action> getAll();
    

    List<Action> findRange(int[] range);

    int count();
    
}
