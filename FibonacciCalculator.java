// Anuneet Anand
// 2018022
// CSE
// Lab - 7

import java.util.*;

class Fibonacci
{
    // Design Pattern : Flyweight
    private final Integer Value;
    private static volatile Map<Integer, Fibonacci> Record = new HashMap<Integer, Fibonacci>();
    private Fibonacci( Integer value) { this.Value = value; }

    public static Fibonacci getInstance(Integer key)
    {
        if (!Record.containsKey(key)) { Record.put(key, new Fibonacci(fib(key))); }
        return Record.get(key);
    }

    private static Integer fib(Integer n)
    {
        if (Record.containsKey(n)) { return Record.get(n).getValue();}
        else if (n < 2) { return n; }
        else
        {
            Integer a = fib(n - 1);
            Integer b = fib(n - 2);
            Record.put(n-1,new Fibonacci(a));
            Record.put(n-2,new Fibonacci(b));
            return a + b;
        }
    }
    public Integer getValue(){ return this.Value; }
}

class Worker extends Thread
{
    // Initially: Consumer
    // Finally : Producer
    // Design Pattern : Observer (Subject - Update())
    private Control C;
    private boolean Flag;
    private String Name;
    public Worker(Control c , String name) { C = c; Flag = true; Name = name;}
    @Override
    public  void run()
    {
        while (Flag)
        {
            if (!C.getInputs().isEmpty())
            {
                Integer v = -1;
                Integer x = C.getInputs().poll();
                Long StartTime = System.nanoTime();
                if (x != null) { v = Fibonacci.getInstance(x).getValue(); }
                Long EndTime = System.nanoTime();
                Long t = EndTime - StartTime;
                if ((x != null)&&(v!=-1)) { Update(x,v,t); }
            }
        }
    }
    private synchronized void Update(Integer x, Integer v, Long t)
    {
        C.getOutPuts().add("Input: " + x + " Output: " + v + " Computation Time: " + t + " " + this.Name);
    }
    public void setFlag(boolean flag) { Flag = flag; }
}

class MenuHandler
{
    // Design Pattern : Facade
    private Scanner in;
    private Control C;
    public MenuHandler(Control c) { C = c; in = new Scanner(System.in);}
    public void Handle(Integer z)
    {
        switch(z)
        {
            case 1 :
            { System.out.print("Enter Number (<45): "); Integer x = Integer.parseInt(in.next());C.getInputs().add(x); break;}
            case 2 :
            { while (!C.getOutPuts().isEmpty()){ String S = C.getOutPuts().peek(); if(S!=null) {System.out.println(S); C.getOutPuts().remove(S);}} break;}
            case 3 :
            { while (!C.getOutPuts().isEmpty()){ String S = C.getOutPuts().peek();if(S!=null) {System.out.println(S); C.getOutPuts().remove(S);}}  C.End(); break;}
            default:
            { System.out.println("Invalid Option"); break;}
        }
    }
}

class Generator implements Runnable
{
    // Initially : Producer
    // Finally : Consumer
    private Scanner in;
    private Control C;
    private MenuHandler M;
    private boolean Flag;

    public Generator(Control c)
    { this.in = new Scanner(System.in); C = c; M = new MenuHandler(C); Flag =true;}

    @Override
    public void run()
    {
        while(Flag)
        {
            System.out.println("Choose An Option");
            System.out.println("1: Enter Number");
            System.out.println("2: Print Pending Queries");
            System.out.println("3: Exit");
            Integer s = Integer.parseInt(in.next());
            M.Handle(s);
        }
    }
    public void setFlag(boolean flag) { Flag = flag; }
}

class Control
{
    // Design Pattern : Observer
    private volatile Queue <Integer> Inputs;
    private volatile Queue <String> OutPuts;
    private volatile ArrayList <Worker> Workers;
    private Generator G;
    public Control()
    {
        Inputs = new LinkedList<Integer>();
        OutPuts = new LinkedList<String>();
        Workers = new ArrayList<Worker>();
    }
    public synchronized void CreateWorkerGroup (Integer n)
    {
        for(Integer i=0;i<n;i++) { Workers.add(new Worker(this,"Thread: "+i));}
        System.out.println("Created A Worker Group With "+n+" Threads");
    }
    public synchronized void CreateGenerator ()
    {
        G = new Generator(this);
        System.out.println("Created An Input Generator On New Thread");}
    public synchronized void Process()
    {
        Thread I = new Thread(G,"Generator");
        I.start();
        for( Worker W : Workers){ W.start();}
    }
    public synchronized void Transfer()
    { try { wait(); } catch (InterruptedException e) { e.printStackTrace(); } notifyAll(); }
    public synchronized void End()
    {
        for( Worker W : Workers){ W.setFlag(false); }
        G.setFlag(false);
        System.out.println("All Threads Stopped Successfully");
        System.exit(0);
    }

    public Queue<Integer> getInputs() { return Inputs; }
    public Queue<String> getOutPuts() { return OutPuts; }
}

public class FibonacciCalculator
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        Control C = new Control();
        System.out.print("Enter Total Number Of Threads To Be Created: ");
        Integer n = Integer.parseInt(in.next());
        C.CreateWorkerGroup(n);
        C.CreateGenerator();
        C.Process();
    }
}