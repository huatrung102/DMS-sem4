/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import entity.GroupDepartmentDetail;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author TrungHTH
 */
@Local
public interface GroupDepartmentDetailFacadeLocal {

    void create(GroupDepartmentDetail groupDepartmentDetail);

    void edit(GroupDepartmentDetail groupDepartmentDetail);

    void remove(GroupDepartmentDetail groupDepartmentDetail);

    GroupDepartmentDetail find(Object id);

    List<GroupDepartmentDetail> findAll();

    List<GroupDepartmentDetail> findRange(int[] range);

    int count();
    
}
