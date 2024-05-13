package com.crio.warmup.stock.quotes;

import org.springframework.web.client.RestTemplate;

public enum StockQuoteServiceFactory {

  INSTANCE;

  public StockQuotesService getService(String provider, RestTemplate restTemplate) {
    if (provider.equalsIgnoreCase("tiingo")) {
      return new TiingoService("YOUR_TIINGO_API_KEY", restTemplate);
    } else {
      return new AlphavantageService("YOUR_ALPHAVANTAGE_API_KEY", restTemplate);
    }
  }
}
