package learn;
public class Animal implements test,Alsotest{
    
    public void eat(){
        System.out.println("------eat------");
    }

    public void sleep(){
        System.out.println("------sleep------");
    }

    public static void main(String[] args){
        Animal animal = new Animal();
        animal.eat();;
        animal.sleep();
    }
}
