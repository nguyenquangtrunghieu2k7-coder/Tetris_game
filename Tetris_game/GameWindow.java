import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JFrame {

    private static final int WINDOW_W = 800;
    private static final int WINDOW_H = 700;

    private final Game game;
    private final GameEngine engine;
    private final Renderer renderer;
    private final GamePanel panel;

    // =====================================================
    // Cờ chống dội phím (Anti-Key Repeat Flags)
    // =====================================================
    private boolean spaceHeld = false;
    private boolean holdKeyHeld = false;
    private boolean upHeld = false;
    private boolean zHeld = false;
    private boolean aHeld = false;

    public GameWindow(Game game, GameEngine engine) {

        super("Modern Tetris");

        this.game = game;
        this.engine = engine;

        renderer = new Renderer();
        panel = new GamePanel();

        panel.setPreferredSize(new Dimension(WINDOW_W, WINDOW_H));
        panel.setBackground(new Color(18, 18, 18));
        panel.setFocusable(true);

        add(panel);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setupInput();

        setVisible(true);

        panel.requestFocusInWindow();
    }

    // =====================================================
    // Refresh (được GameEngine gọi mỗi frame)
    // =====================================================

    public void refresh() {
        panel.repaint();
    }

    // =====================================================
    // Keyboard
    // =====================================================

    private void setupInput() {

        panel.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {

                    case KeyEvent.VK_LEFT:
                        engine.pressLeft();
                        break;

                    case KeyEvent.VK_RIGHT:
                        engine.pressRight();
                        break;

                    case KeyEvent.VK_DOWN:
                        engine.softDrop();
                        break;

                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_X:
                        if (!upHeld) {
                            engine.rotateCW();
                            upHeld = true;
                        }
                        break;

                    case KeyEvent.VK_Z:
                        if (!zHeld) {
                            engine.rotateCCW();
                            zHeld = true;
                        }
                        break;

                    case KeyEvent.VK_A:
                        if (!aHeld) {
                            engine.rotate180();
                            aHeld = true;
                        }
                        break;

                    case KeyEvent.VK_SPACE:
                        if (!spaceHeld) {
                            engine.hardDrop();
                            spaceHeld = true;
                        }
                        break;

                    case KeyEvent.VK_C:
                    case KeyEvent.VK_SHIFT:
                        if (!holdKeyHeld) {
                            engine.hold();
                            holdKeyHeld = true;
                        }
                        break;

                    case KeyEvent.VK_R:
                        if (game.isGameOver()) {
                            engine.restart();
                        }
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

                switch (e.getKeyCode()) {

                    case KeyEvent.VK_LEFT:
                        engine.releaseLeft();
                        break;

                    case KeyEvent.VK_RIGHT:
                        engine.releaseRight();
                        break;

                    // Nhả phím thì reset cờ để cho phép nhấn lần tiếp theo
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_X:
                        upHeld = false;
                        break;

                    case KeyEvent.VK_Z:
                        zHeld = false;
                        break;

                    case KeyEvent.VK_A:
                        aHeld = false;
                        break;

                    case KeyEvent.VK_SPACE:
                        spaceHeld = false;
                        break;

                    case KeyEvent.VK_C:
                    case KeyEvent.VK_SHIFT:
                        holdKeyHeld = false;
                        break;
                }
            }
        });
    }

    // =====================================================
    // Render panel
    // =====================================================

    private class GamePanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            renderer.render((Graphics2D) g, game);
        }
    }
}