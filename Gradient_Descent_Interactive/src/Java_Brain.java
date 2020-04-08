import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

public class Java_Brain {
	public static Math_Toolbox tlb = new Math_Toolbox();
	public static Color_Palette CSET = new Color_Palette();
	public static BufferedReader Reader;
	SPlot plot = new SPlot();
	public ArrayList<ArrayList<String>> CSV_DATA;
	public int Number_Of_Rows=0,Number_Of_Columns=0;
	public Java_Brain() {
		CSV_DATA = new ArrayList<ArrayList<String>>();
		
	}
	
	//Data Set Handling
	public void Load_CSV_File(String CSV_PATH) throws IOException {
		 try {
			Reader = new BufferedReader(new FileReader(CSV_PATH));
			 String line;
			 int max_number_of_Columns = 0;
			 int j =0;
			    while ((line = Reader.readLine()) != null) {
			        String[] values = line.split(",");
			        if(values.length > max_number_of_Columns) {
			        	max_number_of_Columns = values.length;
			        }
			        CSV_DATA.add(new ArrayList<String>());
			        ArrayList ptr = CSV_DATA.get(j);
			        for(int i = 0;i<values.length;i++) {
			        	ptr.add(values[i]);
			        }
			        
			        j++;
			    }
			    Number_Of_Rows=j;
			    Number_Of_Columns=max_number_of_Columns;
			    int mg;
				for(int i = 0 ; i <CSV_DATA.size();i++) {
					ArrayList<String> ptr = CSV_DATA.get(i);
					for(int k =0;k<max_number_of_Columns;k++) {
						mg = ptr.size();
						if(mg < max_number_of_Columns) {
							while(mg < max_number_of_Columns) {
								ptr.add("Null");
								mg++;
							}
						}else {
							break;
						}
						
					}
				}
			    
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error In File Openning");
			e.printStackTrace();
		}
		 catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error In File Openning");
				e.printStackTrace();
			}
		 
	}
	public void Print_CSV_Data() {
		Iterator<String> itr;
		for(int i = 0 ; i <CSV_DATA.size();i++) {
			itr = CSV_DATA.get(i).iterator();
			ArrayList<String> ptr = CSV_DATA.get(i);
			for(int j =0;j<ptr.size();j++) {
				System.out.print(ptr.get(j) + "  ");
			}
			System.out.println(" ");
		}
	}
	public ArrayList<String> Get_Spesific_Row(int Row_Number) {
		if(Row_Number < 0 || Row_Number > this.Number_Of_Rows) {
			System.out.println("Invalid Row Number");
			return null;
		}
		return this.CSV_DATA.get(Row_Number-1);
	}
	public ArrayList<String> Get_Spesific_Column(int Column_Number){
		if(Column_Number < 0 || Column_Number > this.Number_Of_Columns) {
			System.out.println("Invalid Row Number");
			return null;
		}
		ArrayList<String> Spesific_Coulmn = new ArrayList<String>();
		
		for(int  i = 0;i<this.Number_Of_Rows;i++) {
			ArrayList<String> ptr = CSV_DATA.get(i);
			Spesific_Coulmn.add(ptr.get(Column_Number-1));
		}
		
		return Spesific_Coulmn;
		
	}
	public String CSV_Get_Value(int Row,int Column) {
		if(Row > this.Number_Of_Rows || Column > this.Number_Of_Columns || 
			Row < 0 || Column < 0 ){
				System.out.println("Error In Value Location");
				return null;
		}
		return CSV_DATA.get(Row-1).get(Column-1);
	}
	//
	
	//Algorithms
	public Point Get_Linear_Regression_Equation(ArrayList<Point> Data) {
		double a,bx;
		double sum_xy=0,sum_xsquared=0,sum_ysquared=0,sum_x=0,sum_y=0;
		Point t;
		for(int i = 0 ; i<Data.size();i++) {
			t = Data.get(i);
			sum_x += t.x;
			sum_y += t.y;
			sum_xy += t.y*t.x;
			sum_xsquared += t.x*t.x;
			sum_ysquared += t.y*t.y;
		}
		a = (sum_y*sum_xsquared - sum_x*sum_xy)/(Data.size()*sum_xsquared - sum_x*sum_x);
		bx = (Data.size() * sum_xy - sum_x*sum_y)/(Data.size()*sum_xsquared - sum_x*sum_x);
		//System.out.println("A : " + ax);
		//System.out.println("BX : " + b);

		return new Point(a,bx,0); 
		
	}
	
	private double Compute_Error_For_Given_Points(ArrayList<String> X_val,ArrayList<String> Y_val,double b,double m) {
		double totalError=0;
		double x,y;
		for(int i = 0 ;i<this.Number_Of_Rows;i++) {
			x = Double.parseDouble(X_val.get(i));
			y = Double.parseDouble(Y_val.get(i));
			
			totalError += (y - (x * m + b) ) * (y - (x * m + b) );
		}
		
		totalError *= (double)1/(this.Number_Of_Rows);
		return totalError;
	}
	public Point Step_Gradient(double Current_B,double Current_M,double Learning_Rate ,ArrayList<String> X_val,ArrayList<String> Y_val) {
		double b_gradient = 0;
		double m_gradient = 0;
		double x , y;
		double new_b,new_m;
		for(int i =0;i<2;i++) {
			x = Double.parseDouble(X_val.get(i));
			y = Double.parseDouble(Y_val.get(i));
			m_gradient += ((double)2/2)*-x*(y - (Current_M * x + Current_B));
			b_gradient += ((double)2/2)*-(y - (Current_M * x + Current_B));
		}
		new_b = Current_B - (Learning_Rate * b_gradient);
		new_m = Current_M - (Learning_Rate * m_gradient);
		
		return new Point(new_m,new_b,0);
		
	}
	public Point Gradient_Descent(ArrayList<String> X_values,ArrayList<String> Y_values,double Leaning_Rate,int number_of_iterations) {
		//y = mx + b - for slope calculation
		Point LE = new Point();
		for(int i = 0;i<number_of_iterations;i++) {
			LE = this.Step_Gradient(LE.y, LE.x, Leaning_Rate, X_values, Y_values);
		}
		LE.z = Compute_Error_For_Given_Points(X_values,Y_values,LE.y,LE.x);

		return LE;
		
	}

	
	
	
	//Visual
	public void Show_Linear_Regression(ArrayList<Point> Data,String X_Name,String Y_Name) {
		Point LE = Get_Linear_Regression_Equation(Data) ;
		Math_Toolbox tlb = new Math_Toolbox();
		SPlot plt = new SPlot();
		Image data_plot = plt.Get_Scatter_Plot(Data, X_Name, Y_Name);
		double Max_X = Double.MIN_VALUE,Max_Y = Double.MIN_VALUE;
		for(int i = 0 ;i<Data.size();i++) {
			if(Data.get(i).x > Max_X) {
				Max_X = Data.get(i).x;
			}
			if(Data.get(i).y > Max_Y) {
				Max_Y = Data.get(i).y;
			}
		}

		Max_X+=Max_X/4;
		Max_Y+=Max_Y/4;
		double tx,ty;
		double y_val;
		int draw_i = 5000;
		for(double i =0 ;i<=Max_X;i+=0.01) {
			y_val = LE.y * i + LE.x;
			tx = tlb.Remap((float)i, 0, (float)Max_X, 105, 720);
			ty = tlb.Remap((float)y_val, (float)0 , (float)(Max_Y) ,565, 80);
			
			data_plot.Draw_Circle((int)tx, (int)ty, 1, CSET.Red,"Fill");
			//data_plot.Pixel_Matrix[(int)ty][(int)tx] = new Pixel(CSET.Red);

		
		}
		data_plot.Commint_Matrix_Changes();
		data_plot.Show_Image();
		
	}
    public void Show_Scatter_Plot(ArrayList<Point> Data,String X_Name,String Y_Name) {
    	plot.Show_Scatter_Plot(Data, X_Name, Y_Name);
    }
    public void Plot_Gradient_Descent(ArrayList<String> X_values,ArrayList<String> Y_values,double Leaning_Rate,int number_of_iterations) {
		ArrayList<Point> points = new ArrayList<Point>();
		for(int i = 0 ;i<Number_Of_Rows;i++) {
			points.add(new Point(Double.parseDouble(X_values.get(i)),Double.parseDouble(Y_values.get(i)),0));
		}
		
    	Image plot = this.plot.Get_Scatter_Plot(points, "X", "Y");
    	SIPL_Window sw = new SIPL_Window(plot.IMG);
		sw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int z =0;
		Point LE = new Point();
		
		double Max_X = Double.MIN_VALUE,Max_Y = Double.MIN_VALUE;
		for(int i = 0 ;i<points.size();i++) {
			if(points.get(i).x > Max_X) {
				Max_X = points.get(i).x;
			}
			if(points.get(i).y > Max_Y) {
				Max_Y = points.get(i).y;
			}
		}
		Max_X+=Max_X/4;
		Max_Y+=Max_Y/4;
		double y_val,tx,ty;
		plot.Update_Pixel_Matrix();
		Image nolines = new Image(plot);
		while(z<number_of_iterations) {
			//calculating gradient decent and getting the equation
			LE = this.Step_Gradient(LE.y, LE.x, Leaning_Rate, X_values, Y_values);
			
			//line drawing and mapping
			for(double p =0 ;p<=Max_X;p+=0.01) {
				y_val = LE.x * p + LE.y;
				tx = tlb.Remap((float)p, 0, (float)Max_X, 105, 720);
				ty = tlb.Remap((float)y_val, (float)0 , (float)(Max_Y) ,565, 80);
				
				plot.Draw_Circle((int)tx, (int)ty, 1, CSET.Red,"Fill");
				//data_plot.Pixel_Matrix[(int)ty][(int)tx] = new Pixel(CSET.Red);

			
			}
			plot.Commint_Matrix_Changes();
			
			//next iter
			z++;

			//frame rate control
			try {
				TimeUnit.MILLISECONDS.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sw.Refresh_Frame(plot.IMG);
			
			//returning to original background
			for(int a = 0 ;a<plot.Image_Height;a++) {
				for(int b = 0; b<plot.Image_Width;b++) {
					plot.Pixel_Matrix[a][b] = new Pixel(nolines.Pixel_Matrix[a][b]);
				}
			}
		}

		
    }


}
