package dynamoDB;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import lombok.NonNull;
import model.dynamo.PetItem;

public class DynamoDBClient {

    private static final String ACCESS_KEY = "AKIAUIJSBVSHDJ73TBDD";
    private static final String SECRET_KEY = "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";
    private static final String TABLE_NAME = "Pets";
    private DynamoDBMapper mapper;
    
    public DynamoDBClient() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        AWSCredentialsProvider credentials = new AWSStaticCredentialsProvider(awsCreds);
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_EAST_2)
                .withCredentials(credentials)
                .build();
        
        mapper = new DynamoDBMapper(client);
    }

    public void save(@NonNull final PetItem item) {
        mapper.save(item);
    }
    
    public void delete(@NonNull final PetItem item) {
        mapper.delete(item);
    }
    
    public void batchDelete(@NonNull final List<PetItem> items) {
        mapper.batchDelete(items);
    }
    
    public PetItem get(@NonNull final String id) {
        return mapper.load(PetItem.class, id);
    }
    
    public List<PetItem> batchGet(@NonNull final List<String> ids) {
        final List<PetItem> keyObjects = ids.stream()
                .map(id -> getKeyObject(id))
                .collect(Collectors.toList());
        Map<String, List<Object>> petMap = mapper.batchLoad(keyObjects);
        return petMap.get(TABLE_NAME).stream()
                .map(value -> (PetItem)value)
                .collect(Collectors.toList());
    }
    
    public List<PetItem> getAll() {
        final DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        return mapper.scan(PetItem.class, scanExpression);
    }
    
    public List<PetItem> scan(@NonNull final String filterExpression,
                              @NonNull final Map<String, AttributeValue> eav) {
        
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
            .withFilterExpression(filterExpression)
            .withExpressionAttributeValues(eav);
        
        return mapper.scan(PetItem.class, scanExpression);
    }
    
    private PetItem getKeyObject(final String id) {
        return PetItem.builder()
                .id(id)
                .build();
    }
}
