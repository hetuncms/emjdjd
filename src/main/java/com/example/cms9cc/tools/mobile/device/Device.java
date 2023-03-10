/*
 * Copyright 2010-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.cms9cc.tools.mobile.device;

/**
 * A model for the user agent or device that submitted the current request.
 * Callers may introspect this model to vary UI control or rendering logic by device type.
 * @author Keith Donald
 * @author Roy Clarkson
 * @author Scott Rossillo
 * @author Onur Kagan Ozcan
 */
public interface Device {

	/**
	 * True if this device is not a mobile or tablet device.
	 */
	default boolean isNormal() {
		return true;
	}

	/**
	 * True if this device is a mobile device such as an Apple iPhone or an Nexus One Android.
	 * Could be used by a pre-handle interceptor to redirect the user to a dedicated mobile web site.
	 * Could be used to apply a different page layout or stylesheet when the device is a mobile device.
	 */
	default boolean isMobile() {
		return false;
	}

	/**
	 * True if this device is a tablet device such as an Apple iPad or a Motorola Xoom.
	 * Could be used by a pre-handle interceptor to redirect the user to a dedicated tablet web site.
	 * Could be used to apply a different page layout or stylesheet when the device is a tablet device.
	 */
	default boolean isTablet() {
		return false;
	}

	/**
	 *
	 * @return resolved DevicePlatform
	 */
	default DevicePlatform getDevicePlatform() {
		return DevicePlatform.UNKNOWN;
	}

}
