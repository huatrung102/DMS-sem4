/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "WorkFlow")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkFlow.findAll", query = "SELECT w FROM WorkFlow w"),
    @NamedQuery(name = "WorkFlow.findByWorkFlowId", query = "SELECT w FROM WorkFlow w WHERE w.workFlowId = :workFlowId"),
    @NamedQuery(name = "WorkFlow.findByWorkFlowName", query = "SELECT w FROM WorkFlow w WHERE w.workFlowName = :workFlowName"),
    @NamedQuery(name = "WorkFlow.findByWorkFlowStep", query = "SELECT w FROM WorkFlow w WHERE w.workFlowStep = :workFlowStep"),
    @NamedQuery(name = "WorkFlow.findByWorkFlowType", query = "SELECT w FROM WorkFlow w WHERE w.workFlowType = :workFlowType"),
    @NamedQuery(name = "WorkFlow.findByWorkFlowIsAllowReturn", query = "SELECT w FROM WorkFlow w WHERE w.workFlowIsAllowReturn = :workFlowIsAllowReturn"),
    @NamedQuery(name = "WorkFlow.findByWorkFlowIsAllowRemove", query = "SELECT w FROM WorkFlow w WHERE w.workFlowIsAllowRemove = :workFlowIsAllowRemove"),
    @NamedQuery(name = "WorkFlow.findByWorkFlowIsAllowFinish", query = "SELECT w FROM WorkFlow w WHERE w.workFlowIsAllowFinish = :workFlowIsAllowFinish"),
    @NamedQuery(name = "WorkFlow.findByWorkFlowIsAllowUpload", query = "SELECT w FROM WorkFlow w WHERE w.workFlowIsAllowUpload = :workFlowIsAllowUpload"),
    @NamedQuery(name = "WorkFlow.findByWorkFlowIsSetViewed", query = "SELECT w FROM WorkFlow w WHERE w.workFlowIsSetViewed = :workFlowIsSetViewed"),
    @NamedQuery(name = "WorkFlow.findByWorkFlowIsSubmit", query = "SELECT w FROM WorkFlow w WHERE w.workFlowIsSubmit = :workFlowIsSubmit"),
    @NamedQuery(name = "WorkFlow.findByWorkFlowIsTransferMultiple", query = "SELECT w FROM WorkFlow w WHERE w.workFlowIsTransferMultiple = :workFlowIsTransferMultiple"),
    @NamedQuery(name = "WorkFlow.findByWorkFlowIsGenerateNumber", query = "SELECT w FROM WorkFlow w WHERE w.workFlowIsGenerateNumber = :workFlowIsGenerateNumber")})
public class WorkFlow implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "workFlow_Id")
    private String workFlowId;
    @Size(max = 200)
    @Column(name = "workFlow_Name")
    private String workFlowName;
    @Column(name = "workFlow_Step")
    private Short workFlowStep;
    @Column(name = "workFlow_Type")
    private Short workFlowType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "workFlow_IsAllowReturn")
    private boolean workFlowIsAllowReturn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "workFlow_IsAllowRemove")
    private boolean workFlowIsAllowRemove;
    @Basic(optional = false)
    @NotNull
    @Column(name = "workFlow_IsAllowFinish")
    private boolean workFlowIsAllowFinish;
    @Basic(optional = false)
    @NotNull
    @Column(name = "workFlow_IsAllowUpload")
    private boolean workFlowIsAllowUpload;
    @Basic(optional = false)
    @NotNull
    @Column(name = "workFlow_IsSetViewed")
    private boolean workFlowIsSetViewed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "workFlow_IsSubmit")
    private boolean workFlowIsSubmit;
    @Basic(optional = false)
    @NotNull
    @Column(name = "workFlow_IsTransferMultiple")
    private boolean workFlowIsTransferMultiple;
    @Basic(optional = false)
    @NotNull
    @Column(name = "workFlow_IsGenerateNumber")
    private boolean workFlowIsGenerateNumber;
    @OneToMany(mappedBy = "workFlowId")
    private Collection<DraftDetail> draftDetailCollection;
    @OneToMany(mappedBy = "workFlowId")
    private Collection<DocumentDetail> documentDetailCollection;
    @JoinColumn(name = "app_Id", referencedColumnName = "app_Id")
    @ManyToOne(optional = false)
    private Application appId;
    @JoinColumn(name = "role_Id", referencedColumnName = "role_Id")
    @ManyToOne
    private Role roleId;

    public WorkFlow() {
    }

    public WorkFlow(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    public WorkFlow(String workFlowId, boolean workFlowIsAllowReturn, boolean workFlowIsAllowRemove, boolean workFlowIsAllowFinish, boolean workFlowIsAllowUpload, boolean workFlowIsSetViewed, boolean workFlowIsSubmit, boolean workFlowIsTransferMultiple, boolean workFlowIsGenerateNumber) {
        this.workFlowId = workFlowId;
        this.workFlowIsAllowReturn = workFlowIsAllowReturn;
        this.workFlowIsAllowRemove = workFlowIsAllowRemove;
        this.workFlowIsAllowFinish = workFlowIsAllowFinish;
        this.workFlowIsAllowUpload = workFlowIsAllowUpload;
        this.workFlowIsSetViewed = workFlowIsSetViewed;
        this.workFlowIsSubmit = workFlowIsSubmit;
        this.workFlowIsTransferMultiple = workFlowIsTransferMultiple;
        this.workFlowIsGenerateNumber = workFlowIsGenerateNumber;
    }

    public String getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    public String getWorkFlowName() {
        return workFlowName;
    }

    public void setWorkFlowName(String workFlowName) {
        this.workFlowName = workFlowName;
    }

    public Short getWorkFlowStep() {
        return workFlowStep;
    }

    public void setWorkFlowStep(Short workFlowStep) {
        this.workFlowStep = workFlowStep;
    }

    public Short getWorkFlowType() {
        return workFlowType;
    }

    public void setWorkFlowType(Short workFlowType) {
        this.workFlowType = workFlowType;
    }

    public boolean getWorkFlowIsAllowReturn() {
        return workFlowIsAllowReturn;
    }

    public void setWorkFlowIsAllowReturn(boolean workFlowIsAllowReturn) {
        this.workFlowIsAllowReturn = workFlowIsAllowReturn;
    }

    public boolean getWorkFlowIsAllowRemove() {
        return workFlowIsAllowRemove;
    }

    public void setWorkFlowIsAllowRemove(boolean workFlowIsAllowRemove) {
        this.workFlowIsAllowRemove = workFlowIsAllowRemove;
    }

    public boolean getWorkFlowIsAllowFinish() {
        return workFlowIsAllowFinish;
    }

    public void setWorkFlowIsAllowFinish(boolean workFlowIsAllowFinish) {
        this.workFlowIsAllowFinish = workFlowIsAllowFinish;
    }

    public boolean getWorkFlowIsAllowUpload() {
        return workFlowIsAllowUpload;
    }

    public void setWorkFlowIsAllowUpload(boolean workFlowIsAllowUpload) {
        this.workFlowIsAllowUpload = workFlowIsAllowUpload;
    }

    public boolean getWorkFlowIsSetViewed() {
        return workFlowIsSetViewed;
    }

    public void setWorkFlowIsSetViewed(boolean workFlowIsSetViewed) {
        this.workFlowIsSetViewed = workFlowIsSetViewed;
    }

    public boolean getWorkFlowIsSubmit() {
        return workFlowIsSubmit;
    }

    public void setWorkFlowIsSubmit(boolean workFlowIsSubmit) {
        this.workFlowIsSubmit = workFlowIsSubmit;
    }

    public boolean getWorkFlowIsTransferMultiple() {
        return workFlowIsTransferMultiple;
    }

    public void setWorkFlowIsTransferMultiple(boolean workFlowIsTransferMultiple) {
        this.workFlowIsTransferMultiple = workFlowIsTransferMultiple;
    }

    public boolean getWorkFlowIsGenerateNumber() {
        return workFlowIsGenerateNumber;
    }

    public void setWorkFlowIsGenerateNumber(boolean workFlowIsGenerateNumber) {
        this.workFlowIsGenerateNumber = workFlowIsGenerateNumber;
    }

    @XmlTransient
    public Collection<DraftDetail> getDraftDetailCollection() {
        return draftDetailCollection;
    }

    public void setDraftDetailCollection(Collection<DraftDetail> draftDetailCollection) {
        this.draftDetailCollection = draftDetailCollection;
    }

    @XmlTransient
    public Collection<DocumentDetail> getDocumentDetailCollection() {
        return documentDetailCollection;
    }

    public void setDocumentDetailCollection(Collection<DocumentDetail> documentDetailCollection) {
        this.documentDetailCollection = documentDetailCollection;
    }

    public Application getAppId() {
        return appId;
    }

    public void setAppId(Application appId) {
        this.appId = appId;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (workFlowId != null ? workFlowId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkFlow)) {
            return false;
        }
        WorkFlow other = (WorkFlow) object;
        if ((this.workFlowId == null && other.workFlowId != null) || (this.workFlowId != null && !this.workFlowId.equals(other.workFlowId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.WorkFlow[ workFlowId=" + workFlowId + " ]";
    }
    
}
