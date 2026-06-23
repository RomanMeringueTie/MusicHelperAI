package com.example.maps.common.impl.build_type

import com.example.maps.common.api.BuildConfig
import com.example.maps.common.api.build_type.BuildType
import com.example.maps.common.api.build_type.BuildTypeProvider

class BuildTypeProviderImpl : BuildTypeProvider {
    override fun getBuildType(): BuildType =
        if (BuildConfig.IS_DEBUG) {
            BuildType.DEBUG
        } else BuildType.RELEASE
}
