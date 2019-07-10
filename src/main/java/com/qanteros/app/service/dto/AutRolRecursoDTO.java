package com.qanteros.app.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.qanteros.app.domain.AutRolRecurso} entity.
 */
public class AutRolRecursoDTO implements Serializable {

    private Long id;

    @NotNull
    private Long idRol;

    @NotNull
    private Long idRecurso;

    @NotNull
    private String opeusr;

    @NotNull
    private Instant opefecha;

    @NotNull
    private String opeope;


    private Long idRolId;

    private Long idRecursoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public Long getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(Long idRecurso) {
        this.idRecurso = idRecurso;
    }

    public String getOpeusr() {
        return opeusr;
    }

    public void setOpeusr(String opeusr) {
        this.opeusr = opeusr;
    }

    public Instant getOpefecha() {
        return opefecha;
    }

    public void setOpefecha(Instant opefecha) {
        this.opefecha = opefecha;
    }

    public String getOpeope() {
        return opeope;
    }

    public void setOpeope(String opeope) {
        this.opeope = opeope;
    }

    public Long getIdRolId() {
        return idRolId;
    }

    public void setIdRolId(Long autRolId) {
        this.idRolId = autRolId;
    }

    public Long getIdRecursoId() {
        return idRecursoId;
    }

    public void setIdRecursoId(Long autRecursoId) {
        this.idRecursoId = autRecursoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AutRolRecursoDTO autRolRecursoDTO = (AutRolRecursoDTO) o;
        if (autRolRecursoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), autRolRecursoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AutRolRecursoDTO{" +
            "id=" + getId() +
            ", idRol=" + getIdRol() +
            ", idRecurso=" + getIdRecurso() +
            ", opeusr='" + getOpeusr() + "'" +
            ", opefecha='" + getOpefecha() + "'" +
            ", opeope='" + getOpeope() + "'" +
            ", idRol=" + getIdRolId() +
            ", idRecurso=" + getIdRecursoId() +
            "}";
    }
}
