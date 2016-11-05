package com.github._1element.events;

import com.github._1element.domain.Camera;

/**
 * Event for received image.
 */
public class ImageReceivedEvent {

  private String fileName;

  private Camera source;

  public ImageReceivedEvent(String fileName, Camera source) {
    this.fileName = fileName;
    this.source = source;
  }

  public String getFileName() {
    return fileName;
  }

  public Camera getSource() {
    return source;
  }

}
