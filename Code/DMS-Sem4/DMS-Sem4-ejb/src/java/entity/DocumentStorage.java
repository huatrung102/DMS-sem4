/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TrungHTH
 */
@Entity
@Table(name = "DocumentStorage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocumentStorage.findAll", query = "SELECT d FROM DocumentStorage d"),
    @NamedQuery(name = "DocumentStorage.findByDocId", query = "SELECT d FROM DocumentStorage d WHERE d.documentStoragePK.docId = :docId"),
    @NamedQuery(name = "DocumentStorage.findByUserId", query = "SELECT d FROM DocumentStorage d WHERE d.documentStoragePK.userId = :userId"),
    @NamedQuery(name = "DocumentStorage.findByDocStoCreateDate", query = "SELECT d FROM DocumentStorage d WHERE d.docStoCreateDate = :docStoCreateDate")})
public class DocumentStorage implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DocumentStoragePK documentStoragePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "docSto_CreateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date docStoCreateDate;
    @JoinColumn(name = "doc_Id", referencedColumnName = "doc_Id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Document document;
    @JoinColumn(name = "user_Id", referencedColumnName = "user_Id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public DocumentStorage() {
    }

    public DocumentStorage(DocumentStoragePK documentStoragePK) {
        this.documentStoragePK = documentStoragePK;
    }

    public DocumentStorage(DocumentStoragePK documentStoragePK, Date docStoCreateDate) {
        this.documentStoragePK = documentStoragePK;
        this.docStoCreateDate = docStoCreateDate;
    }

    public DocumentStorage(String docId, String userId) {
        this.documentStoragePK = new DocumentStoragePK(docId, userId);
    }

    public DocumentStoragePK getDocumentStoragePK() {
        return documentStoragePK;
    }

    public void setDocumentStoragePK(DocumentStoragePK documentStoragePK) {
        this.documentStoragePK = documentStoragePK;
    }

    public Date getDocStoCreateDate() {
        return docStoCreateDate;
    }

    public void setDocStoCreateDate(Date docStoCreateDate) {
        this.docStoCreateDate = docStoCreateDate;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (documentStoragePK != null ? documentStoragePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentStorage)) {
            return false;
        }
        DocumentStorage other = (DocumentStorage) object;
        if ((this.documentStoragePK == null && other.documentStoragePK != null) || (this.documentStoragePK != null && !this.documentStoragePK.equals(other.documentStoragePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DocumentStorage[ documentStoragePK=" + documentStoragePK + " ]";
    }
    
}
