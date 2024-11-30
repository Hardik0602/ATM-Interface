import java.io.*;
import java.util.Scanner;
public class AtmInterface
{
    static String account_file="AccountFile.txt";
    public static void main(String[] args)
    {
        Scanner scn=new Scanner(System.in);
        System.out.print("Account Number: ");
        String account_number=scn.nextLine();
        if (account_exists((account_number))==false)
        {
            System.out.print("Account Holder Name: ");
            String account_holder=scn.nextLine();
            account_create(account_number,account_holder);
        }
        boolean run=true;
        while (run)
        {
            System.out.println("\n*******ATM*******");
            System.out.println("1.Check Balance");
            System.out.println("2.Deposit");
            System.out.println("3.Withdraw");
            System.out.println("4.Exit");
            System.out.print("Option: ");
            int option=scn.nextInt();
            System.out.print("\n");
            switch (option)
            {
                case 1:
                    check_balance(account_number);
                    break;
                case 2:
                    deposit(account_number,scn);
                    break;
                case 3:
                    withdraw(account_number,scn);
                    break;
                case 4:
                    run=false;
                    break;
                default:
                    System.out.println("Invalid Input.");
            }
        }
        scn.close();
    }
    public static boolean account_exists(String account_number)
    {
        try (BufferedReader reader=new BufferedReader(new FileReader(account_file)))
        {
            String data;
            while ((data=reader.readLine())!=null)
            {
                String[] details=data.split(",");
                if (details[0].equals(account_number))
                {
                    return true;
                }
            }
        }
        catch (Exception e)
        {}
        return false;
    }
    public static void account_create(String account_number,String account_holder)
    {
        try
        {
            BufferedWriter writer=new BufferedWriter(new FileWriter(account_file,true));
            writer.write(account_number+","+account_holder+","+0);
            writer.newLine();
            writer.close();
        }
        catch (Exception e)
        {}
    }
    public static void check_balance(String account_number)
    {
        try
        {
            BufferedReader reader=new BufferedReader(new FileReader(account_file));
            String data;
            while ((data=reader.readLine())!=null)
            {
                String[] details=data.split(",");
                if (details[0].equals(account_number))
                {
                    System.out.println(details[1]+"'s Balance: "+details[2]);
                }
            }
            reader.close();
        }
        catch (Exception e)
        {}
    }
    public static void deposit(String account_number,Scanner scn)
    {
        System.out.print("Amount: ");
        int amount=scn.nextInt();
        int lines=0;
        try
        {
            BufferedReader reader=new BufferedReader(new FileReader(account_file));
            while ((reader.readLine())!=null)
            {
                lines++;
            }
            reader.close();
        }
        catch (Exception e)
        {}
        String[] data=new String[lines];
        try
        {
            BufferedReader reader=new BufferedReader(new FileReader(account_file));
            for (int i=0;i<lines;i++)
            {
                data[i]=reader.readLine();
            }
            reader.close();
        }
        catch (Exception e)
        {}
        for (int i=0;i<lines;i++)
        {
            String[] details=data[i].split(",");
            if (details[0].equals(account_number))
            {
                int balance=Integer.parseInt(details[2]);
                int balance_new=balance+amount;
                data[i]=details[0]+","+details[1]+","+balance_new;
                break;
            }
        }
        try
        {
            BufferedWriter writer=new BufferedWriter(new FileWriter(account_file));
            for (int i=0;i<lines;i++)
            {
                writer.write(data[i]);
                writer.newLine();
            }
            writer.close();
            
            System.out.println("Deposit Successful.");
        }
        catch (Exception e)
        {}
    }
    public static void withdraw(String account_number,Scanner scn)
    {
        System.out.print("Amount: ");
        int amount=scn.nextInt();
        int lines=0;
        try
        {
            BufferedReader reader=new BufferedReader(new FileReader(account_file));
            while ((reader.readLine())!=null)
            {
                lines++;
            }
            reader.close();
        }
        catch (Exception e)
        {}
        String[] data=new String[lines];
        try
        {
            BufferedReader reader=new BufferedReader(new FileReader(account_file));
            for (int i=0;i<lines;i++)
            {
                data[i]=reader.readLine();
            }
            reader.close();
        }
        catch (Exception e)
        {}
        for (int i=0;i<lines;i++)
        {
            String[] details=data[i].split(",");
            if (details[0].equals(account_number))
            {
                int balance=Integer.parseInt(details[2]);
                if (balance>=amount)
                {
                    int balance_new=balance-amount;
                    data[i]=details[0]+","+details[1]+","+balance_new;
                    break;
                }
                else
                {
                    System.out.println("Insufficient Balance.");
                }
            }
        }
        try
        {
            BufferedWriter writer=new BufferedWriter(new FileWriter(account_file));
            for (int i=0;i<lines;i++)
            {
                writer.write(data[i]);
                writer.newLine();
            }
            writer.close();
            System.out.println("Withdraw Successful.");
        }
        catch (Exception e)
        {}
    }
}