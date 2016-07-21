package tj.dushanbe.ibrohim;

import javax.swing.SwingUtilities;

import tj.dushanbe.ibrohim.views.MainFrame;


public class App {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new MainFrame();
			}
		});
	}

}
