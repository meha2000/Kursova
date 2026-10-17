import logik.Random_example_generation;
import windows.Author;
import windows.Menu;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Random_example_generation randomExampleGeneration = new Random_example_generation(2);

        System.out.println(randomExampleGeneration.getExample());
        System.out.println(randomExampleGeneration.getNamber_1());
        System.out.println(randomExampleGeneration.getNamber_2());
        System.out.println(randomExampleGeneration.getZnak());

        JFrame myMenu = new Menu();
        myMenu.setVisible(true);
        //JFrame proAutor = new Author();
        //proAutor.setVisible(true);

    }
}