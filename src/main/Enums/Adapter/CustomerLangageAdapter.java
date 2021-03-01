package Enums.Adapter;

import Enums.Customer.CustomerLangage;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CustomerLangageAdapter extends XmlAdapter<String, CustomerLangage> {

    @Override
    public String marshal(CustomerLangage v) throws Exception {
        return v.getRCUValue();
    }

    @Override
    public CustomerLangage unmarshal(String v) throws Exception {
        return CustomerLangage.valueOf(CustomerLangage.class, v);

    }
}
