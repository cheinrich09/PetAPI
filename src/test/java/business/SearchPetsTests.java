package business;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dynamoDB.DynamoDBClient;
import model.dynamo.PetConstants;

@RunWith(MockitoJUnitRunner.class)
public class SearchPetsTests {
    
    @Mock
    private DynamoDBClient client;
    
    @InjectMocks
    private SearchPets searchPets;

    @Test
    public void givenNoInput_whenSearch_expectGetAllCalled() {
        searchPets.search(null, null, null, null, null, null, null, null);
        verify(client).getAll();
    }
    
    @Test
    public void givenNameInput_whenSearch_expectScanCalled() {
        searchPets.search(null, "name", null, null, null, null, null, null);
        String expectedString = PetConstants.NAME + " = :name";
        verify(client).scan(eq(expectedString), any());
    }
}
