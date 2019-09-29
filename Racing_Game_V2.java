
import java.io.*;
import java.util.*;

abstract class Tile implements Serializable
{
    protected final int Steps;
    public static final long serialVersionUID = 1L;
    public Tile(int steps) { this.Steps = steps; }
    public abstract String Make_Sound();
}

class White extends Tile
{
    public White() { super(0); }
    @Override
    public String Make_Sound()
    { return "            I am a White Tile!"; }
}
class Snake extends Tile
{
    public Snake(int steps) { super(steps); }
    @Override
    public String Make_Sound()
    { return "            Hiss...! I am a Snake , you go back " + Math.abs(this.Steps) + " Tiles!"; }
}
class Cricket extends Tile
{
    public Cricket(int steps) { super(steps); }
    @Override
    public String Make_Sound()
    { return "            Chirp...! I am a Cricket , you go back " + Math.abs(this.Steps) + " Tiles!"; }
}
class Vulture extends Tile
{
    public Vulture(int steps) { super(steps); }
    @Override
    public String Make_Sound() { return "            Yapping...! I am a Vulture , you go back " + Math.abs(this.Steps) + " Tiles!"; }
}
class Trampoline extends Tile
{
    public Trampoline(int steps) { super(steps); }
    @Override
    public String Make_Sound() { return "            Ping Pong! I am a Trampoline , you advance " + this.Steps + " Tiles!"; }
}

class SnakeBiteException extends Exception
{ public SnakeBiteException(String message) { super(message); }}
class CricketBiteException extends Exception
{ public CricketBiteException(String message) { super(message); }}
class VultureBiteException extends Exception
{ public VultureBiteException(String message) { super(message); }}
class TrampolineException extends Exception
{ public TrampolineException(String message) { super(message); }}

class Game_25_Exception extends Exception
{ public Game_25_Exception(String message) { super(message); }}
class Game_50_Exception extends Exception
{ public Game_50_Exception(String message) { super(message); }}
class Game_75_Exception extends Exception
{ public Game_75_Exception(String message) { super(message); }}
class GameWinnerException extends Exception
{ public GameWinnerException(String message) { super(message); }}
class InvalidIntegerException extends Exception
{ public InvalidIntegerException(String message) { super(message); }}

final class Dice implements Serializable
{
    public static final long serialVersionUID = 1L;
    public int Roll() { return new Random().nextInt(6) + 1; }
}

class User implements Serializable
{
    private final String Name;
    private int Rolls;
    private int Snake_Bites;
    private int Vulture_Bites;
    private int Cricket_Bites;
    private int Trampolines;
    public static final long serialVersionUID = 1L;

    public User(String name)
    {
        this.Name = name;
        this.Rolls = 0;
    }

    public String getName() { return this.Name; }
    public int getRolls() { return this.Rolls; }
    public void setRolls(int rolls) { this.Rolls = rolls; }
    public int getSnake_Bites() { return this.Snake_Bites; }
    public void setSnake_Bites(int snake_Bites) { this.Snake_Bites = snake_Bites; }
    public int getVulture_Bites() { return this.Vulture_Bites; }
    public void setVulture_Bites(int vulture_Bites) { this.Vulture_Bites = vulture_Bites; }
    public int getCricket_Bites() { return this.Cricket_Bites; }
    public void setCricket_Bites(int cricket_Bites) { this.Cricket_Bites = cricket_Bites; }
    public int getTrampolines() { return this.Trampolines; }
    public void setTrampolines(int trampolines) { this.Trampolines = trampolines; }
}

class Layout implements Serializable
{
    private transient Random R = new Random();
    private final int Length;
    private int Snake_Count;
    private final int Snake_Move;
    private int Cricket_Count;
    private final int Cricket_Move;
    private int Vulture_Count;
    private final int Vulture_Move;
    private int Trampoline_Count;
    private final int Trampoline_Move;
    private final int Limit;
    private ArrayList<Tile> Track;
    public static final long serialVersionUID = 1L;

    public Layout(int length)
    {
        this.Length = length;
        this.Limit = Math.max(this.Length / 8, 1);
        this.Snake_Count = this.R.nextInt(this.Limit) + 1;
        this.Snake_Move = 0 - (this.R.nextInt(Math.max(Math.min(this.Length / 8, 10), 1)) + 1);
        this.Cricket_Count = this.R.nextInt(this.Limit) + 1;
        this.Cricket_Move = 0 - (this.R.nextInt(Math.max(Math.min(this.Length / 8, 10), 1)) + 1);
        this.Vulture_Count = this.R.nextInt(this.Limit) + 1;
        this.Vulture_Move = 0 - (this.R.nextInt(Math.max(Math.min(this.Length / 8, 10), 1)) + 1);
        this.Trampoline_Count = this.R.nextInt(this.Limit) + 1;
        this.Trampoline_Move = this.R.nextInt(Math.max(Math.min(this.Length / 8, 10), 1)) + 1;
        this.Track = new ArrayList();
    }

    public void Set_Up (int x)
    {
        if (x == 1)
        {
            System.out.println("Setting up the Race Track...");
            System.out.println("DANGER: There are " + this.Snake_Count + "," + this.Cricket_Count + "," + this.Vulture_Count + " number of Snakes, Crickets & Vultures respectively on your Track!");
            System.out.println("DANGER: Each Snake ,Cricket & Vulture can throw you back by  " + Math.abs(this.Snake_Move) + "," + Math.abs(this.Cricket_Move) + "," + Math.abs(this.Vulture_Move) + " number of Tiles respectively.!");
            System.out.println("GOOD NEWS: There are " + this.Trampoline_Count + " Trampolines on your Track!");
            System.out.println("GOOD NEWS: Each Trampoline can help you advance by " + this.Trampoline_Move + " number of Tiles");
        }
        this.Track.add(new White());
        for (int i = 1; i < this.Length; ++i)
        { this.Track.add(this.Allocate()); }
    }

    private Tile Allocate()
    {
        Tile T;
        int x = this.R.nextInt(5) + 1;
        switch (x)
        {
            case 1:
                if (this.Snake_Count > 0)
                { this.Snake_Count--; T = new Snake(this.Snake_Move); }
                T = new White();
                break;
            case 2:
                if (this.Cricket_Count > 0)
                { this.Cricket_Count--; T = new Cricket(this.Cricket_Move); }
                T = new White();
                break;
            case 3:
                if (this.Vulture_Count > 0)
                { this.Vulture_Count--; T = new Vulture(this.Vulture_Move); }
                T = new White();
                break;
            case 4:
                if (this.Trampoline_Count > 0)
                { this.Trampoline_Count--; T = new Trampoline(this.Trampoline_Move); }
                T = new White();
                break;
            case 5:
                T = new White();
                break;
            default:
                T = null;
        }
        return T;
    }

    public int getSnake_Move() { return this.Snake_Move; }
    public int getCricket_Move() { return this.Cricket_Move; }
    public int getVulture_Move() { return this.Vulture_Move; }
    public int getTrampoline_Move() { return this.Trampoline_Move; }
    public ArrayList<Tile> getTrack() { return this.Track; }
}

class Game implements Serializable
{
    private final int Length;
    private Layout G;
    private Dice D;
    private User Y;
    private int CT;
    private int OT;
    private int S;
    public static final long serialVersionUID = 1L;

    public Game(int length, User x)
    {
        this.Length = length;
        this.G = new Layout(this.Length);
        this.D = new Dice();
        this.Y = x;
        this.CT = 1;
        this.OT = 1;
        if (x.getName().equals("|<Tester>|")) { this.G.Set_Up(0); }
        else { this.G.Set_Up(1); }
        this.S = 0;
    }

    public void Play()
    {
        System.out.println("Starting the game with " + this.Y.getName() + " at Tile-" + this.CT + ".");
        System.out.println("Control transferred to computer for rolling the dice for " + this.Y.getName() + ".");
        System.out.println("Hit enter to start the game.");
        String Start = new Scanner(System.in).nextLine();
        try { this.Move(); }
        catch (Game_25_Exception a) { System.out.println(">>> 25% Game Completed!");this.OT = this.CT;this.S = 1; }
        catch (Game_50_Exception b) { System.out.println(">>> 50% Game Completed!");this.OT = this.CT;this.S = 2; }
        catch (Game_75_Exception c) { System.out.println(">>> 75% Game Completed!");this.OT = this.CT;this.S = 3; }
        catch (GameWinnerException d)
        {
            System.out.println("            " + this.Y.getName() + " wins the race in " + this.Y.getRolls() + " rolls.");
            System.out.println("            Total Snake Bites = " + this.Y.getSnake_Bites());
            System.out.println("            Total Vulture Bites = " + this.Y.getVulture_Bites());
            System.out.println("            Total Cricket Bites = " + this.Y.getCricket_Bites());
            System.out.println("            Total Trampolines = " + this.Y.getTrampolines());
        }
    }

    private void Move() throws GameWinnerException, Game_25_Exception, Game_50_Exception, Game_75_Exception
    {
        boolean cage = false;
        int i = this.CT;
        this.CheckPoint(this.CT);
        while (i != this.Length)
        {
            int z;
            if (i == 1) { cage = true; }
            while (cage)
            {
                z = this.D.Roll();
                this.Y.setRolls(this.Y.getRolls() + 1);
                if (z == 6)
                {
                    System.out.println("[Roll:" + this.Y.getRolls() + "] " + this.Y.getName() + " rolled 6 at Tile-1. You are out of cage! You get a free roll!");
                    cage = false;
                }
                else
                {System.out.println("[Roll:" + this.Y.getRolls() + "] " + this.Y.getName() + " rolled " + z + " at Tile-1. Oops you need 6 to start.");}
            }
            z = this.D.Roll();
            this.Y.setRolls(this.Y.getRolls() + 1);
            int k = i + z;
            try { this.G.getTrack().get(k - 1); }
            catch (IndexOutOfBoundsException b) { k = i; }
            finally { System.out.println("[Roll:" + this.Y.getRolls() + "] " + this.Y.getName() + " rolled " + z + " at Tile-" + i + ". Landed on Tile-" + k + "."); }
            this.CT = k;
            this.CheckPoint(this.CT);
            int j = k;
            if (k == i) continue;
            try
            {
                System.out.println("            Trying To Shake The Tile-" + k);
                this.Shake(this.G.getTrack().get(k - 1));
                System.out.println(this.G.getTrack().get(k - 1).Make_Sound());
            }
            catch (SnakeBiteException s)
            {
                System.out.println(s.getMessage());
                j = k + this.G.getSnake_Move();
                this.Y.setSnake_Bites(this.Y.getSnake_Bites() + 1);
            }
            catch (VultureBiteException v)
            {
                System.out.println(v.getMessage());
                j = k + this.G.getVulture_Move();
                this.Y.setVulture_Bites(this.Y.getVulture_Bites() + 1);
            }
            catch (CricketBiteException c)
            {
                System.out.println(c.getMessage());
                j = k + this.G.getCricket_Move();
                this.Y.setCricket_Bites(this.Y.getCricket_Bites() + 1);
            }
            catch (TrampolineException t)
            {
                System.out.println(t.getMessage());
                j = k + this.G.getTrampoline_Move();
                this.Y.setTrampolines(this.Y.getTrampolines() + 1);
            }
            try { this.G.getTrack().get(j - 1); }
            catch (IndexOutOfBoundsException b) { j = j < 1 ? 1 : k; }
            finally { System.out.println("            " + this.Y.getName() + " Moved To Tile-" + j); }
            this.CT = i = j;
            this.CheckPoint(this.CT);
        }
    }

    public void CheckPoint(int i) throws GameWinnerException, Game_25_Exception, Game_50_Exception, Game_75_Exception
    {
        int c1 = this.Length / 4;
        int c2 = this.Length / 2;
        int c3 = 3 * this.Length / 4;
        int c4 = this.Length;
        if (i == c4) { throw new GameWinnerException(""); }
        if (i >= c3 && this.OT < c3) { throw new Game_75_Exception(""); }
        if (i >= c2 && this.OT < c2) { throw new Game_50_Exception(""); }
        if (i >= c1 && this.OT < c1) { throw new Game_25_Exception(""); }
    }

    public void Shake(Tile x) throws SnakeBiteException, VultureBiteException, CricketBiteException, TrampolineException
    {
        if (x.getClass().getName().equals("Snake")) { throw new SnakeBiteException(x.Make_Sound()); }
        if (x.getClass().getName().equals("Vulture")) { throw new VultureBiteException(x.Make_Sound()); }
        if (x.getClass().getName().equals("Cricket")) { throw new CricketBiteException(x.Make_Sound()); }
        if (x.getClass().getName().equals("Trampoline")) { throw new TrampolineException(x.Make_Sound()); }
    }

    public User getY() { return this.Y; }
    public int getCT() { return this.CT; }
    public int getS() { return this.S; }
    public int getLength() { return this.Length; }
}

class Control
{
    private Scanner in = new Scanner(System.in);
    private Game X = null;
    private boolean InputFlag = false;

    public void serialize() throws IOException
    {
        ObjectOutputStream Out =null;
        try
        {
            String File = this.X.getY().getName() + "-GameData.txt";
            Out = new ObjectOutputStream(new FileOutputStream(File));
            Out.writeObject(this.X);
        }
        finally
        { if(Out!=null) {Out.close();}}
    }

    public void deserialize() throws IOException, ClassNotFoundException
    {
        System.out.println("Enter User's Name");
        String S = this.in.next();
        ObjectInputStream In = null;
        try
        {
            String File = S + "-GameData.txt";
            this.X = null;
            In = new ObjectInputStream(new FileInputStream(File));
            this.X = (Game)In.readObject();
        }
        finally
        { if(In!=null) {In.close();}}
    }

    private int ReadMode()
    {
        this.InputFlag = false;
        int M = 0;
        while (!this.InputFlag)
        {
            try
            {
                M = Integer.parseInt(this.in.next());
                if (M == 1 || M == 2)
                { this.InputFlag = true; }
                else
                {System.out.println("Invalid Choice");}
            }
            catch (NumberFormatException m)
            { System.out.println("Invalid Choice"); }
        }
        return M;
    }

    private int ReadSize()
    {
        this.InputFlag = false;
        int N = 1;
        while (!this.InputFlag)
        {
            System.out.println("Enter total number of tiles on the race track (>=100)");
            try
            {
                N = Integer.parseInt(this.in.next());
                if (N < 100)
                { throw new InvalidIntegerException("Please enter an Integer (>=100)"); }
                this.InputFlag = true;
            }
            catch (NumberFormatException m)
            { System.out.println("Please enter an Integer (>=100)"); }
            catch (InvalidIntegerException n)
            { System.out.println(n.getMessage()); }
        }
        return N;
    }

    private void Execute(int P, int N)
    {
        while (P == 1 && this.X != null && this.X.getCT() != N)
        {
            this.X.Play();
            if (this.X.getCT() == N) break;
            System.out.println("1: Continue");
            System.out.println("2: Save & Exit");
            P = this.ReadMode();
        }
    }

    public void Process()
    {
        while (this.X == null)
        {
            System.out.println("Welcome To Racing Game!");
            System.out.println("1: New Game");
            System.out.println("2: Load Game");
            int M = this.ReadMode();
            int N = 1;
            if (M == 1)
            {
                N = this.ReadSize();
                System.out.println("Enter Name Of User");
                String S = this.in.next();
                this.X = new Game(N, new User(S));
            }
            else if (M == 2)
            {
                try { this.deserialize(); }
                catch (ClassNotFoundException c) { System.out.println("No Data Found"); }
                catch (IOException i) { System.out.println("No Existing User Data Found"); }
                if (this.X != null)
                {
                    N = this.X.getLength();
                    if (this.X.getCT() == N)
                  { System.out.println("You Already Won The Game"); }
                }
            }
            this.Execute(1, N);
            if (this.X != null)
            {
                try { this.serialize(); }
                catch (IOException i) { System.out.println("Error In File"); }
            }
        }
    }

    public Game getX() { return this.X; }
}


public class Racing_Game_V2
{
    public static void main(String[] args)
    {
        Control C = new Control();
        C.Process();
    }
}


