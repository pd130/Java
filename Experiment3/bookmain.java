import java.util.ArrayList;
public class bookmain 
{
    public static void main(String[] args)
    {
        ArrayList<book> books = new ArrayList<book>();
        try
        {
            // Default Constructor
           book b1 = new book();
           // Parameterized Constructor
           book b2 = new book("Harry potter Philospher's Stone" , 292.00 , "232322922", "JK Rowling" ,  "Fantasy" , 400 , 45 , 100000);
           // Copy Constructor
           book b3 = new book(b2);
           book b4 = new book("Harry potter Chamber of Secrets" , 292 , "232322922", "JK Rowling" ,  "Fantasy" , 400 , 45 , 100000);
           book b5 = new book("Harry potter Prisioner of Azkaban" , 491 , "25332922", "JK Rowling" ,  "Fantasy" , 700 , 50 , 100600);
           book b6 = new book("Hobbit" , 382 , "3121923" , "IDK" , "Fiction" , 300 , 35 , 2000);
           books.add(b1);
           books.add(b2);
           books.add(b3);
           books.add(b4);
           books.add(b5);
           books.add(b6);
           double totalPrice = 0.0;
           System.out.println("Printing the details of the books :");
           books.forEach(b -> {
            System.out.println("-----------------------------");
            System.out.println("Title : " + b.title);
            System.out.println("price : " + b.price);
            System.out.println("ISBN : " + b.ISBN);
            System.out.println("Genre : " + b.genre);
            System.out.println("Pages : " + b.pages);
            System.out.println("Chapters : " + b.chapters);
            System.out.println("Wordcount : " + b.wordcount);
            System.out.println("-----------------------------");
           });
           for(book b : books)
           {
            totalPrice += b.price;
           }    
           double averagePrice = totalPrice / books.size();
           System.out.println("-----------------------------");
           System.out.println("Total Price: " + totalPrice);
           System.out.println("Average Price: " + averagePrice);
           System.out.println("-----------------------------");
        }
        catch (InvalidBookException ib){};
        System.out.println("Testing wether the exception is working or not");
        System.out.println();

        try
        {
            book b7 = new book("ABCD" , -100 , "123456789" , "XYZ" , "Fiction" , 200 , 20 , 50000);
        }
        catch (InvalidBookException ib)
        {
            System.out.println("Exception Caught : " + ib.getMessage() );
            System.out.println();
        }
        try
        {
            book b8 = new book("EFGH" , 100 , "123456789" , "Unknown" , "Invalid Genre" , 200 , 20 , 50000);
        }
        catch (InvalidBookException ib)
        {
            System.out.println("Exception Caught : " + ib.getMessage());
            System.out.println();
        }
        System.out.println("Printing names of  books having genre Fantasy : ");
        System.out.println();
        books.forEach(b -> {
            if(b.genre.equalsIgnoreCase("Fantasy"))
                System.out.println(b.title);
        });
        


    }
}
