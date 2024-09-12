package com.lcwd.store.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import com.lcwd.store.dtos.PageableResponse;
import com.lcwd.store.dtos.ProductDto;
import com.lcwd.store.entities.Product;

// U -> entity 
// V -> Dto

public class Helper {
	
	public static <U, V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type){
		
		 List<U> obj = page.getContent(); 
	        List<V> objDtos = obj.stream().map(p -> new ModelMapper().map(p, type)).collect(Collectors.toList());
	        
	        PageableResponse<V> response = new PageableResponse<>();
	        response.setContents(objDtos);
	        response.setPageNumber(page.getNumber());
	        response.setPageSize(page.getSize());
	        response.setTotalElements(page.getTotalElements());
	        response.setTotalPages(page.getTotalPages());
	        response.setLastPage(page.isLast());
	        
	        return response;
	
		
	}

}
