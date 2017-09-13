package com.orange.couchbase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.couchbase.client.java.query.Statement;
import com.orange.couchbase.model.Result;

import static com.couchbase.client.java.query.Select.select;
import static com.couchbase.client.java.query.dsl.Expression.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BeerServiceImpl implements BeerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeerServiceImpl.class);

    private Bucket bucket;

    @Autowired
    public BeerServiceImpl(Bucket bucket) {
        this.bucket = bucket;
    }

	
	@Override
	public Result findBeersByState(final String state) {
		
		String stateN1QLValue = "'" + state + "'";
		String bucketN1QLValue = "`" + this.bucket.name() + "`";
		
		Statement selectBeerFromTexas = select("*").from(bucketN1QLValue)
				.where(x("state").eq(stateN1QLValue));
		
		LOGGER.info("Executing Query: {}", selectBeerFromTexas.toString());
		
        N1qlQueryResult result = bucket.query(N1qlQuery.simple(selectBeerFromTexas));
        
		// TODO Auto-generated method stub
        return Result.of(extractResultOrThrow(result), selectBeerFromTexas.toString());
	}
	
    private static List<Map<String, Object>> extractResultOrThrow(N1qlQueryResult result) {
        if (!result.finalSuccess()) {
            LOGGER.warn("Query returned with errors: " + result.errors());
            //throw new DataRetrievalFailureException("Query error: " + result.errors());
        }

        List<Map<String, Object>> content = new ArrayList<Map<String, Object>>();
        for (N1qlQueryRow row : result) {
            content.add(row.value().toMap());
        }
        return content;
    }	

}
