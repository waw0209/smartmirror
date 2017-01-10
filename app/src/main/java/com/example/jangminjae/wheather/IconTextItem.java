package com.example.jangminjae.wheather;

import android.graphics.drawable.Drawable;

/**
 * 데이터를 담고 있을 아이템 정의
 * 
 * @author Mike
 *
 */
public class IconTextItem {

	private String[] mData;

	/**
	 * True if this item is selectable
	 */
	private boolean mSelectable = true;

	/**
	 * Initialize with icon and data array
	 * 
	 * @param icon
	 * @param obj
	 */
	public IconTextItem(Drawable icon, String[] obj) {
		mData = obj;
	}

	/**
	 * Initialize with icon and strings
	 *  @param obj01
	 * @param obj02
	 * @param obj03
	 */
	public IconTextItem(String obj01, String obj02, String obj03,String obj04) {
		
		mData = new String[4];
		mData[0] = obj01;
		mData[1] = obj02;
		mData[2] = obj03;
		mData[3] = obj04;
	}
	
	/**
	 * True if this item is selectable
	 */
	public boolean isSelectable() {
		return mSelectable;
	}

	/**
	 * Set selectable flag
	 */
	public void setSelectable(boolean selectable) {
		mSelectable = selectable;
	}

	/**
	 * Get data array
	 * 
	 * @return
	 */
	public String[] getData() {
		return mData;
	}

	/**
	 * Get data
	 */
	public String getData(int index) {
		if (mData == null || index >= mData.length) {
			return null;
		}
		
		return mData[index];
	}
	
	/**
	 * Set data array
	 * 
	 * @param obj
	 */
	public void setData(String[] obj) {
		mData = obj;
	}

	/**
	 * Set icon
	 * 
	 * @param icon
	 */


	/**
	 * Get icon

	/**
	 * Compare with the input object
	 * 
	 * @param other
	 * @return
	 */
	public int compareTo(IconTextItem other) {
		if (mData != null) {
			String[] otherData = other.getData();
			if (mData.length == otherData.length) {
				for (int i = 0; i < mData.length; i++) {
					if (!mData[i].equals(otherData[i])) {
						return -1;
					}
				}
			} else {
				return -1;
			}
		} else {
			throw new IllegalArgumentException();
		}
		
		return 0;
	}

}
