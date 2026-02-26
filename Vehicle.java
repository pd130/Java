public class Vehicle
{
   public String brandName;
   public String modelName;
   public String color;
   public double price;
   public String fuelType;
   public int sittingcapacity;
   public double load;
   public double speed;
   private String mfgcode;
   
   public void setmfgcode(String mcode)
   {
     mfgcode = mcode;
   }

   public String getMfgCode()
   {
      return mfgcode;
   }
   
   public void start()
   {
      System.out.println("This vehicle has started.");
   }

   public void drive()
   {
      System.out.println("Vehicle can now be driven.");
   }
   
   public void stop()
   {
      System.out.println("You can now kill the engine.");
   }

   public float changespd(float initialspd)
   {
     float newsp;
     if(initialspd < 10)
     {
        newsp = initialspd + 20;
     }

     else
    {
       newsp = initialspd - 20;
    }
    return newsp;
   }

   public void lowfuel(int fuelintnk)
   {
      if(fuelintnk < 2)
      {
        System.out.println("Fuel is low kindly refuel");
      }

   }

   public float mileage(String fuelType)
   {
     if (null == fuelType)
     {
         return 0;
     }
     else switch(fuelType) 
     {
           case "Petrol":
               return 15;
           case "Diseal":
               return 20;
           case "Electric":
               return 300;
           default:
               return 25;
       }
   }
   // Default Constructor
   public Vehicle()
   {
      modelName = "Class C";
      brandName = "Mercedes";
      price = 1000000.0;
      color = "white";
      mfgcode = "LMNO789";
      sittingcapacity = 2;
      load = 150;
      speed = 300;
      fuelType = "Petrol";
   }

   //Parameterized Constructor
   public Vehicle(String bname , String mname , double price , String color , String mfgcode)
   {
      brandName = bname;
      modelName = mname;
      this.price = price;
      this.color = color;
      this.mfgcode = mfgcode;
   }
   
   public Vehicle(String bname , String mname , double price , String fuel)
   {
      brandName = bname;
      modelName = mname;
      this.price = price;
      fuelType = fuel;
   }
   //Copy constructor
   public Vehicle(Vehicle v)
   {
      brandName = v.brandName;
      modelName = v.modelName;
      price = v.price;
      color = v.color;
      mfgcode = v.mfgcode;
      sittingcapacity   = v.sittingcapacity;
      load = v.load;
      speed = v.speed;
      fuelType = v.fuelType;
   }

   
}