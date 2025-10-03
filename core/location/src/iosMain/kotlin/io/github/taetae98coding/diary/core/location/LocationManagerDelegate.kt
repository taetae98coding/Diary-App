package io.github.taetae98coding.diary.core.location

import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.darwin.NSObject

internal class LocationManagerDelegate :
    NSObject(),
    CLLocationManagerDelegateProtocol
