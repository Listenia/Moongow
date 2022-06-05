package test.bidule;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Entity("adresses")
public class Adress {

    @Id
    private ObjectId id;

    private int number;
    private String street;
    private String city;
    private String country;

    @Reference
    private List<Resident> residents = new ArrayList<>();

    public int getNumber () {
        return number;
    }

    public void setNumber (int number) {
        this.number = number;
    }

    public String getStreet () {
        return street;
    }

    public void setStreet (String street) {
        this.street = street;
    }

    public String getCity () {
        return city;
    }

    public void setCity (String city) {
        this.city = city;
    }

    public String getCountry () {
        return country;
    }

    public void setCountry (String country) {
        this.country = country;
    }

    public List<Resident> getResidents () {
        return residents;
    }


}
