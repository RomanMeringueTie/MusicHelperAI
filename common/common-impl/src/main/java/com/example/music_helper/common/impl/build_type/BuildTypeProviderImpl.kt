package com.example.music_helper.common.impl.build_type

import com.example.music_helper.common.api.BuildConfig
import com.example.music_helper.common.api.build_type.BuildType
import com.example.music_helper.common.api.build_type.BuildTypeProvider

class BuildTypeProviderImpl : BuildTypeProvider {
    override fun getBuildType(): BuildType =
        if (BuildConfig.IS_DEBUG) {
            BuildType.DEBUG
        } else BuildType.RELEASE
}
