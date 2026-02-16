package com.example.aggregator.controller;

import com.example.aggregator.dto.AggregatedResponseDto;
import com.example.aggregator.service.AggregatorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/aggregate")
public class AggregatorController {

	private final AggregatorService service;

	public AggregatorController(AggregatorService service) {
		this.service = service;
	}

	@GetMapping("/product/{id}")
	public AggregatedResponseDto getOverview(@PathVariable Long id) {
		return service.getProductOverview(id);
	}
}
