/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author TrungHTH
 */
@Entity
@Table(name = "Document")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Document.findAll", query = "SELECT d FROM Document d"),
    @NamedQuery(name = "Document.findByDocId", query = "SELECT d FROM Document d WHERE d.docId = :docId"),
    @NamedQuery(name = "Document.findByDocNumber", query = "SELECT d FROM Document d WHERE d.docNumber = :docNumber"),
    @NamedQuery(name = "Document.findByDocSourceNumber", query = "SELECT d FROM Document d WHERE d.docSourceNumber = :docSourceNumber"),
    @NamedQuery(name = "Document.findByDocUpdateDate", query = "SELECT d FROM Document d WHERE d.docUpdateDate = :docUpdateDate"),
    @NamedQuery(name = "Document.findByDocCreateDate", query = "SELECT d FROM Document d WHERE d.docCreateDate = :docCreateDate"),
    @NamedQuery(name = "Document.findByDocContent", query = "SELECT d FROM Document d WHERE d.docContent = :docContent"),
    @NamedQuery(name = "Document.findByDocStatus", query = "SELECT d FROM Document d WHERE d.docStatus = :docStatus"),
    @NamedQuery(name = "Document.findByDocIsValid", query = "SELECT d FROM Document d WHERE d.docIsValid = :docIsValid"),
    @NamedQuery(name = "Document.findByDocValidFrom", query = "SELECT d FROM Document d WHERE d.docValidFrom = :docValidFrom"),
    @NamedQuery(name = "Document.findByDocValidTo", query = "SELECT d FROM Document d WHERE d.docValidTo = :docValidTo"),
    @NamedQuery(name = "Document.findByDocDate", query = "SELECT d FROM Document d WHERE d.docDate = :docDate"),
    @NamedQuery(name = "Document.findByDocType", query = "SELECT d FROM Document d WHERE d.docType = :docType")})
public class Document implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "doc_Id")
    private String docId;
    @Size(max = 50)
    @Column(name = "doc_Number")
    private String docNumber;
    @Size(max = 50)
    @Column(name = "doc_SourceNumber")
    private String docSourceNumber;
    @Column(name = "doc_UpdateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date docUpdateDate;
    @Column(name = "doc_CreateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date docCreateDate;
    @Size(max = 500)
    @Column(name = "doc_Content")
    private String docContent;
    @Column(name = "doc_Status")
    private Short docStatus;
    @Column(name = "doc_IsValid")
    private Boolean docIsValid;
    @Size(max = 10)
    @Column(name = "doc_ValidFrom")
    private String docValidFrom;
    @Size(max = 10)
    @Column(name = "doc_ValidTo")
    private String docValidTo;
    @Size(max = 10)
    @Column(name = "doc_Date")
    private String docDate;
    @Column(name = "doc_Type")
    private Short docType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "docId")
    private Collection<DocumentStorage> documentStorageCollection;
    @JoinColumn(name = "app_Id", referencedColumnName = "app_Id")
    @ManyToOne
    private Application appId;
    @JoinColumn(name = "docType_Id", referencedColumnName = "docType_Id")
    @ManyToOne
    private DocumentType docTypeId;
    @JoinColumn(name = "user_Id", referencedColumnName = "user_Id")
    @ManyToOne
    private Users userId;
    @OneToMany(mappedBy = "docId")
    private Collection<DocumentDetail> documentDetailCollection;

    public Document() {
    }

    public Document(String docId) {
        this.docId = docId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getDocSourceNumber() {
        return docSourceNumber;
    }

    public void setDocSourceNumber(String docSourceNumber) {
        this.docSourceNumber = docSourceNumber;
    }

    public Date getDocUpdateDate() {
        return docUpdateDate;
    }

    public void setDocUpdateDate(Date docUpdateDate) {
        this.docUpdateDate = docUpdateDate;
    }

    public Date getDocCreateDate() {
        return docCreateDate;
    }

    public void setDocCreateDate(Date docCreateDate) {
        this.docCreateDate = docCreateDate;
    }

    public String getDocContent() {
        return docContent;
    }

    public void setDocContent(String docContent) {
        this.docContent = docContent;
    }

    public Short getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(Short docStatus) {
        this.docStatus = docStatus;
    }

    public Boolean getDocIsValid() {
        return docIsValid;
    }

    public void setDocIsValid(Boolean docIsValid) {
        this.docIsValid = docIsValid;
    }

    public String getDocValidFrom() {
        return docValidFrom;
    }

    public void setDocValidFrom(String docValidFrom) {
        this.docValidFrom = docValidFrom;
    }

    public String getDocValidTo() {
        return docValidTo;
    }

    public void setDocValidTo(String docValidTo) {
        this.docValidTo = docValidTo;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public Short getDocType() {
        return docType;
    }

    public void setDocType(Short docType) {
        this.docType = docType;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<DocumentStorage> getDocumentStorageCollection() {
        return documentStorageCollection;
    }

    public void setDocumentStorageCollection(Collection<DocumentStorage> documentStorageCollection) {
        this.documentStorageCollection = documentStorageCollection;
    }

    public Application getAppId() {
        return appId;
    }

    public void setAppId(Application appId) {
        this.appId = appId;
    }

    public DocumentType getDocTypeId() {
        return docTypeId;
    }

    public void setDocTypeId(DocumentType docTypeId) {
        this.docTypeId = docTypeId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<DocumentDetail> getDocumentDetailCollection() {
        return documentDetailCollection;
    }

    public void setDocumentDetailCollection(Collection<DocumentDetail> documentDetailCollection) {
        this.documentDetailCollection = documentDetailCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (docId != null ? docId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Document)) {
            return false;
        }
        Document other = (Document) object;
        if ((this.docId == null && other.docId != null) || (this.docId != null && !this.docId.equals(other.docId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Document[ docId=" + docId + " ]";
    }
    
}
