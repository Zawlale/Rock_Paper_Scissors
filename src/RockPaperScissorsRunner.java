import javax.swing.SwingUtilities;

/**
 * Starts the Rock Paper Scissors game.
 */
public class RockPaperScissorsRunner
{
    /**
     * Opens the game window on Swing's event thread.
     *
     * @param args command line inputs, not used here
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            RockPaperScissorsFrame frame = new RockPaperScissorsFrame();
        });
    }
}