package Enums.Adapter;

import Enums.Customer.CustomerCountry;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CustomerCountryAdapter extends XmlAdapter<String, CustomerCountry> {
    @Override
    public CustomerCountry unmarshal(String v) throws Exception {
        return CustomerCountry.valueOf(v);
    }

    @Override
    public String marshal(CustomerCountry v) throws Exception {
        return v.getRCUValue();
    }
}
