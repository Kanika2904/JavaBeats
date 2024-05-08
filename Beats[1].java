import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Beats extends JFrame implements ActionListener {
    private JButton playButton, pauseButton, resumeButton, nextButton, previousButton;
    private JLabel currentlyPlayingLabel;
    private ArrayList<String> playlist;
    private int currentSongIndex;
    private Player player; // JLayer Player

    public Beats() {
           setTitle("Music Player");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        ImageIcon icon = new ImageIcon("music_icon.png");
        setIconImage(icon.getImage());

        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        resumeButton = new JButton("Resume");
        nextButton = new JButton("Next");
        previousButton = new JButton("Previous");

        currentlyPlayingLabel = new JLabel("Currently Playing: ");

        playButton.addActionListener(this);
        pauseButton.addActionListener(this);
        resumeButton.addActionListener(this);
        nextButton.addActionListener(this);
        previousButton.addActionListener(this);

        add(playButton);
        add(pauseButton);
        add(resumeButton);
        add(nextButton);
        add(previousButton);
        add(currentlyPlayingLabel);

        playlist = new ArrayList<>();
        playlist.add("C:\\Users\\Dell\\Downloads\\roop tera.mp3");
        playlist.add("C:\\Users\\Dell\\Downloads\\one direction.mp3");
        playlist.add("C:\\Users\\Dell\\Downloads\\give me some sunshine.mp3");
        playlist.add("C:\\Users\\Dell\\Downloads\\gul.mp3");
        playlist.add("C:\\Users\\Dell\\Downloads\\uthe sabke kadam.unknown");

        currentSongIndex = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            playSong(playlist.get(currentSongIndex));
        } else if (e.getSource() == pauseButton) {
            if (player != null) {
                player.close();
            }
        } else if (e.getSource() == resumeButton) {
            playSong(playlist.get(currentSongIndex));
        } else if (e.getSource() == nextButton) {
            currentSongIndex = (currentSongIndex + 1) % playlist.size();
            playSong(playlist.get(currentSongIndex));
        } else if (e.getSource() == previousButton) {
            currentSongIndex = (currentSongIndex - 1 + playlist.size()) % playlist.size();
            playSong(playlist.get(currentSongIndex));
        }
    }
 private void playSong(String filePath) {
        if (player != null) {
            player.close();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            player = new Player(fileInputStream);
            new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }).start();
            currentlyPlayingLabel.setText("Currently Playing: " + filePath);
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> {
            Beats player = new Beats();
            player.setVisible(true);
        });
    }
}
