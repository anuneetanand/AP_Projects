// Anuneet Anand
// 2018022
// CSE
// AP Lab 2 - Mercury

import java.util.*;

interface user
{
    void print_reward();
    void print_details();
}

class Customer implements user
{
    private static  int count = 1;
    private int ID;
    private String Name;
    private String Address;
    private int Number_Of_Orders;
    private ArrayList <Item> Bought_Items;
    private ArrayList <Item> Cart;
    private double Main_Balance;
    private double Reward_Balance;

    public Customer(String name, String address)
    {
        ID = count++;
        Name = name;
        Address = address;
        Number_Of_Orders = 0;
        Bought_Items = new ArrayList<>();
        Cart = new ArrayList<>();
        Main_Balance = 100;
        Reward_Balance = 0;
    }

    @Override
    public void print_reward()
    { System.out.println("Reward Cash: " + Reward_Balance); }

    @Override
    public void print_details()
    {
        System.out.println("<---------- Customer Details ---------->");
        System.out.println("Name: " + Name);
        System.out.println("Address: " + Address);
        System.out.println("Numbers Of Orders Placed: " + Number_Of_Orders);
        System.out.println("<-------------------------------------->");
    }

    public void Recent_Orders()
    {
        int i=0;
        int L = Bought_Items.size();
        while ((i<10) && (L-i>0))
        {
            Item x = Bought_Items.get(L-i-1);
            System.out.println("Bought item "+x.getName()+ "(Quantity:"+x.getQuantity()+") for Rs."+x.getPrice()+" from Merchant: "+x.getMerchant());
        }
    }

    public int Buy_Item(Item A,Company Z)
    {
        Merchant X=null;
        for (Merchant S: Z.getM())
            if(S.getName().equals(A.getMerchant()))
                X=S;
        if (A.getQuantity() >= A.Required)
        {
            int q=A.Required;
            double cost=A.getPrice();
            if (A.getOffer().equals("BOGO")) {q=(q+1)/2;}
            if (A.getOffer().equals("25% OFF")) {cost=cost*(0.75);}
            cost = cost*q;
            double user_cost=cost*(1.005);
            double merchant_cost=cost*(0.995);
            if (user_cost>Main_Balance+Reward_Balance){System.out.println("Insufficient Balance!");}
            else
            {
                Main_Balance-=user_cost;
                if (Main_Balance<0){Reward_Balance+=Main_Balance;}
                A.setQuantity(A.getQuantity() - q);
                Bought_Items.add(new Item(A.getName(),user_cost,q,A.getCategory(),A.getMerchant()));
                A.Required=0;
                X.setContribution(X.getContribution()+user_cost-merchant_cost);
                if (X.getContribution()>=100)
                {
                    X.setReward_Slots(X.getReward_Slots()+1);
                    X.setContribution(0);
                }
                X.setEarnings(X.getEarnings()+merchant_cost);
                Z.setBalance(Z.getBalance()+user_cost-merchant_cost);
                System.out.println(A.getName()+" bought successfully.");
                return 1;
            }
            return 0;
        }
        else
            System.out.println("Insufficient Stock!");
            return 0;
    }

    public int getID() { return ID; }
    public String getName() { return Name; }
    public String getAddress() { return Address; }
    public int getNumber_Of_Orders() { return Number_Of_Orders; }
    public void setNumber_Of_Orders(int number_Of_Orders) { Number_Of_Orders = number_Of_Orders; }
    public ArrayList<Item> getBought_Items() { return Bought_Items; }
    public double getMain_Balance() { return Main_Balance; }
    public void setMain_Balance(double main_Balance) { Main_Balance = main_Balance; }
    public double getReward_Balance() { return Reward_Balance; }
    public void setReward_Balance(double reward_Balance) { Reward_Balance = reward_Balance; }
    public ArrayList<Item> getCart() { return Cart; }
}

class Merchant implements user
{
    private static  int count = 1;
    private int ID;
    private String Name;
    private String Address;
    private double Contribution;
    private int Slots;
    private int Reward_Slots;
    private double Earnings;

    public Merchant(String name, String address)
    {
        ID = count++;
        Name = name;
        Address = address;
        Contribution = 0;
        Slots = 10;
        Reward_Slots = 0;
        Earnings = 0;
    }

    @Override
    public void print_reward()
    { System.out.println("Reward Slots: " + Reward_Slots);}

    @Override
    public void print_details()
    {
        System.out.println("<---------- Merchant Details ---------->");
        System.out.println("Name: " + Name);
        System.out.println("Address: " + Address);
        System.out.println("Total Contribution To Company: " + Contribution);
        System.out.println("<-------------------------------------->");
    }

    public int getID() { return ID; }
    public String getName() { return Name; }
    public String getAddress() { return Address; }
    public double getContribution() { return Contribution; }
    public void setContribution(double contribution) { Contribution = contribution; }
    public int getSlots() { return Slots; }
    public void setSlots(int slots) { Slots = slots; }
    public int getReward_Slots() { return Reward_Slots; }
    public void setReward_Slots(int reward_Slots) { Reward_Slots = reward_Slots; }
    public double getEarnings() { return Earnings; }
    public void setEarnings(double earnings) { Earnings = earnings; }
}

class Item
{
    public static int count = 1;
    private int Code;
    private String Name;
    private double Price;
    private int Quantity;
    private String Offer;
    private String Category;
    private String Merchant;
    public int Required;

    public Item(String name, double price, int quantity,String category,String merchant)
    {
        Code = count++;
        Name = name;
        Price = price;
        Quantity = quantity;
        Offer = "None";
        Category = category;
        Merchant = merchant;
        Required = 0;
    }

    public void print_details()
    {
        System.out.println(">>> Item Details <<<");
        System.out.println("Code: "+getCode());
        System.out.println("Name: "+getName());
        System.out.println("Price: "+getPrice());
        System.out.println("Available: "+getQuantity()+" Units");
        System.out.println("Offer: "+getOffer());
        System.out.println("Category: "+getCategory());
        System.out.println(">>>--------------<<<");
    }
    public String getMerchant() { return Merchant; }
    public int getCode() { return Code; }
    public String getName() { return Name; }
    public String getCategory() { return Category; }
    public double getPrice() { return Price; }
    public void setPrice(double price) { Price = price; }
    public int getQuantity() { return Quantity; }
    public void setQuantity(int quantity) { Quantity = quantity; }
    public String getOffer() { return Offer; }
    public void setOffer(String offer) { Offer = offer; }
}

class Company
{
    private double Balance;
    private ArrayList<Customer> C;
    private ArrayList<Merchant> M;
    private ArrayList<Item> I;
    private ArrayList<String> Cat;
    public Company()
    {
        Balance = 0;
        // Initialising Customers
        C = new ArrayList<>();
        C.add(new Customer("Robert", "20 ,Sterling Street"));
        C.add(new Customer("Olivia", "42/7 ,Central Park"));
        C.add(new Customer("Mike", "15 ,Sterling Street"));
        C.add(new Customer("Sherlock", "221B ,Baker Street"));
        C.add(new Customer("Ashley", "88C ,Oxford Street"));
        // Initialising Merchants
        M = new ArrayList<>();
        M.add(new Merchant("Brian", "56 ,Oxford Street"));
        M.add(new Merchant("Samantha", "10 ,Abbey Road"));
        M.add(new Merchant("Derek", "7 ,Carnaby Street"));
        M.add(new Merchant("Jack", "9 ,Carnaby Street"));
        M.add(new Merchant("Amanda", "30 ,Piccadilly"));

        I = new ArrayList<>();
        Cat = new ArrayList<>();
    }

    public void print_user_details(user x) {x.print_details();}
    public void print_user_reward(user x) {x.print_reward();}
    public double getBalance() { return Balance; }
    public void setBalance(double balance) { Balance = balance; }
    public ArrayList<Customer> getC() { return C; }
    public ArrayList<Merchant> getM() { return M; }
    public ArrayList<Item> getI() { return I; }
    public ArrayList<String> getCat() { return Cat; }
}

public class Mercury
{
    public static void main(String[] args)
    {
        Company Z = new Company();
        int Q,Qm,Qc,Quc,Qmc,Qcc,Qic;
        Scanner in = new Scanner(System.in);
        do
        {
            System.out.println("<---  WELCOME TO MERCURY  --->");
            System.out.println("1 : Enter As Merchant ");
            System.out.println("2 : Enter As Customer ");
            System.out.println("3 : See User Details ");
            System.out.println("4 : Company Account Balance ");
            System.out.println("5 : Exit ");
            Q = in.nextInt();
            switch (Q)
            {
                case 1:
                    Merchant Cur = null;
                    for (Merchant X : Z.getM())
                    { System.out.println(X.getID()+": "+X.getName()); }
                    while (Cur == null)
                    {
                        Qmc = in.nextInt();
                        for (Merchant X : Z.getM())
                            if (X.getID() == Qmc)
                                Cur = X;
                    }
                    System.out.println(">>> Welcome "+Cur.getName()+" <<<");
                    do
                    {
                        Qm = in.nextInt();
                        switch (Qm)
                        {
                            case 1:
                                if ((Cur.getSlots()>0) || (Cur.getReward_Slots()>0))
                                {
                                    if (Cur.getSlots()>0) { Cur.setSlots(Cur.getSlots()-1);}
                                    else { Cur.setReward_Slots(Cur.getReward_Slots()-1);}
                                    System.out.print("Name: ");
                                    String Name = in.next();
                                    System.out.print("Price: ");
                                    Float Price = Float.parseFloat(in.next());
                                    System.out.print("Quantity: ");
                                    int Quantity = Integer.parseInt(in.next());
                                    System.out.print("Category: ");
                                    String Category = in.next();
                                    if (!Z.getCat().contains(Category)) { Z.getCat().add(Category);}
                                    Item A = new Item(Name,Price,Quantity,Category,Cur.getName());
                                    Z.getI().add(A);
                                    A.print_details();
                                }
                                else
                                { System.out.println("Sorry! You don't have sufficient slots."); }
                                break;
                            case 2:
                                System.out.print("Enter Item Code: ");
                                int c = Integer.parseInt(in.next());
                                for (Item A:Z.getI())
                                    if ((A.getCode()==c) && (A.getMerchant()==Cur.getName()))
                                    {
                                        System.out.println("New Price ? ");
                                        A.setPrice(in.nextInt());
                                        System.out.println("New Quantity ? ");
                                        A.setQuantity(in.nextInt());
                                        A.print_details();
                                    }
                                break;
                            case 3:
                                int i=1;
                                for (String C : Z.getCat() ) { System.out.println(i+" "+C); i++; }
                                Qcc = in.nextInt();
                                if(Qcc>0 && Qcc<=Z.getCat().size())
                                {
                                    String Category = Z.getCat().get(Qcc - 1);
                                    for (Item A : Z.getI())
                                        if (A.getCategory().equals(Category))
                                        {
                                            System.out.println(A.getName());
                                            for (Item B : Z.getI())
                                                if (A.getName().equals(B.getName()))
                                                    if (B.getMerchant().equals(Cur.getName()))
                                                        System.out.println("> Your Price: " + B.getPrice());
                                                    else
                                                        System.out.println("> " + B.getMerchant() + "'s Price: " + B.getPrice());
                                        }
                                }
                                break;
                            case 4:
                                System.out.print("Enter Item Code: ");
                                int d = Integer.parseInt(in.next());
                                for (Item A:Z.getI())
                                    if ((A.getCode()==d) && (A.getMerchant()==Cur.getName()))
                                    {
                                        System.out.println("Offer ?");
                                        A.setOffer(in.next());
                                        A.print_details();
                                    }
                                break;
                            case 5:
                                Z.print_user_reward(Cur);
                                break;
                            case 6:
                                System.out.println("Thank You");
                                break;
                            default:
                                System.out.println("Invalid Query, Please Enter Again");
                                break;
                        }
                    } while (Qm!=6);
                    break;
                case 2:
                    Customer Ptr = null;
                    for (Customer Y : Z.getC())
                    { System.out.println(Y.getID()+": "+Y.getName()); }
                    while (Ptr == null)
                    {
                        Quc = in.nextInt();
                        for (Customer Y : Z.getC())
                            if (Y.getID() == Quc)
                                Ptr = Y;
                    }
                    System.out.println(">>> Welcome "+Ptr.getName()+" <<<");
                    do
                    {
                        Qc=in.nextInt();
                        switch (Qc)
                        {
                            case 1:
                                int i=1;
                                for (String C : Z.getCat() )
                                { System.out.println(i+" "+C); i++; }
                                Qcc = in.nextInt();
                                if(Qcc>0 && Qcc<=Z.getCat().size())
                                {
                                    String Category = Z.getCat().get(Qcc - 1);
                                    for (Item A : Z.getI())
                                    { System.out.println(A.getCode()+" "+A.getName()+" Price: "+A.getPrice()+" Offer: "+A.getOffer()); }
                                }
                                do
                                {
                                    Qic =in.nextInt();
                                    if (Qic==3)
                                        break;
                                    else
                                    {
                                        System.out.println("Enter Item Code: ");
                                        int c = in.nextInt();
                                        System.out.println("Enter Quantity: ");
                                        int q = in.nextInt();
                                        for (Item A : Z.getI())
                                        {
                                            if(A.getCode()==c)
                                            {
                                                A.Required=q;
                                                if (Qic==1)
                                                {
                                                    Ptr.Buy_Item(A,Z);
                                                    Ptr.setNumber_Of_Orders(Ptr.getNumber_Of_Orders()+1);
                                                    if (Ptr.getNumber_Of_Orders()==5)
                                                    {
                                                        Ptr.setReward_Balance(Ptr.getReward_Balance() + 10);
                                                        Ptr.setNumber_Of_Orders(0);
                                                    }
                                                }
                                                else if (Qic==2)
                                                { Ptr.getCart().add(A); }
                                            }
                                        }
                                    }
                                } while(Qic!=3);
                                break;
                            case 2:
                                for (Item P : Ptr.getCart())
                                    if (Ptr.Buy_Item(P,Z)==0)
                                        break;
                                Ptr.setNumber_Of_Orders(Ptr.getNumber_Of_Orders()+1);
                                if (Ptr.getNumber_Of_Orders()==5)
                                {
                                    Ptr.setReward_Balance(Ptr.getReward_Balance() + 10);
                                    Ptr.setNumber_Of_Orders(0);
                                }
                                break;
                            case 3:
                                Z.print_user_reward(Ptr);
                                break;
                            case 4:
                                Ptr.Recent_Orders();
                                break;
                            case 5:
                                System.out.println("Thank You");
                                break;
                            default:
                                System.out.println("Invalid Query, Please Enter Again");
                        }
                    } while (Qc!=5);
                    break;
                case 3:
                    System.out.println("Merchants :-");
                    for (Merchant X : Z.getM())
                    { System.out.println(X.getID()+": "+X.getName()); }
                    System.out.println("Customers :-");
                    for (Customer Y : Z.getC())
                    { System.out.println(Y.getID()+": "+Y.getName()); }
                    break;
                case 4:
                    System.out.println("Company Balance: " + Z.getBalance());
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Query, Please Enter Again");
            }
        } while (Q!=5);
    }
}