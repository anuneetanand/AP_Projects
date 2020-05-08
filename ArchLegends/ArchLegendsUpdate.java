// Anuneet Anand
// 2018022
// CSE
// AP Lab-4

import java.util.*;

// Hero Classes
abstract class Hero
{
    // Parent Class For Different Avatars
    // Attributes Are Kept Protected For Inheritance
    protected int Level;
    protected double HP;
    protected double Max_HP;
    protected int XP;
    protected int AP;
    protected int DP;
    protected int Moves_Special;
    protected int Moves_Count;
    protected int Kills;
    protected int Location;
    protected ArrayList<SideKick> SideKicks;
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
        SideKicks = new ArrayList<>();
    }
    // Common Attack
    public void Attack(Monster M)
    {
        System.out.println("You Chose To Attack.");
        System.out.println("You Inflicted "+ this.AP + " Damage To The Monster");
        M.setHP( Math.round(10*(M.getHP()-this.AP)) /10.0);
        if (M.getHP()<0) {M.setHP(0);}
        System.out.printf("Your HP: %.1f", this.HP);
        System.out.println("/"+this.Max_HP);
        System.out.printf("Monster HP: %.1f", M.getHP());
        System.out.println("/"+M.getMax_HP());
        if (this.Moves_Special==0)
            this.Moves_Count++;
    }
    // Common Defend
    public void Defend(Monster M)
    {
        System.out.println("You Chose To Defend");
        System.out.println("Monster Attack Reduced By "+ this.DP);
        System.out.printf("Your HP: %.1f", this.HP);
        System.out.println("/"+this.Max_HP);
        System.out.printf("Monster HP: %.1f", M.getHP());
        System.out.println("/"+M.getMax_HP());
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
        System.out.printf("HP: %.1f",this.HP);
        System.out.println();
        System.out.println("XP: "+this.XP);
        System.out.println("Kills: "+this.Kills);
        System.out.println("Attack Power: "+this.AP+"  Defense Power: "+this.DP);
        for (SideKick S : this.SideKicks) {S.Print_Details();}
    }
    // Getters & Setters
    public int getLevel() { return Level; }
    public void setLevel(int level) { Level = level; }
    public double getHP() { return HP; }
    public void setHP(double HP) { this.HP = HP; }
    public double getMax_HP() { return Max_HP; }
    public void setMax_HP(double max_HP) { Max_HP = max_HP; }
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
    public ArrayList<SideKick> getSideKicks() { return SideKicks; }
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
        M.setHP( Math.round(10*(0.95*M.getHP())) /10.0 );
        System.out.println("Opponent's HP Reduced By 5%");
        System.out.printf("Monster's New HP: %.1f", M.getHP());
        System.out.println("/"+M.getMax_HP());
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
        this.HP += Math.round(10*(0.3 * M.getHP())) /10.0;
        if (this.HP>this.Max_HP){this.HP=this.Max_HP;}
        M.setHP( Math.round(10*(0.7 * M.getHP())) /10.0);
        System.out.println("You Stole 30% Of Opponent's HP.");
        System.out.printf("Your New HP: %.1f", this.HP);
        System.out.println("/"+this.Max_HP);
        System.out.printf("Monster's New HP: %.1f", M.getHP());
        System.out.println("/"+M.getMax_HP());
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
        this.HP = Math.round(10*(1.05*this.HP)) /10.0;
        if (this.HP>this.Max_HP){this.HP=this.Max_HP;}
        System.out.println("HP Rose By 5%");
        System.out.printf("Your New HP: %.1f", this.HP);
        System.out.println("/"+this.Max_HP);
    }
}

// SideKick Classes
abstract class SideKick implements Comparable <SideKick>
{
    protected double HP;
    protected final double Max_HP;
    protected int XP;
    protected double AP;

    public SideKick(double AP)
    {
        HP=100;
        Max_HP=100;
        XP=0;
        this.AP = AP;
    }

    public void Attack(Monster M)
    {
        System.out.println("SideKick Inflicted "+ this.AP + " Damage To The Monster");
        M.setHP( Math.round(10*(M.getHP()-this.AP)) /10.0);
        if (M.getHP()<0) {M.setHP(0);}
        System.out.printf("SideKick' HP: %.1f", this.HP);
        System.out.println("/"+this.Max_HP);
        System.out.printf("Monster HP: %.1f", M.getHP());
        System.out.println("/"+M.getMax_HP());
    }

    @Override
    public boolean equals(Object O)
    {
        if(O!=null && this.getClass()==O.getClass())
        {
            SideKick other = (SideKick) O;
            boolean f=((this.HP==other.HP)&&(this.Max_HP==other.Max_HP)&&(this.AP==other.AP)&&(this.XP==other.XP));
            return f;
        }
        else
        {
            return false;
        }
    }

    @Override
    public int compareTo(SideKick other)
    {
        if (this.XP>other.XP)
            return -1;
        else if (this.XP<other.XP)
            return 1;
        else
            return 0;
    }

    public void Print_Details()
    {
        System.out.println("<--- SideKick Info --->");
        System.out.println("Type: "+String.valueOf(this.getClass()).substring(6));
        System.out.println("AP: "+this.AP);
        System.out.println("XP: "+this.XP);
    }

    public double getHP() { return HP; }
    public void setHP(double HP) { this.HP = HP; }
    public double getMax_HP() { return Max_HP; }
    public int getXP() { return XP; }
    public void setXP(int XP) { this.XP = XP; }
    public double getAP() { return AP; }
    public void setAP(double AP) { this.AP = AP; }
}

class SideKick_AP_Comparator implements Comparator <SideKick>
{
    // Comparision Based On AP
    public SideKick_AP_Comparator() { }
    @Override
    public int compare(SideKick X, SideKick Y)
    {
        if (X.AP<Y.AP)
            return 1;
        else if (X.AP>Y.AP)
            return -1;
        else
            return 0;
    }
}

class SideKick_XP_Comparator implements Comparator <SideKick>
{
    // Comparision Based On XP
    public SideKick_XP_Comparator() { }
    @Override
    public int compare(SideKick X, SideKick Y)
    {
        if (X.XP<Y.XP)
            return 1;
        else if (X.XP>Y.XP)
            return -1;
        else
            return 0;
    }
}

class Minion extends SideKick implements Cloneable
{
    private ArrayList<Minion> Clones ;
    private int Cloned;
    public Minion(double AP)
    {
        super(AP+1);
        Cloned = 0;
        Clones = new ArrayList<>();
    }

    @Override
    public Minion clone()
    {
        try
        {
            Minion X = (Minion) super.clone();
            if (this.equals(X))
                return X;
            else
                System.out.println("Cloning Problem");
            return X;
        }
        catch (CloneNotSupportedException e)
        { return null; }
    }

    public void Multiply ()
    {
        for (int i=0;i<3;i++)
            Clones.add(this.clone());
        Cloned = 1;
        System.out.println("Cloning Done");
    }

    public ArrayList<Minion> getClones() { return Clones; }
    public int getCloned() { return Cloned; }

}

class Knight extends SideKick
{
    public Knight(double AP)
    { super(AP+2); }
}

// Monster Classes
abstract class Monster
{
    // Parent Class For Monsters
    // Attributes Are Kept Protected For Inheritance
    protected double HP;
    protected final double Max_HP;
    protected final int Level;
    // Constructor
    public Monster(int HP, int Level)
    {
        this.HP = HP;
        this.Max_HP = HP;
        this.Level = Level;
    }
    // Gaussian Attack Value Generator
    private double GAP ()
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
        return x;
    }
    // Common Attack
    public void Attack(Hero H, SideKick S, int x)
    {
        Double D = GAP();
        D=(Math.round((Math.abs(D))*10))/10.0;
        System.out.println("Monster Attack");
        if (this instanceof LionFang)
            if (((LionFang) this).Special_Attack()==1)
            {
                System.out.println("LionFang Special Attack!");
                D = Math.round(10*(H.getHP() / 2))/10.0;
            }
        if (x==1) {D-=H.getDP();}
        if (D<0) {D=0.0;}
        H.setHP(H.getHP()-D);
        if (H.getHP()<0){H.setHP(0.0);}
        System.out.printf("The Monster Attacked & Inflicted %.1f",D);
        System.out.println(" Damage To You.");
        if (S!=null)
        {
            D = Math.round(10*(1.5*D))/10.0;
            System.out.printf("The Monster Attacked & Inflicted %.1f",D);
            System.out.println(" Damage To SideKick.");
            S.setHP(Math.round(10*(S.getHP()-D))/10.0);
            if (S.getHP()<0){S.setHP(0.0);}
            System.out.printf("SideKick HP: %.1f",S.getHP());
            System.out.println("/"+S.getMax_HP());
            if ((S instanceof Minion) && ((Minion) S).getCloned()==1)
            {
                for ( Minion Z : ((Minion)S).getClones())
                {
                    Z.setHP(Math.round(10*(Z.getHP()-D))/10.0);
                    if (Z.getHP()<0){Z.setHP(0.0);}
                    System.out.printf("SideKick HP: %.1f",Z.getHP());
                    System.out.println("/"+Z.getMax_HP());
                }
            }
        }
        System.out.printf("Your HP: %.1f",H.getHP());
        System.out.println("/"+H.getMax_HP());
        System.out.printf("Monster HP: %.1f",this.HP);
        System.out.println("/"+this.Max_HP);
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
    public double getHP() { return HP; }
    public void setHP(double HP) { this.HP = HP; }
    public double getMax_HP() { return Max_HP; }
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
    private Layout X;
    private SideKick_XP_Comparator XPC;
    private SideKick_AP_Comparator APC;

    public Game()
    {
        X = new Layout();
        Usernames = new ArrayList<>();
        XPC = new SideKick_XP_Comparator();
        APC = new SideKick_AP_Comparator();
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
        System.out.println("Choose An Option :-");
        System.out.println("1: Go To Location: "+(3*i+1));
        System.out.println("2: Go To Location: "+(3*i+2));
        System.out.println("3: Go To Location: "+(3*i+3));
        System.out.println("4: Go Back");
        System.out.println("5: Print Stats");
        System.out.println("6: Hint");
        System.out.println("7: Buy A SideKick / Level Upgrade");
        System.out.println("Press -1 To Exit");
        int c = in.nextInt();
        while ((c>4)&&(c<8))
        {
            if (c==5)
                H.Print_Details();
            else if (c==6)
                Hint(i);
            else if (c==7)
                Buy_SideKick(H);
            c=in.nextInt();
        }
        if (c==-1)
            return -1;
        if (i>12)
            return 40;
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
        SideKick S = null;
        int f=0;
        System.out.println("Do You Want To Use A SideKick? (yes/no)");
        String c = in.next();
        if (c.equals("yes"))
        {
            if (!Player.getSideKicks().isEmpty())
            {
                Collections.sort(Player.getSideKicks(), XPC);
                S = Player.getSideKicks().get(0);
                S.Print_Details();
                if ((S instanceof Minion) && ((Minion) S).getCloned() == 0)
                {
                    System.out.println("Do You Want To Clone? (yes/no)");
                    String a = in.next();
                    if (a.equals("yes"))
                        ((Minion) S).Multiply();
                }
                if ((S instanceof Knight) && (M instanceof Zombie))
                {
                    Player.setDP(Player.getDP()+5);
                    f=1;
                }
            }
            else
            {
                System.out.println("No SideKicks Available");
            }
        }
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
                if (M.getHP()<0) break;
                if ((S!=null)&&(S.getHP()>0))
                {
                    S.Attack(M);
                    if ((S instanceof Minion) && ((Minion) S).getCloned()==1)
                    { for (Minion Z : ((Minion) S).getClones()) Z.Attack(M); }
                }
                if (M.getHP()<0) break;
                M.Attack(Player,S,0);
            }
            else if (Q==2)
            {
                f++;
                if (Player.getMoves_Special()>0)
                    Player.Use_Special_Power(M);
                Player.Defend(M);
                if (M.getHP()<0) break;
                M.Attack(Player,S,1);
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
            if (S!=null)
                if ((f==1)&&(S.getHP()<=0))
                { Player.setDP(Player.getDP()-5); f=0;}
        }
        if (f==1) {Player.setDP(Player.getDP()-5);}
        Update(Player,S,M,Pos);
    }

    //Sets Level Of Player
    private void LevelUp(Hero Player)
    {
        if (Player.getXP() >= 60 )
        {
            System.out.println("You Reached Level 4!");
            Player.setMax_HP(250);
            Player.setHP(250);
            Player.setAP(Player.getAP()+4-Player.getLevel());
            Player.setDP(Player.getDP()+4-Player.getLevel());
            Player.setLevel(4);
        }
        else if (Player.getXP() >= 40 )
        {
            System.out.println("You Reached Level 3!");
            Player.setMax_HP(200);
            Player.setHP(200);
            Player.setAP(Player.getAP()+3-Player.getLevel());
            Player.setDP(Player.getDP()+3-Player.getLevel());
            Player.setLevel(3);
        }
        else if (Player.getXP() >= 20 )
        {
            System.out.println("You Reached Level 2!");
            Player.setMax_HP(150);
            Player.setHP(150);
            Player.setAP(Player.getAP()+2-Player.getLevel());
            Player.setDP(Player.getDP()+2-Player.getLevel());
            Player.setLevel(2);
        }
        else
        {
            System.out.println("You Reached Level 1!");
            Player.setMax_HP(100);
            Player.setHP(100);
            Player.setAP(Player.getAP()+1-Player.getLevel());
            Player.setDP(Player.getDP()+1-Player.getLevel());
            Player.setLevel(1);
        }
    }

    // Updating Details After Fight
    private void Update (Hero Player, SideKick S,Monster M,int Pos)
    {
        if (M.getHP()<=0)
        {
            if(S!=null)
            {
                if (S.getHP()<=0)
                    Player.getSideKicks().remove(0);
                else
                {
                    S.setHP(S.Max_HP);
                    S.setXP(S.getXP() + M.getLevel() * 2);
                    S.setAP(S.getAP() + (int) (S.getXP() / 5));
                    if ((S instanceof Minion) && ((Minion) S).getCloned() == 1)
                        (((Minion) S).getClones()).clear();
                }
            }
            Player.setKills(Player.getKills()+1);
            Player.setXP(Player.getXP()+ M.getLevel() * 20);
            System.out.println("Successfully Killed The Monster.");
            System.out.println("HP Restored.");
            Player.setHP(Player.getMax_HP());
            M.setHP(M.getMax_HP());
            if (Pos==40)
                System.out.println(">>>>>----- You Won! You Saved ThunderForge -----<<<<<");
        }
        else if (Player.getHP()<=0)
        {
            System.out.println(">>>>>----- You Lost! Please Start Again -----<<<<<");
        }
    }

    //To Buy SideKicks
    private void Buy_SideKick(Hero H)
    {
        System.out.println("You Have: "+H.getXP()+" XP");
        System.out.println("Choose An Option :-");
        System.out.println("1: Buy Minion (5XP)");
        System.out.println("2: Buy Knight (8XP)");
        System.out.println("3: Go Back & Use XP To Upgrade Level");
        int c = in.nextInt();
        switch(c)
        {
            case 1:
                System.out.println("XP To Spend?");
                int x = in.nextInt();
                if ((x<5)||(x>H.getXP()))
                    System.out.println("Insufficient XP");
                else
                {
                    Minion M = new Minion((x-5)/2.0);
                    H.getSideKicks().add(M);
                    M.Print_Details();
                    H.setXP(H.getXP()-x);
                    LevelUp(H);
                }
                System.out.println("Returning...");
                break;
            case 2:
                System.out.println("XP To Spend?");
                int y = in.nextInt();
                if ((y<8)||(y>H.getXP()))
                    System.out.println("Insufficient XP");
                else
                {
                    Knight K = new Knight((y-8)/2.0);
                    H.getSideKicks().add(K);
                    K.Print_Details();
                    H.setXP(H.getXP()-y);
                    LevelUp(H);
                }
                System.out.println("Returning...");
                break;
            case 3:
                LevelUp(H);
                System.out.println("Returning...");
                break;
            default:
                System.out.println("Invalid Input");
                break;
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

public class ArchLegendsUpdate
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
