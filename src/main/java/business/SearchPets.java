package business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import dynamoDB.DynamoDBClient;
import dynamoDB.ObjectMapper;
import lombok.AllArgsConstructor;
import model.Pet;
import model.PetGender;
import model.PetType;
import model.dynamo.PetConstants;

@RestController
@AllArgsConstructor
public class SearchPets {

    @Autowired
    private DynamoDBClient client;
    
    @RequestMapping(value = "/search", method=RequestMethod.GET)
    public @ResponseBody List<Pet> search(
            @RequestParam(value="id", required = false) String id,
            @RequestParam(value="name", required = false) String name,
            @RequestParam(value="type", required = false) PetType type,
            @RequestParam(value="age", required = false) Integer age,
            @RequestParam(value="sex", required = false) PetGender sex,
            @RequestParam(value="description", required = false) String description,
            @RequestParam(value="owner_email", required = false) String ownerEmail,
            @RequestParam(value="image_url", required = false) String imageURL) {

        final Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        final StringBuilder filterExpressionBuilder = new StringBuilder();
        if(id != null) {
            eav.put(":id", new AttributeValue().withS(id));
            filterExpressionBuilder.append(PetConstants.ID + " = :id and ");
        }
        if(name != null) {
            eav.put(":name", new AttributeValue().withS(name));
            filterExpressionBuilder.append(PetConstants.NAME + " = :name and ");
        }
        if(type != null) {
            eav.put(":type", new AttributeValue().withS(type.toString()));
            filterExpressionBuilder.append(PetConstants.TYPE + " = :type and ");
        }
        if(age != null) {
            eav.put(":age", new AttributeValue().withN(age.toString()));
            filterExpressionBuilder.append(PetConstants.NAME + " = :name and ");
        }
        if(sex != null) {
            eav.put(":sex", new AttributeValue().withS(sex.toString()));
            filterExpressionBuilder.append(PetConstants.SEX + " = :sex and ");
        }
        if(description != null) {
            eav.put(":desc", new AttributeValue().withN(description.toString()));
            filterExpressionBuilder.append(PetConstants.DESCRIPTION + " = :desc and ");
        }
        if(ownerEmail != null) {
            eav.put(":owner_email", new AttributeValue().withS(ownerEmail));
            filterExpressionBuilder.append(PetConstants.OWNER_EMAIL + " = :owner_email and ");
        }
        if(imageURL != null) {
            eav.put(":image_url", new AttributeValue().withS(imageURL));
            filterExpressionBuilder.append(PetConstants.IMAGE_URL + " = :image_url and ");
        }
        
        //Strip the excess and at the end of the key condition expression
        int filterExpressionLength = filterExpressionBuilder.length();
        if(filterExpressionLength != 0) {
            filterExpressionBuilder.delete(filterExpressionLength-5, filterExpressionLength);
        } else {
            //if condition is empty, no values were provided, so need to return all
            return client.getAll().stream()
                    .map(item -> ObjectMapper.getPet(item))
                    .collect(Collectors.toList());
        }
        
        return client.scan(filterExpressionBuilder.toString(), eav).stream()
                .map(item -> ObjectMapper.getPet(item))
                .collect(Collectors.toList());
    }
}
