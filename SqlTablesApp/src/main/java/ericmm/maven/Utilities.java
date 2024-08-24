package ericmm.maven;

import javax.swing.JComponent;

public class Utilities {
	static public void removeComponents(JComponent start, JComponent finish) {
		JComponent prevComponent = null;
		JComponent currComponent = start;
		JComponent finalComponent = (JComponent)finish.getParent();
				
		while (currComponent != finalComponent) {
			//System.out.println(currComponent);
						
			if (prevComponent != null) {
				currComponent.removeAll();
			}
			
			prevComponent = currComponent;
			currComponent = (JComponent)currComponent.getParent();
		}
	}
}
