package Serializable.Customer;

import Enums.Adapter.CustomerCountryAdapter;
import Enums.Adapter.CustomerLangageAdapter;
import Enums.Adapter.CustomerTypeAdapter;
import Enums.Customer.CustomerCountry;
import Enums.Customer.CustomerLangage;
import Enums.Customer.CustomerType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    // Catégorie
    @XmlJavaTypeAdapter(CustomerTypeAdapter.class)
    public CustomerType type;
    /* PERSONAL INFORMATION */
    // Civilité
    public String title;
    // Prénom
    public String firstName;
    // Nom
    public String lastName;
    // Date de naissance, format : AAAA-MM-DD
    public String birthdate;
    // Langue
    @XmlJavaTypeAdapter(CustomerLangageAdapter.class)
    public CustomerLangage language;
    /* INFORMATION DE CONTACT */
    // Num mobile
    public String mobilePhone;
    // Num fixe
    public String phone;
    // Email
    public String email;
    /* AUTRES */
    // Programme de fid ?
    // TODO
    /* PREFERENCE DE CONTACT */
    // Documents contractuels
    public boolean invoiceViaEmail;
    // Consentement client
    public boolean consent;
    // SMS
    public boolean viaSms;
    // Email
    public boolean viaEmail;
    // Même adresse facturation/livraison
    public boolean sameAddress;
    /* Adresse facturation OU même adresse livraison/facturation */
    // Pays
    @XmlJavaTypeAdapter(CustomerCountryAdapter.class)
    public CustomerCountry country;
    // N° et nom de la voie
    public String locationName;
    // Appartement,étage,porte,couloir
    public String line2;
    // Batiment, résident, entrée
    public String line3;
    // Lieu dit
    public String line4;
    // Code postal
    public String postalCode;
    // Ville
    public String city;
    /* Adresse livraison si facturation et livraison diff */
    // Pays
    @XmlJavaTypeAdapter(CustomerCountryAdapter.class)
    public CustomerCountry shipCountry;
    // N° et nom de la voie
    public String shipLocationName;
    // Appartement,étage,porte,couloir
    public String shipLine2;
    // Batiment, résident, entrée
    public String shipLine3;
    // Lieu dit
    public String shipLine4;
    // Code postal
    public String shipPostalCode;
    // Ville
    public String shipCity;
    // identifiant client
    public String customerId;
    // Indique si la création de ce client doit lancer une erreur ou non
    public boolean noErrorOnCreate;

    public Customer() {

    }

    public Customer(CustomerType type, String title, String firstName, String lastName, String birthdate, CustomerLangage language, String mobilePhone, String phone, String email, boolean invoiceViaEmail, boolean consent, boolean viaSms, boolean viaEmail, boolean sameAddress, CustomerCountry country, String locationName, String line2, String line3, String line4, String postalCode, String city, CustomerCountry shipCountry, String shipLocationName, String shipLine2, String shipLine3, String shipLine4, String shipPostalCode, String shipCity, boolean throwError) {
        this.type = type;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.language = language;
        this.mobilePhone = mobilePhone;
        this.phone = phone;
        this.email = email;
        this.invoiceViaEmail = invoiceViaEmail;
        this.consent = consent;
        this.viaSms = viaSms;
        this.viaEmail = viaEmail;
        this.sameAddress = sameAddress;
        this.country = country;
        this.locationName = locationName;
        this.line2 = line2;
        this.line3 = line3;
        this.line4 = line4;
        this.postalCode = postalCode;
        this.city = city;
        this.shipCountry = shipCountry;
        this.shipLocationName = shipLocationName;
        this.shipLine2 = shipLine2;
        this.shipLine3 = shipLine3;
        this.shipLine4 = shipLine4;
        this.shipPostalCode = shipPostalCode;
        this.shipCity = shipCity;
        this.noErrorOnCreate = throwError;
    }
}