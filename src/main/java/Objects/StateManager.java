package Objects;

public class StateManager {
	public static State state;
	
	public StateManager(State state) {
		StateManager.state = state;
	}
	public static State getState() {
		return state;
	}
}
