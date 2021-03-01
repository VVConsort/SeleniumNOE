package Serializable.Customer;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement(name = "customerList")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerList implements Serializable {
    private static final long serialVersionUID = 1L;
    @XmlElement(name ="customer")
    public ArrayList<Customer> customers;
}


