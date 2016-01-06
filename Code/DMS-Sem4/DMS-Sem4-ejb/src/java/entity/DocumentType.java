/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author TrungHTH
 */
@Entity
@Table(name = "DocumentType")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocumentType.findAll", query = "SELECT d FROM DocumentType d"),
    @NamedQuery(name = "DocumentType.findByDocTypeId", query = "SELECT d FROM DocumentType d WHERE d.docTypeId = :docTypeId"),
    @NamedQuery(name = "DocumentType.findByDocTypeName", query = "SELECT d FROM DocumentType d WHERE d.docTypeName = :docTypeName")})
public class DocumentType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "docType_Id")
    private String docTypeId;
    @Size(max = 50)
    @Column(name = "docType_Name")
    private String docTypeName;
    @OneToMany(mappedBy = "docTypeId")
    private Collection<Document> documentCollection;
    @OneToMany(mappedBy = "docTypeId")
    private Collection<Draft> draftCollection;

    public DocumentType() {
    }

    public DocumentType(String docTypeId) {
        this.docTypeId = docTypeId;
    }

    public String getDocTypeId() {
        return docTypeId;
    }

    public void setDocTypeId(String docTypeId) {
        this.docTypeId = docTypeId;
    }

    public String getDocTypeName() {
        return docTypeName;
    }

    public void setDocTypeName(String docTypeName) {
        this.docTypeName = docTypeName;
    }

    @XmlTransient
    public Collection<Document> getDocumentCollection() {
        return documentCollection;
    }

    public void setDocumentCollection(Collection<Document> documentCollection) {
        this.documentCollection = documentCollection;
    }

    @XmlTransient
    public Collection<Draft> getDraftCollection() {
        return draftCollection;
    }

    public void setDraftCollection(Collection<Draft> draftCollection) {
        this.draftCollection = draftCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (docTypeId != null ? docTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentType)) {
            return false;
        }
        DocumentType other = (DocumentType) object;
        if ((this.docTypeId == null && other.docTypeId != null) || (this.docTypeId != null && !this.docTypeId.equals(other.docTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DocumentType[ docTypeId=" + docTypeId + " ]";
    }
    
}
