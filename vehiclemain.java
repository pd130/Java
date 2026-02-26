
public class vehiclemain 
{
    public static void prntvehicledetails(Vehicle v)
   {
      System.out.println("===========Vehicle Details==========");
      System.out.println("Brandname : " + v.brandName);
      System.out.println("Model Type : " + v.modelName);
      System.out.println("Color : " + v.color);
      System.out.println("Manufacturing Code : " + v.getMfgCode());
      System.out.println("Fuel Type : " + v.fuelType);
      System.out.println("Load : " + v.load);
      System.out.println("Price : " + v.price);
      System.out.println("=====================================");

   }

   public static void tabulardetails(Vehicle v)
   {
     System.out.println("|" + v.brandName + "|" + v.modelName + "|" + v.price + "|" + v.fuelType + "|" +  v.mileage(v.fuelType));

   }

   public static void main(String[] args) 
   {
       //Vehicle v1 = new Vehicle();
       //v1.brandName = "Kia";
       //v1.modelName = "Carens";
       //v1.color = "White";
       //v1.price = 1300000;
       //v1.setmfgcode("Joe220");
       //v1.fuelType = "Petrol";
       //v1.load = 13;
       //prntvehicledetails(v1);

       //Vehicle v2 = new Vehicle();
       //prntvehicledetails(v2);

       //Vehicle v3 = new Vehicle("Audio" , "R8" , 949494949 , "black" , "ABCD456");
       //prntvehicledetails(v3);

       //Vehicle v4 = v2;
       //prntvehicledetails(v4);


       //Vehicle[] myvehicles = new Vehicle[]{v1 ,v2 , v3 , v4};
       //for(Vehicle v : myvehicles)
       //{
        //prntvehicledetails(v);
        //v.start();
        //v.drive();
        //v.stop();
       //}
    Vehicle v1 = new Vehicle("Toyota" , "Camry" , 9393939 , "Petrol");
    Vehicle v2 = new Vehicle("Ford" , "Mustang" , 3939392 , "Diseal");
    Vehicle v3 = new Vehicle("Porsche" , "911" , 9219221 , "Electric");
    Vehicle v4 = new Vehicle("Hyundai" , "Creta" , 38322811 , "Gas");
    Vehicle v5 = new Vehicle("Mazda" , "CX-5" , 392919234 , null);
    Vehicle[] tab = new Vehicle[]{v1 , v2 , v3 , v4 , v5};
    //System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    //System.out.println("|" + "brandName" + "|" + "modelName" + "|" + "price" + "|" + "fuelType" + "|" +  "Mileage");
    //for(Vehicle t : tab)
    //{
    //   tabulardetails(t);
    //}
    //System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    System.out.printf("| %-10s | %-10s | %-10s | %-10s | %-7s |%n", "Brand", "Model", "Price", "Fuel", "Mileage");
    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        
    for (Vehicle t : tab)
    {
        tabulardetails(t);
    }
    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }


   


}
