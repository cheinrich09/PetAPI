package business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dynamoDB.DynamoDBClient;
import lombok.AllArgsConstructor;
import model.dynamo.PetItem;

@RestController
@AllArgsConstructor
public class DeletePets {

    @Autowired
    private DynamoDBClient client;
    
    @RequestMapping(value = "/delete", method=RequestMethod.DELETE)
    public void delete(@RequestParam(required = false) List<String> ids,
                       @RequestParam(required = false) Boolean all) {
        if(all == null && ids == null) {
            //logs and metrics
            throw new IllegalArgumentException("Must provide a list of ids to delete, or all=true to delete all entries");
        }
        
        final List<PetItem> toDelete;
        if(all != null && all.booleanValue()) {
            toDelete = client.getAll();
        } else {
            toDelete = client.batchGet(ids);
        }
        
        client.batchDelete(toDelete);
    }
}
