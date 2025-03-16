package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest2 {

	Faker faker;
	User userPayload;
	public Logger logger;
	@BeforeClass
	public void setupData() {
		faker = new Faker();
		userPayload = new User();

		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		
		logger=LogManager.getLogger(this.getClass());
		
		
		

	}

	@Test(priority = 1)
	void testPostUser() {
		logger.info("Creating user");
		Response response = UserEndPoints2.createUser(userPayload);

		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("User Created");

	}

	@Test(priority = 2)
	public void testGetUserByName() throws InterruptedException {
		Thread.sleep(10000);
		String username=this.userPayload.getUsername();
		logger.info("Getting user ("+username+") info");
		Response response = UserEndPoints2.readUser(username);

		response.then().log().all();
		Assert.assertEquals(response.statusCode(), 200);
		logger.info("Got user info");

	}

	@Test(priority = 3)
	public void testUpdateUserByName() throws InterruptedException {
		Thread.sleep(10000);
		String username=this.userPayload.getUsername();

		logger.info("Updating  user ("+username+")");

		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		
		Response response = UserEndPoints2.updateUser(username,userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		
		Response responseAfterUpdate = UserEndPoints2.readUser(this.userPayload.getUsername());

		Assert.assertEquals(responseAfterUpdate.statusCode(), 200);
		logger.info("USer updated");

	}
	
	@Test(priority=4)
	public void testDeleteUserByName() throws InterruptedException
	{
		Thread.sleep(10000);
		String username=this.userPayload.getUsername();

		logger.info("Deleting user ("+username+")");

		Response response = UserEndPoints2.deleteUser(username);
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("User deleted");

	}
}
