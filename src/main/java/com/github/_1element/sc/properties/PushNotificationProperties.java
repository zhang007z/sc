package com.github._1element.sc.properties; //NOSONAR

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Push notification properties.
 */
@Component
@ConfigurationProperties("sc.pushnotification")
public class PushNotificationProperties {

  private boolean enabled = false;

  private String adapter;

  private String apiToken;

  private String userToken;

  private long groupTime = 0;

  private String url;

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(final boolean enabled) {
    this.enabled = enabled;
  }

  public String getAdapter() {
    return adapter;
  }

  public void setAdapter(final String adapter) {
    this.adapter = adapter;
  }

  public String getApiToken() {
    return apiToken;
  }

  public void setApiToken(final String apiToken) {
    this.apiToken = apiToken;
  }

  public String getUserToken() {
    return userToken;
  }

  public void setUserToken(final String userToken) {
    this.userToken = userToken;
  }

  public long getGroupTime() {
    return groupTime;
  }

  public void setGroupTime(final long groupTime) {
    this.groupTime = groupTime;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(final String url) {
    this.url = url;
  }

}
