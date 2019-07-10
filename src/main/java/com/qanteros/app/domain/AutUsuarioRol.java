package com.qanteros.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A AutUsuarioRol.
 */
@Entity
@Table(name = "aut_usuario_rol")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AutUsuarioRol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @NotNull
    @Column(name = "id_rol", nullable = false)
    private Long idRol;

    @NotNull
    @Column(name = "opeusr", nullable = false)
    private String opeusr;

    @NotNull
    @Column(name = "opefecha", nullable = false)
    private Instant opefecha;

    @NotNull
    @Column(name = "opeope", nullable = false)
    private String opeope;

    @ManyToOne
    @JsonIgnoreProperties("autUsuarioRols")
    private UsrUsuario idUsuario;

    @ManyToOne
    @JsonIgnoreProperties("autUsuarioRols")
    private AutRol idRol;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public AutUsuarioRol idUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
        return this;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdRol() {
        return idRol;
    }

    public AutUsuarioRol idRol(Long idRol) {
        this.idRol = idRol;
        return this;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getOpeusr() {
        return opeusr;
    }

    public AutUsuarioRol opeusr(String opeusr) {
        this.opeusr = opeusr;
        return this;
    }

    public void setOpeusr(String opeusr) {
        this.opeusr = opeusr;
    }

    public Instant getOpefecha() {
        return opefecha;
    }

    public AutUsuarioRol opefecha(Instant opefecha) {
        this.opefecha = opefecha;
        return this;
    }

    public void setOpefecha(Instant opefecha) {
        this.opefecha = opefecha;
    }

    public String getOpeope() {
        return opeope;
    }

    public AutUsuarioRol opeope(String opeope) {
        this.opeope = opeope;
        return this;
    }

    public void setOpeope(String opeope) {
        this.opeope = opeope;
    }

    public UsrUsuario getIdUsuario() {
        return idUsuario;
    }

    public AutUsuarioRol idUsuario(UsrUsuario usrUsuario) {
        this.idUsuario = usrUsuario;
        return this;
    }

    public void setIdUsuario(UsrUsuario usrUsuario) {
        this.idUsuario = usrUsuario;
    }

    public AutRol getIdRol() {
        return idRol;
    }

    public AutUsuarioRol idRol(AutRol autRol) {
        this.idRol = autRol;
        return this;
    }

    public void setIdRol(AutRol autRol) {
        this.idRol = autRol;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AutUsuarioRol)) {
            return false;
        }
        return id != null && id.equals(((AutUsuarioRol) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AutUsuarioRol{" +
            "id=" + getId() +
            ", idUsuario=" + getIdUsuario() +
            ", idRol=" + getIdRol() +
            ", opeusr='" + getOpeusr() + "'" +
            ", opefecha='" + getOpefecha() + "'" +
            ", opeope='" + getOpeope() + "'" +
            "}";
    }
}
