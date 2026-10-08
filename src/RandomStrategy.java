import java.util.Random;

public class RandomStrategy implements Strategy
{
    Random rnd = new Random();

    @Override
    public String getMove(String playerMove)
    {
        String computerMove = "";
        int choice = rnd.nextInt(3);

        switch (choice)
        {
            case 0:
                computerMove = "R";
                break;
            case 1:
                computerMove = "P";
                break;
            case 2:
                computerMove = "S";
                break;
        }

        return computerMove;
    }
}