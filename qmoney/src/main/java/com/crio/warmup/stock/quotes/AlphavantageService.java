package com.crio.warmup.stock.quotes;

import com.crio.warmup.stock.dto.Candle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class AlphavantageService implements StockQuotesService {

  private final String apiKey;
  private final RestTemplate restTemplate;

  public AlphavantageService(String apiKey, RestTemplate restTemplate) {
    this.apiKey = apiKey;
    this.restTemplate = restTemplate;
  }

  @Override
  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to) {
    URI uri = buildUri(symbol, from, to);
    // Make API call using restTemplate and process response
    return null; // Implement this
  }

  private URI buildUri(String symbol, LocalDate from, LocalDate to) {
    String baseUrl = "https://www.alphavantage.co/query";
    return UriComponentsBuilder.fromUriString(baseUrl)
        .queryParam("function", "TIME_SERIES_DAILY_ADJUSTED")
        .queryParam("symbol", symbol)
        .queryParam("apikey", apiKey)
        .queryParam("outputsize", "full")
        .build()
        .toUri();
  }
}
