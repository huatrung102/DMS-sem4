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
@Table(name = "User")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUserId", query = "SELECT u FROM User u WHERE u.userId = :userId"),
    @NamedQuery(name = "User.findByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName"),
    @NamedQuery(name = "User.findByUserPassword", query = "SELECT u FROM User u WHERE u.userPassword = :userPassword"),
    @NamedQuery(name = "User.findByUserCreateDate", query = "SELECT u FROM User u WHERE u.userCreateDate = :userCreateDate"),
    @NamedQuery(name = "User.findByUserUpdateDate", query = "SELECT u FROM User u WHERE u.userUpdateDate = :userUpdateDate"),
    @NamedQuery(name = "User.findByUserGender", query = "SELECT u FROM User u WHERE u.userGender = :userGender"),
    @NamedQuery(name = "User.findByUserDOB", query = "SELECT u FROM User u WHERE u.userDOB = :userDOB"),
    @NamedQuery(name = "User.findByUserFullName", query = "SELECT u FROM User u WHERE u.userFullName = :userFullName"),
    @NamedQuery(name = "User.findByUserStatus", query = "SELECT u FROM User u WHERE u.userStatus = :userStatus"),
    @NamedQuery(name = "User.findByUserEmail", query = "SELECT u FROM User u WHERE u.userEmail = :userEmail")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "user_Id")
    private String userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "user_Name")
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "user_Password")
    private String userPassword;
    @Column(name = "user_CreateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date userCreateDate;
    @Column(name = "user_UpdateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date userUpdateDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_Gender")
    private short userGender;
    @Size(max = 10)
    @Column(name = "user_DOB")
    private String userDOB;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "user_FullName")
    private String userFullName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_Status")
    private short userStatus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "user_Email")
    private String userEmail;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<DocumentStorage> documentStorageCollection;
    @JoinColumn(name = "dep_Id", referencedColumnName = "dep_Id")
    @ManyToOne
    private Department depId;
    @JoinColumn(name = "role_Id", referencedColumnName = "role_Id")
    @ManyToOne
    private Role roleId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<DraftStorage> draftStorageCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Document> documentCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<DocumentStaff> documentStaffCollection;

    public User() {
    }

    public User(String userId) {
        this.userId = userId;
    }

    public User(String userId, String userName, String userPassword, short userGender, String userFullName, short userStatus, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userGender = userGender;
        this.userFullName = userFullName;
        this.userStatus = userStatus;
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Date getUserCreateDate() {
        return userCreateDate;
    }

    public void setUserCreateDate(Date userCreateDate) {
        this.userCreateDate = userCreateDate;
    }

    public Date getUserUpdateDate() {
        return userUpdateDate;
    }

    public void setUserUpdateDate(Date userUpdateDate) {
        this.userUpdateDate = userUpdateDate;
    }

    public short getUserGender() {
        return userGender;
    }

    public void setUserGender(short userGender) {
        this.userGender = userGender;
    }

    public String getUserDOB() {
        return userDOB;
    }

    public void setUserDOB(String userDOB) {
        this.userDOB = userDOB;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public short getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(short userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @XmlTransient
    public Collection<DocumentStorage> getDocumentStorageCollection() {
        return documentStorageCollection;
    }

    public void setDocumentStorageCollection(Collection<DocumentStorage> documentStorageCollection) {
        this.documentStorageCollection = documentStorageCollection;
    }

    public Department getDepId() {
        return depId;
    }

    public void setDepId(Department depId) {
        this.depId = depId;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    @XmlTransient
    public Collection<DraftStorage> getDraftStorageCollection() {
        return draftStorageCollection;
    }

    public void setDraftStorageCollection(Collection<DraftStorage> draftStorageCollection) {
        this.draftStorageCollection = draftStorageCollection;
    }

    @XmlTransient
    public Collection<Document> getDocumentCollection() {
        return documentCollection;
    }

    public void setDocumentCollection(Collection<Document> documentCollection) {
        this.documentCollection = documentCollection;
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
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.User[ userId=" + userId + " ]";
    }
    
}
