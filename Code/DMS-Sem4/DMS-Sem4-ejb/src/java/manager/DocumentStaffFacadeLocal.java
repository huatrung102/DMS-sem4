/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import entity.DocumentStaff;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author TrungHTH
 */
@Local
public interface DocumentStaffFacadeLocal {

    void create(DocumentStaff documentStaff);

    void edit(DocumentStaff documentStaff);

    void remove(DocumentStaff documentStaff);

    DocumentStaff find(Object id);

    List<DocumentStaff> findAll();

    List<DocumentStaff> findRange(int[] range);

    int count();
    
}
