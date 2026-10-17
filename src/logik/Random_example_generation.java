package logik;

public class Random_example_generation {

    private int namber_1;
    private int namber_2;
    private int example;
    private char znak;

    public Random_example_generation(int complexity) {
        example_generation(complexity);
    }

    private void random_namber(int complexity) {
        switch (complexity) {
            case 1: {
                namber_1 = 1 + (int) (Math.random() * ((10 - 1) + 1));
                namber_2 = 1 + (int) (Math.random() * ((10 - 1) + 1));
            }
            break;
            case 2: {
                namber_1 = 1 + (int) (Math.random() * ((25 - 1) + 1));
                namber_2 = 1 + (int) (Math.random() * ((25 - 1) + 1));
            }
            break;
            case 3: {
                namber_1 = 1 + (int) (Math.random() * ((50 - 1) + 1));
                namber_2 = 1 + (int) (Math.random() * ((50 - 1) + 1));
            }
            break;
        }

    }


    private void example_generation(int complexity) {
        random_namber(complexity);
        int z = 1 + (int) (Math.random() * ((4 - 1) + 1));
        switch (z) {
            case 1: {
                znak = '+';
                example = namber_1 + namber_2;
            }
            break;
            case 2: {
                znak = '-';
                example = namber_1 - namber_2;
            }
            break;
            case 3: {
                znak = '*';
                example = namber_1 * namber_2;
            }
            break;
            case 4: {
                znak = '/';
                example = namber_1 * namber_2;
            }
            break;
        }
    }

    public int getNamber_1() {
        return namber_1;
    }

    public int getNamber_2() {
        return namber_2;
    }

    public int getExample() {
        return example;
    }

    public char getZnak() {
        return znak;
    }
}

