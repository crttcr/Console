package console.menu;

import xivvic.console.action.Action;



public interface IMenuItem
	extends Action
{
	public String            getText();
	public String        getShortcut();
}