package com.truesoft.construction.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.truesoft.construction.domain.Work} entity.
 */
public class WorkDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String description;

    @NotNull
    private Instant dateCreated;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkDTO workDTO = (WorkDTO) o;
        if (workDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            "}";
    }
}
