/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author TrungHTH
 */
@Entity
@Table(name = "DocumentDepartment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocumentDepartment.findAll", query = "SELECT d FROM DocumentDepartment d"),
    @NamedQuery(name = "DocumentDepartment.findByDocDetailId", query = "SELECT d FROM DocumentDepartment d WHERE d.docDetailId = :docDetailId"),
    @NamedQuery(name = "DocumentDepartment.findByDocDepId", query = "SELECT d FROM DocumentDepartment d WHERE d.docDepId = :docDepId"),
    @NamedQuery(name = "DocumentDepartment.findByDocDepIsActive", query = "SELECT d FROM DocumentDepartment d WHERE d.docDepIsActive = :docDepIsActive")})
public class DocumentDepartment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "docDetail_Id")
    private String docDetailId;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "docDep_Id")
    private String docDepId;
    @Column(name = "docDep_IsActive")
    private Boolean docDepIsActive;
    @JoinColumn(name = "dep_Id", referencedColumnName = "dep_Id")
    @ManyToOne(optional = false)
    private Department depId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "docDepId")
    private Collection<DocumentStaff> documentStaffCollection;

    public DocumentDepartment() {
    }

    public DocumentDepartment(String docDepId) {
        this.docDepId = docDepId;
    }

    public DocumentDepartment(String docDepId, String docDetailId) {
        this.docDepId = docDepId;
        this.docDetailId = docDetailId;
    }

    public String getDocDetailId() {
        return docDetailId;
    }

    public void setDocDetailId(String docDetailId) {
        this.docDetailId = docDetailId;
    }

    public String getDocDepId() {
        return docDepId;
    }

    public void setDocDepId(String docDepId) {
        this.docDepId = docDepId;
    }

    public Boolean getDocDepIsActive() {
        return docDepIsActive;
    }

    public void setDocDepIsActive(Boolean docDepIsActive) {
        this.docDepIsActive = docDepIsActive;
    }

    public Department getDepId() {
        return depId;
    }

    public void setDepId(Department depId) {
        this.depId = depId;
    }

    @XmlTransient
    public Collection<DocumentStaff> getDocumentStaffCollection() {
        return documentStaffCollection;
    }

    public void setDocumentStaffCollection(Collection<DocumentStaff> documentStaffCollection) {
        this.documentStaffCollection = documentStaffCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (docDepId != null ? docDepId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentDepartment)) {
            return false;
        }
        DocumentDepartment other = (DocumentDepartment) object;
        if ((this.docDepId == null && other.docDepId != null) || (this.docDepId != null && !this.docDepId.equals(other.docDepId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DocumentDepartment[ docDepId=" + docDepId + " ]";
    }
    
}
