package guru.sprfwk.controllers;

import guru.sprfwk.commands.RecipeCommand;
import guru.sprfwk.services.ImageService;
import guru.sprfwk.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {
	
	@Mock
	ImageService imageSvc;
	
	@Mock
	RecipeService recipeSvc;
	
	ImageController controller;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		controller = new ImageController(imageSvc, recipeSvc);
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new ControllerExceptionHandler())
				.build();
	}
	
	@Test
	public void getImageForm() throws Exception {
		//given
		RecipeCommand cmd = new RecipeCommand();
		cmd.setId(1L);
		
		when(recipeSvc.findCommandById(anyLong())).thenReturn(cmd);
		
		//when
		mockMvc.perform(get("/recipe/1/image"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("recipe"));
		
		//then
		verify(recipeSvc, times(1)).findCommandById(anyLong());
	}
	
	@Test
	public void handleImagePost() throws Exception {
		MockMultipartFile multipartFile =
				new MockMultipartFile("imagefile", "testing.txt", "text/plain",
						"Spring Framework Guru".getBytes());
		
		mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
				.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/recipe/1/show"));
		
		verify(imageSvc, times(1)).saveImageFile(anyLong(), any());
	}
	
	
	@Test
	public void renderImageFromDB() throws Exception {
		//given
		RecipeCommand cmd = new RecipeCommand();
		cmd.setId(1L);
		
		String s = "fake image text";
		Byte[] bytesBoxed = new Byte[s.getBytes().length];
		
		int i = 0;
		
		for(byte primByte : s.getBytes()) {
			bytesBoxed[i++] = primByte;
		}
		
		cmd.setImage(bytesBoxed);
		
		when(recipeSvc.findCommandById(anyLong())).thenReturn(cmd);
		
		//when
		MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		
		byte[] responseBytes = response.getContentAsByteArray();
		
		assertEquals(s.getBytes().length, responseBytes.length);
	}
	
	@Test
	public void testGetImageNumberFormatException() throws Exception {
		mockMvc.perform(get("/recipe/asdf/recipeimage"))
				.andExpect(status().isBadRequest())
				.andExpect(view().name("error"))
				.andExpect(model().attributeExists("status"))
				.andExpect(model().attribute("status", 404));
	}
}
