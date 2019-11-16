package business;

import java.util.List;
import java.util.UUID;
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
public class CreatePets {

    @Autowired
    private DynamoDBClient client;
    
    @RequestMapping(value = "/create", method=RequestMethod.POST)
    public @ResponseBody List<String> create(@Validated(Pet.New.class) @RequestBody List<Pet> pets) {
        if(pets == null || pets.isEmpty()) {
            //logs and metrics
            throw new IllegalArgumentException("Must provide a list of JSON dictionaries representing pets to create");
        }
        return pets.stream()
                .map(pet -> createPet(pet))
                .collect(Collectors.toList());
    }
    
    private String createPet(Pet pet) {
        //Generate UUID
        final String id = UUID.randomUUID().toString();
        pet.setId(id);
        
        //Write Pet to DynamoDB
        final PetItem item = ObjectMapper.getItem(pet);
        client.save(item);
        
        //Return ID
        return id;
    }
}
