package com.qanteros.app.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.qanteros.app.domain.AutPerfilRol} entity.
 */
public class AutPerfilRolDTO implements Serializable {

    private Long id;

    @NotNull
    private Long idPerfil;

    @NotNull
    private Long idRol;

    @NotNull
    private String opeusr;

    @NotNull
    private Instant opefecha;

    @NotNull
    private String opeope;


    private Long idPerfilId;

    private Long idRolId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Long idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
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

    public Long getIdPerfilId() {
        return idPerfilId;
    }

    public void setIdPerfilId(Long autPerfilId) {
        this.idPerfilId = autPerfilId;
    }

    public Long getIdRolId() {
        return idRolId;
    }

    public void setIdRolId(Long autRolId) {
        this.idRolId = autRolId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AutPerfilRolDTO autPerfilRolDTO = (AutPerfilRolDTO) o;
        if (autPerfilRolDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), autPerfilRolDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AutPerfilRolDTO{" +
            "id=" + getId() +
            ", idPerfil=" + getIdPerfil() +
            ", idRol=" + getIdRol() +
            ", opeusr='" + getOpeusr() + "'" +
            ", opefecha='" + getOpefecha() + "'" +
            ", opeope='" + getOpeope() + "'" +
            ", idPerfil=" + getIdPerfilId() +
            ", idRol=" + getIdRolId() +
            "}";
    }
}
