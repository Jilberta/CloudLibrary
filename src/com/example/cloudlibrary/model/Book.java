package com.example.cloudlibrary.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Book implements Serializable, Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String authorInfo, briefDescription, title, downloadUrl, imageUrl;
    private Bitmap bitmap;

	public Book(String authorInfo, String briefDescription, String title,
			String downloadUrl, String imageUrl, Bitmap bitmap) {
		this.authorInfo = authorInfo;
		this.briefDescription = briefDescription;
		this.title = title;
		this.downloadUrl = downloadUrl;
		this.imageUrl = imageUrl;
        this.bitmap = bitmap;
	}

	public Book() {
	}

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap(){
        return this.bitmap;
    }

	public void setAuthorInfo(String authorInfo) {
		this.authorInfo = authorInfo;
	}

	public void setBriefDescription(String briefDescription) {
		this.briefDescription = briefDescription;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAuthorInfo() {
		return authorInfo;
	}

	public String getBriefDescription() {
		return briefDescription;
	}

	public String getTitle() {
		return title;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}
}
