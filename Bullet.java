import java.awt.Graphics;

public class Bullet extends Character
{

    public Bullet()
    {
        super();
    }
    boolean goUp;
    public Bullet(int x, int y, int w, int h, int s, String u)
    {
        super(x, y, w, h, s, "bullet.png");

    }
    public void draw(Graphics window){
        window.drawImage(getImage(),getX(),getY(),getWidth(),getHeight(),null);
    }

    public  void move(int d){

        if(getY()<0){
            goUp=false;
            setY(600);
        }

        if(goUp)
            setY(getY()-getSpeed());
    }


    public void setLeftRight(int d){
        if(d==37){
            moveLeft = true;
        }

        if(d==39){
            moveRight = true;
        }

    }

    public void stop(){
        moveLeft=false;
        moveRight=false;

    }

}
