/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author TrungHTH
 */
@Entity
@Table(name = "DocumentDetail")
@XmlRootElement

public class DocumentDetail implements Serializable {
    @JoinColumn(name = "docDetail_DepCreate", referencedColumnName = "dep_Id")
    @ManyToOne
    private Department docDetailDepCreate;
    @JoinColumn(name = "docDetail_DepReceive", referencedColumnName = "dep_Id")
    @ManyToOne
    private Department docDetailDepReceive;
    @JoinColumn(name = "docDetail_UserCreate", referencedColumnName = "user_Id")
    @ManyToOne
    private Users docDetailUserCreate;
    @JoinColumn(name = "docDetail_UserReceive", referencedColumnName = "user_Id")
    @ManyToOne
    private Users docDetailUserReceive;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "docDetail_Id")
    private String docDetailId;
    
    
    @Size(max = 150)
    @Column(name = "docDetail_FileName")
    private String docDetailFileName;
    
    @Column(name = "docDetail_CreateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date docDetailCreateDate;
    @Size(max = 500)
    @Column(name = "docDetail_Description")
    private String docDetailDescription;
    @Column(name = "docDetail_IsActive")
    private Boolean docDetailIsActive;
    @Column(name = "docDetail_UpdateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date docDetailUpdateDate;
    @Column(name = "docDetail_IsUrgent")
    private Boolean docDetailIsUrgent;
    @Column(name = "docDetail_IsEdit")
    private Boolean docDetailIsEdit;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "docDetailId")
    private Collection<DocumentDepartment> documentDepartmentCollection;
    @JoinColumn(name = "act_Id", referencedColumnName = "act_Id")
    @ManyToOne
    private Action actId;
    @JoinColumn(name = "doc_Id", referencedColumnName = "doc_Id")
    @ManyToOne
    private Document docId;
    @JoinColumn(name = "workFlow_Id", referencedColumnName = "workFlow_Id")
    @ManyToOne
    private WorkFlow workFlowId;

    public DocumentDetail() {
    }
    public DocumentDetail(int dump) throws ParseException{
        actId = new Action(1);
        docDetailCreateDate = DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(new SimpleDateFormat("dd/MM/yyyy").format(new Date())).toDate();//new Date();
     
        docDetailDepCreate= new Department(1);
        docDetailDepReceive=new Department(1);
        docDetailDescription="";
        
        docDetailFileName="";
        docDetailId=UUID.randomUUID().toString();
        docDetailIsActive = true;
        docDetailIsEdit = false;
        docDetailIsUrgent = false;
        docDetailUpdateDate = DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(new SimpleDateFormat("dd/MM/yyyy").format(new Date())).toDate();//new Date();
        docDetailUserCreate= new Users(1);
        docDetailUserReceive= new Users(1);
        docId = new Document(1);
        documentDepartmentCollection = null;
        workFlowId = new WorkFlow(1);
    }

    public DocumentDetail(String docDetailId) {
        this.docDetailId = docDetailId;
    }

    public String getDocDetailId() {
        return docDetailId;
    }

    public void setDocDetailId(String docDetailId) {
        this.docDetailId = docDetailId;
    }


    public String getDocDetailFileName() {
        return docDetailFileName;
    }

    public void setDocDetailFileName(String docDetailFileName) {
        this.docDetailFileName = docDetailFileName;
    }

   

    public Date getDocDetailCreateDate() {
        return docDetailCreateDate;
    }

    public void setDocDetailCreateDate(Date docDetailCreateDate) {
        this.docDetailCreateDate = docDetailCreateDate;
    }

    public String getDocDetailDescription() {
        return docDetailDescription;
    }

    public void setDocDetailDescription(String docDetailDescription) {
        this.docDetailDescription = docDetailDescription;
    }

    public Boolean getDocDetailIsActive() {
        return docDetailIsActive;
    }

    public void setDocDetailIsActive(Boolean docDetailIsActive) {
        this.docDetailIsActive = docDetailIsActive;
    }

    public Date getDocDetailUpdateDate() {
        return docDetailUpdateDate;
    }

    public void setDocDetailUpdateDate(Date docDetailUpdateDate) {
        this.docDetailUpdateDate = docDetailUpdateDate;
    }

    public Boolean getDocDetailIsUrgent() {
        return docDetailIsUrgent;
    }

    public void setDocDetailIsUrgent(Boolean docDetailIsUrgent) {
        this.docDetailIsUrgent = docDetailIsUrgent;
    }

    public Boolean getDocDetailIsEdit() {
        return docDetailIsEdit;
    }

    public void setDocDetailIsEdit(Boolean docDetailIsEdit) {
        this.docDetailIsEdit = docDetailIsEdit;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<DocumentDepartment> getDocumentDepartmentCollection() {
        return documentDepartmentCollection;
    }

    public void setDocumentDepartmentCollection(Collection<DocumentDepartment> documentDepartmentCollection) {
        this.documentDepartmentCollection = documentDepartmentCollection;
    }

    public Action getActId() {
        return actId;
    }

    public void setActId(Action actId) {
        this.actId = actId;
    }

    public Document getDocId() {
        return docId;
    }

    public void setDocId(Document docId) {
        this.docId = docId;
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
        hash += (docDetailId != null ? docDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentDetail)) {
            return false;
        }
        DocumentDetail other = (DocumentDetail) object;
        if ((this.docDetailId == null && other.docDetailId != null) || (this.docDetailId != null && !this.docDetailId.equals(other.docDetailId))) {
            return false;
        }
        return true; 
    }

    @Override
    public String toString() {
        return "entity.DocumentDetail[ docDetailId=" + docDetailId + " ]";
    }

    public Department getDocDetailDepCreate() {
        return docDetailDepCreate;
    }

    public void setDocDetailDepCreate(Department docDetailDepCreate) {
        this.docDetailDepCreate = docDetailDepCreate;
    }

    public Department getDocDetailDepReceive() {
        return docDetailDepReceive;
    }

    public void setDocDetailDepReceive(Department docDetailDepReceive) {
        this.docDetailDepReceive = docDetailDepReceive;
    }

    public Users getDocDetailUserCreate() {
        return docDetailUserCreate;
    }

    public void setDocDetailUserCreate(Users docDetailUserCreate) {
        this.docDetailUserCreate = docDetailUserCreate;
    }

    public Users getDocDetailUserReceive() {
        return docDetailUserReceive;
    }

    public void setDocDetailUserReceive(Users docDetailUserReceive) {
        this.docDetailUserReceive = docDetailUserReceive;
    }
    
}
