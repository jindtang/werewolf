package jindtang.werewolf;

import java.util.Random;

/**
 * Created by Lin on 3/30/2015.
 */
public class WerewolfGame {

    private static Player[] Players;
    private int num;
    private int num_v;
    private int num_w;
    private int num_s;
    private int a;
    private int b;
    private int c = 1;

    public WerewolfGame (int n, int v, int w,  int s){
        num = n;
        Players = new Player[num];
        num_v = v;
        num_w = w;
        num_s = s;
        a = v;
        b = w;
    }

    public void setRole(Player p){
        int i = a + b +c;
        Random r = new Random();
        int x = r.nextInt(i)+1;

        if(x <= a) {
            p.setRole(Player.roles.Villager);
            p.setid("Good");
            a--;
        }
        if(x < a && x <= a+b) {
            p.setRole(Player.roles.Werewolf);
            p.setid("Bad");
            b--;
        }
        else {
            p.setRole(Player.roles.Seer);
            p.setid("Good");
            c--;
        }
    }

    public void night(){
//        if(p.getRole() == Player.roles.Villager)
//            System.out.println("next");
//        if(p.getRole() == Player.roles.Seer) {
//            System.out.println("select the Player u want to see");
//        }
//        if(p.getRole() == Player.roles.Werewolf){
//            System.out.println("select the Player u want to kill");
//        }
        System.out.println("werewolves choose people to kill");
        //jump to all Players activity

        System.out.println("seer select the Player want to see");
        //jump to all Players activity

    }

    public void daytime(){
        //everyone vote for one death
    }

    public int checkWinner(){
        if(num_w == 0)
            return 1;
        if(num_w >= num_s+num_v)
            return 2;
        return 0;
    }


}
