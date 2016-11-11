package com.mycompany.a3;

import com.codename1.charts.models.Point;

public interface ISelectable {
	public void setSelected(boolean yesNo);
	public boolean isSelected();
	public boolean contains(Point pPtrRelprnt, Point pCmpRelPrnt);
}
