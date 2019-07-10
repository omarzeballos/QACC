package com.qanteros.app.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.qanteros.app.domain.AutUsuarioModulo} entity.
 */
public class AutUsuarioModuloDTO implements Serializable {

    private Long id;

    @NotNull
    private Long idUsuario;

    @NotNull
    private Long idModulo;

    @NotNull
    private String opeusr;

    @NotNull
    private Instant opefecha;

    @NotNull
    private String opeope;


    private Long idUsuarioId;

    private Long idModuloId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Long idModulo) {
        this.idModulo = idModulo;
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

    public Long getIdUsuarioId() {
        return idUsuarioId;
    }

    public void setIdUsuarioId(Long usrUsuarioId) {
        this.idUsuarioId = usrUsuarioId;
    }

    public Long getIdModuloId() {
        return idModuloId;
    }

    public void setIdModuloId(Long autModuloId) {
        this.idModuloId = autModuloId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AutUsuarioModuloDTO autUsuarioModuloDTO = (AutUsuarioModuloDTO) o;
        if (autUsuarioModuloDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), autUsuarioModuloDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AutUsuarioModuloDTO{" +
            "id=" + getId() +
            ", idUsuario=" + getIdUsuario() +
            ", idModulo=" + getIdModulo() +
            ", opeusr='" + getOpeusr() + "'" +
            ", opefecha='" + getOpefecha() + "'" +
            ", opeope='" + getOpeope() + "'" +
            ", idUsuario=" + getIdUsuarioId() +
            ", idModulo=" + getIdModuloId() +
            "}";
    }
}
