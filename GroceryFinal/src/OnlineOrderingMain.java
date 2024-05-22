import javax.swing.JFrame;
import javax.swing.JPanel;

import view.OrderPanel;

/***************************************************************
* Name : Digital Order Tracker
* Author: Cory Howard
* Created : May 20, 2024
* Course: CIS 152 - Data Structures
* Version: 1.0
* OS: Windows 10
* IDE: Java Eclipse EE
* Copyright : This is my own original work 
* based on specifications issued by our instructor
* Description : An app that tries to mimic what my online grocery job system is like.
* 		This will be nowhere near close to how it actually is.
*            Input: Many strings and buttons
*            Output: Order information as strings on JPanels
*            BigO: O(n^2)
* Academic Honesty: I attest that this is my original work.
* I have not used unauthorized source code, either modified or
* unmodified. I have not given other fellow student(s) access
* to my program.
*
* ChatGPT assisted in the creation of this program by helping with
* the more repetitive aspects, such as many of the GUI elements like
* labels, text fields, and buttons. It also helped flesh out program logic.
***************************************************************/
public class OnlineOrderingMain {

	public static void main(String[] args) {

		JFrame frame = new JFrame("Totally Real Online Ordering");
		JPanel panel = new OrderPanel();
		
		frame.add(panel);
		
		// this is a random window size I picked, feel free to change it in here or by resizing the window while the application is running
		frame.setSize(512, 512);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}
