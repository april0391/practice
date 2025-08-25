package edu.springbasic.basic;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientBean {

    @Autowired
    private ObjectProvider<PrototypeBean> provider;

    public PrototypeBean logic() {
        PrototypeBean object = provider.getObject();
        return object;
    }
}
