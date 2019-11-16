package business;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dynamoDB.DynamoDBClient;
import model.Pet;
import model.dynamo.PetItem;

@RunWith(MockitoJUnitRunner.class)
public class UpdatePetsTests {

    @Mock
    private DynamoDBClient client;
    
    @Mock
    private PetItem item;
    
    @InjectMocks
    private UpdatePets updatePets;
    
    @Before 
    public void setup() {
        when(client.get(any())).thenReturn(item);
    }
    
    @Test
    public void givenPet_whenUpdate_expectIdReturned() {
        Pet testPet = Pet.builder()
                .id("1")
                .name("test")
                .build();
        List<Pet> input = new ArrayList<>();
        input.add(testPet);
        List<String> output = updatePets.update(input);
        assertThat(output, hasSize(1));
        verify(client).get(any());
        verify(client).save(any(PetItem.class));
    }
    
    @Test
    public void givenTwoPets_whenUpdate_expectTwoIdsReturned() {
        Pet testPet = Pet.builder()
                .id("1")
                .name("test")
                .build();
        Pet testPet2 = Pet.builder()
                .id("2")
                .name("test2")
                .build();
        List<Pet> input = new ArrayList<>();
        input.add(testPet);
        input.add(testPet2);
        List<String> output = updatePets.update(input);
        assertThat(output, hasSize(2));
        verify(client, times(2)).get(any());
        verify(client, times(2)).save(any(PetItem.class));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void givenNoPets_whenUpdate_expectExceptionThrown() {
        List<Pet> input = new ArrayList<>();
        updatePets.update(input);
    }
}
