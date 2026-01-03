package com.example.maps.toggles

data object ListensFilterToggle : Toggle {

    override val title: String = "Фильтрация уведомлений"
    override val description: String = "Фильтровать уведомления, добавляемые в прослушивания?"

    private var enabled = false

    override fun changeState() {
        enabled = !enabled
    }

    override fun isEnabled(): Boolean = enabled

}