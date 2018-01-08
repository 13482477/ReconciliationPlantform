package com.yonyou.reconciliation.image.service;

import com.yonyou.reconciliation.image.entity.Image;

public interface ImageService {
	
	public Image saveFile(String fileName, byte[] byteArray);
	
	public Image loadFile(String fileName);

}
