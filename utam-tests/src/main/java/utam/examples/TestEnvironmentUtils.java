/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.examples;

import java.util.ResourceBundle;
import java.util.Set;

public class TestEnvironmentUtils {

  private final String baseUrl;
  private final String returnUrl;
  private final String userName;
  private final String password;
  private final String loginUrl;

  public TestEnvironmentUtils(String envNamePrefix) {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("env");
    String urlKey = envNamePrefix + ".url";
    String retUrlKey = envNamePrefix + ".returnUrl";
    String loginUrlKey = envNamePrefix + ".loginUrl";
    String usernameKey = envNamePrefix + ".username";
    String passwordKey = envNamePrefix + ".password";
    Set<String> keys = resourceBundle.keySet();
    final String errorString = "Property '%s' is not set in env.properties";
    if (!keys.contains(urlKey)) {
      throw new IllegalArgumentException(String.format(errorString, urlKey));
    }
    this.baseUrl = resourceBundle.getString(urlKey);
    if (!keys.contains(usernameKey)) {
      throw new IllegalArgumentException(String.format(errorString, usernameKey));
    }
    this.userName = resourceBundle.getString(usernameKey);
    if (!keys.contains(passwordKey)) {
      throw new IllegalArgumentException(String.format(errorString, passwordKey));
    }
    this.password = resourceBundle.getString(passwordKey);
    this.returnUrl = keys.contains(retUrlKey) ? resourceBundle.getString(retUrlKey) : "";
    this.loginUrl = keys.contains(loginUrlKey) ? resourceBundle.getString(loginUrlKey) : "";
  }

  public final String getCDPSegmentWizardUrl(String recordId) {
    return String.format(
        "%slightning/cmp/runtime_cdp__segmentWizardLanding?runtime_cdp__record_id=%s",
        baseUrl, recordId);
  }

  public final String getAppBuilderViewUrl(String recordTypeName, String viewId) {
    return String.format(
        "https://%s/visualEditor/appBuilder.app?&retUrl=https%3A%2F%2F%2Flightning%2Fr%2F%s%2Fa01S7000000Ls5OIAS%2Fview&id=%s",
        returnUrl, returnUrl, recordTypeName, viewId);
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

  public String getLoginUrl() {
    return loginUrl;
  }
}
