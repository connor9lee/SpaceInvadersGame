import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.*;

public class RunGame {

    private JFrame frame;
    int fW = 600;
    int fH = 600;


    public RunGame() {

        frame = new JFrame("Space Invaders!");
        frame.setSize(fW, fH);
        frame.setPreferredSize(frame.getSize());
        frame.add(new showGraphics(frame.getSize()));
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }


    public static void main(String[] argv) {
        new RunGame();

    }

    public static class showGraphics extends JPanel implements Runnable, MouseListener, KeyListener {

        private Thread animation;

        int xAxis = 0;
        int yAxis = 0;
        Ship s;
        Bullet bu;
        Alien[][] a = new Alien[3][10];
        boolean gameOn = false;
        boolean gameOver = false;

        public showGraphics(Dimension dimension) {
            s = new Ship(277,500,45,45,10,"spaceship2.png");
            bu = new Bullet(285,500,30,40,25,"bullet.png");
            int x = 10;
            int y = 10;
            for(int r = 0; r<a.length; r++){
                for (int c = 0; c<a[0].length; c++){
                    a[r][c] = new Alien(x,y,28,18,3,"alien2.png");
                    x += 35;
                }
                x=10;
                y += 25;
            }

            addMouseListener(this);
            addKeyListener ( this ) ;
            setFocusable(true);
            setSize(dimension);
            setPreferredSize(dimension);
            setDoubleBuffered(true);

            if (animation == null) {
                animation = new Thread(this);
                animation.start();
            }

        }
        public void reset(){
            xAxis = 30;
            yAxis = 30;

            a = new Alien[3][10];
            gameOn = false;
            gameOver = false;
            s = new Ship(277,500,45,45,10,"spaceship2.png");
            bu = new Bullet(285,500,30,40,25,"bullet.png");
            int x = 10;
            int y = 10;
            for(int r = 0; r<a.length; r++) {
                for (int c = 0; c < a[0].length; c++) {
                    a[r][c] = new Alien(x, y, 28, 18, 3, "alien2.png");
                    x += 35;
                }
                x = 10;
                y += 25;
            }
        }
        @Override

        public void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D)  g;
            Dimension d = getSize();

            g2.setColor(Color.black);
            g2.fillRect(0, 0, d.width, d.height);




            if (gameOn == true) {
                moveAlien();
                s.move(0);
                bu.move(0);
            }else if(gameOver==true) {
                g2.setColor(Color.red);
                //g2.fillRect(0, 0, d.width, d.height);
                g.drawString("Game Over", 100,100);
                g.drawString("Press P to restart", 100, 200);
            }else{
                g2.setColor(Color.white);
                g.drawString("Press P to start game", 10, 200);
            }

            //winDetect();
            hitDetect();
            s.draw(g2);
            bu.draw(g2);

            for(int r = 0; r<a.length; r++){
                for (int c = 0; c<a[0].length; c++){
                    if(a[r][c].isVis)
                        a[r][c].draw(g2);
                }
            }

        }

        /*public void winDetect(){
            for(int r = 0; r<a.length; r++){
                for (int c = 0; c<a[0].length; c++) {
                    if (!a[r][c].isVis) {
                    }
                }
            }
        }*/
        public void hitDetect(){

            for(int r = 0; r<a.length; r++){
                for (int c = 0; c<a[0].length; c++){

                    if (a[r][c].isVis == true && bu.getX() + bu.getWidth() >= a[r][c].getX() &&
                    bu.getX() <= a[r][c].getX() + a[r][c].getWidth() &&
                    bu.getY() + bu.getHeight() >= (a[r][c].getY()) &&
                    bu.getY() <= a[r][c].getY() + a[r][c].getHeight()) {

                            a[r][c].isVis=false; 
                            bu.x = -30;
                    }


                }}

        }

        public void moveAlien(){
            for(int r = 0; r<a.length; r++){
                for (int c = 0; c<a[0].length; c++){
                    if(a[r][c].moveLeft)
                        a[r][c].setX(a[r][c].getX()-a[r][c].getSpeed());

                    if(a[r][c].moveRight){
                        a[r][c].setX(a[r][c].getX()+a[r][c].getSpeed());
                    }
                }}

            for(int r = 0; r<a.length; r++){
                for (int c = 0; c<a[0].length; c++){

                    if(a[r][c].getX()>600){
                        moveLeftRight(1);
                        break;
                    }

                    if(a[r][c].getX()<0){
                        moveLeftRight(2);
                        break;
                    }
                }}

        }

        public void moveLeftRight(int d){
            for(int r = 0; r<a.length; r++){
                for (int c = 0; c<a[0].length; c++){
                    if(d==1){
                        a[r][c].moveLeft=true;
                        a[r][c].moveRight=false;
                    }else{
                        a[r][c].moveLeft=false;
                        a[r][c].moveRight=true;
                    }

                    a[r][c].setY(a[r][c].getY()+10);

                    if(a[r][c].getY()>500){
                        gameOn=false;
                        gameOver=true;
                    }

                }}
        }

        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void keyTyped ( KeyEvent e ){  

        }  

        public void keyPressed ( KeyEvent e){
            int k = e.getKeyCode();
            s.setLeftRight(k);
            if(k==80){
                reset();
                gameOn=true;
            }
            if (k==79){
                gameOver=true;
            }
            if(k==32)  {
                bu.goUp=true;
                bu.setX(s.getX()+8) ;
                bu.setY(s.getY() );
            }
        }  

        public void keyReleased ( KeyEvent e ){  
            int k = e.getKeyCode();
            s.stop();

        }  
        public void run() {
            long beforeTime, timeDiff, sleep;
            beforeTime = System.currentTimeMillis();
            int animationDelay = 37;
            long time = System.currentTimeMillis();
            while (true) {
                repaint();
                try {
                    time += animationDelay;
                    Thread.sleep(Math.max(0, time - System.currentTimeMillis()));
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }

    }

}