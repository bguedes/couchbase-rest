package com.rest.couchbase.service;

import static com.couchbase.client.java.query.Select.select;
import static com.couchbase.client.java.query.dsl.Expression.x;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.couchbase.client.core.time.Delay;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.query.AsyncN1qlQueryResult;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.Statement;
import com.couchbase.client.java.util.retry.RetryBuilder;
import com.rest.couchbase.model.Result;

@Service
public class BeerServiceImpl implements BeerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeerServiceImpl.class);

	private Bucket bucket;

	@Autowired
	public BeerServiceImpl(Bucket bucket) {
		this.bucket = bucket;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result findBeersByState(final String state) {

		String stateN1QLValue = "'" + state + "'";
		String bucketN1QLValue = "`" + this.bucket.name() + "`";

		Statement selectBeerFromState = select("*").from(bucketN1QLValue).where(x("state").eq(stateN1QLValue));

		LOGGER.info("Executing Query: {}", selectBeerFromState.toString());

		return Result.of(executeQuery(selectBeerFromState), selectBeerFromState.toString());
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> executeQuery(Statement statementQuery) {
		return bucket.async().query(N1qlQuery.simple(statementQuery))
				.flatMap(AsyncN1qlQueryResult::rows)
				.map(result -> result.value().toMap())
				.timeout(1, TimeUnit.SECONDS)
				.retryWhen(RetryBuilder.anyOf(java.net.ConnectException.class)
						.delay(Delay.exponential(TimeUnit.MILLISECONDS, 100))
						.max(5)
						.build())
				.toList()
				.toBlocking()
				.single();
	}
}
