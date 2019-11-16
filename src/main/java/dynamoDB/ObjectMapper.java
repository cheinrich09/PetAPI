package dynamoDB;

import lombok.NonNull;
import model.Pet;
import model.dynamo.PetItem;

public class ObjectMapper {

    public static PetItem getItem(@NonNull final Pet pet) {
        return PetItem.builder()
                .id(pet.getId())
                .name(pet.getName())
                .type(pet.getType())
                .age(pet.getAge())
                .sex(pet.getSex())
                .description(pet.getDescription())
                .ownerEmail(pet.getOwnerEmail())
                .imageURL(pet.getImageURL())
                .build();
    }
    
    public static Pet getPet(@NonNull final PetItem petItem) {
        return Pet.builder()
                .id(petItem.getId())
                .name(petItem.getName())
                .type(petItem.getType())
                .age(petItem.getAge())
                .sex(petItem.getSex())
                .description(petItem.getDescription())
                .ownerEmail(petItem.getOwnerEmail())
                .imageURL(petItem.getImageURL())
                .build();
    }
}
