package com.qanteros.app.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.qanteros.app.domain.enumeration.Estado;

/**
 * A DTO for the {@link com.qanteros.app.domain.AutRecurso} entity.
 */
public class AutRecursoDTO implements Serializable {

    private Long id;

    @NotNull
    private Long idRecurso;

    @NotNull
    private Long idRecursoDominio;

    private Long idRecursoPadre;

    @NotNull
    private Long idModulo;

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


    private Long idRecursoDominioId;

    private Long idRecursoPadreId;

    private Long idModuloId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(Long idRecurso) {
        this.idRecurso = idRecurso;
    }

    public Long getIdRecursoDominio() {
        return idRecursoDominio;
    }

    public void setIdRecursoDominio(Long idRecursoDominio) {
        this.idRecursoDominio = idRecursoDominio;
    }

    public Long getIdRecursoPadre() {
        return idRecursoPadre;
    }

    public void setIdRecursoPadre(Long idRecursoPadre) {
        this.idRecursoPadre = idRecursoPadre;
    }

    public Long getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Long idModulo) {
        this.idModulo = idModulo;
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

    public Long getIdRecursoDominioId() {
        return idRecursoDominioId;
    }

    public void setIdRecursoDominioId(Long autRecursoDominioId) {
        this.idRecursoDominioId = autRecursoDominioId;
    }

    public Long getIdRecursoPadreId() {
        return idRecursoPadreId;
    }

    public void setIdRecursoPadreId(Long autRecursoId) {
        this.idRecursoPadreId = autRecursoId;
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

        AutRecursoDTO autRecursoDTO = (AutRecursoDTO) o;
        if (autRecursoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), autRecursoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AutRecursoDTO{" +
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
            ", idRecursoDominio=" + getIdRecursoDominioId() +
            ", idRecursoPadre=" + getIdRecursoPadreId() +
            ", idModulo=" + getIdModuloId() +
            "}";
    }
}
