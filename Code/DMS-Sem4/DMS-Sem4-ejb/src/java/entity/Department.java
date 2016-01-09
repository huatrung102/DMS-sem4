/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Table(name = "Department")
@XmlRootElement

public class Department implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "dep_Id")
    private String depId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "dep_Name")
    private String depName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dep_Status")
    private short depStatus;
    @Size(max = 10)
    @Column(name = "dep_Code")
    private String depCode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "depId",fetch = FetchType.LAZY)
    private Collection<DocumentDepartment> documentDepartmentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "depId",fetch = FetchType.LAZY)
    
    @JsonBackReference
    private Collection<Users> usersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "depId",fetch = FetchType.LAZY)
    private Collection<GroupDepartmentDetail> groupDepartmentDetailCollection;

    public Department() {
    }

    public Department(String depId) {
        this.depId = depId;
    }

    public Department(String depId, String depName, short depStatus) {
        this.depId = depId;
        this.depName = depName;
        this.depStatus = depStatus;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public short getDepStatus() {
        return depStatus;
    }

    public void setDepStatus(short depStatus) {
        this.depStatus = depStatus;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<DocumentDepartment> getDocumentDepartmentCollection() {
        return documentDepartmentCollection;
    }

    public void setDocumentDepartmentCollection(Collection<DocumentDepartment> documentDepartmentCollection) {
        this.documentDepartmentCollection = documentDepartmentCollection;
    }
    
    @XmlTransient
  //  @JsonManagedReference
    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<GroupDepartmentDetail> getGroupDepartmentDetailCollection() {
        return groupDepartmentDetailCollection;
    }

    public void setGroupDepartmentDetailCollection(Collection<GroupDepartmentDetail> groupDepartmentDetailCollection) {
        this.groupDepartmentDetailCollection = groupDepartmentDetailCollection;
    }

    
}
