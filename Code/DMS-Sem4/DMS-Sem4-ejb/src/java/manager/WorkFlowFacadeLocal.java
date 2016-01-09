/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import entity.WorkFlow;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author TrungHTH
 */
@Local
public interface WorkFlowFacadeLocal {

    void create(WorkFlow workFlow);

    void edit(WorkFlow workFlow);

    void remove(WorkFlow workFlow);

    WorkFlow find(Object id);

    List<WorkFlow> findAll();

    List<WorkFlow> findRange(int[] range);

    int count();
    
}