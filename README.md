# Sample Spring5 MVC REST Application

- This code follows the section of RESTFul WebServices with Spring MVC from John Thompson's Spring Framework 5: Beginner to Guru course.
	- In this section with the help of Spring Boot and Spring MVC there were a few assignments asking to implement a Vendors API similiar to the one seen here, https://api.predic8.de/shop/docs 
	- RESTFul API calls could be made using a variety of HTTP methods such as: GET, POST, DELETE, GET, PATCH, and PUT.
	- In this section we also discussed using TDD (Test Driven Development) to perform test automation on the implementations of the APIs created.
		- Some technologies listed below and a brief discussion about them:
			- JUnit: The framework used to create the automated tests.
			- Mockito: The framework used to help the unit tests create objects required to perform the tests.
			- MockMvc: A Java related technology which helps simulate web requests to query the implemented APIs.
	- We also discussed using Postman which was a useful tool to simulate a request such as making a GET request to get the list of all the vendors.
	- Use of CircleCI to ensure best practices and to practice continuous integration.
	    - This was used to ensure that every change pushed to GitHub would not result in a failed build. If the build failed there would be an e-mail notification.