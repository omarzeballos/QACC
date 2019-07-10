package com.qanteros.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A AutPerfilRol.
 */
@Entity
@Table(name = "aut_perfil_rol")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AutPerfilRol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_perfil", nullable = false)
    private Long idPerfil;

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
    @JsonIgnoreProperties("autPerfilRols")
    private AutPerfil idPerfil;

    @ManyToOne
    @JsonIgnoreProperties("autPerfilRols")
    private AutRol idRol;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPerfil() {
        return idPerfil;
    }

    public AutPerfilRol idPerfil(Long idPerfil) {
        this.idPerfil = idPerfil;
        return this;
    }

    public void setIdPerfil(Long idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Long getIdRol() {
        return idRol;
    }

    public AutPerfilRol idRol(Long idRol) {
        this.idRol = idRol;
        return this;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getOpeusr() {
        return opeusr;
    }

    public AutPerfilRol opeusr(String opeusr) {
        this.opeusr = opeusr;
        return this;
    }

    public void setOpeusr(String opeusr) {
        this.opeusr = opeusr;
    }

    public Instant getOpefecha() {
        return opefecha;
    }

    public AutPerfilRol opefecha(Instant opefecha) {
        this.opefecha = opefecha;
        return this;
    }

    public void setOpefecha(Instant opefecha) {
        this.opefecha = opefecha;
    }

    public String getOpeope() {
        return opeope;
    }

    public AutPerfilRol opeope(String opeope) {
        this.opeope = opeope;
        return this;
    }

    public void setOpeope(String opeope) {
        this.opeope = opeope;
    }

    public AutPerfil getIdPerfil() {
        return idPerfil;
    }

    public AutPerfilRol idPerfil(AutPerfil autPerfil) {
        this.idPerfil = autPerfil;
        return this;
    }

    public void setIdPerfil(AutPerfil autPerfil) {
        this.idPerfil = autPerfil;
    }

    public AutRol getIdRol() {
        return idRol;
    }

    public AutPerfilRol idRol(AutRol autRol) {
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
        if (!(o instanceof AutPerfilRol)) {
            return false;
        }
        return id != null && id.equals(((AutPerfilRol) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AutPerfilRol{" +
            "id=" + getId() +
            ", idPerfil=" + getIdPerfil() +
            ", idRol=" + getIdRol() +
            ", opeusr='" + getOpeusr() + "'" +
            ", opefecha='" + getOpefecha() + "'" +
            ", opeope='" + getOpeope() + "'" +
            "}";
    }
}
