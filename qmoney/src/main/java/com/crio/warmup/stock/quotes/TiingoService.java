package com.crio.warmup.stock.quotes;

import com.crio.warmup.stock.dto.Candle;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

public class TiingoService implements StockQuotesService {

  private final String apiKey;
  private final RestTemplate restTemplate;

  public TiingoService(String apiKey, RestTemplate restTemplate) {
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
    String baseUrl = "https://api.tiingo.com/tiingo/daily/{symbol}/prices";
    return UriComponentsBuilder.fromUriString(baseUrl)
        .queryParam("startDate", from)
        .queryParam("endDate", to)
        .queryParam("token", apiKey)
        .buildAndExpand(symbol)
        .toUri();
  }
}
