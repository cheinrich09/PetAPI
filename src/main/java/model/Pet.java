package model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Pet {
    
    @NotNull(groups = Existing.class)
    @Null(groups = New.class)
    private String id;
    
    private String name;
    private PetType type;
    private Integer age;
    private PetGender sex;
    private String description;
    
    @JsonProperty("owner_email")
    private String ownerEmail;
    @JsonProperty("image_url")
    private String imageURL;

    public Pet update(@NonNull final Pet other) {
        if(other.getName() != null) {
            this.name = other.getName();
        }
        if(other.getType() != null) {
            this.type = other.getType();
        }
        if (other.getAge() != null) {
            this.age = other.getAge();
        }
        if (other.getSex() != null) {
            this.sex = other.getSex();
        }
        if (other.getDescription() != null) {
            this.description = other.getDescription();
        }
        if (other.getOwnerEmail() != null) {
            this.ownerEmail = other.getOwnerEmail();
        }
        if (other.getImageURL() != null) {
            this.imageURL = other.getImageURL();
        }
        return this;
    }
    
    public interface Existing {
    }
 
    public interface New {
    }
}
