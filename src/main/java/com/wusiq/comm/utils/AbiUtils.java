package com.wusiq.comm.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.wusiq.comm.pojo.contract.MethodAbi;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.web3j.abi.datatypes.AbiTypes;
import org.web3j.abi.datatypes.Type;

import java.util.ArrayList;
import java.util.List;

public class AbiUtils {

    /**
     * 构建abi的Function入参列表
     *
     * @param methodAbi
     * @param inputParamList
     * @return
     */
    public static List<Type> buildInputParamTypeList(MethodAbi methodAbi, List<?> inputParamList) {
        List<Type> types = new ArrayList<>();

        List<MethodAbi.InputOutput> inputList = methodAbi.getInputs();
        if (CollectionUtils.isEmpty(inputList))
            return types;

        for (int i = 0; i < inputList.size(); i++)
            types.add(JsonUtils.objToJavaBean(inputParamList.get(i), AbiTypes.getType(inputList.get(i).getType())));

        return types;
    }


    /**
     * 构建abi的Function出参列表
     *
     * @param methodAbi
     * @return
     */
    public static List<org.web3j.abi.TypeReference<?>> buildOutParamTypeList(MethodAbi methodAbi) {
        List<org.web3j.abi.TypeReference<?>> types = new ArrayList<>();
        if (!CollectionUtils.isEmpty(methodAbi.getOutputs()))
            methodAbi.getOutputs().stream().forEach(o -> types.add(org.web3j.abi.TypeReference.create(AbiTypes.getType(o.getType()))));

        return types;
    }


    /**
     * 根据abiJson和函数名获取MethodAbi的对象
     *
     * @param abiJson
     * @param methodName
     * @return
     */
    public static MethodAbi getMethodAbiFromJsonByMethodName(String abiJson, String methodName) {
        if (StringUtils.isEmpty(abiJson) || StringUtils.isEmpty(methodName)) {
            throw new RuntimeException("fail exec method[getContractAbiFromJsonByMethodName], abiJson or methodName is empty");
        }

        List<MethodAbi> methodAbiList = JsonUtils.stringToObj(abiJson, new TypeReference<List<MethodAbi>>() {
        });

        MethodAbi methodAbi = methodAbiList.stream().filter(abiObj -> methodName.equalsIgnoreCase(abiObj.getName())).findFirst().orElse(null);
        return methodAbi;
    }

}
