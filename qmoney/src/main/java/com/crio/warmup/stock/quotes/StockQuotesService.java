package com.crio.warmup.stock.quotes;

import com.crio.warmup.stock.dto.Candle;
import java.time.LocalDate;
import java.util.List;

public interface StockQuotesService {

  List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to);
}
