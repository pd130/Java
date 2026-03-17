import java.util.Scanner;

public class calculator
{
   double num1 , num2;
   public int choice;

    public double addition(double n1 , double n2)
    {
        return n1 + n2;
    }

    public double substract(double n1 , double n2)
    {
        return n1 - n2;
    }

    public double product(double n1 , double n2)
    {
        return n1 * n2;
    }

    public double divide(double n1 , double n2)
    {
        return n1 / n2;
    }

    public double mod(double n1 , double n2)
    {
        return n1 % n2;
    }
    
    public static void main(String[] args)
    {
        calculator calc = new calculator();
        Scanner input = new Scanner(System.in);
        while(calc.choice != 6)
        {
            System.out.println("1. Addition ");
        System.out.println("2. Substraction ");
        System.out.println("3. Multiply ");
        System.out.println("4. Divide ");
        System.out.println("5. Modulus ");
        System.out.println("6. For Exit ");
        System.out.println("Enter your choice for arithematic operations : ");
        calc.choice = input.nextInt();
        if(calc.choice == 6)
        {
            System.exit(0);
        }
        System.out.println("Enter the first number : ");
        calc.num1 = input.nextDouble();
        System.out.println("Enter the second number : ");
        calc.num2 = input.nextDouble();

        switch(calc.choice)
        {
            case 1 :
                System.out.println("Addition is : " + calc.addition(calc.num1 , calc.num2));
                break;
            
            case 2 :
                System.out.println("Substraction is : " + calc.substract(calc.num1 , calc.num2));
                break;
            
            case 3:
                System.out.println("Product is : " + calc.product(calc.num1 , calc.num2));
                break;
            
            case 4:
                if(calc.num2 == 0)
                {
                    System.out.println("Cannot Divide by 0!");
                    break;
                }
                System.out.println("Division is : " + calc.divide(calc.num1 , calc.num2));
                break;

            case 5:
                System.out.println("Modulus is : " + calc.mod(calc.num1 , calc.num2));
                break;
            
            default:
                System.out.println("Please select a valid choice! ");
        }




     }
    }

}
