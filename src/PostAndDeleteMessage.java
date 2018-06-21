import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.Resources;
import files.Payload;

public class PostAndDeleteMessage {

	//public static void main(String[] args) {
	
	Properties prop;
	
	
	
	@BeforeTest
	public void getData() throws Exception
	{
		prop=new Properties();
		FileInputStream fis=new FileInputStream("E:\\Rest API Assured Projects\\RestProject1\\src\files\\env.properties");
		prop.load(fis);
	}
	
	
	
	@Test
	public void Test(){
		

		//program starts with below line. it is either baseURI  or host
		RestAssured.baseURI=prop.getProperty("HOST");
		
		//Add the response and Grab the response
		Response res=given().
				
		queryParam("key",prop.getProperty("KEY")).
		body(Payload.getPOSTData()).	
		when().
		post(Resources.placePOSTData()).
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("status",equalTo("OK")).
		extract().response();   //To extract the response
		
		//Grab the place id from the response
		//converting the raw format into String by using the below line
		String responseString=res.toString();
		//Converting the String "responseString" into JSON format
		JsonPath js=new JsonPath(responseString);
		String placeid=js.get("place_id");
		System.out.println(placeid);
		
		//Place the placeid in the delete request
		given().
		queryParam("key","AIzaSyCWg2j-SAAUKIy8rTVK_dPlw7z14kRL7Io").
		body("{"+
				"\"place_id\": \""+placeid+"\""+
				"}").
		when().
		post("/maps/api/place/delete/json").
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("status",equalTo("OK"));
	
	}

}
