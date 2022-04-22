/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.utils.salesforce;

/**
 * Helper class with standard Salesforce records types
 *
 * @author salesforce
 * @since 2021
 */
public enum RecordType {
  Account,
  Contact,
  Lead,
  Opportunity;

  public String getObjectHomeUrl(String baseUrl) {
    return String.format("%slightning/o/%s/list?filterName=Recent", baseUrl, name());
  }

  public final String getRecordHomeUrl(String redirectUrl, String recordId) {
    return redirectUrl + String.format("lightning/r/%s/%s/view", name(), recordId);
  }
}
