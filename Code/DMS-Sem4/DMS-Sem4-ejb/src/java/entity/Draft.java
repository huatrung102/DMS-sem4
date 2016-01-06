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

/**
 *
 * @author TrungHTH
 */
@Entity
@Table(name = "Draft")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Draft.findAll", query = "SELECT d FROM Draft d"),
    @NamedQuery(name = "Draft.findByDraftId", query = "SELECT d FROM Draft d WHERE d.draftId = :draftId"),
    @NamedQuery(name = "Draft.findByDraftNumber", query = "SELECT d FROM Draft d WHERE d.draftNumber = :draftNumber"),
    @NamedQuery(name = "Draft.findByDraftCreateDate", query = "SELECT d FROM Draft d WHERE d.draftCreateDate = :draftCreateDate"),
    @NamedQuery(name = "Draft.findByDraftUpdateDate", query = "SELECT d FROM Draft d WHERE d.draftUpdateDate = :draftUpdateDate"),
    @NamedQuery(name = "Draft.findByDraftStatus", query = "SELECT d FROM Draft d WHERE d.draftStatus = :draftStatus"),
    @NamedQuery(name = "Draft.findByDraftContent", query = "SELECT d FROM Draft d WHERE d.draftContent = :draftContent")})
public class Draft implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "draft_Id")
    private String draftId;
    @Size(max = 50)
    @Column(name = "draft_Number")
    private String draftNumber;
    @Column(name = "draft_CreateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date draftCreateDate;
    @Column(name = "draft_UpdateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date draftUpdateDate;
    @Column(name = "draft_Status")
    private Short draftStatus;
    @Size(max = 500)
    @Column(name = "draft_Content")
    private String draftContent;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "draft")
    private Collection<DraftStorage> draftStorageCollection;
    @JoinColumn(name = "app_Id", referencedColumnName = "app_Id")
    @ManyToOne
    private Application appId;
    @JoinColumn(name = "docType_Id", referencedColumnName = "docType_Id")
    @ManyToOne
    private DocumentType docTypeId;

    public Draft() {
    }

    public Draft(String draftId) {
        this.draftId = draftId;
    }

    public String getDraftId() {
        return draftId;
    }

    public void setDraftId(String draftId) {
        this.draftId = draftId;
    }

    public String getDraftNumber() {
        return draftNumber;
    }

    public void setDraftNumber(String draftNumber) {
        this.draftNumber = draftNumber;
    }

    public Date getDraftCreateDate() {
        return draftCreateDate;
    }

    public void setDraftCreateDate(Date draftCreateDate) {
        this.draftCreateDate = draftCreateDate;
    }

    public Date getDraftUpdateDate() {
        return draftUpdateDate;
    }

    public void setDraftUpdateDate(Date draftUpdateDate) {
        this.draftUpdateDate = draftUpdateDate;
    }

    public Short getDraftStatus() {
        return draftStatus;
    }

    public void setDraftStatus(Short draftStatus) {
        this.draftStatus = draftStatus;
    }

    public String getDraftContent() {
        return draftContent;
    }

    public void setDraftContent(String draftContent) {
        this.draftContent = draftContent;
    }

    @XmlTransient
    public Collection<DraftStorage> getDraftStorageCollection() {
        return draftStorageCollection;
    }

    public void setDraftStorageCollection(Collection<DraftStorage> draftStorageCollection) {
        this.draftStorageCollection = draftStorageCollection;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (draftId != null ? draftId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Draft)) {
            return false;
        }
        Draft other = (Draft) object;
        if ((this.draftId == null && other.draftId != null) || (this.draftId != null && !this.draftId.equals(other.draftId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Draft[ draftId=" + draftId + " ]";
    }
    
}
