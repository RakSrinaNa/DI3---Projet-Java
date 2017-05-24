package fr.polytech.projectjava.utils.jfx;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;

/**
 * A TableView that can be sorted.
 *
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 24/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-24
 */
public class SortedTableView <T> extends TableView<T>
{
	/**
	 * Constructor.
	 */
	public SortedTableView()
	{
		super();
		setSortPolicy(p -> true);
	}
	
	/**
	 * Set the list of this table.
	 *
	 * @param list The list to set.
	 */
	public void setList(ObservableList<T> list)
	{
		SortedList<T> sortedItems = new SortedList<>(list);
		setItems(sortedItems);
		sortedItems.comparatorProperty().bind(comparatorProperty());
	}
}
