/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.utils.salesforce;

import java.util.ResourceBundle;
import java.util.Set;

/**
 * Utility that reads properties file with environment information, format assuming that "sandbox"
 * is an environment name:
 *
 * <p>sandbox.url=https://sandbox.salesforce.com/
 *
 * <p>sandbox.username=my.user@salesforce.com
 *
 * <p>sandbox.password=secretPassword
 *
 * <p>Only 3 properties are required to use login via UI method. When creating instance of this
 * class, pass environment name prefix as parameter to constructor: new TestEnvironment("sandbox")
 *
 * @since 2021
 * @author salesforce
 */
public class TestEnvironment {

  private static final String MISSING_PROPERTY_ERR = "Property '%s' is not set in env.properties";

  private final String envPrefix;
  private final String baseUrl;
  private final String redirectUrl;
  private final String userName;
  private final String password;
  private final String sfdxLoginUrl;
  private final String accountId;
  private final String contactId;
  private final String leadId;

  public TestEnvironment(String envNamePrefix) {
    this.envPrefix = envNamePrefix;
    ResourceBundle resourceBundle = ResourceBundle.getBundle("env");
    Set<String> keys = resourceBundle.keySet();
    String urlKey = getBaseUrlKey();
    this.baseUrl = keys.contains(urlKey) ? wrapUrl(resourceBundle.getString(urlKey)) : "";
    String usernameKey = getUsernameKey();
    this.userName = keys.contains(usernameKey) ? resourceBundle.getString(usernameKey) : "";
    String passwordKey = getPasswordKey();
    this.password = keys.contains(passwordKey) ? resourceBundle.getString(passwordKey) : "";
    String redUrlKey = getRedirectUrlKey();
    this.redirectUrl = keys.contains(redUrlKey) ? wrapUrl(resourceBundle.getString(redUrlKey)) : "";
    String loginUrlKey = getSfdxLoginUrlKey();
    this.sfdxLoginUrl =
        keys.contains(loginUrlKey) ? wrapUrl(resourceBundle.getString(loginUrlKey)) : "";
    String acctIdKey = getAccountIdKey();
    this.accountId = keys.contains(acctIdKey) ? resourceBundle.getString(acctIdKey) : "";
    String contactIdKey = getContactIdKey();
    this.contactId = keys.contains(contactIdKey) ? resourceBundle.getString(contactIdKey) : "";
    String leadIdKey = getLeadIdKey();
    this.leadId = keys.contains(leadIdKey) ? resourceBundle.getString(leadIdKey) : "";
  }

  private static String wrapUrl(String url) {
    String transformed = url;
    // if url does not start from http or https - add
    if (!url.startsWith("http")) {
      transformed = "http://" + url;
    }
    // if url does not end with "/", add
    if (!url.endsWith("/")) {
      transformed = transformed.concat("/");
    }
    return transformed;
  }

  private String getBaseUrlKey() {
    return envPrefix + ".url";
  }

  private String getRedirectUrlKey() {
    return envPrefix + ".redirectUrl";
  }

  private String getSfdxLoginUrlKey() {
    return envPrefix + ".sfdx.url";
  }

  private String getUsernameKey() {
    return envPrefix + ".username";
  }

  private String getPasswordKey() {
    return envPrefix + ".password";
  }

  private String getAccountIdKey() {
    return envPrefix + ".account.id";
  }

  private String getContactIdKey() {
    return envPrefix + ".contact.id";
  }

  private String getLeadIdKey() {
    return envPrefix + ".lead.id";
  }

  public String getBaseUrl() {
    if (baseUrl.isEmpty()) {
      throw new IllegalArgumentException(String.format(MISSING_PROPERTY_ERR, getBaseUrlKey()));
    }
    return baseUrl;
  }

  public String getUserName() {
    if (userName.isEmpty()) {
      throw new IllegalArgumentException(String.format(MISSING_PROPERTY_ERR, getUsernameKey()));
    }
    return userName;
  }

  public String getPassword() {
    if (password.isEmpty()) {
      throw new IllegalArgumentException(String.format(MISSING_PROPERTY_ERR, getPasswordKey()));
    }
    return password;
  }

  public String getSfdxLoginUrl() {
    if (sfdxLoginUrl.isEmpty()) {
      throw new IllegalArgumentException(String.format(MISSING_PROPERTY_ERR, getSfdxLoginUrlKey()));
    }
    return sfdxLoginUrl;
  }

  public String getRedirectUrl() {
    if (redirectUrl.isEmpty()) {
      return baseUrl;
    }
    return redirectUrl;
  }

  public String getAccountId() {
    if (accountId.isEmpty()) {
      throw new IllegalArgumentException(String.format(MISSING_PROPERTY_ERR, getAccountIdKey()));
    }
    return accountId;
  }

  public String getContactId() {
    if (contactId.isEmpty()) {
      throw new IllegalArgumentException(String.format(MISSING_PROPERTY_ERR, getContactIdKey()));
    }
    return contactId;
  }

  public String getLeadId() {
    if (leadId.isEmpty()) {
      throw new IllegalArgumentException(String.format(MISSING_PROPERTY_ERR, getLeadId()));
    }
    return leadId;
  }
}
