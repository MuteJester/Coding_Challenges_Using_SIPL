import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

public class SIPL_Chaos_Game {
	public static Color_Palette CSET = new Color_Palette();
	public static Math_Toolbox tlb = new Math_Toolbox();

	public static int canvas_width = 900;
	public static int canvas_height = 700;
	public static Pixel Background_Color = CSET.Black;
	
	public static void main(String[] args) {
		Image canvas = new Image();
		canvas.Load_Blank_Canvas(canvas_height, canvas_width, Background_Color);
		Point p1 = new Point((int)tlb.random_int_in_range(2, canvas_width-3),(int)tlb.random_int_in_range(1, canvas_height-1),0),
				p2= new Point((int)tlb.random_int_in_range(2, canvas_width-3),(int)tlb.random_int_in_range(1, canvas_height-1),0),
				p3= new Point((int)tlb.random_int_in_range(2, canvas_width-3),(int)tlb.random_int_in_range(1, canvas_height-1),0),
				p4= new Point((int)tlb.random_int_in_range(2, canvas_width-3),(int)tlb.random_int_in_range(1, canvas_height-1),0);
		SIPL_Window frame = new SIPL_Window(canvas.IMG);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//canvas.Draw_Circle((int)p1.x, (int)p1.y, 2, CSET.Dark_Red,"Fill");
		//canvas.Draw_Circle((int)p2.x, (int)p2.y, 2, CSET.Dark_Red,"Fill");
		//canvas.Draw_Circle((int)p3.x, (int)p3.y, 2, CSET.Dark_Red,"Fill");
		canvas.Set_Color((int)p1.y, (int)p1.x, CSET.Lime);
		canvas.Set_Color((int)p2.y, (int)p2.x, CSET.Lime);
		canvas.Set_Color((int)p3.y, (int)p3.x, CSET.Lime);
		canvas.Set_Color((int)p4.y, (int)p4.x, CSET.Lime);


		Point move = new Point(0,0,0);
		double rand = 0;
		 double nx=0,ny=0;
		while(true) {
			
			//canvas.Draw_Circle((int)move.x, (int)move.y, 2, CSET.Dark_Red,"Fill");
			 rand = tlb.random_double_in_range(0.0, 1);
			 
			
			 if(rand < 0.01) {
				 nx=0;
				 ny = 0.16*ny;
			 }
			 else if(rand < 0.86) {
			 
				 nx = 0.85 * nx + 0.04 * ny;
				 ny = (-0.04 * nx + 0.85 *ny) + 1.6;
			 }
			 else if ( rand < 0.93) {
			
				 nx = 0.20*nx + (-0.26 *ny);
				 ny = 0.23 * nx + 0.22 * ny + 1.6;
			 }
			 else {
				 nx = -0.15 *nx + 0.28 * ny;
				 ny = 0.26 * nx + 0.24 * ny + 0.44;
				

			}
			 
			 //System.out.println(nx + " "  + ny);
			 move.x = tlb.Remap((float)nx, (float)(-2.1820),  (float)2.6558,  canvas_width,   0);
			 move.y = tlb.Remap((float)ny,  0,  (float) 9.9983,  canvas_height,   0);

			  canvas.Set_Color((int)move.y, (int)move.x,  CSET.Dark_Olive_Green);
			  
				frame.Refresh_Frame(canvas.IMG);

		}
	}

}
