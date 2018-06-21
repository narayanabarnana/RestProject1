import io.restassured.RestAssured;
import io.restassured.http.ContentType;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;;

public class Basics {

	//public static void main(String[] args) {
	@Test
	public void Test(){
		
		// TODO Auto-generated method stub
		
		//program starts with below line. it is either baseURI  or host
		RestAssured.baseURI="https://maps.googleapis.com";
		
		given().
				param("location","-33.8670522,151.1957362").
				param("radius","500").
				param("key","AIzaSyCWg2j-SAAUKIy8rTVK_dPlw7z14kRL7Io").
				when().
				get("/maps/api/place/nearbysearch/json").
				then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
				//body("results[0].geometry.location.lat", equalTo("-33.8688197")).and().
				body("results[0].name",equalTo("Sydney")).and().
				header("Content-Length ", "10155").and().
				body("results[7].photos.html_attributions",equalTo("<a href=\"https://maps.google.com/maps/contrib/105717038561558812042/photos\">murray mcallister</a>"));
				
				
	}

}
