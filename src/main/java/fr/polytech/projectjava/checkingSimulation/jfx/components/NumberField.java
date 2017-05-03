package fr.polytech.projectjava.checkingSimulation.jfx.components;

import javafx.scene.control.TextField;
import java.util.function.Function;

public class NumberField extends TextField
{
	private final Function<Integer, Boolean> verify;
	
	public NumberField()
	{
		this(0, null);
	}
	
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
	
	private boolean validate(String text)
	{
		return text.matches("[0-9]*") && ((verify == null || text.equals("")) ? true : verify.apply(Integer.parseInt(text)));
	}
	
	public int getInt()
	{
		return Integer.parseInt(getText());
	}
}