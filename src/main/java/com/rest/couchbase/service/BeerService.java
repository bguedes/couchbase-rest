package com.orange.couchbase.service;

import com.orange.couchbase.model.Result;

public interface BeerService {

	public Result findBeersByState(String state);

}
