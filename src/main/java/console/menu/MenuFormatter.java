package console.menu;

import java.util.Deque;
import java.util.Iterator;
import java.util.stream.Collectors;

import xivvic.util.text.TextFormatter;

public class MenuFormatter
{

	private static String getMenuHeaderContent(Deque<IMenu> stack)
	{
		String content = stack.stream()
				.map(m -> m.getTitle())
				.collect(Collectors.joining(" >> "));
		
		return content;
	}

	public static String format(IMenu menu, Deque<IMenu> stack)
	{
		if (menu == null)
		{
			return "Menu was null";
		}
		
		int          width = 80;
		StringBuffer    sb = new StringBuffer();
		String  headerText = MenuFormatter.getMenuHeaderContent(stack);
		
		String line = TextFormatter.edgedLine('*', '-', width);
		sb.append(line);
		sb.append("\n");
		String box = TextFormatter.padLeft(headerText, 3 + headerText.length(), ' ');
		box = TextFormatter.pad(box, width, ' ');
		sb.append("*");
		sb.append(box);
		sb.append("*");
		sb.append("\n");
		sb.append(line);
		sb.append("\n");
		
		int index = 1;

		Iterator<IMenuItem> it = menu.iterator();
		while (it.hasNext())
		{
			IMenuItem item = it.next();
			String  number = TextFormatter.padLeft(Integer.toString(index), 2, ' ');
			sb.append(number);
			index++;
			sb.append(": ");
			String sc = item.getShortcut();
			String text = TextFormatter.pad(sc, 8, ' ');
			sb.append(text);
			sb.append(": ");
			if (item.isEnabled())
				sb.append(" Enabled- ");
			else
				sb.append(" Disabled ");
			sb.append(": ");
			sb.append(item.getText());
			sb.append("\n");
		}
		
		String number = TextFormatter.padLeft(Integer.toString(0), 2, ' ');
		sb.append(number);
		sb.append(": ");
		String text = TextFormatter.pad(null, 8, ' ');
		sb.append(text);
		sb.append(": ");

		boolean  isSubmenu = stack.size() > 1;
		String      upText = MenuFormatter.getUpOneText(isSubmenu); 

		sb.append(upText);
		sb.append("\n");
		
		String s = sb.toString();
		
		return s;
	}
	
	private static String getUpOneText(boolean isSubmenu)
	{
		if (isSubmenu)
			return "Return to previous menu";
		
		return "Exit the application";
	}

}
