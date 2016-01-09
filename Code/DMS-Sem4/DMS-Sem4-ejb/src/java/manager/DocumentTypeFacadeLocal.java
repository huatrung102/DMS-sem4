/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import entity.DocumentType;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author TrungHTH
 */
@Local
public interface DocumentTypeFacadeLocal {

    void create(DocumentType documentType);

    void edit(DocumentType documentType);

    void remove(DocumentType documentType);

    DocumentType find(Object id);

    List<DocumentType> findAll();
    List<DocumentType> getAll();

    List<DocumentType> findRange(int[] range);

    int count();
    
}
