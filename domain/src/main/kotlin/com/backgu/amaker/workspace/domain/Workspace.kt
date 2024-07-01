package com.backgu.amaker.workspace.domain

import com.backgu.amaker.common.domain.BaseTime

class Workspace(
    val id: Long = 0L,
    var name: String,
    var thumbnail: String = "/images/default_thumbnail.png",
) : BaseTime()
