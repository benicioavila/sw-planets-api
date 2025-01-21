package com.example.sw_planet_api.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.sw_planet_api.domain.Planet;
import com.example.sw_planet_api.domain.PlanetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static com.example.sw_planet_api.common.PlanetConstants.PLANET;

@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SuppressWarnings("removal")
    @MockBean
    private PlanetService planetService;

    @Test
    public void createPlanet_WithValidData_ReturnsCreated() throws Exception {
        when(planetService.create(PLANET)).thenReturn(PLANET);

        mockMvc.perform(post("/planets")                
                .content(objectMapper.writeValueAsString(PLANET)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(PLANET));

    }

    @Test
    public void createPlanet_WithInvalidData_ReturnsBadRequest() throws Exception {
        Planet emptyPlanet = new Planet();
        Planet invalidPlanet = new Planet("","", "");

        
        mockMvc.perform(post("/planets")                
                .content(objectMapper.writeValueAsString(emptyPlanet)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        
        mockMvc.perform(post("/planets")                
                .content(objectMapper.writeValueAsString(invalidPlanet)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
                


    }

    @Test
    public void createPlanet_WithExistingName_ReturnsConflict() throws JsonProcessingException, Exception{
        when(planetService.create(any())).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(post("/planets")                
                .content(objectMapper.writeValueAsString(PLANET)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());


    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() throws Exception{
        when(planetService.get(1L)).thenReturn(java.util.Optional.of(PLANET));

        mockMvc.perform(get("/planets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(PLANET));
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsNotFound() throws Exception{
        
        mockMvc.perform(get("/planets/1"))
        .andExpect(status().isNotFound());
        
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet() throws Exception{
        when(planetService.getByName("Tatooine")).thenReturn(java.util.Optional.of(PLANET));

        mockMvc.perform(get("/planets/name/Tatooine"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(PLANET));
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsNotFound() throws Exception{
        
        mockMvc.perform(get("/planets/name/Tatooine"))
        .andExpect(status().isNotFound());
        
    }
    
}
