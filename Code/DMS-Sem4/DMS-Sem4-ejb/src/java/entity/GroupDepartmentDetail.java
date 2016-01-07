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
@Table(name = "GroupDepartmentDetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupDepartmentDetail.findAll", query = "SELECT g FROM GroupDepartmentDetail g"),
    @NamedQuery(name = "GroupDepartmentDetail.findByGroupDepDetailId", query = "SELECT g FROM GroupDepartmentDetail g WHERE g.groupDepDetailId = :groupDepDetailId")})
public class GroupDepartmentDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "groupDepDetail_Id")
    private String groupDepDetailId;
    @JoinColumn(name = "dep_Id", referencedColumnName = "dep_Id")
    @ManyToOne(optional = false)
    private Department depId;
    @JoinColumn(name = "groupDep_Id", referencedColumnName = "groupDep_Id")
    @ManyToOne(optional = false)
    private GroupDepartment groupDepId;

    public GroupDepartmentDetail() {
    }

    public GroupDepartmentDetail(String groupDepDetailId) {
        this.groupDepDetailId = groupDepDetailId;
    }

    public String getGroupDepDetailId() {
        return groupDepDetailId;
    }

    public void setGroupDepDetailId(String groupDepDetailId) {
        this.groupDepDetailId = groupDepDetailId;
    }

    public Department getDepId() {
        return depId;
    }

    public void setDepId(Department depId) {
        this.depId = depId;
    }

    public GroupDepartment getGroupDepId() {
        return groupDepId;
    }

    public void setGroupDepId(GroupDepartment groupDepId) {
        this.groupDepId = groupDepId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupDepDetailId != null ? groupDepDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupDepartmentDetail)) {
            return false;
        }
        GroupDepartmentDetail other = (GroupDepartmentDetail) object;
        if ((this.groupDepDetailId == null && other.groupDepDetailId != null) || (this.groupDepDetailId != null && !this.groupDepDetailId.equals(other.groupDepDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.GroupDepartmentDetail[ groupDepDetailId=" + groupDepDetailId + " ]";
    }
    
}
