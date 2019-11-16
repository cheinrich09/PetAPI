package business;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.ArgumentMatchers.*;

import dynamoDB.DynamoDBClient;
import model.Pet;
import model.dynamo.PetItem;

@RunWith(MockitoJUnitRunner.class)
public class CreatePetsTests {

    @Mock
    private DynamoDBClient client;
    
    @Mock
    private PetItem item;
    
    @InjectMocks
    private CreatePets createPets;
    
    @Test
    public void givenPet_whenCreate_expectIdReturned() {
        Pet testPet = Pet.builder()
                .name("test")
                .build();
        List<Pet> input = new ArrayList<>();
        input.add(testPet);
        List<String> output = createPets.create(input);
        assertThat(output, hasSize(1));
        verify(client).save(any(PetItem.class));
    }
    
    @Test
    public void givenTwoPets_whenCreate_expectTwoIdsReturned() {
        Pet testPet = Pet.builder()
                .name("test")
                .build();
        Pet testPet2 = Pet.builder()
                .name("test2")
                .build();
        List<Pet> input = new ArrayList<>();
        input.add(testPet);
        input.add(testPet2);
        List<String> output = createPets.create(input);
        assertThat(output, hasSize(2));
        verify(client, times(2)).save(any(PetItem.class));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void givenNoPets_whenCreate_expectExceptionThrown() {
        List<Pet> input = new ArrayList<>();
        createPets.create(input);
    }
}
