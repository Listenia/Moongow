package test.bidule;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import org.bson.types.ObjectId;

@Entity("residents")
public class Resident {

    @Id
    private ObjectId id;

    private String name;
    private int age;

    @Reference
    private Adress adress;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getAge () {
        return age;
    }

    public void setAge (int age) {
        this.age = age;
    }

    public Adress getAdress () {
        return adress;
    }

    public void setAdress (Adress adress) {
        this.adress = adress;
    }


}
