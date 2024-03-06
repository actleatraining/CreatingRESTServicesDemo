package com.example.SpringBootIntroductionDemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SpringBootIntroductionDemoApplicationTests {

	@Autowired
	MockMvc mvc;

	@Autowired
	ObjectMapper mapper; // used to write a Java object to JSON for PUT and POST requests

	@Test
	public void testGetBook() throws Exception {
		mvc.perform(
						MockMvcRequestBuilders.get("/book")
				)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(containsString("The Iliad")))
				.andExpect(MockMvcResultMatchers.content().string(containsString("The Hitchhikers Guide to the Galaxy")))
				.andExpect(MockMvcResultMatchers.content().string(containsString("Pippi Longstocking")));
	}

	@Test
	public void testGetBookById() throws Exception {
		mvc.perform(
						MockMvcRequestBuilders.get("/book/1")
				)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(containsString("The Iliad")));
	}

	@Test
	public void testPostBook() throws Exception {
		mvc.perform(
						MockMvcRequestBuilders.get("/book")
				)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(not(containsString("New Book"))));

		mvc.perform(
						MockMvcRequestBuilders.post("/book")
								.content(mapper.writeValueAsString(new Book(null, "New Book", "New Author", 10)))
								.contentType(MediaType.APPLICATION_JSON_UTF8)
				)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(containsString("New Book")));

		mvc.perform(
						MockMvcRequestBuilders.get("/book")
				)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(containsString("New Book")));
	}

	@Test
	public void testPutBook() throws Exception {
		mvc.perform(
						MockMvcRequestBuilders.get("/book/3")
				)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(containsString("Pippi Longstocking")));

		mvc.perform(
						MockMvcRequestBuilders.put("/book/3")
								.content(mapper.writeValueAsString(new Book(3L, "Pippi Longstocking 2 with a vengence", "Astrid Lindgren", 10)))
								.contentType(MediaType.APPLICATION_JSON_UTF8)
				)
				.andExpect(status().is2xxSuccessful());

		mvc.perform(
						MockMvcRequestBuilders.get("/book/3")
				)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(containsString("Pippi Longstocking 2 with a vengence")));
	}

	@Test
	public void testDeleteBook() throws Exception {
		mvc.perform(
						MockMvcRequestBuilders.get("/book/1")
				)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(containsString("The Iliad")));

		mvc.perform(
						MockMvcRequestBuilders.delete("/book/1")
				)
				.andExpect(status().is2xxSuccessful());

		mvc.perform(
						MockMvcRequestBuilders.get("/book")
				)
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(not(containsString("The Iliad"))));
	}
}
