public class book 
{
    public String title;
    public double price;
    public String ISBN;
    public String genre;
    public String author;
    public int pages;
    public int chapters;
    public int wordcount;
    //Default
    public book()
    {
        title = "Title";
        price = 0.0;
        author = "NA";
        ISBN = "NA";
        genre = "Generic";
        pages = 0;
        chapters = 0;
        wordcount = 0;
    }
    //Parameterized
    public book(String title , double price , String ISBN ,String author ,  String genre , int pages , int chapt , int wordcount) throws InvalidBookException
    {
        this.title  = title;
        if(price < 0)
            throw new InvalidBookException("Price cannot be negative");
        this.price = price;
        this.ISBN = ISBN;
        this.author = author;
        if (!genre.equalsIgnoreCase("fiction") && !genre.equalsIgnoreCase("autobiography") && !genre.equalsIgnoreCase("fantasy"))
            throw new InvalidBookException("Genre must be either fiction , autobiography or fantasy");
        this.genre = genre;
        this.pages = pages;
        chapters = chapt;
        this.wordcount = wordcount;
    }
    // Copy constructor
    public book(book b) throws InvalidBookException
    {
        title = b.title;
        if(b.price < 0)
            throw new InvalidBookException("Price cannot be negative");
        price = b.price;
        ISBN = b.ISBN;
        author = b.author;
        if (!b.genre.equalsIgnoreCase("fiction") && !b.genre.equalsIgnoreCase("autobiography") && !b.genre.equalsIgnoreCase("fantasy"))
            throw new InvalidBookException("Genre must be either fiction , autobiography or fantasy");
        genre = b.genre;
        pages = b.pages;
        chapters = b.chapters;
        wordcount = b.wordcount;
    }

}
