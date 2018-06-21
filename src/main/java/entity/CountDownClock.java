package entity;

public class CountDownClock {

	public String getTimeLeft(int time) {
		String output = "";
		int mins = time / 60;
		int secs = time % 60;
		// set minutes
		if (mins < 10) {
			output = "0" + mins;
		} else {
			output = "" + mins;
		}
		output += ":";
		// set seconds
		if (secs < 10) {
			output += "0" + secs;
		} else {
			output += "" + secs;
		}
		return output;
	}
}
