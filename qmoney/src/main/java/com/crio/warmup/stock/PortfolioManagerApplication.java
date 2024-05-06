
package com.crio.warmup.stock;


import com.crio.warmup.stock.dto.*;
import com.crio.warmup.stock.log.UncaughtExceptionHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.client.RestTemplate;


public class PortfolioManagerApplication {

  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Task:
  //       - Read the json file provided in the argument[0], The file is available in the classpath.
  //       - Go through all of the trades in the given file,
  //       - Prepare the list of all symbols a portfolio has.
  //       - if "trades.json" has trades like
  //         [{ "symbol": "MSFT"}, { "symbol": "AAPL"}, { "symbol": "GOOGL"}]
  //         Then you should return ["MSFT", "AAPL", "GOOGL"]
  //  Hints:
  //    1. Go through two functions provided - #resolveFileFromResources() and #getObjectMapper
  //       Check if they are of any help to you.
  //    2. Return the list of all symbols in the same order as provided in json.

  //  Note:
  //  1. There can be few unused imports, you will need to fix them to make the build pass.
  //  2. You can use "./gradlew build" to check if your code builds successfully.

  public static List<String> mainReadFile(String[] args) throws IOException, URISyntaxException {
    File file = resolveFileFromResources(args[0]);
    ObjectMapper om = getObjectMapper();
    PortfolioTrade[] trade = om.readValue(file, PortfolioTrade[].class);
    List<String> data = new ArrayList<>();
    for (PortfolioTrade alpha : trade) {
      data.add(alpha.getSymbol());
    }
    return data;

  }


  // Note:
  // 1. You may need to copy relevant code from #mainReadQuotes to parse the Json.
  // 2. Remember to get the latest quotes from Tiingo API.









  // TODO: CRIO_TASK_MODULE_REST_API
  //  Find out the closing price of each stock on the end_date and return the list
  //  of all symbols in ascending order by its close value on end date.

  // Note:
  // 1. You may have to register on Tiingo to get the api_token.
  // 2. Look at args parameter and the module instructions carefully.
  // 2. You can copy relevant code from #mainReadFile to parse the Json.
  // 3. Use RestTemplate#getForObject in order to call the API,
  //    and deserialize the results in List<Candle>



  private static void printJsonObject(Object object) throws IOException {
    Logger logger = Logger.getLogger(PortfolioManagerApplication.class.getCanonicalName());
    ObjectMapper mapper = new ObjectMapper();
    logger.info(mapper.writeValueAsString(object));
  }

  private static File resolveFileFromResources(String filename) throws URISyntaxException {
    return Paths.get(
        Thread.currentThread().getContextClassLoader().getResource(filename).toURI()).toFile();
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }


  // TODO: CRIO_TASK_MODULE_JSON_PARSING
  //  Follow the instructions provided in the task documentation and fill up the correct values for
  //  the variables provided. First value is provided for your reference.
  //  A. Put a breakpoint on the first line inside mainReadFile() which says
  //    return Collections.emptyList();
  //  B. Then Debug the test #mainReadFile provided in PortfoliomanagerApplicationTest.java
  //  following the instructions to run the test.
  //  Once you are able to run the test, perform following tasks and record the output as a
  //  String in the function below.
  //  Use this link to see how to evaluate expressions -
  //  https://code.visualstudio.com/docs/editor/debugging#_data-inspection
  //  1. evaluate the value of "args[0]" and set the value
  //     to the variable named valueOfArgument0 (This is implemented for your reference.)
  //  2. In the same window, evaluate the value of expression below and set it
  //  to resultOfResolveFilePathArgs0
  //     expression ==> resolveFileFromResources(args[0])
  //  3. In the same window, evaluate the value of expression below and set it
  //  to toStringOfObjectMapper.
  //  You might see some garbage numbers in the output. Dont worry, its expected.
  //    expression ==> getObjectMapper().toString()
  //  4. Now Go to the debug window and open stack trace. Put the name of the function you see at
  //  second place from top to variable functionNameFromTestFileInStackTrace
  //  5. In the same window, you will see the line number of the function in the stack trace window.
  //  assign the same to lineNumberFromTestFileInStackTrace
  //  Once you are done with above, just run the corresponding test and
  //  make sure its working as expected. use below command to do the same.
  //  ./gradlew test --tests PortfolioManagerApplicationTest.testDebugValues

  public static List<String> debugOutputs() {

    String valueOfArgument0 = "trades.json";
    String resultOfResolveFilePathArgs0 = "/home/crio-user/workspace/jains5833-ME_QMONEY_V2/qmoney/bin/main/trades.json";
    String toStringOfObjectMapper = "com.fasterxml.jackson.databind.ObjectMapper@67c27493";
    String functionNameFromTestFileInStackTrace = "PortfolioManagerApplicationTest.mainReadFile()";
    String lineNumberFromTestFileInStackTrace = "29:1";

    return Arrays.asList(new String[]{valueOfArgument0, resultOfResolveFilePathArgs0,
      toStringOfObjectMapper, functionNameFromTestFileInStackTrace,
      lineNumberFromTestFileInStackTrace});
  }



  // Note:
  // Remember to confirm that you are getting same results for annualized returns as in Module 3.
  public static List<String> mainReadQuotes(String[] args) throws IOException, URISyntaxException {
    List<PortfolioTrade> allTrades = readTradesFromJson(args[0]);
    LocalDate endDate = LocalDate.parse(args[1]);
    String token = getToken();
    RestTemplate restTemplate = new RestTemplate();
    List<TotalReturnsDto> priceList = new ArrayList<>();

    for (PortfolioTrade trade : allTrades) {
        String url = prepareUrl(trade, endDate, token);
        TiingoCandle[] candles = restTemplate.getForObject(url, TiingoCandle[].class);
        if (candles != null && candles.length > 0) {
            // Assuming the candles are sorted by date in ascending order
            TiingoCandle closingCandle = candles[candles.length - 1];
            TotalReturnsDto dto = new TotalReturnsDto(trade.getSymbol(), closingCandle.getClose());
            priceList.add(dto);
        }
    }

    // Sort the priceList based on closing prices
    Collections.sort(priceList, Comparator.comparing(TotalReturnsDto::getClosingPrice));

    // Extract symbols from sorted list
    List<String> result = new ArrayList<>();
    for (TotalReturnsDto dto : priceList) {
        result.add(dto.getSymbol());
    }
    return result;
}

/*
 public static List<String> mainReadQuotes(String[] args) throws IOException, URISyntaxException{
    
    List <PortfolioTrade> portfolioTrades = readTradesFromJson(args[0]);
    LocalDate endDate = LocalDate.parse(args[1]);

    String token = "289464e8faf5cf34aba42001442fb59b3c854b6c";

    RestTemplate restTemplate = new RestTemplate();

    List <TotalReturnsDto> allTrades = new ArrayList<>();

    for(PortfolioTrade trade : portfolioTrades) {
      // String url = String.format("https://api.tiingo.com/tiingo/daily/%s/prices?startDate=%s&endDate=%s&token=%s", symbol, date, date, token);
      String url = prepareUrl(trade, endDate, token);
      System.out.println(url);
      TiingoCandle[] tradeDetails = restTemplate.getForObject(url, TiingoCandle[].class);
      TotalReturnsDto finalTradeVar = new TotalReturnsDto(trade.getSymbol(), tradeDetails[tradeDetails.length - 1].getClose());
      allTrades.add(finalTradeVar);     
    }

    Collections.sort(allTrades, new SortTrades());

    List <String> sortedTrades = new ArrayList<>();

    for(TotalReturnsDto trade : allTrades) sortedTrades.add(trade.getSymbol());

    return sortedTrades;

  }
 */

  // TODO:
  //  After refactor, make sure that the tests pass by using these two commands
  //  ./gradlew test --tests PortfolioManagerApplicationTest.readTradesFromJson
  //  ./gradlew test --tests PortfolioManagerApplicationTest.mainReadFile
  public static List<PortfolioTrade> readTradesFromJson(String filename) throws IOException, URISyntaxException {
    File newFile = resolveFileFromResources(filename);
    ObjectMapper newOm = getObjectMapper();
    List<PortfolioTrade> allData = newOm.readValue(newFile, new TypeReference<List<PortfolioTrade>>() {
    });
    // System.out.println(allData);
    return allData;
  }

  private static TiingoCandle getClosingPrice(TiingoCandle[] fewDaysPrices) {
    Arrays.sort(fewDaysPrices, (x, y) -> x.getDate().compareTo(y.getDate()));
    return fewDaysPrices[fewDaysPrices.length - 1];
  }


  // TODO:
  //  Build the Url using given parameters and use this function in your code to cann the API.
  public static String prepareUrl(PortfolioTrade trade, LocalDate endDate, String token) {
    String endpoint = "https://api.tiingo.com/tiingo/daily/";
    String path = "/prices?";
    StringBuilder str = new StringBuilder(endpoint);
    str.append(trade.getSymbol());
    str.append(path);
    str.append("startDate=" + trade.getPurchaseDate().toString() + "&");
    str.append("endDate=" + endDate.toString() + "&");
    str.append("token=" + token);
    return str.toString();
  }

  /*
    public static String prepareUrl(PortfolioTrade trade, LocalDate endDate, String token) {
    // Check if end date is greater than purchase date
    if (trade.getPurchaseDate().compareTo(endDate) > 0) {
        throw new RuntimeException("End date is greater than purchased date");
    }

    // Tiingo API base URL for fetching daily stock prices
    String baseUrl = "https://api.tiingo.com/tiingo/daily/";

    // Build the complete URL with the required parameters
    String url = String.format("%s%s/prices?startDate=%s&endDate=%s&token=%s",
            baseUrl, trade.getSymbol(), trade.getPurchaseDate(), endDate, token);

    return url;
}
   */
  public static String getToken() {
    String token = "e96ab14eb9eb79153b7132439945e5a2e0b1011d";
    return token;
  }
  

  public static void main(String[] args) throws Exception {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    ThreadContext.put("runId", UUID.randomUUID().toString());

    printJsonObject(mainReadFile(args));


    printJsonObject(mainReadQuotes(args));


  }
}

