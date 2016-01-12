/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import entity.DocumentDetail;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author TrungHTH
 */
@Local
public interface DocumentDetailFacadeLocal {

    void create(DocumentDetail documentDetail);
    boolean createDocument(DocumentDetail docDetail);
    void edit(DocumentDetail documentDetail);
    DocumentDetail getDefault();

    void remove(DocumentDetail documentDetail);

    DocumentDetail find(Object id);
    
    DocumentDetail getById(String id);

    List<DocumentDetail> findAll();
    

    List<DocumentDetail> findRange(int[] range);

    int count();
    
}
