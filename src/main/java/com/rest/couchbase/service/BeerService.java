package com.rest.couchbase.service;

import com.rest.couchbase.model.Result;

public interface BeerService {

	public Result findBeersByState(String state);

}
