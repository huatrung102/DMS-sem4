/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import entity.GroupDepartment;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author TrungHTH
 */
@Local
public interface GroupDepartmentFacadeLocal {

    void create(GroupDepartment groupDepartment);

    void edit(GroupDepartment groupDepartment);

    void remove(GroupDepartment groupDepartment);

    GroupDepartment find(Object id);

    List<GroupDepartment> findAll();

    List<GroupDepartment> findRange(int[] range);

    int count();
    
}
