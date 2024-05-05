
package com.crio.warmup.stock;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ModuleTwoTest {


  @Test
  void mainReadQuotesEdgeCase() throws Exception {
    //given
    String filename = "assessments/empty.json";
    List<String> expected = Arrays.asList(new String[]{});

    //when
    List<String> actual = PortfolioManagerApplication
        .mainReadQuotes(new String[]{filename, "2019-12-12"});

    //then
    Assertions.assertEquals(expected, actual);
  }
  @Test
  void mainReadQuotes() throws Exception {
    // Given
    String filename = "assessments/trades.json";
    List<String> expected = Arrays.asList("CTS", "CSCO", "MSFT");

    // When
    List<String> actual = PortfolioManagerApplication.mainReadQuotes(new String[]{filename, "2019-12-12"});

    // Then
    Assertions.assertNotNull(actual); // Ensure that the actual list is not null
    Assertions.assertEquals(expected.size(), actual.size()); // Ensure that the sizes of expected and actual lists are the same
    Assertions.assertTrue(actual.containsAll(expected)); // Ensure that all elements in the expected list are present in the actual list
    Assertions.assertTrue(expected.containsAll(actual)); // Ensure that all elements in the actual list are present in the expected list
  }

  @Test
void mainReadQuotesInvalidDates() throws Exception {
    //given
    String filename = "assessments/trades_invalid_dates.json";
    
    //when & then
    Assertions.assertThrows(IllegalArgumentException.class, () -> PortfolioManagerApplication
        .mainReadQuotes(new String[]{filename, "invalid-date"}));
}

@Test
void mainReadQuotesInvalidStocks() throws Exception {
    //given
    String filename = "assessments/trades_invalid_stock.json";
    
    //when & then
    Assertions.assertThrows(RuntimeException.class, () -> PortfolioManagerApplication
        .mainReadQuotes(new String[]{filename, "2017-12-12"}));
}

  @Test
  void mainReadQuotesOldTrades() throws Exception {
    // Given
    String filename = "assessments/trades_old.json";
    List<String> expected = Arrays.asList("CTS", "ABBV", "MMM");

    // When
    List<String> actual = PortfolioManagerApplication.mainReadQuotes(new String[]{filename, "2019-12-12"});

    // Then
    Assertions.assertNotNull(actual); // Ensure that the actual list is not null
    Assertions.assertEquals(expected.size(), actual.size()); // Ensure that the sizes of expected and actual lists are the same
    Assertions.assertTrue(actual.containsAll(expected)); // Ensure that all elements in the expected list are present in the actual list
    Assertions.assertTrue(expected.containsAll(actual)); // Ensure that all elements in the actual list are present in the expected list
  }
}
