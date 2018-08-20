package com.snhu.app.rest;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.snhu.app.adapter.InspectionAdapter;
import com.snhu.app.model.Inspection;
import com.snhu.app.service.InspectionsDAO;
import com.snhu.app.service.StocksDAO;
import com.snhu.app.util.UriUtil;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * InpsectionsResource
 */
@RestController
@RequestMapping("/stocks")
public class StocksResource {

	@Autowired
	StocksDAO stocksDAO;

	@Autowired
	UriUtil uriUtil;

	@Autowired
	Logger log;

	/**
	 * creates a stock based on the passed in item
	 * Ensures that the stock at least contains a ticker key
	 */
	@PostMapping(path = "/createStock", consumes = "application/json", produces = "text/plain")
	public ResponseEntity<String> create(@RequestBody Map<String, ?> item) {
		try {
			log.debug("Recieved: {}", item);
			if (!item.containsKey("Ticker")) {
				return ResponseEntity.unprocessableEntity().build();
			}
			String ticker = Objects.toString(item.get("Ticker"), "");
			if (stocksDAO.create(stocksDAO.object(item))) {
				return ResponseEntity.created(new URI("/getStock/" + ticker)).build();
			} else {
				return ResponseEntity.ok().build();
			}
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	/**
	 * retrieves a stock by ticker string
	 */
	@GetMapping("/getStock/{ticker}")
	public ResponseEntity getStock(@PathVariable("ticker") String ticker) throws TickerNotFoundException {
		try {
			log.debug("Looking Up: {}", ticker);
			Map stock = stocksDAO.readTicker(ticker).map(DBObject::toMap).findFirst()
					.orElseThrow(NotFoundException::new);
			return ResponseEntity.ok().body(stock);
		} catch (NotFoundException e) {
			throw new TickerNotFoundException(ticker);
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * updates a stock by the ticker value and applies the changes mapping to the ticker
	 */
	@PutMapping("/updateStock/{ticker}")
	public ResponseEntity<Void> update(@PathVariable("ticker") String ticker, @RequestBody Map<String, ?> changes) {
		try {
			return writeResponse(stocksDAO.updateTicker(ticker, changes), ResponseEntity.accepted(),
					TickerNotFoundException.supply(ticker));
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * deletes a stock for a provided ticker
	 */
	@DeleteMapping("/deleteStock/{ticker}")
	public ResponseEntity<Void> deleteStock(@PathVariable("ticker") String ticker) {
		try {
			return writeResponse(stocksDAO.deleteTicker(ticker), ResponseEntity.ok(),
					TickerNotFoundException.supply(ticker));
		} catch (Exception e) {
			log.error("", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	/**
	 * gets a stock report for a list of tickers
	 */
	@PostMapping("/stockReport")
	public ResponseEntity<List<Map>> stockReport(@RequestBody Map tickers) {
		if (tickers.containsKey("list")) {
			List<String> strings = (List<String>) tickers.get("list");
			List<Map> stocks = stocksDAO.readTickers(strings.toArray(new String[0])).map(DBObject::toMap)
					.collect(Collectors.toList());
			if (stocks.isEmpty()) {
				throw new TickerNotFoundException(strings.toString());
			}
			return ResponseEntity.ok(stocks);
		} else {
			return ResponseEntity.unprocessableEntity().build();
		}
	}

	/**
	 * gets the top 5 stocks portfolio for an Industry
	 */
	@PostMapping("/industryReport/{industry}")
	public ResponseEntity<List<Map>> industryReport(@PathVariable("industry") String industry) {
		List<Map> stocks = stocksDAO.readTopFiveByIndustry(industry).map(DBObject::toMap).collect(Collectors.toList());
		if (stocks.isEmpty()) {
			throw new IndustryNotFoundException(industry);
		}
		return ResponseEntity.ok(stocks);
	}

	/**
	 * gets the top 5 stocks portfolio for a company
	 */
	@PostMapping("/portfolio/{company}")
	public ResponseEntity<List<Map>> portfolio(@PathVariable("company") String company) {
		List<Map> stocks = stocksDAO.readTopFiveByCompany(company).map(DBObject::toMap).collect(Collectors.toList());
		if (stocks.isEmpty()) {
			throw new CompanyNotFoundException(company);
		}
		return ResponseEntity.ok(stocks);
	}

	/**
	 * chooses between a response or throwing an exception bsed on if there are changes
	 */
	public <T extends Exception> ResponseEntity<Void> writeResponse(DBObject result, BodyBuilder changedResponse,
			Supplier<T> exceptionSupplier) throws T {
		int changed = ((int) result.get("changed"));
		if (changed > 0) {
			return changedResponse.build();
		} else {
			throw exceptionSupplier.get();
		}
	}
}