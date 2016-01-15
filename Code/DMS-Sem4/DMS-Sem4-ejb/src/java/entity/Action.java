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
@Table(name = "Action")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Action.findAll", query = "SELECT a FROM Action a"),
    @NamedQuery(name = "Action.findByActId", query = "SELECT a FROM Action a WHERE a.actId = :actId"),
    @NamedQuery(name = "Action.findByActName", query = "SELECT a FROM Action a WHERE a.actName = :actName"),
    @NamedQuery(name = "Action.findByActDescription", query = "SELECT a FROM Action a WHERE a.actDescription = :actDescription"),
    @NamedQuery(name = "Action.findByActStep", query = "SELECT a FROM Action a WHERE a.actStep = :actStep")})
public class Action implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "act_Id")
    private Short actId;
    @Size(max = 100)
    @Column(name = "act_Name")
    private String actName;
    @Size(max = 150)
    @Column(name = "act_Description")
    private String actDescription;
    @Column(name = "act_Step")
    private Short actStep;

    public Action() {
    }
    public Action(int dump){
        actId = 1;
        actName = "";
        actStep = 0;
        
    }
    public Action(Short actId) {
        this.actId = actId;
    }

    public Short getActId() {
        return actId;
    }

    public void setActId(Short actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActDescription() {
        return actDescription;
    }

    public void setActDescription(String actDescription) {
        this.actDescription = actDescription;
    }

    public Short getActStep() {
        return actStep;
    }

    public void setActStep(Short actStep) {
        this.actStep = actStep;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actId != null ? actId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Action)) {
            return false;
        }
        Action other = (Action) object;
        if ((this.actId == null && other.actId != null) || (this.actId != null && !this.actId.equals(other.actId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Action[ actId=" + actId + " ]";
    }
    
}
