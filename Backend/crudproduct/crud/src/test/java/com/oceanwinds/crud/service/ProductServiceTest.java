package com.oceanwinds.crud.service;

import Global.exceptions.AttributeException;
import Global.exceptions.ResourceNotFoundException;
import com.oceanwinds.crud.entity.Yachts;
import com.oceanwinds.crud.entity.dto.YachtsDto;
import com.oceanwinds.crud.repository.YachtsRepository;
import com.oceanwinds.crud.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductServiceTest {

    @Mock
    private YachtsRepository yachtsRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllYachts() {
        List<Yachts> yachtsList = new ArrayList<>();


        Mockito.when(yachtsRepository.findAll()).thenReturn(yachtsList);

        List<Yachts> result = productService.getAllYachts();


        Assertions.assertEquals(yachtsList.size(), result.size());

    }

    @Test
    public void testGetYachtById() {
        Long yachtId = 1L;
        Yachts yacht = new Yachts();
        yacht.setId(yachtId);


        Mockito.when(yachtsRepository.findById(yachtId)).thenReturn(Optional.of(yacht));

        Yachts result = productService.getYachtById(yachtId);

        Assertions.assertEquals(yacht.getId(), result.getId());

    }

    @Test
    public void testGetYachtById_ResourceNotFoundException() {
        Long invalidYachtId = 999L;

        Mockito.when(yachtsRepository.findById(invalidYachtId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.getYachtById(invalidYachtId));
    }



}