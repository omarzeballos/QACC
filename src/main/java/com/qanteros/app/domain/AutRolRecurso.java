package com.qanteros.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A AutRolRecurso.
 */
@Entity
@Table(name = "aut_rol_recurso")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AutRolRecurso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_rol", nullable = false)
    private Long idRol;

    @NotNull
    @Column(name = "id_recurso", nullable = false)
    private Long idRecurso;

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
    @JsonIgnoreProperties("autRolRecursos")
    private AutRol idRol;

    @ManyToOne
    @JsonIgnoreProperties("autRolRecursos")
    private AutRecurso idRecurso;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRol() {
        return idRol;
    }

    public AutRolRecurso idRol(Long idRol) {
        this.idRol = idRol;
        return this;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public Long getIdRecurso() {
        return idRecurso;
    }

    public AutRolRecurso idRecurso(Long idRecurso) {
        this.idRecurso = idRecurso;
        return this;
    }

    public void setIdRecurso(Long idRecurso) {
        this.idRecurso = idRecurso;
    }

    public String getOpeusr() {
        return opeusr;
    }

    public AutRolRecurso opeusr(String opeusr) {
        this.opeusr = opeusr;
        return this;
    }

    public void setOpeusr(String opeusr) {
        this.opeusr = opeusr;
    }

    public Instant getOpefecha() {
        return opefecha;
    }

    public AutRolRecurso opefecha(Instant opefecha) {
        this.opefecha = opefecha;
        return this;
    }

    public void setOpefecha(Instant opefecha) {
        this.opefecha = opefecha;
    }

    public String getOpeope() {
        return opeope;
    }

    public AutRolRecurso opeope(String opeope) {
        this.opeope = opeope;
        return this;
    }

    public void setOpeope(String opeope) {
        this.opeope = opeope;
    }

    public AutRol getIdRol() {
        return idRol;
    }

    public AutRolRecurso idRol(AutRol autRol) {
        this.idRol = autRol;
        return this;
    }

    public void setIdRol(AutRol autRol) {
        this.idRol = autRol;
    }

    public AutRecurso getIdRecurso() {
        return idRecurso;
    }

    public AutRolRecurso idRecurso(AutRecurso autRecurso) {
        this.idRecurso = autRecurso;
        return this;
    }

    public void setIdRecurso(AutRecurso autRecurso) {
        this.idRecurso = autRecurso;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AutRolRecurso)) {
            return false;
        }
        return id != null && id.equals(((AutRolRecurso) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AutRolRecurso{" +
            "id=" + getId() +
            ", idRol=" + getIdRol() +
            ", idRecurso=" + getIdRecurso() +
            ", opeusr='" + getOpeusr() + "'" +
            ", opefecha='" + getOpefecha() + "'" +
            ", opeope='" + getOpeope() + "'" +
            "}";
    }
}
