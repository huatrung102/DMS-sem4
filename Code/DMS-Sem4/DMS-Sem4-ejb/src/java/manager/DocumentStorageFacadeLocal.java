/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import entity.DocumentStorage;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author TrungHTH
 */
@Local
public interface DocumentStorageFacadeLocal {

    void create(DocumentStorage documentStorage);

    void edit(DocumentStorage documentStorage);

    void remove(DocumentStorage documentStorage);

    DocumentStorage find(Object id);

    List<DocumentStorage> findAll();

    List<DocumentStorage> findRange(int[] range);

    int count();
    
}
