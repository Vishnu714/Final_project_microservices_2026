package com.example.aggregator.dto;

public record ProductDetailsDto(
	Long id,
	String name,
	String description,
	Double price,
	String sku,
	Boolean active
) {}
