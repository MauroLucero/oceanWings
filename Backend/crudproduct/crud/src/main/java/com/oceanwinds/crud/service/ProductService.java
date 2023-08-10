package com.oceanwinds.crud.service;


import Global.exceptions.AttributeException;
import Global.exceptions.ResourceNotFoundException;
import Global.util.PaginatedResponse;
import com.oceanwinds.crud.entity.Category;
import com.oceanwinds.crud.entity.dto.YachtsDto;
import com.oceanwinds.crud.entity.Yachts;
import com.oceanwinds.crud.repository.YachtsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    @Autowired
    YachtsRepository yachtsRepository;

    public List<Yachts> getAllYachts() {
        return yachtsRepository.findAll();
    }

    public Yachts getYachtById(Long id) {
        return yachtsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Yacht not found"));
    }

    public Yachts getYachtByName(String name) {
        return yachtsRepository.findByName(name).get();
    }

    public Yachts createYacht(YachtsDto dto) throws AttributeException {

        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new AttributeException("Name is required");
        }
        if (dto.getDescription() == null || dto.getDescription().isEmpty()) {
            throw new AttributeException("Description is required");
        }
        if (dto.getImageUrl() == null || dto.getImageUrl().isEmpty()) {
            throw new AttributeException("Image is required");
        }


        Yachts yacht = new Yachts();

        yacht.setName(dto.getName());
        yacht.setSku(dto.getSku());
        yacht.setDescription(dto.getDescription());
        yacht.setImageUrl(dto.getImageUrl());
        yacht.setAvailable(dto.getAvailable());
        yacht.setPricePerDay(dto.getPricePerDay());
        yacht.setCategory(dto.getCategory());
        yacht.setPricePerHour(dto.getPricePerHour());
        yacht.setPricePerWeek(dto.getPricePerWeek());


        return yachtsRepository.save(yacht);
    }


    public Yachts updateYacht(Long id, YachtsDto dto) throws AttributeException {
        Yachts yacht = yachtsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Yacht not found"));
        yacht.setName(dto.getName());
        yacht.setSku(dto.getSku());
        yacht.setDescription(dto.getDescription());
        yacht.setImageUrl(dto.getImageUrl());
        yacht.setAvailable(dto.getAvailable());
        yacht.setPricePerDay(dto.getPricePerDay());
        yacht.setCategory(dto.getCategory());
        yacht.setPricePerHour(dto.getPricePerHour());
        yacht.setPricePerWeek(dto.getPricePerWeek());


        return yachtsRepository.save(yacht);
    }

    public void deleteYacht(Long id) {

        Yachts yacht = yachtsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Yacht not found"));
        yachtsRepository.deleteById(id);
    }

    public boolean existsByAvailable(boolean available) throws AttributeException {
        if (!available) {
            throw new AttributeException("Yacht is not Available for rent");

        }
        return yachtsRepository.existsByAvailable(available);

    }


    public List<Yachts> getAvailableYachtsByCategory(String category) {
        return yachtsRepository.findByAvailableAndCategory(true, category);
    }

    public List<Yachts> getYachtsByCategory(Category category) {
        return yachtsRepository.findByCategory(category);
    }

    public List<Yachts> getAvailableYachts() {

        return yachtsRepository.findByAvailable(true);
    }

    public List<Map<String, Object>> findYachtsWithModifiedImages(Long id) {
        List<Map<String, Object>> modifiedImagesList = new ArrayList<>();
        Yachts yacht = yachtsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Yacht not found"));

        for (int i = 1; i <= 13; i++) {
            Map<String, Object> modifiedImage = new HashMap<>();
            modifiedImage.put("id", i);
            modifiedImage.put("url", yacht.getImageUrl() + i + ".png");
            modifiedImagesList.add(modifiedImage);
        }
        return modifiedImagesList;
    }

    public PaginatedResponse<Yachts> getAllYachts(int page, int perPage) {
        Page<Yachts> yachtsPage = yachtsRepository.findAll(PageRequest.of(page - 1, perPage));

        PaginatedResponse<Yachts> paginatedResponse = new PaginatedResponse<>();
        paginatedResponse.setPage(yachtsPage.getNumber() + 1);
        paginatedResponse.setPer_page(yachtsPage.getSize());
        paginatedResponse.setTotal(yachtsPage.getTotalElements());
        paginatedResponse.setTotal_pages(yachtsPage.getTotalPages());
        paginatedResponse.setData(yachtsPage.getContent());

        return paginatedResponse;
    }

    public PaginatedResponse<Yachts> getYachtsByPage(int page, int perPage) {
        PaginatedResponse<Yachts> allYachts = getAllYachts(page, perPage);
        List<Yachts> yachts = allYachts.getData();
        List<Yachts> yachtsByPage = new ArrayList<>();
        for (int i = (page - 1) * perPage; i < page * perPage; i++) {
            if (i < yachts.size()) {
                yachtsByPage.add(yachts.get(i));
            }
        }
        allYachts.setData(yachtsByPage);
        return allYachts;

    }
}


