import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

public class SIPL_GOL {
	public static Color_Palette CSET = new Color_Palette();
	public static Math_Toolbox tlb = new Math_Toolbox();

	public static int Starting_Random_Values = 13000;
	public static int canvas_width = 600;
	public static int canvas_height = 600;
	public static Pixel Background_Color = CSET.Azure;
	


	public static void main(String[] args) {
		Image Canvas = new Image();
		Canvas.Load_Blank_Canvas(canvas_height, canvas_width, Background_Color);
		SIPL_Window sw = new SIPL_Window(Canvas.IMG);
		sw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		for(int i = 0;i<Starting_Random_Values;i++) {
			int x = tlb.random_int_in_range(0, canvas_width-1);
			int y = tlb.random_int_in_range(0, canvas_height-1);
			Canvas.Set_Color(y, x, CSET.Black);

		}
		Canvas.Update_Pixel_Matrix();
		sw.Refresh_Frame(Canvas.IMG);
		
		while(true) {
			
			for(int i = 1;i<canvas_height-1;i++) {
				for(int j = 1;j<canvas_width-1;j++) {
						int live_neighbours=0;
						if(Canvas.Pixel_Matrix[i-1][j-1].is_equal(CSET.Black)) {
							live_neighbours++;
						}
						if(Canvas.Pixel_Matrix[i-1][j].is_equal(CSET.Black)) {
							live_neighbours++;

						}
						if(Canvas.Pixel_Matrix[i-1][j+1].is_equal(CSET.Black)) {
							live_neighbours++;

						}
						if(Canvas.Pixel_Matrix[i][j-1].is_equal(CSET.Black)) {
							live_neighbours++;

						}
						if(Canvas.Pixel_Matrix[i][j+1].is_equal(CSET.Black)) {
							live_neighbours++;

						}
						if(Canvas.Pixel_Matrix[i+1][j-1].is_equal(CSET.Black)) {
							live_neighbours++;

						}
						if(Canvas.Pixel_Matrix[i+1][j].is_equal(CSET.Black)) {
							live_neighbours++;
							
						}
						if(Canvas.Pixel_Matrix[i+1][j+1].is_equal(CSET.Black)) {
							live_neighbours++;

						}

					if(Canvas.Pixel_Matrix[i][j].is_equal(CSET.Black)) {
						if(live_neighbours < 2) {
							//Canvas.Pixel_Matrix[i][j] = new Pixel(CSET.Azure);
							Canvas.Set_Color(i, j, CSET.Azure);
							live_neighbours=0;

							//sw.Refresh_Frame(Canvas.IMG);
						}
						else if(live_neighbours ==2 && live_neighbours ==3) {
							live_neighbours=0;

						}else if(live_neighbours > 3){
							//Canvas.Pixel_Matrix[i][j] = new Pixel(CSET.Azure);
							Canvas.Set_Color(i, j, CSET.Azure);
							//sw.Refresh_Frame(Canvas.IMG);
							live_neighbours=0;

						}
					}
					else {
						if(live_neighbours==3) {
							//Canvas.Pixel_Matrix[i][j] = new Pixel(CSET.Black);
							Canvas.Set_Color(i, j, CSET.Black);
							
							live_neighbours=0;
							//sw.Refresh_Frame(Canvas.IMG);

						}

					}	
					
				}
			}
			Canvas.Update_Pixel_Matrix();

			
			
			try {
				TimeUnit.MILLISECONDS.sleep(35);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sw.Refresh_Frame(Canvas.IMG);
		}
	}

}
