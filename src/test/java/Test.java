import fun.listenia.moogow.Moongow;
import test.bidule.PlayerGame;


public class Test {

    public static void main(String[] args) {


        Moongow.start("mongodb+srv://root:CeciEstUnTest@cluster0.dg6zd.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");

        Moongow moongow = new Moongow("DB");
        moongow.mapping("test.bidule");


        moongow.finder(PlayerGame.class, (filter) -> {
            filter.eq("rank", "test");
        }).findMany();


        moongow.finder(PlayerGame.class, (filter, sort) -> {
            filter.eq("rank", "test");

            filter.lt("rank", 50);
            filter.lte("rank", 50);

            sort.asc("balance");
        }).findMany();


        moongow.updateMany(PlayerGame.class, (filter, update) -> {
            filter.eq("rank", "test");
            update.set("rank", "test2");
        });





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
