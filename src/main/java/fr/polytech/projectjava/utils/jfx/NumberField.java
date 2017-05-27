package fr.polytech.projectjava.utils.jfx;

import javafx.scene.control.TextField;
import java.util.function.Function;

/**
 * A text field accepting only numbers.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 24/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-24
 */
public class NumberField extends TextField
{
	private final Function<Integer, Boolean> verify;
	
	/**
	 * Constructor.
	 * The default value will be set to 0.
	 */
	public NumberField()
	{
		this(0, null);
	}
	
	/**
	 * Constructor.
	 *
	 * @param defaultValue The default value to set at the beginning.
	 * @param verify       The function to use to allow the new changes.
	 */
	public NumberField(int defaultValue, Function<Integer, Boolean> verify)
	{
		super("" + defaultValue);
		this.verify = verify;
	}
	
	@Override
	public void replaceText(int start, int end, String text)
	{
		if(validate(getText().substring(0, start) + text + getText().substring(Math.min(start + 1, getText().length()))))
			super.replaceText(start, end, text);
	}
	
	@Override
	public void replaceSelection(String text)
	{
		if(validate(text))
			super.replaceSelection(text);
	}
	
	/**
	 * Validate the changes or not.
	 *
	 * @param text The text to valid.
	 *
	 * @return True if a number matching the verifier, false else.
	 */
	private boolean validate(String text)
	{
		return text.matches("[0-9]*") && ((verify == null || text.equals("")) ? true : verify.apply(Integer.parseInt(text)));
	}
	
	/**
	 * Get the text as an int.
	 *
	 * @return The value.
	 */
	public int getInt()
	{
		return Integer.parseInt(getText());
	}
}
