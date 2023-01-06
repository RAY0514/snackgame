import javax.swing.*;

public class gameframe  extends JFrame {

    gameframe(){
       /*
        gamepanel panel=new gamepanel();
        this.add(panel);
        可以寫成 this.add(new gamepanel());
        */
        this.add(new gamepanel());
        this.setTitle("snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();  //pack()方法是要通知frame將其尺寸設定為可以將其內部所有的元件緊致包起來的大小
        this.setVisible(true);
        this.setLocationRelativeTo(null);//出現在中間

    }
}
