
package com.crio.warmup.stock.portfolio;

import org.springframework.web.client.RestTemplate;

public class PortfolioManagerFactory {

  // TODO: CRIO_TASK_MODULE_REFACTOR
  // Implement the method to return new instance of PortfolioManager.
  // Remember, pass along the RestTemplate argument that is provided to the new
  // instance.

  @Deprecated
  public static PortfolioManager getPortfolioManager(String string, RestTemplate restTemplate) {
    return new PortfolioManagerImpl(restTemplate);
    
  }

}
