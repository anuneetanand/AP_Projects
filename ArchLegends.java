// Anuneet Anand
// 2018022
// CSE
// AP Lab-3

import java.util.*;

abstract class Hero
{
    // Parent Class For Different Avatars
    // Attributes Are Kept Protected For Inheritance
    protected int Level;
    protected int HP;
    protected int Max_HP;
    protected int XP;
    protected int AP;
    protected int DP;
    protected int Moves_Special;
    protected int Moves_Count;
    protected int Kills;
    protected int Location;
    //Constructor
    public Hero(int AP, int DP)
    {
        Level = 1;
        HP = 100;
        Max_HP = 100;
        XP = 0;
        this.AP = AP;
        this.DP = DP;
        Moves_Special = 0;
        Moves_Count = 0;
        Kills = 0;
        Location = 0;
    }
    // Common Attack
    public void Attack(Monster M)
    {
        System.out.println("You Chose To Attack.");
        System.out.println("Inflicted "+ this.AP + " Damage To The Monster");
        M.setHP(M.getHP()-this.AP);
        if (M.getHP()<0) {M.setHP(0);}
        System.out.println("Your HP: "+ this.HP+"/"+this.Max_HP);
        System.out.println("Monster HP: "+ M.getHP()+"/"+M.getMax_HP());
        if (this.Moves_Special==0)
            this.Moves_Count++;
    }
    // Common Defend
    public void Defend(Monster M)
    {
        System.out.println("You Chose To Defend");
        System.out.println("Monster Attack Reduced By "+ this.DP);
        System.out.println("Your HP: "+ this.HP+"/"+this.Max_HP);
        System.out.println("Monster HP: "+ M.getHP()+"/"+M.getMax_HP());
        if (this.Moves_Special==0)
            this.Moves_Count++;
    }
    // Special Attack
    protected abstract void Use_Special_Power(Monster M);
    // Info
    public void Print_Details()
    {
        System.out.println("<--- Player Info --->");
        System.out.println("Avatar: "+String.valueOf(this.getClass()).substring(6));
        System.out.println("Level: "+this.Level);
        System.out.println("HP: "+this.HP);
        System.out.println("XP: "+this.XP);
        System.out.println("Kills: "+this.Kills);
        System.out.println("Attack Power: "+this.AP+"  Defense Power: "+this.DP);
    }
    // Getters & Setters
    public int getLevel() { return Level; }
    public void setLevel(int level) { Level = level; }
    public int getHP() { return HP; }
    public void setHP(int HP) { this.HP = HP; }
    public int getMax_HP() { return Max_HP; }
    public void setMax_HP(int max_HP) { Max_HP = max_HP; }
    public int getXP() { return XP; }
    public void setXP(int XP) { this.XP = XP; }
    public int getAP() { return AP; }
    public void setAP(int AP) { this.AP = AP; }
    public int getDP() { return DP; }
    public void setDP(int DP) { this.DP = DP; }
    public int getMoves_Special() { return Moves_Special; }
    public void setMoves_Special(int moves_Special) { Moves_Special = moves_Special; }
    public int getMoves_Count() { return Moves_Count; }
    public void setMoves_Count(int moves_Count) { Moves_Count = moves_Count; }
    public int getKills() { return Kills; }
    public void setKills(int kills) { Kills = kills; }
    public int getLocation() { return Location; }
    public void setLocation(int location) { Location = location; }
}

class Warrior extends Hero
{
    public Warrior()
    { super(10,3); }
    @Override
    public void Use_Special_Power(Monster M)
    {
        if (this.Moves_Special==4)
        {
            this.AP += 5;
            this.DP += 5;
            System.out.println("Attack & Defense Power Boosted By 5" );
            System.out.println("Attack Power: "+this.AP);
            System.out.println("Defense Power: "+this.DP);
        }
        this.Moves_Special--;
        if (this.Moves_Special==0)
        {
            this.AP -=5;
            this.DP -=5;
            System.out.println("Attack & Defense Power Reset" );
        }
    }
}

class Mage extends Hero
{
    public Mage()
    { super(5,5); }
    @Override
    public void Use_Special_Power(Monster M)
    {
        this.Moves_Special--;
        M.setHP((int)(0.95*M.getHP()));
        System.out.println("Opponent's HP Reduced By 5%");
        System.out.println("Monster's New HP:"+M.getHP()+"/"+M.getMax_HP());
    }
}

class Thief extends Hero
{
    public Thief()
    { super(6,4); }
    @Override
    public void Use_Special_Power(Monster M)
    {
        this.Moves_Special=0;
        this.HP += (int)(0.3 * M.getHP());
        if (this.HP>this.Max_HP){this.HP=this.Max_HP;}
        M.setHP((int)(0.7 * M.getHP()));
        System.out.println("You Stole 30% Of Opponent's HP.");
        System.out.println("Your New HP:"+this.HP+"/"+this.Max_HP);
        System.out.println("Monster's New HP:"+M.getHP()+"/"+M.getMax_HP());
    }
}

class Healer extends Hero
{
    public Healer()
    { super(4,8); }
    @Override
    public void Use_Special_Power(Monster M)
    {
        this.Moves_Special--;
        this.HP = (int)(1.05*this.HP);
        if (this.HP>this.Max_HP){this.HP=this.Max_HP;}
        System.out.println("HP Rose By 5%");
        System.out.println("Your New HP:"+this.HP+"/"+this.Max_HP);
    }
}

abstract class Monster
{
    // Parent Class For Monsters
    // Attributes Are Kept Protected For Inheritance
    protected int HP;
    protected final int Max_HP;
    protected final int Level;
    // Constructor
    public Monster(int HP, int Level)
    {
        this.HP = HP;
        this.Max_HP = HP;
        this.Level = Level;
    }
    // Gaussian Attack Value Generator
    private int GAP ()
    {
        Random r = new Random();
        double x=-2;
        double z=HP/4;
        double M = (z+1)/2;
        double SD = Math.sqrt((z*z-1)/12);
        while ((x<-1)||(x>1))
        { x = r.nextGaussian(); }
        x *= SD;
        x += M;
        return (int)(Math.abs(x));
    }
    // Common Attack
    public void Attack(Hero H, int x)
    {
        int D=GAP();
        System.out.println("Monster Attacks!");
        if (this instanceof LionFang)
            if (((LionFang) this).Special_Attack()==1)
            {
                System.out.println("LionFang Special Attack!");
                D = H.getHP() / 2;
            }
        if (x==1) {D-=H.getDP();}
        if (D<0) {D=0;}
        H.setHP(H.getHP()-D);
        if (H.getHP()<0){H.setHP(0);}
        System.out.println("The Monster Attacked & Inflicted "+D+" Damage To You.");
        System.out.println("Your HP: "+ H.getHP()+"/"+H.getMax_HP());
        System.out.println("Monster HP: "+this.HP+"/"+this.Max_HP);
    }
    // Info
    public void Print_Details()
    {
        System.out.println("<--- Monster Info --->");
        System.out.println("Type: "+String.valueOf(this.getClass()).substring(6));
        System.out.println("Level: "+this.Level);
        System.out.println("HP: "+this.HP);
    }
    // Getters & Setters
    public int getHP() { return HP; }
    public void setHP(int HP) { this.HP = HP; }
    public int getMax_HP() { return Max_HP; }
    public int getLevel() { return Level; }
}

class Goblin extends Monster
{
    public Goblin()
    { super(100, 1); }
}

class Zombie extends Monster
{
    public Zombie()
    { super(150, 2); }
}

class Fiend extends Monster
{
    public Fiend()
    { super(200, 3); }
}

class LionFang extends Monster
{
    public LionFang()
    { super(250, 4); }

    public int Special_Attack()
    {
        Random r = new Random();
        if ((r.nextInt(10)+1)==5)
            return 1;
        else
            return 0;
    }
}

class User
{
    // For Storing User Info
    private final String Name;
    private final Hero Avatar;
    public User(String name ,Hero A) { Name = name; Avatar = A; }
    public String getName() { return Name; }
    public Hero getAvatar() { return Avatar; }
}

class Layout
{
    // Generates Game Layout
    private ArrayList <Monster> Area;
    public Layout()
    {
        Area = new ArrayList<>();
        Area.add(null);
        for(int i=1;i<40;i++)
            Area.add(Random_Monster());
        Area.add(new LionFang());
    }
    // Random Monster Allocation
    private Monster Random_Monster()
    {
        Random r = new Random();
        int x = r.nextInt(3)+1;
        switch(x)
        {
            case 1:
                return new Goblin();
            case 2:
                return new Zombie();
            case 3:
                return new Fiend();
            default:
                return null;
        }
    }
    public ArrayList<Monster> getArea() { return Area; }
}

class Game
{
    // Game Simulation
    private Scanner in = new Scanner(System.in);
    private ArrayList <User> Usernames;
    private Layout X = new Layout();

    public Game()
    {
        Usernames = new ArrayList<>();
    }
    // Playing Game
    public void Play(User U)
    {
        System.out.println("Starting Game...");
        System.out.println("Welcome "+ U.getName()+", You Are At Location: "+U.getAvatar().getLocation());
        Hero Player = U.getAvatar();
        Player.Print_Details();
        int Pos = 0;
        while(Pos>=0 && Pos<40 && Player.getHP()>0)
        {
            Pos=Move(Player,Player.getLocation());
            if (Pos!=-1)
                Player.setLocation(Pos);
            if (Pos==40)
                System.out.println("> Boss Level");
            else
                System.out.println("> You Are At Location: "+Player.getLocation());
            if (Pos>0 && Pos<=40 && X.getArea().get(Pos)!=null)
                Fight(Player,X.getArea().get(Pos),Pos);
        }
        Player.setHP(Player.getMax_HP());
        if (Pos!=-1)
        {
            Player.setLocation(0);
            Player.setHP(100);
            Player.setXP(0);
            Player.setAP(Player.getAP()-Player.getLevel()+1);
            Player.setDP(Player.getDP()-Player.getLevel()+1);
            Player.setLevel(1);
            Player.setMoves_Special(0);
            Player.setMoves_Count(0);
            Player.setMax_HP(100);
        }
    }
    // Moving Avatar
    public int Move(Hero H,int i)
    {
        if (i>12)
            return 40;
        System.out.println("Choose An Option :-");
        System.out.println("1: Go To Location: "+(3*i+1));
        System.out.println("2: Go To Location: "+(3*i+2));
        System.out.println("3: Go To Location: "+(3*i+3));
        System.out.println("4: Go Back");
        System.out.println("5: Print Stats");
        System.out.println("6: Hint");
        System.out.println("Press -1 To Exit");
        int c = in.nextInt();
        while (c==5)
        {
            H.Print_Details();
            c=in.nextInt();
        }
        while (c==6)
        {
            Hint(i);
            c=in.nextInt();
        }
        if (c==-1)
            return -1;
        else if (c==1)
            return 3*i+1;
        else if (c==2)
            return 3*i+2;
        else if(c==3)
            return 3*i+3;
        else if(c==4)
            return (i-1)/3;
        else
        {
            System.out.println("Invalid Choice, Returning To Current Location!");
            return i;
        }
    }
    // Fighting
    public void Fight(Hero Player,Monster M,int Pos)
    {
        M.Print_Details();
        System.out.println("Starting Fight!");
        while((Player.getHP()>0) && (M.getHP()>0))
        {
            int SA=0;
            System.out.println("Choose Move :-");
            System.out.println("1: Attack");
            System.out.println("2: Defend");
            if (Player.getMoves_Count()/3>0){ SA=1;}
            if (SA==1)
                System.out.println("3: Special Attack");
            int Q = in.nextInt();
            if (Q==1)
            {
                if (Player.getMoves_Special()>0)
                    Player.Use_Special_Power(M);
                Player.Attack(M);
                M.Attack(Player,0);
            }
            else if (Q==2)
            {
                if (Player.getMoves_Special()>0)
                    Player.Use_Special_Power(M);
                Player.Defend(M);
                M.Attack(Player,1);
            }
            else if ((Q==3) && (SA==1))
            {
                Player.setMoves_Count(0);
                Player.setMoves_Special(3);
                if (Player instanceof Warrior)
                    Player.setMoves_Special(4);
                System.out.println("Special Power Activated!");
            }
            else
            {
                System.out.println("Invalid Move");
            }
        }
        Update(Player,M,Pos);
    }
    // Updating Details After Fight
    private void Update (Hero Player, Monster M,int Pos)
    {
        if (M.getHP()<=0)
        {
            Player.setKills(Player.getKills()+1);
            Player.setXP(Player.getXP()+ M.getLevel() * 20);
            if (Player.getXP() >= 60 && Player.getLevel()<4)
            {
                System.out.println("Level Up To Level 4!");
                Player.setMax_HP(250);
                Player.setAP(Player.getAP()+4-Player.getLevel());
                Player.setDP(Player.getDP()+4-Player.getLevel());
                Player.setLevel(4);
            }
            else if (Player.getXP() >= 40 && Player.getLevel()<3)
            {
                System.out.println("Level Up To Level 3!");
                Player.setMax_HP(200);
                Player.setAP(Player.getAP()+3-Player.getLevel());
                Player.setDP(Player.getDP()+3-Player.getLevel());
                Player.setLevel(3);
            }
            else if (Player.getXP() >= 20 && Player.getLevel()<2)
            {
                System.out.println("Level Up To Level 2!");
                Player.setMax_HP(150);
                Player.setAP(Player.getAP()+2-Player.getLevel());
                Player.setDP(Player.getDP()+2-Player.getLevel());
                Player.setLevel(2);
            }
            System.out.println("Successfully Killed The Monster.");
            System.out.println("HP Restored.");
            Player.setHP(Player.getMax_HP());
            M.setHP(M.getMax_HP());
            if (Pos==40)
                System.out.println(">>>>>----- You Won! You Saved ThunderForge -----<<<<<,,");
        }

        if (Player.getHP()<=0)
        {
            System.out.println(">>>>>----- You Lost! Please Start Again -----<<<<<");
        }
    }
    //BONUS: Hint System
    private void Hint(int i)
    {
        System.out.println("------- Hint -------");
        int p1=0,p2=0,p3=0;
        if (i<13)
        {
            p1 = Weight(3 * i + 1) * X.getArea().get(3 * i + 1).getLevel();
            p2 = Weight(3 * i + 2) * X.getArea().get(3 * i + 2).getLevel();
            p3 = Weight(3 * i + 3) * X.getArea().get(3 * i + 3).getLevel();
        }
        if(p1<=p2 && p1<=p3)
            System.out.println("Choose First Path");
        else if(p2<=p1 && p2<=p3)
            System.out.println("Choose Second Path");
        else if(p3<=p1 && p3<=p2)
            System.out.println("Choose Third Path");
        System.out.println("--------------------");
    }
    // Finding Path Weights
    private int Weight(int i)
    {
        int sum=0;
        while(i<40)
        {
            sum += X.getArea().get(i).getLevel();
            if (3*i+3<40)
            {
                if (X.getArea().get(3*i+1).getLevel() <= X.getArea().get(3*i+2).getLevel() && X.getArea().get(3*i+1).getLevel() <= X.getArea().get(3*i+3).getLevel())
                    i = 3*i+1;
                else if (X.getArea().get(3*i+2).getLevel() <= X.getArea().get(3*i+1).getLevel() && X.getArea().get(3*i+2).getLevel() <= X.getArea().get(3*i+3).getLevel())
                    i = 3*i+2;
                else
                    i = 3*i+3;
            }
            else
                i = 40;
        }
        return sum;
    }

    public ArrayList<User> getUsernames() { return Usernames; }

}

public class ArchLegends
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        Game X = new Game();
        int Q_Main;
        do
        {
            System.out.println("--->>> Welcome To ArchLegends <<<---");
            System.out.println("1: New User");
            System.out.println("2: Existing User");
            System.out.println("3: Exit");
            Q_Main = in.nextInt();
            switch(Q_Main)
            {
                case 1:
                    System.out.println("Enter Username");
                    String S = in.next();
                    int Exists = 0;
                    for (User U:X.getUsernames())
                    {
                        if (U.getName().equals(S))
                        {
                            System.out.println("Username Already Exists! Please Login");
                            Exists = 1;
                        }
                    }
                    if (Exists == 0)
                    {
                        Hero A = null;
                        System.out.println("Choose A Hero :-");
                        System.out.println("1: Warrior ");
                        System.out.println("2: Thief ");
                        System.out.println("3: Mage ");
                        System.out.println("4: Healer ");
                        int Q_Hero = in.nextInt();
                        switch(Q_Hero)
                        {
                            case 1:
                                A = new Warrior();
                                break;
                            case 2:
                                A =new Thief();
                                break;
                            case 3:
                                A= new Mage();
                                break;
                            case 4:
                                A = new Healer();
                                break;
                            default:
                                System.out.println("Invalid Choice!");
                        }
                        if (A!=null)
                        {
                            User U = new User(S,A);
                            X.getUsernames().add(U);
                            System.out.println("User Created");
                        }
                    }
                    break;

                case 2:
                    System.out.println("Enter Username");
                    String Name = in.next();
                    Exists = 0;
                    for (User U:X.getUsernames())
                    {
                        if (U.getName().equals(Name))
                        {
                            System.out.println("User Found. Logging In...");
                            User Z = U;
                            Exists = 1;
                            X.Play(Z);
                        }
                    }
                    if (Exists ==0)
                        System.out.println("Username Not Found, Please Create An Account.");
                    break;
                case 3:
                    System.out.println("Closing Game...");
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        }while (Q_Main!=3);
    }
}

// END OF CODE
