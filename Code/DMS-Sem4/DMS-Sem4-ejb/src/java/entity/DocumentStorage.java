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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "DocumentStorage.findByDocStoCreateDate", query = "SELECT d FROM DocumentStorage d WHERE d.docStoCreateDate = :docStoCreateDate"),
    @NamedQuery(name = "DocumentStorage.findByDocStoId", query = "SELECT d FROM DocumentStorage d WHERE d.docStoId = :docStoId")})
public class DocumentStorage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "docSto_CreateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date docStoCreateDate;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "docSto_Id")
    private String docStoId;
    @JoinColumn(name = "doc_Id", referencedColumnName = "doc_Id")
    @ManyToOne(optional = false)
    private Document docId;
    @JoinColumn(name = "user_Id", referencedColumnName = "user_Id")
    @ManyToOne(optional = false)
    private User userId;

    public DocumentStorage() {
    }

    public DocumentStorage(String docStoId) {
        this.docStoId = docStoId;
    }

    public DocumentStorage(String docStoId, Date docStoCreateDate) {
        this.docStoId = docStoId;
        this.docStoCreateDate = docStoCreateDate;
    }

    public Date getDocStoCreateDate() {
        return docStoCreateDate;
    }

    public void setDocStoCreateDate(Date docStoCreateDate) {
        this.docStoCreateDate = docStoCreateDate;
    }

    public String getDocStoId() {
        return docStoId;
    }

    public void setDocStoId(String docStoId) {
        this.docStoId = docStoId;
    }

    public Document getDocId() {
        return docId;
    }

    public void setDocId(Document docId) {
        this.docId = docId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (docStoId != null ? docStoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentStorage)) {
            return false;
        }
        DocumentStorage other = (DocumentStorage) object;
        if ((this.docStoId == null && other.docStoId != null) || (this.docStoId != null && !this.docStoId.equals(other.docStoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DocumentStorage[ docStoId=" + docStoId + " ]";
    }
    
}
