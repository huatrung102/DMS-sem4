/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "Application")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Application.findAll", query = "SELECT a FROM Application a"),
    @NamedQuery(name = "Application.findByAppId", query = "SELECT a FROM Application a WHERE a.appId = :appId"),
    @NamedQuery(name = "Application.findByAppName", query = "SELECT a FROM Application a WHERE a.appName = :appName"),
    @NamedQuery(name = "Application.findByAppIsActive", query = "SELECT a FROM Application a WHERE a.appIsActive = :appIsActive"),
    @NamedQuery(name = "Application.findByAppCreateDate", query = "SELECT a FROM Application a WHERE a.appCreateDate = :appCreateDate"),
    @NamedQuery(name = "Application.findByAppIsWorkFlow", query = "SELECT a FROM Application a WHERE a.appIsWorkFlow = :appIsWorkFlow"),
    @NamedQuery(name = "Application.findByAppType", query = "SELECT a FROM Application a WHERE a.appType = :appType"),
    @NamedQuery(name = "Application.findByAppisReply", query = "SELECT a FROM Application a WHERE a.appisReply = :appisReply")})
public class Application implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "app_Id")
    private String appId;
    @Size(max = 100)
    @Column(name = "app_Name")
    private String appName;
    @Column(name = "app_IsActive")
    private Boolean appIsActive;
    @Column(name = "app_CreateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date appCreateDate;
    @Column(name = "app_IsWorkFlow")
    private Boolean appIsWorkFlow;
    @Column(name = "app_Type")
    private Short appType;
    @Column(name = "app_isReply")
    private Boolean appisReply;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appId")
    private Collection<WorkFlow> workFlowCollection;

    public Application() {
    }
    public Application(int dump) {
        appId = "";
        appCreateDate = new Date();
        appIsActive = false;
        appIsWorkFlow = false;
        appName = "";
        appType = 0;
        appisReply = false;
        
    }
    public Application(String appId) {
        this.appId = appId;
        
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Boolean getAppIsActive() {
        return appIsActive;
    }

    public void setAppIsActive(Boolean appIsActive) {
        this.appIsActive = appIsActive;
    }

    public Date getAppCreateDate() {
        return appCreateDate;
    }

    public void setAppCreateDate(Date appCreateDate) {
        this.appCreateDate = appCreateDate;
    }

    public Boolean getAppIsWorkFlow() {
        return appIsWorkFlow;
    }

    public void setAppIsWorkFlow(Boolean appIsWorkFlow) {
        this.appIsWorkFlow = appIsWorkFlow;
    }

    public Short getAppType() {
        return appType;
    }

    public void setAppType(Short appType) {
        this.appType = appType;
    }

    public Boolean getAppisReply() {
        return appisReply;
    }

    public void setAppisReply(Boolean appisReply) {
        this.appisReply = appisReply;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<WorkFlow> getWorkFlowCollection() {
        return workFlowCollection;
    }

    public void setWorkFlowCollection(Collection<WorkFlow> workFlowCollection) {
        this.workFlowCollection = workFlowCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appId != null ? appId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Application)) {
            return false;
        }
        Application other = (Application) object;
        if ((this.appId == null && other.appId != null) || (this.appId != null && !this.appId.equals(other.appId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Application[ appId=" + appId + " ]";
    }
    
}
