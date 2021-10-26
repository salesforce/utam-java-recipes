/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.base;

/**
 * Base Class for Salesforce application Mobile tests
 *
 * @author salesforce
 * @since 236
 */
public abstract class SalesforceMobileTestBase extends UtamMobileTestBase {

  protected final void setupAndroid() {
    super.setupAndroid("Login | Salesforce");
  }

  protected final void setupIOS() {
    super.setupIOS("Login | Salesforce");
  }
}
