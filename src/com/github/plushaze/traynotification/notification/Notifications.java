package com.github.plushaze.traynotification.notification;

public enum Notifications implements Notification {

	INFORMATION("com/github/plushaze/traynotification/images/info.png", "#2C54AB"),
	NOTICE("com/github/plushaze/traynotification/images/notice.png", "#8D9695"),
	SUCCESS("com/github/plushaze/traynotification/images/success.png", "#009961"),
	WARNING("com/github/plushaze/traynotification/images/warning.png", "#E23E0A"),
	ERROR("com/github/plushaze/traynotification/images/error.png", "#CC0033");

	private final String urlResource;
	private final String paintHex;

	Notifications(String urlResource, String paintHex) {
		this.urlResource = urlResource;
		this.paintHex = paintHex;
	}

	@Override
	public String getURLResource() {
		return urlResource;
	}

	@Override
	public String getPaintHex() {
		return paintHex;
	}

}
