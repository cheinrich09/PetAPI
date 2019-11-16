package business;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dynamoDB.DynamoDBClient;
import model.Pet;
import model.PetType;

public class ApplicationIntegrationTests {

    private DynamoDBClient client;
    
    private CreatePets createPets;
    
    private UpdatePets updatePets;
    
    private SearchPets searchPets;
    
    private DeletePets deletePets;
    
    @Before
    public void setup() {
        client = new DynamoDBClient();
        createPets = new CreatePets(client);
        updatePets = new UpdatePets(client);
        searchPets = new SearchPets(client);
        deletePets = new DeletePets(client);
    }
    
    @Test
    public void givenPet_createUpdateSearchThenDelete() {
        final Pet testPet = Pet.builder()
                .name("integPet")
                .type(PetType.cat)
                .build();
        
        final List<Pet> input = new ArrayList<Pet>();
        input.add(testPet);
        
        final List<String> ids = createPets.create(input);
        assertThat(ids, hasSize(1));
        
        input.get(0).setId(ids.get(0));
        input.get(0).setImageURL("integ image url");
        
        final List<String> updateIds = updatePets.update(input);
        assertEquals(ids, updateIds);
        
        final List<Pet> searchedPets = searchPets.search(null, null, null, null,
                null, null, null, "integ image url");
        assertThat(searchedPets, hasSize(1));
        
        deletePets.delete(ids, null);
    }
}
