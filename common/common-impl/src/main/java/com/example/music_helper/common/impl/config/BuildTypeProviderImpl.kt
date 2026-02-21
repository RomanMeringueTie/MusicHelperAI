package com.example.music_helper.common.impl.config

import com.example.music_helper.common.api.BuildConfig
import com.example.music_helper.common.api.config.BuildType
import com.example.music_helper.common.api.config.BuildTypeProvider

class BuildTypeProviderImpl : BuildTypeProvider {
    override fun getBuildType(): BuildType =
        if (BuildConfig.IS_DEBUG) {
            BuildType.DEBUG
        } else BuildType.RELEASE
}
