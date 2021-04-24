package com.wusiq.comm.pojo.contract;

import lombok.Data;

import java.util.List;

@Data
public class MethodAbi {
    private Boolean constant;
    private List<InputOutput> inputs;
    private String name;
    private List<InputOutput> outputs;
    private Boolean payable;
    private String stateMutability;
    private String type;

    @Data
    public static class InputOutput {
        private String name;
        private String type;
    }
}


