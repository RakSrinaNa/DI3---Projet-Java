package fr.polytech.projectjava.checkingSimulation.jfx.components;

import fr.polytech.projectjava.checkingSimulation.CheckInfos;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-03
 */
public class CheckList extends TableView<CheckInfos>
{
	public CheckList(ObservableList<CheckInfos> checkings)
	{
		super(checkings);
		setEditable(false);
	}
}
