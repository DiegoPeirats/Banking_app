package com.diego_peirats.application.response;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CurrenciesRates {
	
	private Boolean success;
	private Long timestamp;
	private String base;
	private String date;
    private Map<String, Double> rates;
    
    public Double getSpecificRate(String coin){
    	return this.rates.get(coin);
    }

}
