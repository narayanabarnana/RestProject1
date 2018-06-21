package twitterAPI;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;;


public class GetLatestTweet {
	
	String ConsumerKey="DDmWFEfZykc8i8F5H77uPJKGz";
	String ConsumerSecret="p72WOkuqhQ0EFXZlYvrjHJBgIbNMWmlkw6vjEXd8PJHCKDBCMT";
	String Token="421068780-hiDAiyTqwVgwsuALOrzs5dtHU49lLIq3EoV9mN7W";
	String TokenSecret="1GR9se72qfMNkRpmjIB9frIF333kTWyalqZzJITqEj77U";
	String id;
	
	@Test(enabled=false)
	public void getLatestTweet()
	{
		RestAssured.baseURI="https://api.twitter.com/1.1/statuses";
		
		Response res=given().
		auth().oauth(ConsumerKey, ConsumerSecret, Token, TokenSecret).queryParam("count","1").
		//auth().oauth(ConsumerKey, ConsumerSecret, Token, TokenSecret).queryParam("count", "1").
		when().
		get("home_timeline.json").
		then().extract().response();
		
		String response=res.asString();
		System.out.println(response);
		
		JsonPath js=new JsonPath(response);
		String txt=js.get("text");
		System.out.println(txt);
		System.out.println(txt);
		String id1=js.get("id").toString();
		System.out.println(id1);
	}
	
	@Test
	public void createTweet()
	{
		RestAssured.baseURI="https://api.twitter.com/1.1/statuses";
		
		Response res=given().
		auth().oauth(ConsumerKey, ConsumerSecret, Token, TokenSecret).queryParam("status","Hello Rest API").
		//auth().oauth(ConsumerKey, ConsumerSecret, Token, TokenSecret).queryParam("count", "1").
		when().
		post("/update.json").
		then().extract().response();
		
		String response=res.asString();
		System.out.println(response);
		
		JsonPath js=new JsonPath(response);
		//String txt=js.get("text");
		//System.out.println(txt);
		//System.out.println(txt);
		id=js.get("id").toString();
		System.out.println(id);
	}
	
	@Test
	public void deleteTweet(){
		
		createTweet();
		RestAssured.baseURI="https://api.twitter.com/1.1/statuses";
		
		Response res=given().
		auth().oauth(ConsumerKey, ConsumerSecret, Token, TokenSecret).
		//auth().oauth(ConsumerKey, ConsumerSecret, Token, TokenSecret).queryParam("count", "1").
		when().
		post("/destroy/"+id+".json").
		then().extract().response();
		
		String response=res.asString();
		System.out.println(response);
		
		
		
	}

}
