package com.qanteros.app.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.qanteros.app.domain.enumeration.Estado;

/**
 * A DTO for the {@link com.qanteros.app.domain.UsrUsuario} entity.
 */
public class UsrUsuarioDTO implements Serializable {

    private Long id;

    @NotNull
    private Long idUsuario;

    @NotNull
    private String cuenta;

    @NotNull
    private String clave;

    @NotNull
    private Estado estado;

    @NotNull
    private String opeusr;

    @NotNull
    private Instant opefecha;

    @NotNull
    private String opeope;


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

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UsrUsuarioDTO usrUsuarioDTO = (UsrUsuarioDTO) o;
        if (usrUsuarioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), usrUsuarioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UsrUsuarioDTO{" +
            "id=" + getId() +
            ", idUsuario=" + getIdUsuario() +
            ", cuenta='" + getCuenta() + "'" +
            ", clave='" + getClave() + "'" +
            ", estado='" + getEstado() + "'" +
            ", opeusr='" + getOpeusr() + "'" +
            ", opefecha='" + getOpefecha() + "'" +
            ", opeope='" + getOpeope() + "'" +
            "}";
    }
}
