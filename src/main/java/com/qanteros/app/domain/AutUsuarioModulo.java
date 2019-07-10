package com.qanteros.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A AutUsuarioModulo.
 */
@Entity
@Table(name = "aut_usuario_modulo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AutUsuarioModulo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @NotNull
    @Column(name = "id_modulo", nullable = false)
    private Long idModulo;

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
    @JsonIgnoreProperties("autUsuarioModulos")
    private UsrUsuario idUsuario;

    @ManyToOne
    @JsonIgnoreProperties("autUsuarioModulos")
    private AutModulo idModulo;

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

    public AutUsuarioModulo idUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
        return this;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdModulo() {
        return idModulo;
    }

    public AutUsuarioModulo idModulo(Long idModulo) {
        this.idModulo = idModulo;
        return this;
    }

    public void setIdModulo(Long idModulo) {
        this.idModulo = idModulo;
    }

    public String getOpeusr() {
        return opeusr;
    }

    public AutUsuarioModulo opeusr(String opeusr) {
        this.opeusr = opeusr;
        return this;
    }

    public void setOpeusr(String opeusr) {
        this.opeusr = opeusr;
    }

    public Instant getOpefecha() {
        return opefecha;
    }

    public AutUsuarioModulo opefecha(Instant opefecha) {
        this.opefecha = opefecha;
        return this;
    }

    public void setOpefecha(Instant opefecha) {
        this.opefecha = opefecha;
    }

    public String getOpeope() {
        return opeope;
    }

    public AutUsuarioModulo opeope(String opeope) {
        this.opeope = opeope;
        return this;
    }

    public void setOpeope(String opeope) {
        this.opeope = opeope;
    }

    public UsrUsuario getIdUsuario() {
        return idUsuario;
    }

    public AutUsuarioModulo idUsuario(UsrUsuario usrUsuario) {
        this.idUsuario = usrUsuario;
        return this;
    }

    public void setIdUsuario(UsrUsuario usrUsuario) {
        this.idUsuario = usrUsuario;
    }

    public AutModulo getIdModulo() {
        return idModulo;
    }

    public AutUsuarioModulo idModulo(AutModulo autModulo) {
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
        if (!(o instanceof AutUsuarioModulo)) {
            return false;
        }
        return id != null && id.equals(((AutUsuarioModulo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AutUsuarioModulo{" +
            "id=" + getId() +
            ", idUsuario=" + getIdUsuario() +
            ", idModulo=" + getIdModulo() +
            ", opeusr='" + getOpeusr() + "'" +
            ", opefecha='" + getOpefecha() + "'" +
            ", opeope='" + getOpeope() + "'" +
            "}";
    }
}
