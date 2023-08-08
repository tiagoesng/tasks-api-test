package apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://tomcat9:8080/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured
			.given()
			.when()
				.get("/todo")
			.then()
				.log().all()
				.statusCode(200)
			;
	}
	
	@Test
	public void deveAdicionarTaskComSucesso() {
		RestAssured
			.given()
				.contentType(ContentType.JSON)
				.body("{"
						+"\"task\":\"Descricao da Task\","+
						"\"dueDate\":\"2030-12-30\""
						+ "}")
			.when()
				.post("/todo")
			.then()
				.log().all()
				.statusCode(201)	
			;
	}
	
	@Test
	public void naodeveAdicionarTaskInvalida() {
		RestAssured
			.given()
				.contentType(ContentType.JSON)
				.body("{"
						+"\"task\":\"Descricao da Task\","+
						"\"dueDate\":\"2010-12-30\""
						+ "}")
			.when()
				.post("/todo")
			.then()
				.log().all()
				.statusCode(400)
				.body("message", CoreMatchers.is("Due date must not be in past"))
			;
	}

}
