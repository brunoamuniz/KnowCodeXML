// (c) 2020 by TotalCross Global Mobile Platform LTDA
// SPDX-License-Identifier: GPL-2.0-only

package com.totalcross.knowcode.ui;

import java.util.TreeMap;
import totalcross.ui.Container;
import totalcross.ui.Control;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.ImageException;

public class XmlScreenRelativeLayout extends XmlScreenAbstractLayout {
	boolean isLayout = true;

	int xpos = LEFT;
	int ypos = TOP;
	int widthPos = PARENTSIZE;
	int heightPos = PARENTSIZE;

	TreeMap<String, Control> componentsMap = new TreeMap<String, Control>();

	public void addscreen(NodeSax node) throws totalcross.io.IOException, ImageException {
		int xLocal = xpos;
		int yLocal = ypos;
		int widLocal = widthPos;
		int heiLocal = heightPos;

		if (isLayout) {
			isLayout = false;
			setBackColor(Color.getRGB(node.getBackgroundColor()));
			xLocal = LEFT + node.getPaddingLeft();
			yLocal = TOP + node.getPaddingTop();
			widLocal = FILL - node.getPaddingRight();
			heiLocal = FILL - node.getPaddingBottom();

			centralContainer = new Container();

			add(centralContainer, xLocal, yLocal, widLocal, heiLocal);
		} else if (node.getAttributeName().equals("Switch")) {
			node.setAttributeName("TextView");
			node.setId(node.getId() + "l");
			centralContainer.add(createInstanceOf(node), node.getRelativeX(), node.getRelativeY(), node.getW(),
					node.getH());
			node.setAttributeName("Switch");
			centralContainer.add(createInstanceOf(node), AFTER, node.getRelativeY(), node.getW(), node.getH());
		} else {
			centralContainer.add(createInstanceOf(node), node.getRelativeX(), node.getRelativeY(), node.getW(),
					node.getH(), getRelativeControl(node));
		}
	}

	private Control getRelativeControl(NodeSax node) {
		if (node.getRelative() != null) {
			return getControlByID(node.getRelative());
		} else
			return lastControl;
	}

	@Override
	public void afterInitUI() throws totalcross.io.IOException, ImageException {
		super.afterInitUI();
		createBackButton();
	}
}
