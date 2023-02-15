package com.pjh.common;

import java.io.Serializable;

/**
 * @author yueyinghaibao
 */
public class Invocation implements Serializable {

    private String interfaceName;
    private String methodName;
    private String version;
    private Class[] parametersTypes;
    private Object[] parameters;

    public Invocation(String interfaceName, String methodName, String version, Class[] parametersTypes, Object[] parameters) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.version = version;
        this.parametersTypes = parametersTypes;
        this.parameters = parameters;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Class[] getParametersTypes() {
        return parametersTypes;
    }

    public void setParametersTypes(Class[] parametersTypes) {
        this.parametersTypes = parametersTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
