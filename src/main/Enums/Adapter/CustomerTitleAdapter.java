package Enums.Adapter;

import Enums.Customer.CustomerTitle;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CustomerTitleAdapter extends XmlAdapter<String, CustomerTitle> {
    @Override
    public CustomerTitle unmarshal(String v) throws Exception {
        return CustomerTitle.valueOf(v);
    }

    @Override
    public String marshal(CustomerTitle v) throws Exception {
        return v.getRCUValue();
    }
}
