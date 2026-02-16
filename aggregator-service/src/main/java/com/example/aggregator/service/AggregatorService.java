package com.example.aggregator.service;

import com.example.aggregator.dto.AggregatedResponseDto;

public interface AggregatorService {
	AggregatedResponseDto getProductOverview(Long productId);
}
