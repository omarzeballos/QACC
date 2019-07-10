package com.qanteros.app.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.qanteros.app.domain.enumeration.Estado;

/**
 * A DTO for the {@link com.qanteros.app.domain.AutRol} entity.
 */
public class AutRolDTO implements Serializable {

    private Long id;

    @NotNull
    private Long idRol;

    @NotNull
    private String nombre;

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

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

        AutRolDTO autRolDTO = (AutRolDTO) o;
        if (autRolDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), autRolDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AutRolDTO{" +
            "id=" + getId() +
            ", idRol=" + getIdRol() +
            ", nombre='" + getNombre() + "'" +
            ", estado='" + getEstado() + "'" +
            ", opeusr='" + getOpeusr() + "'" +
            ", opefecha='" + getOpefecha() + "'" +
            ", opeope='" + getOpeope() + "'" +
            "}";
    }
}
