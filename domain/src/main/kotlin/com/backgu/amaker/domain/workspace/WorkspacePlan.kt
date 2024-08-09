package com.backgu.amaker.domain.workspace

enum class WorkspacePlan(
    val planName: String,
    val planPrice: Int,
    val planDescription: String,
    val belongingLimit: Int,
) {
    BASIC(
        "기본",
        0,
        "기본적인 기능을 사용할 수 있는 플랜입니다.",
        10,
    ),
    ENTERPRISE(
        "엔터프라이즈",
        10000,
        "모든 기능을 사용할 수 있는 플랜입니다.",
        20,
    ),
}
