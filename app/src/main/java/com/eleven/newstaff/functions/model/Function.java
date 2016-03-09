package com.eleven.newstaff.functions.model;

/**
 * Created by eleven on 16/2/22.
 */
public class Function<T> {
    public String functionName;
    public Class<T> className;

    public Function(String functionName, Class<T> className) {
        this.functionName = functionName;
        this.className = className;
    }
}
