package file;
import static io.restassured.RestAssured.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.path.json.JsonPath;

public class oAuthTest {

	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver" ,"C://Users//asharm9x//Downloads//chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		driver.findElement(By.xpath("//input[@type='email'])")).sendKeys("p1@gmail.com");
		driver.findElement(By.xpath("//span[text()='Next']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[@id='password']")).sendKeys("12345");
		driver.findElement(By.xpath("//div[@id='password']")).sendKeys(Keys.ENTER);
		Thread.sleep(4000);
		String url = driver.getCurrentUrl();
		String partialcode = url.split("code=")[1];
		String code = url.split("&scope")[0];
		System.out.println(code);
		
		
		String accesstoken=given().urlEncodingEnabled(false).queryParams("code",code)
		.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type", "authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		
		JsonPath js =  ReUsableMethods.rawToJson(accesstoken);
        String access = js.get("access_token");
		
		
		
		
		String response = given().queryParam("access_token", access)
				.when().log().all().
				get("https://www.rahulshettyacademy.com/getCourse.php").asString();
		
		System.out.println(response);
		
		
	}

}
