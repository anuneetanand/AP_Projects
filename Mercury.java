// Anuneet Anand
// 2018022
// CSE
// AP Lab 2 - Mercury

import java.util.*;

interface user
{
    // Interface for the two types of users : Merchant & Customer
    void print_reward();
    void print_details();
}

class Customer implements user
{
    // Class for storing information about customer.
    private static  int count = 1;
    private int ID;
    private String Name;
    private String Address;
    private int Number_Of_Orders;
    private ArrayList <Item> Bought_Items;
    private ArrayList <Item> Cart;
    private double Main_Balance;
    private double Reward_Balance;
    // Constructor
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
    { System.out.printf("Reward Cash: Rs %.2f", Reward_Balance); System.out.println(); }

    @Override
    public void print_details()
    {
        System.out.println("<---------- Customer Details ---------->");
        System.out.println("Name: " + Name);
        System.out.println("Address: " + Address);
        System.out.println("Numbers Of Orders Placed: " + Number_Of_Orders);
        System.out.println("<-------------------------------------->");
    }
    // To print last 10 transactions of the customer.
    public void Recent_Orders()
    {
        int i=0;
        int L = Bought_Items.size();
        while ((i<10) && (L-i>0))
        {
            Item x = Bought_Items.get(L-i-1);
            System.out.print("Bought item "+x.getName()+ " (Quantity:"+x.getQuantity()+") for Rs ");
            System.out.printf("%.2f",x.getPrice());
            System.out.print(" from Merchant:"+x.getMerchant());
            System.out.println();
            i++;
        }
    }
    // To complete transaction of an item.
    public int Buy_Item(Item A,Company Z)
    {
        Merchant X = null;
        for (Merchant S : Z.getM())
            if (S.getName().equals(A.getMerchant()))
                X = S;
        if ((A.getQuantity() >= A.Required) && (X!=null))
        {
            int q = A.Required;
            double cost = A.getPrice();
            if (A.getOffer().equals("BOGO")) { q = (q + 1) / 2; A.Required=q*2; }
            if (A.getOffer().equals("25%OFF")) { cost = cost * (0.75); }
            if (A.getQuantity()<A.Required) {A.Required=A.getQuantity();}
            cost = cost * q;
            double user_cost = cost * (1.005);
            double merchant_cost = cost * (0.995);
            if (user_cost > Main_Balance + Reward_Balance)
            {
                System.out.println("Insufficient Balance for buying "+A.getName());
                return 0;
            }
            else
            {
                Main_Balance -= user_cost;
                if (Main_Balance < 0) { Reward_Balance += Main_Balance; }
                A.setQuantity(A.getQuantity() - (A.Required));
                Bought_Items.add(new Item(A.getName(), user_cost, A.Required, A.getCategory(), A.getMerchant()));
                A.Required = 0;
                X.setContribution(X.getContribution() + user_cost - merchant_cost);
                X.setReward_Slots((int)(X.getContribution()/100));
                X.setEarnings(X.getEarnings() + merchant_cost);
                Z.setBalance(Z.getBalance() + user_cost - merchant_cost);
                System.out.println(A.getName() + " bought successfully.");
                return 1;
            }
        }
        else
        {
            System.out.println("Insufficient Stock for "+A.getName());
            return 0;
        }
    }
    // Getters & Setters
    public int getID() { return ID; }
    public String getName() { return Name; }
    public int getNumber_Of_Orders() { return Number_Of_Orders; }
    public void setNumber_Of_Orders(int number_Of_Orders) { Number_Of_Orders = number_Of_Orders; }
    public double getReward_Balance() { return Reward_Balance; }
    public void setReward_Balance(double reward_Balance) { Reward_Balance = reward_Balance; }
    public ArrayList<Item> getCart() { return Cart; }
    public double getMain_Balance() { return Main_Balance; }
    public void setMain_Balance(double main_Balance) { Main_Balance = main_Balance; }
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
    // Constructor
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
        System.out.printf("Total Contribution To Company: Rs %.2f",Contribution);
        System.out.println();
        System.out.println("<-------------------------------------->");
    }
    //Getters & Setters
    public int getID() { return ID; }
    public String getName() { return Name; }
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
    // Constructor
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
        System.out.println(">>>>  Item Details  <<<<");
        System.out.println("Code: "+getCode());
        System.out.println("Name: "+getName());
        System.out.printf("Price: Rs %.2f",getPrice());
        System.out.println();
        System.out.println("Available: "+getQuantity()+" Units");
        System.out.println("Offer: "+getOffer());
        System.out.println("Category: "+getCategory());
        System.out.println(">>>------------------<<<");
    }
    // Getters & Setters
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
    // Constructor
    public Company()
    {
        Balance = 0;
        I = new ArrayList<>();
        Cat = new ArrayList<>();
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
    }
    // Getters & Setters
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
        int Q,Q_Merchant,Q_Customer,Q_Merchant_List,Q_Customer_List,Q_Category_List,Q_Item_List;
        Scanner in = new Scanner(System.in);
        do
        {
            System.out.println("<---  WELCOME TO MERCURY  --->");
            System.out.println("1: Enter As Merchant ");
            System.out.println("2: Enter As Customer ");
            System.out.println("3: See User Details ");
            System.out.println("4: Company Account Balance ");
            System.out.println("5: Exit ");
            System.out.println(">>>-----------------------<<<");
            Q = in.nextInt();
            in.nextLine();
            switch (Q)
            {
                case 1:
                    // Merchant User
                    System.out.println("Select A Valid Merchant :-");
                    Merchant Cur = null;
                    for (Merchant X : Z.getM())
                    { System.out.println(X.getID()+": "+X.getName()); }
                    while (Cur == null)
                    {
                        // Selecting Merchant
                        Q_Merchant_List = in.nextInt();
                        for (Merchant X : Z.getM())
                            if (X.getID() == Q_Merchant_List)
                                Cur = X;
                    }
                    do
                    {
                        // Queries of Merchant
                        System.out.println(">>>>>>  Welcome "+Cur.getName()+"  <<<<<<");
                        System.out.println("1: Add Item ");
                        System.out.println("2: Edit Item ");
                        System.out.println("3: Search By Category ");
                        System.out.println("4: Add Offer ");
                        System.out.println("5: Rewards Won ");
                        System.out.println("6: Exit ");
                        System.out.println(">>>-----------------------<<<");
                        Q_Merchant = in.nextInt();
                        in.nextLine();
                        switch (Q_Merchant)
                        {
                            case 1:
                                // Add Item
                                if ((Cur.getSlots()>0) || (Cur.getReward_Slots()>0))
                                {
                                    if (Cur.getSlots()>0) { Cur.setSlots(Cur.getSlots()-1);}
                                    else { Cur.setReward_Slots(Cur.getReward_Slots()-1);}
                                    System.out.print("Name: ");
                                    String Name = in.nextLine();
                                    System.out.print("Price: ");
                                    Double Price = Double.parseDouble(in.next());
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
                                { System.out.println("Sorry! You don't have sufficient slots"); }
                                break;

                            case 2:
                                // Edit Item Details
                                for (Item S : Z.getI())
                                    if(S.getMerchant().equals(Cur.getName()))
                                        System.out.println(S.getCode()+": "+S.getName());
                                System.out.print("Enter Item Code: ");
                                int c = Integer.parseInt(in.next());
                                for (Item A:Z.getI())
                                    if ((A.getCode()==c) && (A.getMerchant().equals(Cur.getName())))
                                    {
                                        System.out.println("New Price for "+A.getName()+" ?");
                                        A.setPrice(in.nextDouble());
                                        System.out.println("New Quantity for "+A.getName()+" ?");
                                        A.setQuantity(in.nextInt());
                                        A.print_details();
                                    }
                                break;

                            case 3:
                                // Searching
                                int i=1;
                                for (String C : Z.getCat() ) { System.out.println(i+" "+C); i++; }
                                Q_Category_List = in.nextInt();
                                if(Q_Category_List>0 && Q_Category_List<=Z.getCat().size())
                                {
                                    String Category = Z.getCat().get(Q_Category_List - 1);
                                    ArrayList <String> Seen = new ArrayList<>();
                                    for (Item A : Z.getI())
                                        if (A.getCategory().equals(Category) && (!Seen.contains(A.getName())))
                                        {
                                            Seen.add(A.getName());
                                            System.out.println(A.getName());
                                            for (Item B : Z.getI())
                                            {
                                                if (A.getName().equals(B.getName()))
                                                {
                                                    if (B.getMerchant().equals(Cur.getName()))
                                                    {
                                                        System.out.printf(">> Your Price: Rs %.2f", B.getPrice());
                                                        System.out.println();
                                                        System.out.println("  Quantity Available With You: "+ B.getQuantity());
                                                    }
                                                        else
                                                    {
                                                        System.out.printf(">> " + B.getMerchant() + "'s Price: Rs %.2f",B.getPrice());
                                                        System.out.println();
                                                        System.out.println("   Quantity Available with "+B.getMerchant()+": "+B.getQuantity());
                                                    }
                                                }
                                            }
                                        }
                                }
                                break;

                            case 4:
                                // Add Offer
                                for (Item S : Z.getI())
                                    if(S.getMerchant().equals(Cur.getName()))
                                        System.out.println(S.getCode()+": "+S.getName());
                                System.out.print("Enter Item Code: ");
                                int d = in.nextInt();
                                for (Item A:Z.getI())
                                    if ((A.getCode()==d) && (A.getMerchant().equals(Cur.getName())))
                                    {
                                        System.out.println("Offer ?");
                                        System.out.println("> BOGO");
                                        System.out.println("> 25%OFF");
                                        String Offer = in.next();
                                        A.setOffer(Offer);
                                        A.print_details();
                                    }
                                break;

                            case 5:
                                // Print Reward Slots
                                Z.print_user_reward(Cur);
                                break;

                            case 6:
                                // Exit
                                System.out.println("Thank You");
                                break;

                            default:
                                System.out.println("Invalid Query, Please Enter Again");
                                break;
                        }
                    } while (Q_Merchant!=6);
                    break;

                case 2:
                    // Customer User
                    System.out.println("Select A Valid Customer :-");
                    Customer Ptr = null;
                    for (Customer Y : Z.getC())
                    { System.out.println(Y.getID()+": "+Y.getName()); }
                    while (Ptr == null)
                    {
                        // Selecting Customer
                        Q_Customer_List = in.nextInt();
                        for (Customer Y : Z.getC())
                            if (Y.getID() == Q_Customer_List)
                                Ptr = Y;
                    }

                    do
                    {
                        // Queries of Customer
                        System.out.println(">>>>>>  Welcome "+Ptr.getName()+"  <<<<<<");
                        System.out.println("1: Search Item ");
                        System.out.println("2: Checkout Cart ");
                        System.out.println("3: Reward Won ");
                        System.out.println("4: Print Latest Orders ");
                        System.out.println("5: Exit ");
                        System.out.println(">>>-----------------------<<<");
                        Q_Customer=in.nextInt();
                        in.nextLine();
                        switch (Q_Customer)
                        {
                            case 1:
                                // Searching
                                int i=1;
                                for (String C : Z.getCat() )
                                { System.out.println(i+" "+C); i++; }
                                Q_Category_List = in.nextInt();
                                if(Q_Category_List>0 && Q_Category_List<=Z.getCat().size())
                                {
                                    String Category = Z.getCat().get(Q_Category_List - 1);
                                    for (Item A : Z.getI())
                                        if (A.getCategory().equals(Category))
                                            {
                                                System.out.print(A.getCode()+" "+A.getName());
                                                System.out.printf(" Price: Rs %.2f",A.getPrice());
                                                System.out.print(" Offer: "+A.getOffer());
                                                System.out.println();
                                            }
                                }
                                // Selecting Item
                                do
                                {
                                    System.out.println("1: Buy");
                                    System.out.println("2: Add To Cart");
                                    System.out.println("3: Exit");
                                    Q_Item_List =in.nextInt();
                                    in.nextLine();
                                    if (Q_Item_List==3)
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
                                                if (Q_Item_List==1)
                                                {
                                                    Ptr.Buy_Item(A,Z);
                                                    System.out.printf("Your Balance : Rs %.2f",Ptr.getMain_Balance());
                                                    System.out.println();
                                                    Ptr.setNumber_Of_Orders(Ptr.getNumber_Of_Orders()+1);
                                                    Ptr.setReward_Balance(10*((int)(Ptr.getNumber_Of_Orders()/5)));
                                                }
                                                else if (Q_Item_List==2)
                                                {
                                                    if (A.getQuantity()<A.Required)
                                                    { System.out.println("Sorry Not Enough Stock Available!");}
                                                    else
                                                    {
                                                        Ptr.getCart().add(A);
                                                        System.out.println(A.Required + " units of " + A.getName() + " added to cart");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } while(Q_Item_List!=3);
                                break;

                            case 2:
                                // Checkout
                                if (Ptr.getCart().isEmpty())
                                    System.out.println("Your Cart Is Empty.");
                                else
                                    {
                                        int f = 1;
                                        for (Item P : Ptr.getCart())
                                            if (Ptr.Buy_Item(P, Z) == 0)
                                            {
                                                f = 0;
                                                break;
                                            }
                                        System.out.printf("Your Balance : Rs %.2f", Ptr.getMain_Balance());
                                        System.out.println();
                                        if (f == 1)
                                        {
                                            Ptr.setNumber_Of_Orders(Ptr.getNumber_Of_Orders() + 1);
                                            Ptr.setReward_Balance(10 * ((int) (Ptr.getNumber_Of_Orders() / 5)));
                                        }
                                        Ptr.getCart().clear();
                                    }
                                break;

                            case 3:
                                // Print Reward Cash
                                Z.print_user_reward(Ptr);
                                break;

                            case 4:
                                // Print Last 10 Transactions
                                Ptr.Recent_Orders();
                                break;

                            case 5:
                                // Exit
                                System.out.println("Thank You");
                                break;

                            default:
                                System.out.println("Invalid Query, Please Enter Again");
                        }
                    } while (Q_Customer!=5);
                    break;

                case 3:
                    // Print User Details
                    System.out.println("Merchants :-");
                    for (Merchant X : Z.getM())
                    { System.out.println(X.getID()+": "+X.getName()); }
                    System.out.println("Customers :-");
                    for (Customer Y : Z.getC())
                    { System.out.println(Y.getID()+": "+Y.getName()); }
                    String Type = in.next();
                    int ID = in.nextInt();
                    if (Type.equals("M"))
                    {
                        for (Merchant X : Z.getM())
                            if (X.getID()==ID)
                                Z.print_user_details(X);
                    }
                    if (Type.equals("C"))
                    {
                        for (Customer Y : Z.getC())
                            if (Y.getID()==ID)
                                Z.print_user_details(Y);
                    }
                    break;

                case 4:
                    // Print Company Balance
                    System.out.printf("Company Balance: Rs %.2f",Z.getBalance());
                    System.out.println();
                    break;

                case 5:
                    // Exit Application
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid Query, Please Enter Again");
            }
        } while (Q!=5);
    }
}
// END OF CODE