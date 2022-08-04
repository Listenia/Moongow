import fun.listenia.moogow.Moongow;
import fun.listenia.moogow.Sort;
import test.bidule.Adress;


public class Test {

    public static void main(String[] args) {


        Moongow.start("mongodb+srv://root:CeciEstUnTest@cluster0.dg6zd.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");

        Moongow moongow = new Moongow("DB");
        moongow.mapping("test.bidule");


        moongow.finder(Adress.class, (filter) -> {
           filter.eq("number", 10);
        }).sort(Sort.NEAR("location", (geo) -> {
            geo.setX(10);
            geo.setY(10);
            geo.setMaxDistance(10);
        })).findOne();


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
