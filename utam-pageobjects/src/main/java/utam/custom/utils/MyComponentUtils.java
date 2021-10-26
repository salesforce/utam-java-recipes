/*
 * Copyright (c) 2021, salesforce.com, inc.
 * All rights reserved.
 * SPDX-License-Identifier: MIT
 * For full license text, see the LICENSE file in the repo root
 * or https://opensource.org/licenses/MIT
 */
package utam.custom.utils;

import utam.core.framework.base.UtamUtilitiesContext;
import utam.custom.pageobjects.MyComponent;

public class MyComponentUtils {

  public static void imperativeMethod(UtamUtilitiesContext context, String parameter) {

    MyComponent myComponent = (MyComponent) context.getPageObject();
    // do something unconventional
    myComponent.getCustomInput();
  }
}
