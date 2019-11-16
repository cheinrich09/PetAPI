package business;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dynamoDB.DynamoDBClient;

@RunWith(MockitoJUnitRunner.class)
public class DeletePetsTests {

    @Mock
    private DynamoDBClient client;
    
    @InjectMocks
    private DeletePets deletePets;
    
    @Test(expected = IllegalArgumentException.class)
    public void givenNoInput_whenDelete_expectExceptionThrown() {
        deletePets.delete(null, null);
    }
    
    @Test
    public void givenAllIsTrue_whenDelete_expectGetAllCalled() {
        deletePets.delete(null, true);
        verify(client).getAll();
        verify(client).batchDelete(any());
    }
    
    @Test
    public void givenIds_whenDelete_expectBatchGetCalled() {
        List<String> ids = new ArrayList<>();
        ids.add("1");
        ids.add("2");
        deletePets.delete(ids, null);
        verify(client).batchGet(ids);
        verify(client).batchDelete(any());
    }
}
