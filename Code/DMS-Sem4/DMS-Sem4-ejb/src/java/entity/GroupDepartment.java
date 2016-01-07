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
@Table(name = "GroupDepartment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupDepartment.findAll", query = "SELECT g FROM GroupDepartment g"),
    @NamedQuery(name = "GroupDepartment.findByGroupDepId", query = "SELECT g FROM GroupDepartment g WHERE g.groupDepId = :groupDepId"),
    @NamedQuery(name = "GroupDepartment.findByGroupDepName", query = "SELECT g FROM GroupDepartment g WHERE g.groupDepName = :groupDepName"),
    @NamedQuery(name = "GroupDepartment.findByGroupDepStatus", query = "SELECT g FROM GroupDepartment g WHERE g.groupDepStatus = :groupDepStatus")})
public class GroupDepartment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "groupDep_Id")
    private String groupDepId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "groupDep_Name")
    private String groupDepName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "groupDep_Status")
    private short groupDepStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupDepId")
    private Collection<GroupDepartmentDetail> groupDepartmentDetailCollection;

    public GroupDepartment() {
    }

    public GroupDepartment(String groupDepId) {
        this.groupDepId = groupDepId;
    }

    public GroupDepartment(String groupDepId, String groupDepName, short groupDepStatus) {
        this.groupDepId = groupDepId;
        this.groupDepName = groupDepName;
        this.groupDepStatus = groupDepStatus;
    }

    public String getGroupDepId() {
        return groupDepId;
    }

    public void setGroupDepId(String groupDepId) {
        this.groupDepId = groupDepId;
    }

    public String getGroupDepName() {
        return groupDepName;
    }

    public void setGroupDepName(String groupDepName) {
        this.groupDepName = groupDepName;
    }

    public short getGroupDepStatus() {
        return groupDepStatus;
    }

    public void setGroupDepStatus(short groupDepStatus) {
        this.groupDepStatus = groupDepStatus;
    }

    @XmlTransient
    public Collection<GroupDepartmentDetail> getGroupDepartmentDetailCollection() {
        return groupDepartmentDetailCollection;
    }

    public void setGroupDepartmentDetailCollection(Collection<GroupDepartmentDetail> groupDepartmentDetailCollection) {
        this.groupDepartmentDetailCollection = groupDepartmentDetailCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupDepId != null ? groupDepId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupDepartment)) {
            return false;
        }
        GroupDepartment other = (GroupDepartment) object;
        if ((this.groupDepId == null && other.groupDepId != null) || (this.groupDepId != null && !this.groupDepId.equals(other.groupDepId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.GroupDepartment[ groupDepId=" + groupDepId + " ]";
    }
    
}
