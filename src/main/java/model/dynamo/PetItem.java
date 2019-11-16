package model.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.PetGender;
import model.PetType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDBTable(tableName=PetConstants.TABLE_NAME)
public class PetItem {

    @DynamoDBHashKey(attributeName=PetConstants.ID)
    private String id;

    @DynamoDBAttribute(attributeName=PetConstants.NAME)
    private String name;
    
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName=PetConstants.TYPE)
    private PetType type;
    
    @DynamoDBAttribute(attributeName=PetConstants.AGE)
    private Integer age;
    
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName=PetConstants.SEX)
    private PetGender sex;

    @DynamoDBAttribute(attributeName=PetConstants.DESCRIPTION)
    private String description;

    @DynamoDBAttribute(attributeName=PetConstants.OWNER_EMAIL)
    private String ownerEmail;

    @DynamoDBAttribute(attributeName=PetConstants.IMAGE_URL)
    private String imageURL;
}
