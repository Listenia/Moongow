import fun.listenia.moogow.Moongow;
import test.bidule.Adress;
import test.bidule.Resident;

import static dev.morphia.query.experimental.filters.Filters.eq;

public class Test {

    public static void main(String[] args) {


        Moongow.init("mongodb+srv://root:CeciEstUnTest@cluster0.dg6zd.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");

        Moongow moongow = new Moongow("DB");
        moongow.mapping("test.bidule");

        /*
        moongow.finder(Adress.class, (filter) -> {
           filter.eq("number", 10);
        }).findOne();
         */

        /*
        Adress adress = new Adress();
        adress.setNumber(10);
        adress.setStreet("rue de la paix");
        adress.setCity("paris");
        adress.setCountry("france");

        Resident resident = new Resident();
        resident.setName("toto");
        resident.setAge(20);
        resident.setAdress(adress);

        moongow.save(adress);
        moongow.save(resident);

         */





    }

}
