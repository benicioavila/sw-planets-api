package com.example.sw_planet_api.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;

import jakarta.persistence.criteria.CriteriaBuilder.In;

import static com.example.sw_planet_api.common.PlanetConstants.PLANET;
import static com.example.sw_planet_api.common.PlanetConstants.INVALID_PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest(classes = PlanetService.class)
public class PlanetServiceTest {

    //@Autowired
    @InjectMocks
    private PlanetService planetService;

    //@MockBean
    @Mock
    private PlanetRepository planetRepository;

    //operacao_estado_retorno
    @Test
    public void createPlanet_WithValidData_ReturnsPlanet(){
        //AAA
        //Arrange
        when(planetRepository.save(PLANET)).thenReturn(PLANET);

        // system under test
        //ACT
        Planet sut = planetService.create(PLANET);
        
        // Assert
        assertThat(sut).isEqualTo(PLANET);



    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException(){
        when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> planetService.create(INVALID_PLANET)).isInstanceOf(RuntimeException.class);
        
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet(){
        when(planetRepository.findById(1L)).thenReturn(Optional.of(PLANET));
        
        Optional<Planet> sut = planetService.get(1L);

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);

        
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsEmpty(){
        when(planetRepository.findById(1L)).thenReturn(Optional.empty());        
        
        Optional<Planet> sut = planetService.get(1L);
        
        assertThat(sut).isEmpty();        
        
    }


    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet(){
        when(planetRepository.findByName(PLANET.getName())).thenReturn(Optional.of(PLANET));
        
        Optional<Planet> sut = planetService.getByName(PLANET.getName());

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);

        
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnsEmpty(){
        when(planetRepository.findByName("UNEXISTING NAME")).thenReturn(Optional.empty());        
        
        Optional<Planet> sut = planetService.getByName(PLANET.getName());
        
        assertThat(sut).isEmpty();        
        
    }
    
    @Test
    public void listPlanets_ReturnsAllPlanets(){
        List<Planet> planets = new ArrayList<>(){
            {
                add(PLANET);
            }
            
        };

        Example<Planet> query = QueryBuilder.makeQuery(new Planet(PLANET.getClimate(), PLANET.getTerrain()));

        when(planetRepository.findAll(query)).thenReturn(planets);
        
        List<Planet> sut = planetService.list(PLANET.getTerrain(),PLANET.getClimate());

        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(PLANET);

       
    }

    @Test
    public void listPlanets_ReturnsNoPlanets(){
        when(planetRepository.findAll(any())).thenReturn(Collections.emptyList());
        
        List<Planet> sut = planetService.list(PLANET.getTerrain(),PLANET.getClimate());

        assertThat(sut).isEmpty();        
        

       
    } 

    @Test
    public void removePlanets_WithExistingId_doesNotThrowAnyException(){
       assertThatCode(() -> planetService.remove(1L)).doesNotThrowAnyException();                       
       
    } 

    @Test
    public void removePlanets_WithUnexistingId_ThrowException(){
       doThrow(new RuntimeException()).when(planetRepository).deleteById(99L);
       assertThatThrownBy(() -> planetService.remove(99L)).isInstanceOf(RuntimeException.class);                                 

       
    } 


    
}
