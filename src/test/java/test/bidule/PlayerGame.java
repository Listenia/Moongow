package test.bidule;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;


@Entity("playerGame")
public class PlayerGame {

    @Id
    private ObjectId id;

    private List<String> ranks;

    private int balance;

    public PlayerGame() {
        this.ranks = new ArrayList<>();
    }




}
