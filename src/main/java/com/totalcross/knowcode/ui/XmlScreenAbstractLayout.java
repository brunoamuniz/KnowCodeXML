// (c) 2020 by TotalCross Global Mobile Platform LTDA
// SPDX-License-Identifier: GPL-2.0-only

package com.totalcross.knowcode.ui;

import java.io.IOException;
import java.util.TreeMap;

import com.totalcross.knowcode.util.Colors;

import totalcross.sys.Vm;
import totalcross.ui.Button;
import totalcross.ui.Check;
import totalcross.ui.ComboBox;
import totalcross.ui.Container;
import totalcross.ui.Control;
import totalcross.ui.Edit;
import totalcross.ui.ImageControl;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.ProgressBar;
import totalcross.ui.Radio;
import totalcross.ui.Slider;
import totalcross.ui.Spinner;
import totalcross.ui.Switch;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.font.Font;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;
import totalcross.ui.image.ImageException;
import totalcross.ui.layout.VBox;
import totalcross.util.UnitsConverter;
import totalcross.xml.AttributeList;
import totalcross.xml.ContentHandler;
import totalcross.xml.SyntaxException;
import totalcross.xml.XmlReader;

public abstract class XmlScreenAbstractLayout extends Container {
	// boolean isLayout = true;
	int layout = 0;
	private String pathXml;
	// int xpos = LEFT+5;
	// int ypos = TOP+10;
	// int widthPos = PARENTSIZE;
	// int heightPos = PARENTSIZE;

	TreeMap<String, Control> componentsMap = new TreeMap<String, Control>();
	Control lastControl = null;

	Container centralContainer;

	// private int centralLayout=0;

	public void initUI() {
		try {

			// centralContainer = new Container();
			// add(centralContainer,xpos, ypos, widthPos, heightPos);

			readXml();
			afterInitUI();
			// Label botao = (Label) getControlByID("botao");
			// botao.setText("helooo");
			// FindByID: get a control and change some property
			// getControlByID("@+id/buttonCadastro").setBackColor(Color.BRIGHT);
			// getControlByID("@+id/nomeEmpresa").setFont(Fonts.latoBoldMinus1);
			// getControlByID("@+id/imageCadastro").setVisible(false);
			// ((Spinner) getControlByID("@+id/progressCadastro")).start();

			// Add the main control to screen
			// add(firstControl, xpos, ypos, width, height);
			System.out.println();
		} catch (IOException | ArrayIndexOutOfBoundsException ea) {
			ea.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void afterInitUI() throws totalcross.io.IOException, ImageException {

	}

	public abstract void addscreen(NodeSax node) throws totalcross.io.IOException, ImageException;

	public Control createInstanceOf(NodeSax nodes) throws totalcross.io.IOException, ImageException {

		if (nodes.getAttributeName().contains("Button")) {
			componentsMap.put(nodes.getId(), createButton(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("Switch")) {
			componentsMap.put(nodes.getId(), createSwitch(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("SeekBar")) {
			componentsMap.put(nodes.getId(), createSlider(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().contains("TextView")) {
			componentsMap.put(nodes.getId(), createLabel(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("ProgressBar")) {
			componentsMap.put(nodes.getId(), createProgressBar(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("CheckBox")) {
			componentsMap.put(nodes.getId(), createCheck(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("EditText")) {
			componentsMap.put(nodes.getId(), createEdit(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("RadioButton")) {
			componentsMap.put(nodes.getId(), createRadio(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().contains("ImageView")) {
			componentsMap.put(nodes.getId(), createImageView(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else if (nodes.getAttributeName().equals("Spinner")) {
			componentsMap.put(nodes.getId(), createSpinner(nodes));
			lastControl = componentsMap.get(componentsMap.lastKey());
			return componentsMap.get(nodes.getId());
		} else {
			return new Label("");
		}
	}

	private Control createSpinner(NodeSax node) throws totalcross.io.IOException, ImageException {
		String[] items = { "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten" };

		ComboBox.usePopupMenu = false;
		ComboBox simpleComboBox = new ComboBox(items);
		simpleComboBox.caption = "Teste Combo";

		return simpleComboBox;
	}

	private Control createImageView(NodeSax node) throws totalcross.io.IOException, ImageException {
		ImageControl ic = null;
		String bg = node.getBackgroundImage();
		if (bg != null) {
			ic = new ImageControl(new Image(bg).getHwScaledInstance(node.getW(), node.getH()));
		} else {
			bg = node.getSrc();
			if (bg != null) {
				ic = new ImageControl(new Image(bg).getHwScaledInstance(node.getW(), node.getH()));
				// ic.scaleToFit = true;
			}
		}
		return ic;
	}

	public Container createInstanceOfLinearLayout(NodeSax node) {
		return new VBox(VBox.LAYOUT_FILL, VBox.ALIGNMENT_STRETCH);
	}

	private Control createButton(NodeSax node) throws totalcross.io.IOException, ImageException {
		Button button = null;
		String background = node.getBackgroundImage();
		if (background != null && "".equals(background) == false) {
			button = new Button(new Image(node.getBackgroundImage()).getHwScaledInstance(node.getW(), node.getH()));
		} else {
			background = node.getBackgroundColor();
			button = new Button(node.getText());
			button.setBackColor(Color.getRGB(background));
		}

		return button;
	}

	private Control createSwitch(NodeSax node) {
		return new Switch();
	}

	private Control createSlider(NodeSax node) {
		Slider s = new Slider();
		s.setMaximum(Integer.parseInt(node.getMax()));
		// s.setUnitIncrement(Integer.parseInt(node.getProgress()));
		return s;
	}

	private Control createLabel(NodeSax node) {
		Label label = new Label(node.getText());
		String bg = node.getBackgroundColor();
		String color = node.getTextColor();
		boolean txStyleBold = node.getTextStyleBold();
		String txSize = node.getTextsize();

		if (bg != null)
			label.setBackForeColors(Color.getRGB(bg), Color.getRGB(color));

		if (txStyleBold != false && txSize != null)
			label.setFont(Font.getFont(txStyleBold, UnitsConverter.toPixels(Integer.parseInt(txSize) + DP)));

		return label;

	}

	private Control createProgressBar(NodeSax node) {
		if (node.getStyle() == null || node.getStyle().contains("Horizontal")) {

			ProgressBar bar = new ProgressBar(0, Integer.parseInt(node.getMax()));
			bar.setValue(Integer.parseInt(node.getProgress()));
			return bar;
		} else {
			return new Spinner();
		}
	}

	private Control createCheck(NodeSax node) {
		return new Check(node.getText());
	}

	private Control createEdit(NodeSax node) {
		Edit edit = new Edit();

		if (node.getText() != null) {
			edit.caption = node.getText();
		} else if (node.getHint() != null) {
			edit.caption = node.getHint();
		}

		return edit;
	}

	private Control createRadio(NodeSax node) {
		return new Radio(node.getText());
	}

	protected Control getControlByID(String a) {
		return componentsMap.get(a);
	}

	private class Handler extends ContentHandler {
		NodeSax auxNodeSax;

		private Handler() throws totalcross.io.IOException, ImageException {
			auxNodeSax = new NodeSax();
		}

		@Override
		public void characters(String arg0) {
			System.out.println("Characters " + arg0);
		}

		@Override
		public void endElement(int arg0) {

		}

		@Override
		public void startElement(int arg0, AttributeList atts) {
			AttributeList.Iterator it = atts.new Iterator();
			while (it.next()) {
				auxNodeSax.inserts(it.getAttributeName(), it.getAttributeValue());
			}
			try {
				addscreen(auxNodeSax);
			} catch (totalcross.io.IOException e) {
				e.printStackTrace();
			} catch (ImageException e) {
				e.printStackTrace();
			}
		}

		// @Override
		public void endElementString(String arg0) {

		}

		// @Override
		public void startElementString(String arg0, AttributeList arg1) {
			System.out.println("startElementString " + arg0);
			auxNodeSax.setAttributeName(arg0);
			auxNodeSax.reset();
		}
	}

	private void readXml() throws totalcross.io.IOException, ImageException {

		Handler handler = new Handler();
		XmlReader rdr = new XmlReader();
		rdr.setContentHandler(handler);

		byte[] xml = Vm.getFile(getPathXml());

		if (xml != null) {

			try {
				rdr.parse(xml, 0, xml.length);
			} catch (SyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			MessageBox mb = new MessageBox("Message", "XML not found.", new String[] { "Close" });
			mb.setForeColor(Colors.SURFACE);
			mb.popup();
		}
	}

	public void createBackButton() throws totalcross.io.IOException, ImageException {
		Button btVoltar = null;

		btVoltar = new Button(new Image("drawable/arrowgray.jpg"), Button.BORDER_ROUND);
		btVoltar.setForeColor(Colors.BACKGROUND_GRAY_01);

		centralContainer.add(btVoltar, LEFT, BOTTOM);
		btVoltar.addPressListener((e) -> {
			InicialScreen scr1 = new InicialScreen();

			MainWindow.getMainWindow().swap(scr1);
		});

	}

	public String getPathXml() {
		return pathXml;
	}

	public void setPathXml(String pathXml) {
		this.pathXml = pathXml;
	}

}