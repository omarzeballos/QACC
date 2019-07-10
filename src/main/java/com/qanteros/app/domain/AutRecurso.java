package com.qanteros.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.qanteros.app.domain.enumeration.Estado;

/**
 * A AutRecurso.
 */
@Entity
@Table(name = "aut_recurso")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AutRecurso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_recurso", nullable = false)
    private Long idRecurso;

    @NotNull
    @Column(name = "id_recurso_dominio", nullable = false)
    private Long idRecursoDominio;

    @Column(name = "id_recurso_padre")
    private Long idRecursoPadre;

    @NotNull
    @Column(name = "id_modulo", nullable = false)
    private Long idModulo;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

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
    @JsonIgnoreProperties("autRecursos")
    private AutRecursoDominio idRecursoDominio;

    @ManyToOne
    @JsonIgnoreProperties("autRecursos")
    private AutRecurso idRecursoPadre;

    @ManyToOne
    @JsonIgnoreProperties("autRecursos")
    private AutModulo idModulo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRecurso() {
        return idRecurso;
    }

    public AutRecurso idRecurso(Long idRecurso) {
        this.idRecurso = idRecurso;
        return this;
    }

    public void setIdRecurso(Long idRecurso) {
        this.idRecurso = idRecurso;
    }

    public Long getIdRecursoDominio() {
        return idRecursoDominio;
    }

    public AutRecurso idRecursoDominio(Long idRecursoDominio) {
        this.idRecursoDominio = idRecursoDominio;
        return this;
    }

    public void setIdRecursoDominio(Long idRecursoDominio) {
        this.idRecursoDominio = idRecursoDominio;
    }

    public Long getIdRecursoPadre() {
        return idRecursoPadre;
    }

    public AutRecurso idRecursoPadre(Long idRecursoPadre) {
        this.idRecursoPadre = idRecursoPadre;
        return this;
    }

    public void setIdRecursoPadre(Long idRecursoPadre) {
        this.idRecursoPadre = idRecursoPadre;
    }

    public Long getIdModulo() {
        return idModulo;
    }

    public AutRecurso idModulo(Long idModulo) {
        this.idModulo = idModulo;
        return this;
    }

    public void setIdModulo(Long idModulo) {
        this.idModulo = idModulo;
    }

    public String getNombre() {
        return nombre;
    }

    public AutRecurso nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Estado getEstado() {
        return estado;
    }

    public AutRecurso estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getOpeusr() {
        return opeusr;
    }

    public AutRecurso opeusr(String opeusr) {
        this.opeusr = opeusr;
        return this;
    }

    public void setOpeusr(String opeusr) {
        this.opeusr = opeusr;
    }

    public Instant getOpefecha() {
        return opefecha;
    }

    public AutRecurso opefecha(Instant opefecha) {
        this.opefecha = opefecha;
        return this;
    }

    public void setOpefecha(Instant opefecha) {
        this.opefecha = opefecha;
    }

    public String getOpeope() {
        return opeope;
    }

    public AutRecurso opeope(String opeope) {
        this.opeope = opeope;
        return this;
    }

    public void setOpeope(String opeope) {
        this.opeope = opeope;
    }

    public AutRecursoDominio getIdRecursoDominio() {
        return idRecursoDominio;
    }

    public AutRecurso idRecursoDominio(AutRecursoDominio autRecursoDominio) {
        this.idRecursoDominio = autRecursoDominio;
        return this;
    }

    public void setIdRecursoDominio(AutRecursoDominio autRecursoDominio) {
        this.idRecursoDominio = autRecursoDominio;
    }

    public AutRecurso getIdRecursoPadre() {
        return idRecursoPadre;
    }

    public AutRecurso idRecursoPadre(AutRecurso autRecurso) {
        this.idRecursoPadre = autRecurso;
        return this;
    }

    public void setIdRecursoPadre(AutRecurso autRecurso) {
        this.idRecursoPadre = autRecurso;
    }

    public AutModulo getIdModulo() {
        return idModulo;
    }

    public AutRecurso idModulo(AutModulo autModulo) {
        this.idModulo = autModulo;
        return this;
    }

    public void setIdModulo(AutModulo autModulo) {
        this.idModulo = autModulo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AutRecurso)) {
            return false;
        }
        return id != null && id.equals(((AutRecurso) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AutRecurso{" +
            "id=" + getId() +
            ", idRecurso=" + getIdRecurso() +
            ", idRecursoDominio=" + getIdRecursoDominio() +
            ", idRecursoPadre=" + getIdRecursoPadre() +
            ", idModulo=" + getIdModulo() +
            ", nombre='" + getNombre() + "'" +
            ", estado='" + getEstado() + "'" +
            ", opeusr='" + getOpeusr() + "'" +
            ", opefecha='" + getOpefecha() + "'" +
            ", opeope='" + getOpeope() + "'" +
            "}";
    }
}
