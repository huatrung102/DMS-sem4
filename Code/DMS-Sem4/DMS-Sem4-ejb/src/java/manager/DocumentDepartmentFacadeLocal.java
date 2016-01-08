/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import entity.DocumentDepartment;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author TrungHTH
 */
@Local
public interface DocumentDepartmentFacadeLocal {

    void create(DocumentDepartment documentDepartment);

    void edit(DocumentDepartment documentDepartment);

    void remove(DocumentDepartment documentDepartment);

    DocumentDepartment find(Object id);

    List<DocumentDepartment> findAll();

    List<DocumentDepartment> findRange(int[] range);

    int count();
    
}
