package pboevoy;

public class ClearScreen {
    
     public void clrscr() {  
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } 
        catch(Exception e) {
            System.out.println(e);
        }
    } 
}
