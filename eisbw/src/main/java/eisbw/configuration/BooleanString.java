package eisbw.configuration;

/**
 * @author Danny & Harm - A boolean as a string.
 *
 */
public class BooleanString {
	private final String data;

	/**
	 * The BooleanString constructor.
	 * 
	 * @param data
	 *            The boolean string.
	 */
	public BooleanString(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public boolean getValue() {
		return "true".equals(data);
	}
}
