import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RockPaperScissorsFrame extends JFrame
{
    JPanel mainPnl;
    JPanel topPnl;
    JPanel controlPnl;
    JPanel statsPnl;
    JPanel displayPnl;

    JLabel titleLbl;

    JButton rockBtn;
    JButton paperBtn;
    JButton scissorsBtn;
    JButton quitBtn;

    JTextField playerWinsTF;
    JTextField computerWinsTF;
    JTextField tiesTF;

    JTextArea displayTA;
    JScrollPane scroller;

    int playerWins = 0;
    int computerWins = 0;
    int ties = 0;

    int rockCnt = 0;
    int paperCnt = 0;
    int scissorsCnt = 0;

    String lastPlayerMove = "";

    Random rnd = new Random();

    Strategy cheat = new Cheat();
    Strategy random = new RandomStrategy();
    Strategy leastUsed = new LeastUsed();
    Strategy mostUsed = new MostUsed();
    Strategy lastUsed = new LastUsed();

    public RockPaperScissorsFrame()
    {
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout(10, 10));
        mainPnl.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10));

        topPnl = new JPanel();
        topPnl.setLayout(new BorderLayout(10, 10));

        titleLbl = new JLabel("Rock Paper Scissors", JLabel.CENTER);
        titleLbl.setFont(new Font("SansSerif", Font.BOLD, 28));
        topPnl.add(titleLbl, BorderLayout.NORTH);

        createControlPanel();
        topPnl.add(controlPnl, BorderLayout.CENTER);

        createStatsPanel();
        topPnl.add(statsPnl, BorderLayout.SOUTH);

        mainPnl.add(topPnl, BorderLayout.NORTH);

        createDisplayPanel();
        mainPnl.add(displayPnl, BorderLayout.CENTER);

        add(mainPnl);

        setTitle("Rock Paper Scissors Game");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createControlPanel()
    {
        controlPnl = new JPanel();
        controlPnl.setLayout(new GridLayout(1, 4, 10, 10));
        controlPnl.setBorder(
                new TitledBorder(new EtchedBorder(), "Choose Your Move"));

        rockBtn = createButton("Rock", "src/rock.jpg");
        paperBtn = createButton("Paper", "src/paper.jpg");
        scissorsBtn = createButton("Scissors", "src/scissors.jpg");
        quitBtn = createButton("Quit", "src/quit.jpg");

        ActionListener moveListener = (ActionEvent ae) ->
        {
            String playerMove = "";

            if (ae.getSource() == rockBtn)
            {
                playerMove = "R";
            }
            else if (ae.getSource() == paperBtn)
            {
                playerMove = "P";
            }
            else if (ae.getSource() == scissorsBtn)
            {
                playerMove = "S";
            }

            playRound(playerMove);
        };

        rockBtn.addActionListener(moveListener);
        paperBtn.addActionListener(moveListener);
        scissorsBtn.addActionListener(moveListener);

        quitBtn.addActionListener((ActionEvent ae) -> System.exit(0));

        controlPnl.add(rockBtn);
        controlPnl.add(paperBtn);
        controlPnl.add(scissorsBtn);
        controlPnl.add(quitBtn);
    }

    private JButton createButton(String text, String imageFile)
    {
        JButton button = new JButton(text);
        ImageIcon icon = new ImageIcon(imageFile);

        if (icon.getIconWidth() > 0)
        {
            Image image = icon.getImage().getScaledInstance(
                    70, 70, Image.SCALE_SMOOTH);

            button.setIcon(new ImageIcon(image));
        }

        button.setVerticalTextPosition(JButton.BOTTOM);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(150, 115));

        return button;
    }

    private void createStatsPanel()
    {
        statsPnl = new JPanel();
        statsPnl.setLayout(new GridLayout(1, 6, 10, 10));
        statsPnl.setBorder(
                new TitledBorder(new EtchedBorder(), "Score"));

        playerWinsTF = new JTextField("0", 5);
        computerWinsTF = new JTextField("0", 5);
        tiesTF = new JTextField("0", 5);

        playerWinsTF.setEditable(false);
        computerWinsTF.setEditable(false);
        tiesTF.setEditable(false);

        playerWinsTF.setHorizontalAlignment(JTextField.CENTER);
        computerWinsTF.setHorizontalAlignment(JTextField.CENTER);
        tiesTF.setHorizontalAlignment(JTextField.CENTER);

        statsPnl.add(new JLabel("Player Wins:"));
        statsPnl.add(playerWinsTF);

        statsPnl.add(new JLabel("Computer Wins:"));
        statsPnl.add(computerWinsTF);

        statsPnl.add(new JLabel("Ties:"));
        statsPnl.add(tiesTF);
    }

    private void createDisplayPanel()
    {
        displayPnl = new JPanel();
        displayPnl.setLayout(new BorderLayout());
        displayPnl.setBorder(
                new TitledBorder(new EtchedBorder(), "Game Results"));

        displayTA = new JTextArea(15, 60);
        displayTA.setEditable(false);

        scroller = new JScrollPane(displayTA);
        displayPnl.add(scroller, BorderLayout.CENTER);
    }

    private void playRound(String playerMove)
    {
        int chance = rnd.nextInt(100) + 1;
        Strategy computerStrategy;
        String strategyName;

        if (chance <= 10)
        {
            computerStrategy = cheat;
            strategyName = "Cheat";
        }
        else if (chance <= 30)
        {
            computerStrategy = leastUsed;
            strategyName = "Least Used";
        }
        else if (chance <= 50)
        {
            computerStrategy = mostUsed;
            strategyName = "Most Used";
        }
        else if (chance <= 70)
        {
            if (lastPlayerMove.equals(""))
            {
                computerStrategy = random;
                strategyName = "Random (first round)";
            }
            else
            {
                computerStrategy = lastUsed;
                strategyName = "Last Used";
            }
        }
        else
        {
            computerStrategy = random;
            strategyName = "Random";
        }

        String computerMove = computerStrategy.getMove(playerMove);
        String result;

        if (playerMove.equals(computerMove))
        {
            ties++;
            result = moveName(playerMove) + " matches "
                    + moveName(computerMove) + ". (Tie!";
        }
        else if (playerMove.equals("R") && computerMove.equals("S")
                || playerMove.equals("P") && computerMove.equals("R")
                || playerMove.equals("S") && computerMove.equals("P"))
        {
            playerWins++;
            result = winningMessage(playerMove) + ". (Player wins!";
        }
        else
        {
            computerWins++;
            result = winningMessage(computerMove) + ". (Computer wins!";
        }

        displayTA.append(result + " Computer: " + strategyName + ")\n");

        playerWinsTF.setText("" + playerWins);
        computerWinsTF.setText("" + computerWins);
        tiesTF.setText("" + ties);

        displayTA.setCaretPosition(displayTA.getDocument().getLength());

        switch (playerMove)
        {
            case "R":
                rockCnt++;
                break;
            case "P":
                paperCnt++;
                break;
            case "S":
                scissorsCnt++;
                break;
        }

        lastPlayerMove = playerMove;
    }

    private String moveName(String move)
    {
        String name = "";

        switch (move)
        {
            case "R":
                name = "Rock";
                break;
            case "P":
                name = "Paper";
                break;
            case "S":
                name = "Scissors";
                break;
        }

        return name;
    }

    private String winningMessage(String move)
    {
        String message = "";

        switch (move)
        {
            case "R":
                message = "Rock breaks scissors";
                break;
            case "P":
                message = "Paper covers rock";
                break;
            case "S":
                message = "Scissors cuts paper";
                break;
        }

        return message;
    }

    private String counterMove(String move)
    {
        String computerMove = "";

        switch (move)
        {
            case "R":
                computerMove = "P";
                break;
            case "P":
                computerMove = "S";
                break;
            case "S":
                computerMove = "R";
                break;
        }

        return computerMove;
    }

    private String pickMove(int count)
    {
        String[] choices = new String[3];
        int total = 0;

        if (rockCnt == count)
        {
            choices[total] = "R";
            total++;
        }

        if (paperCnt == count)
        {
            choices[total] = "P";
            total++;
        }

        if (scissorsCnt == count)
        {
            choices[total] = "S";
            total++;
        }

        return choices[rnd.nextInt(total)];
    }

    class LeastUsed implements Strategy
    {
        @Override
        public String getMove(String playerMove)
        {
            int lowest = rockCnt;

            if (paperCnt < lowest)
            {
                lowest = paperCnt;
            }

            if (scissorsCnt < lowest)
            {
                lowest = scissorsCnt;
            }

            String move = pickMove(lowest);
            return counterMove(move);
        }
    }

    class MostUsed implements Strategy
    {
        @Override
        public String getMove(String playerMove)
        {
            int highest = rockCnt;

            if (paperCnt > highest)
            {
                highest = paperCnt;
            }

            if (scissorsCnt > highest)
            {
                highest = scissorsCnt;
            }

            String move = pickMove(highest);
            return counterMove(move);
        }
    }

    class LastUsed implements Strategy
    {
        @Override
        public String getMove(String playerMove)
        {
            return lastPlayerMove;
        }
    }
}