package jindtang.werewolf;

/**
 * Created by Lin on 3/30/2015.
 */
public class Player {
    private String name;
    private roles role;
    private boolean dead = false;
    private String id;

    public Player(){
    }

    public enum roles {Villager, Werewolf, Seer};

    public void setRole(roles r){
        role = r;
    }

    public roles getRole(){
        return role;
    }

    public String getName(){
        return name;
    }

    public void setDead(Boolean b){
        dead = b;
    }

    public String getid() {return id;}

    public void setid(String s) {id = s;}

    public void setName(String s ){name = s;}

    public boolean getDead(){return dead;}
}
