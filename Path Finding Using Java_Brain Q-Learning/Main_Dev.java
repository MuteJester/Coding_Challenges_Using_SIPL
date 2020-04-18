import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main_Dev {

	public static void main(String[] args) throws IOException {
		Java_Brain brain = new Java_Brain();
		brain.Load_CSV_File("insurance.csv");
		double mw = Double.parseDouble(JOptionPane.showInputDialog("Input Map Size  (Recomended <100 ):"));

		PF_QL_Game game = new PF_QL_Game((int)mw, (int)mw);
		Point[] holes=new Point[(int)(mw*1.3)];
		for(int i=0;i<(int)(mw*1.3);i++) {
			holes[i] = new Point(Math_Toolbox.random_int_in_range((int)((mw/100)*20), (int)((mw/100)*85)),Math_Toolbox.random_int_in_range((int)((mw/100)*20), (int)((mw/100)*85)),Math_Toolbox.random_int_in_range((int)((mw/100)*20), (int)((mw/100)*85)));
		}
		
		for(int i=0;i<(int)(mw*1.3);i++) {
			game.Set_Hole_Position((int)holes[i].x, (int)holes[i].y);
		}
		int target_x = Math_Toolbox.random_int_in_range((int)((mw/100)*20), (int)((mw/100)*85));
		int target_y = Math_Toolbox.random_int_in_range((int)((mw/100)*20), (int)((mw/100)*85));
		

		game.Set_Target_Position(target_x, target_y);
		game.Set_Start_Position(0, 0);
		double lr = Double.parseDouble(JOptionPane.showInputDialog("Input Leaning Rate: x (0<x<1)"));
		double dr = Double.parseDouble(JOptionPane.showInputDialog("Input Discount Rate: y (0<y<1)" ));


		Q_Learner agent = new Q_Learner((int)(mw * mw), 4, lr, 0.97);
		agent.Q_Table.print_Matrix();
		int action, state;
		int won = 0, lost = 0;
		SIPL_Window sw = new SIPL_Window(game.get_Visual().IMG);
		sw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Scanner s = new Scanner(System.in);
		while(true) {
			sw.Refresh_Frame(game.get_Visual().IMG);
		
			//game.Show_In_Console();
			action = agent.Get_Action((game.Pos_Y) * game.Field_Height + game.Pos_X);
			state = (game.Pos_Y) * game.Field_Height + game.Pos_X;
			game.Move(action + 1);

			if (game.is_Alive != true || game.Win != false) {
				if (game.is_Alive == false) {
					agent.Train(state, action, (game.Pos_Y) * game.Field_Height + game.Pos_X, -10, false);
					//System.out.println("____LOOOOSEE____");
					lost++;

				} else if (game.Win == true) {
					agent.Train(state, action, (game.Pos_Y) * game.Field_Height + game.Pos_X, 10.0, true);
					System.out.println("________WIN____");
					System.out.println("Epsilon: " + agent.Epsilon);

					won++;
				}
				game = new PF_QL_Game((int)mw, (int)mw);
				for(int i=0;i<(int)(mw*1.3);i++) {
					game.Set_Hole_Position((int)holes[i].x, (int)holes[i].y);
				}
		
				game.Set_Target_Position(target_x, target_y);
				game.Set_Start_Position(0, 0);

			}

			agent.Train(state, action, (game.Pos_Y) * game.Field_Height + game.Pos_X, 0, false);

			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (InterruptedException e) { // TODO Auto-generated catch block e.printStackTrace(); } }
				game.Show_In_Console();

				System.out.println("GAMES WON = " + won);
				System.out.println("GAMES LOST = " + lost);

			}
		}
	}

}
