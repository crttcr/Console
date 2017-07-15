package xivvic.console.menu;

import java.util.Deque;
import java.util.Iterator;
import java.util.stream.Collectors;

import xivvic.util.text.TextFormatter;

public class MenuFormatter
{

	private static String getMenuHeaderContent(Deque<Menu> stack)
	{
		String content = stack.stream()
		.map(m -> m.title())
		.collect(Collectors.joining(" >> "));

		return content;
	}

	public static String format(Menu menu, Deque<Menu> stack)
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

		Iterator<MenuItem> it = menu.iterator();
		while (it.hasNext())
		{
			MenuItem item = it.next();
			String  number = TextFormatter.padLeft(Integer.toString(index), 2, ' ');
			sb.append(number);
			index++;
			sb.append(": ");
			String sc = item.shortcut();
			String text = TextFormatter.pad(sc, 8, ' ');
			sb.append(text);
			sb.append(": ");
			if (item.is_enabled())
			{
				sb.append("    	 ");
			}
			else
			{
				sb.append(" [X]	 ");
			}
			sb.append(": ");
			sb.append(item.text());
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
		{
			return "Up to previous menu";
		}

		return "Exit application";
	}

}
