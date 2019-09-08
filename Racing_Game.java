// Anuneet Anand
// 2018022
// CSE
// Lab-5

import java.util.*;

// Tile Classes

abstract class Tile
{
    // Parent Class For Different Types Of Tiles
    protected final int Steps;
    public Tile(int steps) { Steps = steps; }
    public abstract String  Make_Sound();
}

class White extends Tile
{
    public White() { super(0); }
    @Override
    public String Make_Sound()
    { return  "            I am a White Tile!"; }
}

class Trampoline extends Tile
{
    public Trampoline(int steps) { super(steps); }
    @Override
    public String Make_Sound()
    { return  "            Ping Pong! I am a Trampoline , you advance " + this.Steps + " Tiles!"; }
}

class Snake extends Tile
{
    public Snake(int steps) { super(steps); }
    @Override
    public String Make_Sound()
    { return  "            Hiss...! I am a Snake , you go back " + Math.abs(this.Steps) + " Tiles!"; }
}

class Vulture extends Tile
{
    public Vulture(int steps) { super(steps); }
    @Override
    public String Make_Sound()
    { return "            Yapping...! I am a Vulture , you go back " + Math.abs(this.Steps) + " Tiles!"; }
}

class Cricket extends Tile
{
    public Cricket(int steps) { super(steps); }
    @Override
    public String Make_Sound()
    { return "            Chirp...! I am a Cricket , you go back " + Math.abs(this.Steps) + " Tiles!"; }
}

// Exceptions
class SnakeBiteException extends Exception
{ public SnakeBiteException(String message) { super(message); }}

class VultureBiteException extends Exception
{ public VultureBiteException(String message) { super(message); }}

class CricketBiteException extends Exception
{ public CricketBiteException(String message) { super(message); }}

class TrampolineException extends Exception
{ public TrampolineException(String message) { super(message); }}

class GameWinnerException extends Exception
{ public GameWinnerException(String message) { super(message); }}

class NoNameException extends Exception
{ public NoNameException(String message) { super(message); }}

class NegativeIntegerException extends Exception
{ public NegativeIntegerException(String message) { super(message); }}

class GenericList <T>
{
    private ArrayList <T> A;
    public GenericList() { A = new ArrayList<T>(); }
    public void add(T o) {A.add(o);}
    public T get(int i) {return A.get(i);}
}

// Game Classes
class User
{
    // Storing User Data
    private final String Name;
    private int  Rolls;
    private int Snake_Bites;
    private int Vulture_Bites;
    private int Cricket_Bites;
    private int Trampolines;
    public User(String name)
    {
        Name = name;
        Rolls = 0;
    }
    public String getName() { return Name; }
    public int getRolls() { return Rolls; }
    public void setRolls(int rolls) { Rolls = rolls; }
    public int getSnake_Bites() { return Snake_Bites; }
    public void setSnake_Bites(int snake_Bites) { Snake_Bites = snake_Bites; }
    public int getVulture_Bites() { return Vulture_Bites; }
    public void setVulture_Bites(int vulture_Bites) { Vulture_Bites = vulture_Bites; }
    public int getCricket_Bites() { return Cricket_Bites; }
    public void setCricket_Bites(int cricket_Bites) { Cricket_Bites = cricket_Bites; }
    public int getTrampolines() { return Trampolines; }
    public void setTrampolines(int trampolines) { Trampolines = trampolines; }
}

final class Dice
{
    private final Random R;
    public Dice() { R = new Random(); }
    public int Roll() { return R.nextInt(6)+1;}
}

class Layout
{
    // Setting Up Game Layout
    private Random R;
    private final int Length;
    private  int Snake_Count;
    private final int Snake_Move;
    private  int Cricket_Count;
    private final int Cricket_Move;
    private  int Vulture_Count;
    private final int Vulture_Move;
    private  int Trampoline_Count;
    private final int Trampoline_Move;
    private final int Limit;
    private GenericList <Tile> Track;

    public Layout(int length)
    {
        R = new Random();
        Length = length;
        Limit = Math.max(Length/8,1);
        Snake_Count = R.nextInt(Limit)+1;
        Snake_Move = 0 - (R.nextInt(Math.max(Math.min(Length/8,10),1))+1);
        Cricket_Count = R.nextInt(Limit)+1;
        Cricket_Move = 0 - (R.nextInt(Math.max(Math.min(Length/8,10),1))+1);
        Vulture_Count = R.nextInt(Limit)+1;
        Vulture_Move = 0 - (R.nextInt(Math.max(Math.min(Length/8,10),1))+1);
        Trampoline_Count = R.nextInt(Limit)+1;
        Trampoline_Move = (R.nextInt(Math.max(Math.min(Length/8,10),1))+1);
        Track = new GenericList<Tile>();
        Set_Up();
    }

    private void Set_Up()
    {
        System.out.println("Setting up the Race Track...");
        Track.add(new White());
        System.out.println("DANGER: There are "+Snake_Count+","+Cricket_Count+","+Vulture_Count+" number of Snakes, Crickets & Vultures respectively on your Track!" );
        System.out.println("DANGER: Each Snake ,Cricket & Vulture can throw you back by  "+Math.abs(Snake_Move)+","+Math.abs(Cricket_Move)+","+Math.abs(Vulture_Move)+" number of Tiles respectively.!" );
        System.out.println("GOOD NEWS: There are "+Trampoline_Count+" Trampolines on your Track!");
        System.out.println("GOOD NEWS: Each Trampoline can help you advance by " + Trampoline_Move + " number of Tiles");
        for (int i=1;i<Length;i++)
            Track.add(Allocate());
    }

    private Tile Allocate()
    {
        int x = R.nextInt(5)+1;
        Tile T;
        switch(x)
        {
            case 1:
                if (Snake_Count>0)
                {
                    Snake_Count--;
                    T = new Snake(Snake_Move);
                }
                else
                    T = new White();
                break;
            case 2:
                if (Cricket_Count>0)
                {
                    Cricket_Count--;
                   T = new Cricket(Cricket_Move);
                }
                else
                    T = new White();
                break;
            case 3:
                if (Vulture_Count>0)
                {
                    Vulture_Count--;
                    T = new Vulture(Vulture_Move);
                }
                else
                    T = new White();
                break;
            case 4:
                if (Trampoline_Count>0)
                {
                    Trampoline_Count--;
                    T = new Trampoline(Trampoline_Move);
                }
                else
                    T = new White();
                break;
            case 5:
                T = new White();
                break;
            default:
                T = null;
                break;
        }
        return T;
    }
    public int getSnake_Move() { return Snake_Move; }
    public int getCricket_Move() { return Cricket_Move; }
    public int getVulture_Move() { return Vulture_Move; }
    public int getTrampoline_Move() { return Trampoline_Move; }
    public GenericList<Tile> getTrack() { return Track; }
}

class Game
{
    private  Scanner in;
    private final int Length;
    private Layout G;
    private Dice D;

    public Game(int length)
    {
        in = new Scanner(System.in);
        Length = length;
        G = new Layout(Length);
        D = new Dice();
    }

    public void Play()
    {
        boolean InputFlag = false;
        String S="";
        while(!InputFlag)
        {
            System.out.println("Enter the Player's Name:- ");
            try
            {
                S = in.nextLine();
                if (S.equals(""))
                    throw new NoNameException("Invalid Name!");
                else
                    InputFlag = true;
            }
            catch (NoNameException n)
            { System.out.println(n.getMessage()); }
        }
        User X = new User(S);
        System.out.println("Starting the game with "+ X.getName()+" at Tile-1.");
        System.out.println("Control transferred to computer for rolling the dice for "+ X.getName()+".");
        System.out.println("Hit enter to start the game.");
        String Start = in.nextLine();
        try
        {
            Move(X);
        }
        catch (GameWinnerException g)
        {
            System.out.println("            "+X.getName()+" wins the race in "+X.getRolls()+ " rolls.");
            System.out.println("            Total Snake Bites = "+X.getSnake_Bites());
            System.out.println("            Total Vulture Bites = "+X.getVulture_Bites());
            System.out.println("            Total Cricket Bites = "+X.getCricket_Bites());
            System.out.println("            Total Trampolines = "+X.getTrampolines());
        }
    }

    private void Move(User X) throws GameWinnerException
    {
        int i=1,j,k,z;
        int cage = 0;
        if (i == Length)
            throw new GameWinnerException("");
        while (i!=Length)
        {
            k = i;
            if (i == 1)
                cage = 1;
            while (cage == 1)
            {
                z = D.Roll();
                X.setRolls(X.getRolls()+1);
                if (z == 6)
                {
                    System.out.println("[Roll:"+X.getRolls()+"] "+X.getName()+" rolled 6 at Tile-1. You are out of cage! You get a free roll!");
                    cage = 0;
                }
                else
                    System.out.println("[Roll:"+X.getRolls()+"] "+X.getName()+" rolled "+z+" at Tile-1. Oops you need 6 to start.");
            }
            z = D.Roll();
            X.setRolls(X.getRolls()+1);
            k = i + z;
            try
            { G.getTrack().get(k-1); }
            catch (IndexOutOfBoundsException b)
            { k = i; }
            finally
            { System.out.println("[Roll:"+X.getRolls()+"] "+X.getName()+" rolled "+z+" at Tile-"+i+". Landed on Tile-"+k+".");}

            j = k;
            if (k == Length)
                throw new GameWinnerException("");
            else if (k!=i)
            {
                try
                {
                    System.out.println("            Trying To Shake The Tile-" + k);
                    Shake(G.getTrack().get(k - 1));
                    System.out.println(G.getTrack().get(k - 1).Make_Sound());
                }
                catch (SnakeBiteException s)
                {
                    System.out.println(s.getMessage());
                    j = k + G.getSnake_Move();
                    X.setSnake_Bites(X.getSnake_Bites() + 1);
                }
                catch (VultureBiteException v)
                {
                    System.out.println(v.getMessage());
                    j = k + G.getVulture_Move();
                    X.setVulture_Bites(X.getVulture_Bites() + 1);
                }
                catch (CricketBiteException c)
                {
                    System.out.println(c.getMessage());
                    j = k + G.getCricket_Move();
                    X.setCricket_Bites(X.getCricket_Bites() + 1);
                }
                catch (TrampolineException t)
                {
                    System.out.println(t.getMessage());
                    j = k + G.getTrampoline_Move();
                    X.setTrampolines(X.getTrampolines() + 1);
                }

                try
                { G.getTrack().get(j - 1); }
                catch (IndexOutOfBoundsException b) { j = k; }
                finally
                { System.out.println("            " + X.getName() + " Moved To Tile-" + j); }
                i = j;
            }
        }
    }

    private void Shake(Tile x) throws SnakeBiteException,VultureBiteException,CricketBiteException,TrampolineException
    {
        if (x.getClass().getName().equals("Snake"))
            throw new SnakeBiteException(x.Make_Sound());
        else if (x.getClass().getName().equals("Vulture"))
            throw new VultureBiteException(x.Make_Sound());
        else if (x.getClass().getName().equals("Cricket"))
            throw new CricketBiteException(x.Make_Sound());
        else if (x.getClass().getName().equals("Trampoline"))
            throw new TrampolineException(x.Make_Sound());
    }
}

public class Racing_Game
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome To Racing Game!");
        int N=1;
        boolean InputFlag = false;
        while(!InputFlag)
        {
            System.out.println("Enter total number of tiles on the race track (Length)");
            try
            {
                N = Integer.parseInt((in.next()));
                if (N<=0)
                    throw new NegativeIntegerException("Please enter a Positive Integer.");
                else
                    InputFlag = true;
            }
            catch (NumberFormatException m)
            { System.out.println("Please enter a Positive Integer."); }
            catch (NegativeIntegerException n)
            { System.out.println(n.getMessage());}
        }

        Game X = new Game(N);
        X.Play();
    }
}

//END OF CODE