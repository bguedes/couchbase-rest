package com.rest.couchbase.model;

public class Error implements IValue {

    private final String failure;

    public Error(String failure) {
        this.failure = failure;
    }

    public String getFailure() {
        return failure;
    }
}
