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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TrungHTH
 */
@Entity
@Table(name = "DraftStorage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DraftStorage.findAll", query = "SELECT d FROM DraftStorage d"),
    @NamedQuery(name = "DraftStorage.findByDraftId", query = "SELECT d FROM DraftStorage d WHERE d.draftStoragePK.draftId = :draftId"),
    @NamedQuery(name = "DraftStorage.findByUserId", query = "SELECT d FROM DraftStorage d WHERE d.draftStoragePK.userId = :userId"),
    @NamedQuery(name = "DraftStorage.findByDraftStoCreateDate", query = "SELECT d FROM DraftStorage d WHERE d.draftStoCreateDate = :draftStoCreateDate")})
public class DraftStorage implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DraftStoragePK draftStoragePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "draftSto_CreateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date draftStoCreateDate;
    @JoinColumn(name = "draft_Id", referencedColumnName = "draft_Id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Draft draft;
    @JoinColumn(name = "user_Id", referencedColumnName = "user_Id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public DraftStorage() {
    }

    public DraftStorage(DraftStoragePK draftStoragePK) {
        this.draftStoragePK = draftStoragePK;
    }

    public DraftStorage(DraftStoragePK draftStoragePK, Date draftStoCreateDate) {
        this.draftStoragePK = draftStoragePK;
        this.draftStoCreateDate = draftStoCreateDate;
    }

    public DraftStorage(String draftId, String userId) {
        this.draftStoragePK = new DraftStoragePK(draftId, userId);
    }

    public DraftStoragePK getDraftStoragePK() {
        return draftStoragePK;
    }

    public void setDraftStoragePK(DraftStoragePK draftStoragePK) {
        this.draftStoragePK = draftStoragePK;
    }

    public Date getDraftStoCreateDate() {
        return draftStoCreateDate;
    }

    public void setDraftStoCreateDate(Date draftStoCreateDate) {
        this.draftStoCreateDate = draftStoCreateDate;
    }

    public Draft getDraft() {
        return draft;
    }

    public void setDraft(Draft draft) {
        this.draft = draft;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (draftStoragePK != null ? draftStoragePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DraftStorage)) {
            return false;
        }
        DraftStorage other = (DraftStorage) object;
        if ((this.draftStoragePK == null && other.draftStoragePK != null) || (this.draftStoragePK != null && !this.draftStoragePK.equals(other.draftStoragePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DraftStorage[ draftStoragePK=" + draftStoragePK + " ]";
    }
    
}
