import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
 
@SuppressWarnings("serial")
class Surface extends JPanel
        implements ActionListener {
    private Ellipse2D.Float[] ellipses;
    private double esize[];
    private float estroke[];
    private double maxSize = 0;
    private final int NUMBER_OF_ELLIPSES = 18;
    private final int DELAY = 30;
    private final int INITIAL_DELAY = 100;    
    private Timer timer;
 
    public Surface() {
 
        initSurface();
        initEllipses();
        initTimer();
    }
 
    private void initSurface() {
        ellipses = new Ellipse2D.Float[NUMBER_OF_ELLIPSES];
        esize = new double[ellipses.length];
        estroke = new float[ellipses.length];
    }
 
    private void initEllipses() {
 
        int spreadWidth = 300;
        int spreadLength = 200;
 
        maxSize = spreadWidth / 10;
 
        for (int i = 0; i < ellipses.length; i++) {
 
            ellipses[i] = new Ellipse2D.Float();
            posRandEllipses(i, maxSize * Math.random(), spreadWidth, spreadLength);
        }
    }
 
    private void initTimer() {
 
        timer = new Timer(DELAY, this);
        timer.setInitialDelay(INITIAL_DELAY);
        timer.start();
    }
 
    private void posRandEllipses(int i, double size, int spreadWidth, int spreadLength) {
 
        esize[i] = size;
        estroke[i] = 1.0f;
        double x = Math.random() * (spreadWidth - (maxSize / 2));
        double y = Math.random() * (spreadLength - (maxSize / 2));
        ellipses[i].setFrame(x, y, size, size);
    }
 
    private void doStep(int w, int h) {
 
        for (int i = 0; i < ellipses.length; i++) { 
            estroke[i] += 0.025f; 
            esize[i]++; 
            if (esize[i] > maxSize) {
                posRandEllipses(i, 1, w, h);
            } else {
                ellipses[i].setFrame(ellipses[i].getX(), 
                                     ellipses[i].getY(),
                                     esize[i], 
                                     esize[i]);
            }
        }
    }
 
    private void drawEllipses(Graphics2D g2d) {
 
        for (int i = 0; i < ellipses.length; i++) {
            g2d.setColor(Color.darkGray);
            g2d.setStroke(new BasicStroke(estroke[i]));
            g2d.draw(ellipses[i]);
        }
    }
 
    private void doDrawing(Graphics g) {
 
        Graphics2D g2d = (Graphics2D) g.create();
 
        RenderingHints rh
                = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
 
        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
 
        g2d.setRenderingHints(rh);
 
        Dimension size = getSize();
        doStep(size.width, size.height);
        drawEllipses(g2d);
         
        g2d.dispose();
    }
 
    @Override
    public void paintComponent(Graphics g) {
 
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0, 0, Color.decode("#fff1eb"), 0, getHeight(), Color.decode("#13547a"));
        g2.setPaint(gp);
        g2.fillRoundRect(0,0, getWidth(), getHeight(), 0, 0);
        g2.fillRect(getWidth() , 0, getWidth(), getHeight());
        g2.fillRect(0, getHeight() , getWidth(), getHeight());
        doDrawing(g);
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
 
public class RainBubbles extends JFrame {
 
    public RainBubbles() {
        initUI();
    }
 
    private void initUI() {
 
        add(new Surface());
         
        setTitle("Bubbles");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
    }
 
    public static void main(String[] args) {
 
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
 
                RainBubbles ex = new RainBubbles();
                ex.setVisible(true);
            }
        });
    }
}