package business;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dynamoDB.DynamoDBClient;
import dynamoDB.ObjectMapper;
import lombok.AllArgsConstructor;
import model.Pet;
import model.dynamo.PetItem;

@RestController
@AllArgsConstructor
public class UpdatePets {

    @Autowired
    private DynamoDBClient client;
    @RequestMapping(value = "/update", method=RequestMethod.PUT)
    public @ResponseBody List<String> update(@Validated(Pet.Existing.class) @RequestBody List<Pet> pets) {
        if(pets == null || pets.isEmpty()) {
            //logs and metrics
            throw new IllegalArgumentException("Must provide a list of JSON dictionaries representing pets to update");
        }
        return pets.stream()
                .map(pet -> updatePet(pet))
                .collect(Collectors.toList());
    }
    
    private String updatePet(final Pet pet) {
        final String id = pet.getId();
        
        final PetItem savedItem = client.get(id);
        final Pet saved = ObjectMapper.getPet(savedItem);
        
        final Pet updated = saved.update(pet);
        
        final PetItem item = ObjectMapper.getItem(updated);
        client.save(item);
        
        return id;
    }
}
