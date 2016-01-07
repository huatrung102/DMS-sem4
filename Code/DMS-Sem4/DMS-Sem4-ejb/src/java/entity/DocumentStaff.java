/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TrungHTH
 */
@Entity
@Table(name = "DocumentStaff")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocumentStaff.findAll", query = "SELECT d FROM DocumentStaff d"),
    @NamedQuery(name = "DocumentStaff.findByDocStaffId", query = "SELECT d FROM DocumentStaff d WHERE d.docStaffId = :docStaffId"),
    @NamedQuery(name = "DocumentStaff.findByDocStaffIsActive", query = "SELECT d FROM DocumentStaff d WHERE d.docStaffIsActive = :docStaffIsActive")})
public class DocumentStaff implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "docStaff_Id")
    private String docStaffId;
    @Column(name = "docStaff_IsActive")
    private Boolean docStaffIsActive;
    @JoinColumn(name = "docDep_Id", referencedColumnName = "docDep_Id")
    @ManyToOne(optional = false)
    private DocumentDepartment docDepId;
    @JoinColumn(name = "user_Id", referencedColumnName = "user_Id")
    @ManyToOne(optional = false)
    private User userId;

    public DocumentStaff() {
    }

    public DocumentStaff(String docStaffId) {
        this.docStaffId = docStaffId;
    }

    public String getDocStaffId() {
        return docStaffId;
    }

    public void setDocStaffId(String docStaffId) {
        this.docStaffId = docStaffId;
    }

    public Boolean getDocStaffIsActive() {
        return docStaffIsActive;
    }

    public void setDocStaffIsActive(Boolean docStaffIsActive) {
        this.docStaffIsActive = docStaffIsActive;
    }

    public DocumentDepartment getDocDepId() {
        return docDepId;
    }

    public void setDocDepId(DocumentDepartment docDepId) {
        this.docDepId = docDepId;
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
        hash += (docStaffId != null ? docStaffId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentStaff)) {
            return false;
        }
        DocumentStaff other = (DocumentStaff) object;
        if ((this.docStaffId == null && other.docStaffId != null) || (this.docStaffId != null && !this.docStaffId.equals(other.docStaffId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DocumentStaff[ docStaffId=" + docStaffId + " ]";
    }
    
}
