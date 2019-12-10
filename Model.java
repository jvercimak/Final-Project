/**
 * The Model class serves as a model for the 
 * data that is being pulled from the website.
 * Responsible for parsing data pulled from website.
 * 
 * @author Monique Cauty
 * @author Antonio Elhakim
 * @author Dan Laskero
 * @author John Vercimak
 */

import java.util.ArrayList;
import java.util.LinkedList;

public class Model {
	private String tag;
	private String endtag;
	private ArrayList<Model> subModel;
	private String content;
	private String[] tags = {
			"<head>"
	};

	/****************************************************/
	public Model() {
		subModel = new ArrayList<>();
		content = "";
	}

	/****************************************************/
	public Model(String tag, String content) {
		subModel = new ArrayList<>();
		this.tag = tag;
		this.content = content;
	}

	/****************************************************/
	public String getContent() {
		return content;
	}

	/****************************************************/
	public ArrayList<Model> getSubModel() {
		return subModel;
	}

	/****************************************************/
	public String getTag() {
		return tag;
	}

	/****************************************************/
	public void setEndtag(String endtag) {
		this.endtag = endtag;
	}

	/****************************************************/
	public void setContent(String content) {
		this.content = content;
	}

	/****************************************************/
	public void setSubModel(ArrayList<Model> subModel) {
		this.subModel = subModel;
	}

	/****************************************************/
	public void setTag(String tag) {
		this.tag = tag;
	}

	/****************************************************/
	/**
	 * The model class is responsible for parsing
	 * @param linked list of data
	 */
	public void parse(LinkedList<String> ll) {
		while (ll.isEmpty() == false) {
			String str = ll.pollFirst();
			str = str.trim();

			/******************************************/
			if (str.equalsIgnoreCase(endtag)) {
				//   System.out.println("End " + endtag);
				break;
			}
			/******************************************/
			if (str.equalsIgnoreCase("<!doctype")) {
				Model m = new Model();
				m.setTag("doctype");
				String a_content = "";
				while (ll.isEmpty() == false) {
					str = ll.pollFirst();
					if (str.endsWith(">")) {
						str = str.substring(0, str.length() - 1);
						a_content += " " + str;
						break;
					}
					a_content += " " + str;
				}
				a_content = a_content.replace("\"", "").trim();

				m.setContent(a_content);
				subModel.add(m);

				/******************************************/

			} else if (str.equalsIgnoreCase("<html>")) {
				Model m = new Model();
				m.setTag("html");
				m.setEndtag("</html>");
				m.parse(ll);
				subModel.add(m);

				/******************************************/

			} else if (str.equalsIgnoreCase("<head>")) {
				Model m = new Model();
				m.setTag("head");
				m.setEndtag("</head>");
				m.parse(ll);
				subModel.add(m);

				/******************************************/

			} else if (str.equalsIgnoreCase("<meta")) {
				Model m = new Model();
				m.setTag("metal");
				boolean metaEnd = false;
				String metaStr = "";
				while (metaEnd == false
						&& ll.isEmpty() == false) {
					String tmp = ll.pollFirst();

					/******************************/
					if (tmp.endsWith(">")) {
						metaEnd = true;
						tmp = tmp.substring(0, tmp.length() - 1);
					}
					metaStr += " " + tmp;
				}
				// System.out.println(metaStr.trim());

				/******************************************/

				for (String object : metaParse(metaStr.trim())) {
					int index = object.indexOf('=');
					m.subModel.add(new Model(
							object.substring(0, index),
							object.substring(index + 1)));
				}
				subModel.add(m);

				/******************************************/

			} else if (str.contains("<title>")) {
				Model m = new Model();
				m.setTag("title");
				String title = str.replace("<title>", "");

				/******************************************/

				while (ll.isEmpty() == false
						&& str.contains("</title>") == false) {
					str = ll.pollFirst();
					title += " " + str;
				}
				title = title.replace("</title>", "");
				m.setContent(title);
				subModel.add(m);

				/******************************************/

			} else if (str.equalsIgnoreCase("<body")) {
				Model m = new Model();
				m.setTag("body");
				m.setEndtag("</body>");
				String body_css = "";

				/*****************************/


				while (ll.isEmpty() == false) {
					str = ll.pollFirst();
					body_css += " " + str;
					if (str.contains(">")) {
						break;
					}
				}
				body_css = body_css.replace(">", "").trim();

				/******************************************/

				if (body_css.length() > 0) {
					Model style = new Model();
					style.setTag("style");
					for (String string : metaParse(body_css)) {
						String[] sp = string.split("=");
						style.subModel.add(new Model(sp[0], sp[1]));
					}
					m.subModel.add(style);
				}
				m.parse(ll);
				subModel.add(m);

				/******************************************/

			} else if (str.contains("<font") && str.indexOf("<font") > 0) {
				int index = str.indexOf("<font");
				ll.addFirst(str.substring(index));
				ll.addFirst(str.substring(0, index));

				/******************************************/

			} else if (str.equalsIgnoreCase("<font")) {
				Model m = new Model();
				m.setTag("font");
				m.setEndtag("</font>");
				String font_css = "";

				/**********************************/

				while (ll.isEmpty() == false) {
					str = ll.pollFirst();
					font_css += " " + str;
					if (str.contains(">")) {
						break;
					}
				}

				/******************************************/

				int index = font_css.indexOf(">");
				String right = font_css.substring(index + 1);

				/******************************************/

				if (right.isEmpty() == false) {
					ll.addFirst(right);
				}
				font_css = font_css.substring(0, index);
				Model style = new Model();
				style.setTag("style");
				String[] sp = font_css.split("\\s+");

				/******************************************/

				for (String string : sp) {
					String[] p = string.split("=");
					if (p.length == 2) {
						style.subModel.add(new Model(p[0], p[1]));
					}
					//style.subModel.add(new Model(p[0], p[1]));
				}
				m.subModel.add(style);
				m.parse(ll);
				subModel.add(m);


			} else if (str.contains("</font>")) {
				int index = str.indexOf("</font>");
				String left = str.substring(0, index);
				String right = str.substring(index + "</font>".length());
				if (right.isEmpty() == false) {
					ll.addFirst(right);
				}
				ll.addFirst("</font>");
				if (left.isEmpty() == false) {
					ll.addFirst(left);
				}
			} else if (str.contains("<b>") && str.indexOf("<b>") > 0) {

				int index = str.indexOf("<b>");
				ll.addFirst(str.substring(index));
				ll.addFirst(str.substring(0, index));
			} else if (str.contains("</b>") && str.indexOf("</b>") > 0) {

				int index = str.indexOf("</b>");
				ll.addFirst(str.substring(index));
				ll.addFirst(str.substring(0, index));
			} else if (str.contains("<b>") && str.length() > "<b>".length()) {

				int index = "<b>".length();
				ll.addFirst(str.substring(index));
				ll.addFirst("<b>");
			} else if (str.contains("</b>") && str.length() > "</b>".length()) {
				int index = "</b>".length();
				ll.addFirst(str.substring(index));
				ll.addFirst("</b>");
			} else if (str.equalsIgnoreCase("<b>")) {
				Model m = new Model();
				m.setTag("b");
				m.setEndtag("</b>");
				m.parse(ll);
				subModel.add(m);
			} else if (str.contains("<p>") && str.length() > "<p>".length()) {
				int index = "<p>".length();
				ll.addFirst(str.substring(index));
				ll.addFirst("<p>");
			} else if (str.contains("</p>") && str.length() > "</p>".length()) {
				int index = "</p>".length();
				ll.addFirst(str.substring(index));
				ll.addFirst("</p>");
			} else if (str.equalsIgnoreCase("<p>") && tag.equals("p")) {
				ll.addFirst(str);
				ll.addFirst("</p>");
			} else if (str.equalsIgnoreCase("</body>") && tag.equals("p")) {
				//   System.out.println("Model.parse()");
				//    System.out.println(tag);
				ll.addFirst(str);
				ll.addFirst("</p>");
			} else if (str.equalsIgnoreCase("<p>")) {
				//  System.out.println("Model.parse() to p");
				Model m = new Model();
				m.setTag("p");
				m.setEndtag("</p>");
				m.parse(ll);
				subModel.add(m);
			} else if (str.contains("<a") && str.indexOf("<a") > 0) {

				int index = str.indexOf("<a");
				ll.addFirst(str.substring(index));
				ll.addFirst(str.substring(0, index));
			} else if (str.contains("</a>") && str.indexOf("</a>") > 0) {

				int index = str.indexOf("</a>");
				ll.addFirst(str.substring(index));
				ll.addFirst(str.substring(0, index));
			} else if (str.contains("<a") && str.length() > "<a".length()) {

				int index = "<a".length();
				ll.addFirst(str.substring(index));
				ll.addFirst("<a");
			} else if (str.contains("</a>") && str.length() > "</a>".length()) {
				int index = "</a>".length();
				ll.addFirst(str.substring(index));
				ll.addFirst("</a>");
			} else if (str.equalsIgnoreCase("<a")) {
				Model m = new Model();
				m.setTag("a");
				m.setEndtag("</a>");
				String aStr = "";
				while (ll.isEmpty() == false) {
					String tmp = ll.pollFirst();
					aStr += " " + tmp;
					if (tmp.contains(">")) {
						break;
					}
				}
				if (aStr.isEmpty() == false) {
					int index = aStr.indexOf(">");

					String right = aStr.substring(index + 1).trim();
					if (right.isEmpty() == false) {
						ll.addFirst(right);
					}
					Model style = new Model();
					String css = aStr.substring(0, index).trim();
					if (css.isEmpty() == false) {
						style.setTag("style");
						String[] sp = css.split("\\s+");
						for (String string : sp) {
							String[] p = string.split("=");
							if (p.length == 2) {
								style.subModel.add(new Model(p[0], p[1]));
							}
							//style.subModel.add(new Model(p[0], p[1]));
						}
						m.subModel.add(style);
					}
				}
				m.parse(ll);
				subModel.add(m);
			} else if (str.contains("<img") && str.indexOf("<img") > 0) {

				int index = str.indexOf("<img");
				ll.addFirst(str.substring(index));
				ll.addFirst(str.substring(0, index));
			} else if (str.contains("<img") && str.length() > "<img".length()) {

				int index = "<img".length();
				ll.addFirst(str.substring(index));
				ll.addFirst("<img");
			} else if (str.equalsIgnoreCase("<img")) {
				Model m = new Model();
				m.setTag("img");
				String iStr = "";
				while (ll.isEmpty() == false) {
					String tmp = ll.pollFirst();
					iStr += " " + tmp;
					if (tmp.contains(">")) {
						break;
					}
				}
				if (iStr.isEmpty() == false) {
					int index = iStr.indexOf(">");

					String right = iStr.substring(index + 1).trim();
					if (right.isEmpty() == false) {
						ll.addFirst(right);
					}
					Model style = new Model();
					String css = iStr.substring(0, index).trim();
					if (css.isEmpty() == false) {
						style.setTag("style");
						String[] sp = css.split("\\s+");
						for (String string : sp) {
							String[] p = string.split("=");
							if (p.length == 2) {
								style.subModel.add(new Model(p[0], p[1]));
							}
							//style.subModel.add(new Model(p[0], p[1]));
						}
						m.subModel.add(style);
					}
				}
				subModel.add(m);
			} else {
				content += " " + str;
			}

		}
	}

	/************************************************************/
	/**
	 * 
	 * @param line
	 * @return ArrayList 
	 */
	private ArrayList<String> metaParse(String line) {
		ArrayList<String> al = new ArrayList<>();
		String str = "";
		boolean isQuoted = false;
		for (int i = 0; i < line.length(); i++) {
			char ch = line.charAt(i);
			switch (ch) {
			case ' ':
				if (isQuoted == false) {
					al.add(str);
					str = "";
				} else {
					str += ch;
				}
				break;
			case '"':
				str += ch;
				isQuoted = !isQuoted;
				break;
			default:
				str += ch;
				break;
			}
		}
		if (str.length() > 0) {
			al.add(str);
		}
		return al;
	}

	/************************************************************/
	/**
	 * 
	 * @param level
	 * @return
	 */
	public String toString(int level) {
		String result = "";

		if (content != null && content.trim().isEmpty() == false) {
			if (content.contains("\"")) {
				result += content.trim();
			} else {
				result += '"' + content.trim() + '"';
			}
		}
		if (content != null && content.trim().isEmpty() == false
				&& subModel.isEmpty() == false) {
			result += ", ";
			result += System.lineSeparator();
		}

		for (int i = 0; i < subModel.size() - 1; i++) {
			Model get = subModel.get(i);
			result += get.toString(level + 1) + ", " + System.lineSeparator();
		}

		if (subModel.size() > 0) {
			Model get = subModel.get(subModel.size() - 1);
			result += get.toString(level + 1) + System.lineSeparator();
			for (int i = 0; i < level; i++) {
				result += " ";
			}
		}

		String tmp = "";
		for (int i = 0; i < level; i++) {
			tmp += " ";
		}
		tmp += "{";

		result = tmp + "\"" + tag + "\" : "
				+ "[" + result + "]";

		return result
				+ "}";
	}

	@Override
	public String toString() {
		return toString(0);
	}


}
