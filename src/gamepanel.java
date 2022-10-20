import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


//extends 是繼承某個類, 繼承之後可以使用父類的方法, 也可以重寫父類的方法; implements 是實現多個介面, 介面的方法一般為空的, 必須重寫才能使用
public class gamepanel extends JPanel implements ActionListener {




    static final  int SCREEN_WIDTH = 600;
    static final  int SCREEN_HEIGHT = 600;
    static final  int UNIT_SIZE = 25;//單位尺寸
    static final  int GAME_UNITS =(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE ;
    static final  int DELAY = 75;
    //蛇的身體
    final  int x[]=new int[GAME_UNITS];
    final  int y[]=new int[GAME_UNITS];
    int bodyParts=6;//蛇的身體初始數量
    int applesEaten;//吃蘋果的數量
    int applleX;
    int applleY;
    char direction='R';//初始方向
    boolean running=false;
    Timer timer;//排程、定時、週期性執行工作任務
    Random random;




    gamepanel(){

        random= new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);//背景顏色
        this.setFocusable(true);
        this.addKeyListener(new MykeyAdapter());//鍵盤
        starGame();




    }
    public  void starGame() {

            newApple();
            running = true;
            timer = new Timer(DELAY, this);
            timer.start();


    }
    //圖形類基本的畫法，主要提供有畫線段、畫法、畫法、畫法、畫帶的圖形、畫畫幾何、畫圓、畫弧、畫法等。
    public void paintComponent (Graphics g){

        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){

        if (running) {
            //畫網格
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);//直線(起始X,起始Y,終點X,終點Y)
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);//橫線
            }

            //蘋果圖案
            g.setColor(Color.red);
            g.fillOval(applleX, applleY, UNIT_SIZE, UNIT_SIZE);

            //蛇身體
            for (int i = 0; i < bodyParts; i++) {
                        if (i == 0) {//蛇頭
                             g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);//位置, 大小
                } else {//身體
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);


                }
            }
            //分數
            g.setColor(Color.red);
            g.setFont(new Font("Ink free",Font.BOLD,20));
            FontMetrics metrics=getFontMetrics(g.getFont());
            g.drawString("score:"+applesEaten,(SCREEN_WIDTH-metrics.stringWidth("score:"+applesEaten))/2,g.getFont().getSize());


        }
        else{
            gameOver(g);
        }
    }


    public void newApple(){
        //蘋果位置
        applleX=random.nextInt((int) (SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;//隨機X座標
        applleY=random.nextInt((int) (SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;//隨機Y座標

    }
    public void move(){
        for (int i= bodyParts ;i>0;i-- ){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch (direction){
            case 'U'://往上
                y[0]=y[0]-UNIT_SIZE;
                break;
            case 'D'://往下
                y[0]=y[0]+UNIT_SIZE;
                break;
            case 'L'://往左
                x[0]=x[0]-UNIT_SIZE;
                break;
            case 'R'://往右
                x[0]=x[0]+UNIT_SIZE;
                break;
        }

    }
    public void chechApple(){
        if((x[0]==applleX) && (y[0]==applleY)) {
            bodyParts++;
            applesEaten++;
            newApple();//調用newApple()生成新蘋果

        }
    }
    public void checkCollisions(){
        //檢查如果頭撞到身體
        for(int i =bodyParts;i>0;i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
            //檢查頭撞到左邊邊界
            if (x[0] < 0){
                running=false;
            }
            //檢查頭撞到右邊邊界
            if (x[0] > SCREEN_WIDTH){
                running=false;
            }
            //檢查頭撞到上面邊界
            if (y[0] < 0){
                running=false;
            }
            //檢查頭撞到下面邊界
            if (y[0] > SCREEN_HEIGHT){
                running=false;
            }

            if(!running){
                timer.stop();
            }






        }




    public void gameOver(Graphics g){
        //分數
        g.setColor(Color.red);
        g.setFont(new Font("Ink free",Font.BOLD,20));
        FontMetrics metrics1=getFontMetrics(g.getFont());
        g.drawString("score:"+applesEaten,(SCREEN_WIDTH-metrics1.stringWidth("score:"+applesEaten))/2,g.getFont().getSize());


        //遊戲結束文字
        g.setColor(Color.red);
        g.setFont(new Font("Ink free",Font.BOLD,75));
        FontMetrics metrics2 =getFontMetrics(g.getFont());
        g.drawString("Game Over",(SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);

    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            chechApple();
            checkCollisions();

        }

        repaint();
    }




    public class MykeyAdapter extends KeyAdapter{//在按鍵被按下時做一些事情

        @Override
        public void keyPressed(KeyEvent e){
            //不讓蛇可以180度轉
            switch (e.getKeyCode()){//檢查e按鍵事件
                case KeyEvent.VK_LEFT ://非數字鍵盤左箭頭鍵的常量
                    if (direction!='R'){
                        direction='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT :
                    if (direction!='L'){
                        direction='R';
                    }
                    break;
                case KeyEvent.VK_UP :
                    if (direction!='D'){
                        direction='U';
                    }
                    break;
                case KeyEvent.VK_DOWN :
                    if (direction!='U'){
                        direction='D';
                    }
                    break;


            }



        }

    }



}


