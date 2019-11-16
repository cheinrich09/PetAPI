# PetAPI
 This REST API can be used to store, update, search and delete pets for a hypothetical Pet Service app.
 The API is built in Java, using Spring Boot, Maven, and Lombok. DynamoDB is used to store the Pet Entries. 
 
 # Requirements
  Maven must be installed on the system to be able to build the jar file
  Java 12+ must be installed on the system to run the jar file
  To run successfully, the credentials from the separately provided credentials file must be placed in the correct default location    as described here: https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
  
 # To Build
  To build the jar file, run mvnw clean package in the root folder. This will trigger the full build cycle (compile, install, etc.) to create the executable jar.
  Tests will be run during the build process.
 
 # To Run
  First, make sure that the credentials file has been saved in the correct location, or the access key and secret access key saved in an alternative manner following the instructions in https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
 
 # Endpoint URL and corresponding HTTP verbs
  Create Pets: POST http://localhost:8080/create
   - The list of JSON Dictionaries are provided in the request body, with content-type application/json
   - The JSON Dictionaries should not include an Id value, since a UUID will be set when the pet entries are saved
  Update Pets: PUT http://localhost:8080/update
   - The list of JSON Dictionaries are provided in the request body, with content-type application/json
   - The JSON Dictionaries must include the ID value. An exception will be thrown if any pets are missing this
  Search Pets: GET http://localhost:8080/search
   - The filter values are provided as Query params, resuling in a request like http://localhost:8080/search?name=Test&sex=m to look up all pets with the name Test which are male.
  Delete Pets: DELET http://localhost:8080/delete
   - The input is provided as query params. To delete all entries, the input is http://localhost:8080/delete?all=true
   - To delete a list of Ids, pass in each Id in turn, separated by &. For example http://localhost:8080/delete?ids=3dda2b0f-6287-4b30-9462-9fa99f215aa3&ids=68c70607-f510-4aad-a4d7-1f0431bb08d6
  
