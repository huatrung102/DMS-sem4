/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;

import entity.Document;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author TrungHTH
 */
@Local
public interface DocumentFacadeLocal {

    void create(Document document);
    boolean createDocument(Document document);
    void edit(Document document);

    void remove(Document document);

    Document find(Object id);
    boolean removeDocument(String docId);
    List<Document> findAll();

    List<Document> findRange(int[] range);

    int count();
    
}
