// Anuneet Anand
// 2018022
// CSE
// AP Lab-3

import java.util.*;

abstract class Hero
{
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
        Location =0;
    }

    public void Attack(Monster M)
    {
        System.out.println("You Chose To Attack.");
        System.out.println("Inflicted "+ this.AP + " Damage To The Monster");
        System.out.println("Your HP: "+ this.HP+"/"+this.Max_HP);
        M.HP=M.HP-this.AP;
        if (M.HP<=0) {M.HP=0;}
        System.out.println("Monster HP: "+ M.HP+"/"+M.Max_HP);
        if (Moves_Special==0)
            Moves_Count++;
    }
    public  void Defend(Monster M)
    {
        System.out.println("You Choose To Defend");
        System.out.println("Monster Attack Reduced By "+ this.DP);
        System.out.println("Your HP: "+ this.HP+"/"+this.Max_HP);
        System.out.println("Monster HP: "+ M.HP+"/"+M.Max_HP);
        if (Moves_Special==0)
            Moves_Count++;
    }

    public abstract void Use_Special_Power(Monster M);
}

class Warrior extends Hero
{
    public Warrior()
    { super(10,3); }
    @Override
    public void Use_Special_Power(Monster M)
    {
        if (this.Moves_Special==3)
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
        M.HP = (int)(0.95*M.HP);
        System.out.println("Opponent's HP Reduced By 5%");
        System.out.println("Monster's New HP:"+M.HP+"/"+M.Max_HP);
    }
}

class Thief extends Hero
{
    public Thief()
    { super(6,4); }
    @Override
    public void Use_Special_Power(Monster M)
    {
        this.Moves_Special--;
        this.HP += (int)(0.3 * M.HP);
        M.HP=(int)(0.7 * M.HP);
        System.out.println("You Stole 30% Of Opponent's HP.");
        System.out.println("Your New HP:"+this.HP+"/"+this.Max_HP);
        System.out.println("Monster's New HP:"+M.HP+"/"+M.Max_HP);
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
        System.out.println("HP Rose By 5%");
        System.out.println("Your New HP:"+this.HP+"/"+this.Max_HP);
    }
}

abstract class Monster
{
    protected int HP;
    protected int Max_HP;
    protected final int Level;

    public Monster(int HP, int Level)
    {
        this.HP = HP;
        this.Max_HP = HP;
        this.Level = Level;
    }

    public int GAP ()
    {
        Random r = new Random();
        double x=-2;
        while ((x<-1)||(x>1))
        { x = r.nextGaussian(); }
        x+=1;
        x *=this.HP/8;
        return (int)(x);
    }

    public void Attack(Hero H, int x)
    {
        int D=GAP();
        System.out.println("Monster Attacks!");
        if (this instanceof LionFang)
            if (((LionFang) this).Special_Attack()==1)
            {
                System.out.println("LionFang Special Attack!");
                D = H.HP / 2;
            }
        if (x==1) {D-=H.DP;}
        H.HP-=D;
        if (H.HP<0){H.HP=0;}
        System.out.println("The Monster Attacked & Inflicted "+D+" Damage To You.");
        System.out.println("Your HP: "+ H.HP+"/"+H.Max_HP);
        System.out.println("Monster HP: "+HP+"/"+Max_HP);
    }
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

class user
{
    private final String Name;
    private final Hero Avatar;
    public user(String name ,Hero A) { Name = name; Avatar = A; }
    public String getName() { return Name; }
    public Hero getAvatar() { return Avatar; }
}

class Game
{
    Scanner in = new Scanner(System.in);
    private ArrayList <user> Usernames;
    private ArrayList <Monster> Area;

    public Game()
    {
        Usernames = new ArrayList<>();
        Area = new ArrayList<>();
        Area.add(null);
        for(int i=1;i<40;i++)
            Area.add(Random_Monster());
        Area.add(new LionFang());
    }

    public Monster Random_Monster()
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

    public void Play(user U)
    {
        System.out.println("Starting Game...");
        System.out.println("Welcome "+ U.getName()+", You Are At Location"+U.getAvatar().Location);
        Hero Player = U.getAvatar();
        int Pos = -1;
        while(Pos!=0 && Pos!=40)
        {
            Pos=Move(Player,Player.Location);
            Player.Location=Pos;
            if (Pos==40)
                System.out.println("Boss Level");
            else
                System.out.println("You Are At Location"+Player.Location);
            if (Pos>0 && Pos<=40)
                Fight(Player,Area.get(Pos),Pos);
        }
    }

    public int Move(Hero H,int i)
    {
        System.out.println("Choose A Path :-");
        System.out.println("1: Go To Location"+(3*i+1));
        System.out.println("2: Go To Location"+(3*i+2));
        System.out.println("3: Go To Location"+(3*i+3));
        System.out.println("4: Go Back");
        System.out.println("Press Any Other Key To Exit");
        int c = in.nextInt();
        if (i>12)
            return 40;
        switch(c)
        {
            case 1:
                return (3*i+1);
            case 2:
                return (3*i+2);
            case 3:
                return (3*i+3);
            case 4:
                return (int)((i-1)/3);
            default:
                return 0;
        }
    }

    public void Fight(Hero Player,Monster M,int Pos)
    {
        System.out.println("Starting Fight!");
        while((Player.HP>0) && (M.HP>0))
        {
            int SA=0;
            System.out.println("Choose Move :-");
            System.out.println("1: Attack");
            System.out.println("2: Defend");
            if (Player.Moves_Count/4>0){ SA=1;}
            if (SA==1)
                System.out.println("3: Special Attack");
            int Q = in.nextInt();
            if (Q==1)
            {
                if (Player.Moves_Special>0)
                    Player.Use_Special_Power(M);
                Player.Attack(M);
                M.Attack(Player,0);
            }
            else if (Q==2)
            {
                if (Player.Moves_Special>0)
                    Player.Use_Special_Power(M);
                Player.Defend(M);
                M.Attack(Player,1);
            }
            else if ((Q==3) && (SA==1))
            {
                Player.Moves_Count=0;
                Player.Moves_Special=3;
                System.out.println("Special Power Activated!");
            }
            else
            {
                System.out.println("Invalid Move");
            }
        }

        if (M.HP<=0)
        {
            Player.Kills++;
            Player.XP += M.Level * 20;
            if (Player.XP >= 60)
            {
                Player.Level = 4;
                System.out.println("Level Up To Level 4!");
                Player.Max_HP = 250;
                Player.AP++;
                Player.DP++;
            }
            else if (Player.XP >= 40)
            {
                Player.Level = 3;
                System.out.println("Level Up To Level 3!");
                Player.Max_HP = 200;
                Player.AP++;
                Player.DP++;
            }
            else if (Player.XP >= 20)
            {
                Player.Level = 2;
                System.out.println("Level Up To Level 2!");
                Player.Max_HP = 150;
                Player.AP++;
                Player.DP++;
            }
            System.out.println("Successfully Killed The Monster.");
            System.out.println("HP Restored.");
            Player.HP = Player.Max_HP;
            M.HP = M.Max_HP;
            if (Pos==40)
                System.out.println("You Won!");
        }

        if (Player.HP<=0)
            System.out.println("You Lost! Please Start Again");

    }
    public ArrayList<user> getUsernames() { return Usernames; }
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
                    for (user U:X.getUsernames())
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
                            user U = new user(S,A);
                            X.getUsernames().add(U);
                            System.out.println("User Created.");
                        }

                    }
                    break;
                case 2:
                    System.out.println("Enter Username");
                    String Name = in.next();
                    Exists = 0;
                    for (user U:X.getUsernames())
                    {
                        if (U.getName().equals(Name))
                        {
                            System.out.println("User Found. Logging In...");
                            user Z = U;
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

