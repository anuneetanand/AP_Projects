// Anuneet Anand
// 2018022
// CSE
// AP Lab 1 - Placements

import java.util.*;

class TS
{
    // NodeClass to store score of a student in technical round of a company.
    // Used in custom linked list Score_List.
    private String Company;
    private int Score;
    private TS Link;
    //Constructor
    public TS(String company, int score)
    {
        Company = company;
        Score = score;
        Link = null;
    }
    // Getters & Setters
    public String getCompany() { return Company; }
    public int getScore() { return Score; }
    public TS getLink() { return Link; }
    public void setLink(TS link) { Link = link; }
}

class Score_List
{
    // A custom linked list class to store scores of a student in technical rounds of different companies.
    private TS Head;
    //Constructor
    public Score_List()
    { Head = null; }

    public void Insert(TS X)
    {
        // Inserts a new node of type TS.
        if(Head==null)
        {   Head=X; }
        else
        {
            TS Cur=Head;
            while (Cur.getLink()!=null)
            {Cur=Cur.getLink();}
            Cur.setLink(X);
        }
    }

    public int Search_Score(String Y)
    {
        // Returns score of the student in the technical round of company Y.
        // Returns -1 if the student has not taken technical round of concerned company.
        TS Cur=Head;
        while(Cur!=null)
        {
            if (Cur.getCompany().equals(Y))
                return Cur.getScore();
            Cur=Cur.getLink();
        }
        return -1;
    }

    public void prn()
    {
        // Display scores of the student in technical rounds of different companies.
        TS Cur=Head;
        while(Cur!=null)
        {
            System.out.println(Cur.getCompany()+": "+Cur.getScore());
            Cur=Cur.getLink();
        }
    }
}

class Student
{
    // Class to store student data.
    private static int Count = 1;
    private final int Roll_Number;
    private final float CGPA;
    private final String Course;
    private String Placement_Status;
    private String Company;
    private Score_List Tech_Score;
    //Constructor
    public Student(float CGPA, String Course)
    {
        this.Roll_Number = Count;
        this.CGPA = CGPA;
        this.Course = Course;
        Placement_Status = "Not Placed";
        Company = "";
        Tech_Score = new Score_List();
        Count++;
    }

    public void prn()
    {
        // Displays information of the student.
        System.out.println("<---- Student Details ---->");
        System.out.println("Roll Number: " + Roll_Number);
        System.out.println("CGPA: " + CGPA);
        System.out.println("Course: " + Course);
        System.out.println("Placement Status: " + Placement_Status);
        if (!Company.equals(""))
            System.out.println("Company: " + Company);
        System.out.println("---------------------------");
    }
    // Getters & Setters
    public static int getCount() { return Count; }
    public int getRoll_Number() { return Roll_Number; }
    public float getCGPA() { return CGPA; }
    public String getCourse() { return Course; }
    public String getPlacement_Status() { return Placement_Status; }
    public void setPlacement_Status(String placement_Status) { Placement_Status = placement_Status; }
    public String getCompany() { return Company; }
    public void setCompany(String company) { Company = company; }
    public Score_List getTech_Score() { return Tech_Score; }
}

class Company
{
    // Class to store company data.
    private int Count;
    private final String Name;
    private int Number_Of_Eligible_Courses;
    private ArrayList<String> Eligible_Courses;
    private int Number_Of_Required_Students;
    private String Application_Status;
    private ArrayList <Integer> Students;
    //Constructor
    public Company(String Name, int Number_Of_Eligible_Courses, ArrayList<String> Eligible_Courses, int Number_Of_Required_Students)
    {
        this.Name = Name;
        this.Number_Of_Eligible_Courses = Number_Of_Eligible_Courses;
        this.Eligible_Courses = Eligible_Courses;
        this.Number_Of_Required_Students = Number_Of_Required_Students;
        Application_Status = "OPEN";
        Students = new ArrayList <>();
        Count=0;
    }

    public void prn()
    {
        // Displays information about the company.
        System.out.println("<---- Company Details ---->");
        System.out.println("Name: " + Name);
        System.out.println("Course Criteria :-");
        for(String z: Eligible_Courses )
            System.out.println("- " + z);
        System.out.println("Number Of Required Students: " + Number_Of_Required_Students);
        System.out.println("Application Status: " + Application_Status);
        System.out.println("---------------------------");
    }
    //Getters & Setters
    public String getName() { return Name; }
    public int getNumber_Of_Eligible_Courses() { return Number_Of_Eligible_Courses; }
    public ArrayList<String> getEligible_Courses() { return Eligible_Courses; }
    public int getNumber_Of_Required_Students() { return Number_Of_Required_Students; }
    public String getApplication_Status() { return Application_Status; }
    public void setApplication_Status(String application_Status) { Application_Status = application_Status; }
    public ArrayList<Integer> getStudents() { return Students; }
    public int getCount() { return Count; }
    public void setCount(int count) { Count = count; }
}

class Office
{
    //  Class to work as Placement Office
    Scanner in = new Scanner (System.in);
    private ArrayList<Student> S;
    private ArrayList<Company> C;
    private int P;
    private int U;
    //Constructor
    public Office()
    {
        S = new ArrayList<>();
        C = new ArrayList<>();
        P=0;
        U=0;
    }

    public void Tech_Round(String Company_Name)
    {
        // Store scores of student in technical round of a company.
        for (Company B : C)
        {
            if (B.getName().equals(Company_Name))
            {
                System.out.println("Enter Scores For Technical Round:- ");
                for (Student A : S)
                {
                    if (B.getEligible_Courses().contains(A.getCourse()))
                    {
                        System.out.println("Enter Score For Roll No. " + A.getRoll_Number());
                        int x = Integer.parseInt(in.nextLine());
                        TS z = new TS(Company_Name,x);
                        A.getTech_Score().Insert(z);
                    }
                }
            }
        }
    }

    public void Select(String Company_Name)
    {
        // Select students for placement in a company.
        for (Company B : C)
            if (B.getName().equals(Company_Name))
            {
                if (B.getCount()>U)
                {
                    for (Student A : S)
                    {
                        if(B.getEligible_Courses().contains(A.getCourse()) && A.getPlacement_Status().equals("Not Placed"))
                        {
                            P++;
                            U--;
                            A.setPlacement_Status("Placed");
                            A.setCompany(Company_Name);
                            B.getStudents().add(A.getRoll_Number());
                            B.setCount(B.getCount() + 1);
                        }
                    }
                }
                while ( (B.getCount()<B.getNumber_Of_Required_Students()) && (S.size()>0))
                {
                    Student X=null;
                    for (Student A : S)
                    {
                        if(B.getEligible_Courses().contains(A.getCourse()) && A.getPlacement_Status().equals("Not Placed"))
                        {
                            if (X==null)
                                X=A;
                            else if (A.getTech_Score().Search_Score(Company_Name)>X.getTech_Score().Search_Score(Company_Name))
                                X=A;
                            else if (A.getTech_Score().Search_Score(Company_Name)==X.getTech_Score().Search_Score(Company_Name))
                            {
                                if (A.getCGPA()>X.getCGPA())
                                    X=A;
                            }
                        }
                    }
                    if (X==null)
                        break;
                    P++;
                    U--;
                    X.setPlacement_Status("Placed");
                    X.setCompany(Company_Name);
                    B.getStudents().add(X.getRoll_Number());
                    B.setCount(B.getCount()+1);
                }
                if (B.getCount()==B.getNumber_Of_Required_Students())
                {   B.setApplication_Status("CLOSED");  }
            }
    }
    //Getters & Setters
    public ArrayList<Student> getS() { return S; }
    public ArrayList<Company> getC() { return C; }
    public int getP() { return P; }
    public int getU()  {return U;}
    public void setU(int u) { U = u; }
}

public class Placements
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner (System.in);
        Office O = new Office();
        int Q,R,i;
        ArrayList <String> Valid_Courses = new ArrayList<>(List.of("CSE","ECE","CSD","CSAM","CSB","CSSS"));
        System.out.print("Enter Number Of Registrations: ");
        int N = Integer.parseInt(in.nextLine());
        O.setU(N);
        System.out.println("Enter CGPA & Course:- ");
        for (i=0;i<N;i++)
        {
            String Ip[] = in.nextLine().split(" ");
            float CGPA = Float.parseFloat(Ip[0]);
            String Course = Ip[1];
            if (!Valid_Courses.contains(Course) || CGPA<0 || CGPA>10)
            {
                System.out.println("Invalid Data , Please Enter Again.");
                i--;
            }
            else
            {
                Student X = new Student(CGPA, Course);
                (O.getS()).add(X);
            }
        }
        System.out.println("----- Students Registered -----");
        while((!O.getS().isEmpty()) && (O.getP()<N))
        {
            String Qx[] = in.nextLine().split(" ");
            Q=Integer.parseInt(Qx[0]);
            switch (Q)
            {
                case 1:
                    // Add Company and print its details.
                    // Record scores of eligible students in technical round.
                    String Name =  in.nextLine();
                    System.out.print("Number Of Eligible Courses: ");
                    int Number_Of_Eligible_Courses = Integer.parseInt(in.nextLine());
                    ArrayList <String> Eligible_Courses = new ArrayList <>();
                    for(i=0;i<Number_Of_Eligible_Courses;i++)
                    {
                        String Course_Name=in.nextLine();
                        Eligible_Courses.add(Course_Name);
                    }
                    System.out.print("Number Of Required Students: ");
                    int Number_Of_Required_Students= Integer.parseInt(in.nextLine());
                    Company X = new Company(Name,Number_Of_Eligible_Courses,Eligible_Courses,Number_Of_Required_Students);
                    O.getC().add(X);
                    X.prn();
                    O.Tech_Round(Name);
                    break;

                case 2:
                    // Remove accounts of placed students.
                    System.out.println("Accounts Removed For Roll Numbers:- ");
                    for(i=0;i<O.getS().size();i++)
                    {
                        if (O.getS().get(i).getPlacement_Status().equals("Placed"))
                        {
                            System.out.println(O.getS().get(i).getRoll_Number());
                            O.getS().remove(i);
                            i--;
                        }
                    }
                    break;

                case 3:
                    // Remove accounts for companies whose applications are closed.
                    System.out.println("Accounts Removed For Companies:- ");
                    for(i=0;i<O.getC().size();i++)
                    {
                        if (O.getC().get(i).getApplication_Status().equals("CLOSED"))
                        {
                            System.out.println(O.getC().get(i).getName());
                            O.getC().remove(i);
                            i--;
                        }
                    }
                    break;

                case 4:
                    // Display number of students who have not been placed yet.
                    System.out.println(O.getU()+ " Students Left For Placement");
                    break;

                case 5:
                    // Display companies whose applications are still open.
                    for(i=0;i<O.getC().size();i++)
                    {
                        if (O.getC().get(i).getApplication_Status().equals("OPEN"))
                            System.out.println(O.getC().get(i).getName());
                    }
                    break;

                case 6:
                    // Select students for placement in a company.
                    Name = Qx[1];
                    O.Select(Name);
                    System.out.println("Roll Numbers Of Selected Students:- ");
                    for(i=0;i<O.getC().size();i++)
                    {
                        if (O.getC().get(i).getName().equals(Name))
                        {
                            for (Integer j : O.getC().get(i).getStudents())
                                System.out.println(j);
                        }
                    }
                    break;

                case 7:
                    // Display details of company.
                    int a=0;
                    Name = Qx[1];
                    for(i=0;i<O.getC().size();i++)
                    {
                        if (O.getC().get(i).getName().equals(Name))
                        {
                            O.getC().get(i).prn();
                            a=1;
                        }
                    }
                    if (a==0)
                        System.out.println("No Company with the given Name has an account.");
                    break;

                case 8:
                    // Display details of student.
                    int b=0;
                    R=Integer.parseInt(Qx[1]);
                    for(i=0;i<O.getS().size();i++)
                    {
                        if (O.getS().get(i).getRoll_Number()==R)
                        {
                            O.getS().get(i).prn();
                            b=1;
                        }
                    }
                    if (b==0)
                        System.out.println("No student with the given Roll Number has an account.");
                    break;

                case 9:
                    // Display Technical scores of a student in different companies.
                    int c=0;
                    R=Integer.parseInt(Qx[1]);
                    for(i=0;i<O.getS().size();i++)
                    {
                        if (O.getS().get(i).getRoll_Number()==R)
                        {
                            O.getS().get(i).getTech_Score().prn();
                            c=1;
                        }
                    }
                    if (c==0)
                        System.out.println("No student with the given Roll Number has an account.");
                    break;

                case 10:
                    // Terminate Application
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid Query!");
                    break;
            }
        }
        System.out.println("---- All Students Placed ----");
        System.exit(0);
    }
}
