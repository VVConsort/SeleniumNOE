package Enums.Adapter;

import Enums.Customer.CustomerType;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CustomerTypeAdapter extends XmlAdapter<String, CustomerType> {

    @Override
    public CustomerType unmarshal(String v) throws Exception {
        return CustomerType.valueOf(v);
    }

    @Override
    public String marshal(CustomerType v) throws Exception {
        return v.getRCUValue();
    }
}
