/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TrungHTH
 */
@Entity
@Table(name = "DraftDetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DraftDetail.findAll", query = "SELECT d FROM DraftDetail d"),
    @NamedQuery(name = "DraftDetail.findByDraftDetailId", query = "SELECT d FROM DraftDetail d WHERE d.draftDetailId = :draftDetailId"),
    @NamedQuery(name = "DraftDetail.findByDraftId", query = "SELECT d FROM DraftDetail d WHERE d.draftId = :draftId"),
    @NamedQuery(name = "DraftDetail.findByDraftDetailUserCreate", query = "SELECT d FROM DraftDetail d WHERE d.draftDetailUserCreate = :draftDetailUserCreate"),
    @NamedQuery(name = "DraftDetail.findByDraftDetailDepCreate", query = "SELECT d FROM DraftDetail d WHERE d.draftDetailDepCreate = :draftDetailDepCreate"),
    @NamedQuery(name = "DraftDetail.findByDraftDetailUserReceive", query = "SELECT d FROM DraftDetail d WHERE d.draftDetailUserReceive = :draftDetailUserReceive"),
    @NamedQuery(name = "DraftDetail.findByDraftDetailDepReceive", query = "SELECT d FROM DraftDetail d WHERE d.draftDetailDepReceive = :draftDetailDepReceive"),
    @NamedQuery(name = "DraftDetail.findByDraftDetailFileName", query = "SELECT d FROM DraftDetail d WHERE d.draftDetailFileName = :draftDetailFileName"),
    @NamedQuery(name = "DraftDetail.findByDraftDetailCreateDate", query = "SELECT d FROM DraftDetail d WHERE d.draftDetailCreateDate = :draftDetailCreateDate"),
    @NamedQuery(name = "DraftDetail.findByDraftDetailDescription", query = "SELECT d FROM DraftDetail d WHERE d.draftDetailDescription = :draftDetailDescription"),
    @NamedQuery(name = "DraftDetail.findByDraftDetailIsActive", query = "SELECT d FROM DraftDetail d WHERE d.draftDetailIsActive = :draftDetailIsActive"),
    @NamedQuery(name = "DraftDetail.findByDraftDetailUpdateDate", query = "SELECT d FROM DraftDetail d WHERE d.draftDetailUpdateDate = :draftDetailUpdateDate")})
public class DraftDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "draftDetail_Id")
    private String draftDetailId;
    @Size(max = 36)
    @Column(name = "draft_Id")
    private String draftId;
    @Size(max = 36)
    @Column(name = "draftDetail_UserCreate")
    private String draftDetailUserCreate;
    @Size(max = 36)
    @Column(name = "draftDetail_DepCreate")
    private String draftDetailDepCreate;
    @Size(max = 36)
    @Column(name = "draftDetail_UserReceive")
    private String draftDetailUserReceive;
    @Size(max = 36)
    @Column(name = "draftDetail_DepReceive")
    private String draftDetailDepReceive;
    @Size(max = 150)
    @Column(name = "draftDetail_FileName")
    private String draftDetailFileName;
    @Lob
    @Column(name = "draftDetail_FileContent")
    private byte[] draftDetailFileContent;
    @Column(name = "draftDetail_CreateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date draftDetailCreateDate;
    @Size(max = 500)
    @Column(name = "draftDetail_Description")
    private String draftDetailDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "draftDetail_IsActive")
    private boolean draftDetailIsActive;
    @Column(name = "draftDetail_UpdateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date draftDetailUpdateDate;
    @JoinColumn(name = "act_Id", referencedColumnName = "act_Id")
    @ManyToOne(optional = false)
    private Action actId;
    @JoinColumn(name = "workFlow_Id", referencedColumnName = "workFlow_Id")
    @ManyToOne
    private WorkFlow workFlowId;

    public DraftDetail() {
    }

    public DraftDetail(String draftDetailId) {
        this.draftDetailId = draftDetailId;
    }

    public DraftDetail(String draftDetailId, boolean draftDetailIsActive) {
        this.draftDetailId = draftDetailId;
        this.draftDetailIsActive = draftDetailIsActive;
    }

    public String getDraftDetailId() {
        return draftDetailId;
    }

    public void setDraftDetailId(String draftDetailId) {
        this.draftDetailId = draftDetailId;
    }

    public String getDraftId() {
        return draftId;
    }

    public void setDraftId(String draftId) {
        this.draftId = draftId;
    }

    public String getDraftDetailUserCreate() {
        return draftDetailUserCreate;
    }

    public void setDraftDetailUserCreate(String draftDetailUserCreate) {
        this.draftDetailUserCreate = draftDetailUserCreate;
    }

    public String getDraftDetailDepCreate() {
        return draftDetailDepCreate;
    }

    public void setDraftDetailDepCreate(String draftDetailDepCreate) {
        this.draftDetailDepCreate = draftDetailDepCreate;
    }

    public String getDraftDetailUserReceive() {
        return draftDetailUserReceive;
    }

    public void setDraftDetailUserReceive(String draftDetailUserReceive) {
        this.draftDetailUserReceive = draftDetailUserReceive;
    }

    public String getDraftDetailDepReceive() {
        return draftDetailDepReceive;
    }

    public void setDraftDetailDepReceive(String draftDetailDepReceive) {
        this.draftDetailDepReceive = draftDetailDepReceive;
    }

    public String getDraftDetailFileName() {
        return draftDetailFileName;
    }

    public void setDraftDetailFileName(String draftDetailFileName) {
        this.draftDetailFileName = draftDetailFileName;
    }

    public byte[] getDraftDetailFileContent() {
        return draftDetailFileContent;
    }

    public void setDraftDetailFileContent(byte[] draftDetailFileContent) {
        this.draftDetailFileContent = draftDetailFileContent;
    }

    public Date getDraftDetailCreateDate() {
        return draftDetailCreateDate;
    }

    public void setDraftDetailCreateDate(Date draftDetailCreateDate) {
        this.draftDetailCreateDate = draftDetailCreateDate;
    }

    public String getDraftDetailDescription() {
        return draftDetailDescription;
    }

    public void setDraftDetailDescription(String draftDetailDescription) {
        this.draftDetailDescription = draftDetailDescription;
    }

    public boolean getDraftDetailIsActive() {
        return draftDetailIsActive;
    }

    public void setDraftDetailIsActive(boolean draftDetailIsActive) {
        this.draftDetailIsActive = draftDetailIsActive;
    }

    public Date getDraftDetailUpdateDate() {
        return draftDetailUpdateDate;
    }

    public void setDraftDetailUpdateDate(Date draftDetailUpdateDate) {
        this.draftDetailUpdateDate = draftDetailUpdateDate;
    }

    public Action getActId() {
        return actId;
    }

    public void setActId(Action actId) {
        this.actId = actId;
    }

    public WorkFlow getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(WorkFlow workFlowId) {
        this.workFlowId = workFlowId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (draftDetailId != null ? draftDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DraftDetail)) {
            return false;
        }
        DraftDetail other = (DraftDetail) object;
        if ((this.draftDetailId == null && other.draftDetailId != null) || (this.draftDetailId != null && !this.draftDetailId.equals(other.draftDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DraftDetail[ draftDetailId=" + draftDetailId + " ]";
    }
    
}
