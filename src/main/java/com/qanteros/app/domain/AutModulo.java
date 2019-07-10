package com.qanteros.app.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.qanteros.app.domain.enumeration.Estado;

/**
 * A AutModulo.
 */
@Entity
@Table(name = "aut_modulo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AutModulo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_modulo", nullable = false)
    private Long idModulo;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "abreviacion", nullable = false)
    private String abreviacion;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdModulo() {
        return idModulo;
    }

    public AutModulo idModulo(Long idModulo) {
        this.idModulo = idModulo;
        return this;
    }

    public void setIdModulo(Long idModulo) {
        this.idModulo = idModulo;
    }

    public String getNombre() {
        return nombre;
    }

    public AutModulo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviacion() {
        return abreviacion;
    }

    public AutModulo abreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
        return this;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }

    public Estado getEstado() {
        return estado;
    }

    public AutModulo estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getOpeusr() {
        return opeusr;
    }

    public AutModulo opeusr(String opeusr) {
        this.opeusr = opeusr;
        return this;
    }

    public void setOpeusr(String opeusr) {
        this.opeusr = opeusr;
    }

    public Instant getOpefecha() {
        return opefecha;
    }

    public AutModulo opefecha(Instant opefecha) {
        this.opefecha = opefecha;
        return this;
    }

    public void setOpefecha(Instant opefecha) {
        this.opefecha = opefecha;
    }

    public String getOpeope() {
        return opeope;
    }

    public AutModulo opeope(String opeope) {
        this.opeope = opeope;
        return this;
    }

    public void setOpeope(String opeope) {
        this.opeope = opeope;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AutModulo)) {
            return false;
        }
        return id != null && id.equals(((AutModulo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AutModulo{" +
            "id=" + getId() +
            ", idModulo=" + getIdModulo() +
            ", nombre='" + getNombre() + "'" +
            ", abreviacion='" + getAbreviacion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", opeusr='" + getOpeusr() + "'" +
            ", opefecha='" + getOpefecha() + "'" +
            ", opeope='" + getOpeope() + "'" +
            "}";
    }
}
