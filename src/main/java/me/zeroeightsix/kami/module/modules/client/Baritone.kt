package me.zeroeightsix.kami.module.modules.client

import baritone.api.BaritoneAPI
import me.zero.alpine.listener.EventHandler
import me.zero.alpine.listener.EventHook
import me.zero.alpine.listener.Listener
import me.zeroeightsix.kami.event.events.LocalPlayerUpdateEvent
import me.zeroeightsix.kami.module.Module
import me.zeroeightsix.kami.setting.Setting.SettingListeners
import me.zeroeightsix.kami.setting.Settings
import me.zeroeightsix.kami.util.MessageSendHelper

/**
 * Created by Dewy on the 21st of April, 2020
 */
@Module.Info(
        name = "Baritone",
        category = Module.Category.CLIENT,
        description = "Configures Baritone settings",
        showOnArray = Module.ShowOnArray.OFF
)
class Baritone : Module() {
    private var allowBreak = register(Settings.b("Allow Break", true))
    private var allowSprint = register(Settings.b("Allow Sprint", true))
    private var allowPlace = register(Settings.b("Allow Place", true))
    private var allowInventory = register(Settings.b("Allow Inventory", true))
    private var freeLook = register(Settings.b("Free Look", true))
    private var allowDownwardTunneling = register(Settings.b("Downward Tunneling", true))
    private var allowParkour = register(Settings.b("Allow Parkour", true))
    private var allowParkourPlace = register(Settings.b("Allow Parkour Place", true))
    private var avoidPortals = register(Settings.b("Avoid Portals", false))
    private var mapArtMode = register(Settings.b("Map Art Mode", false))
    private var renderGoal = register(Settings.b("Render Goals", true))
    private var hasRun = register(Settings.booleanBuilder("hasRun").withValue(false).withVisibility { false }.build())

    init {
        allowBreak.settingListener = SettingListeners { mc.player?.let { BaritoneAPI.getSettings().allowBreak.value = allowBreak.value } }
        allowSprint.settingListener = SettingListeners { mc.player?.let { BaritoneAPI.getSettings().allowSprint.value = allowSprint.value } }
        allowPlace.settingListener = SettingListeners { mc.player?.let { BaritoneAPI.getSettings().allowPlace.value = allowPlace.value } }
        allowInventory.settingListener = SettingListeners { mc.player?.let { BaritoneAPI.getSettings().allowInventory.value = allowInventory.value } }
        freeLook.settingListener = SettingListeners { mc.player?.let { BaritoneAPI.getSettings().freeLook.value = freeLook.value } }
        allowDownwardTunneling.settingListener = SettingListeners { mc.player?.let { BaritoneAPI.getSettings().allowDownward.value = allowDownwardTunneling.value } }
        allowParkour.settingListener = SettingListeners { mc.player?.let { BaritoneAPI.getSettings().allowParkour.value = allowParkour.value } }
        allowParkourPlace.settingListener = SettingListeners { mc.player?.let { BaritoneAPI.getSettings().allowParkourPlace.value = allowParkourPlace.value } }
        avoidPortals.settingListener = SettingListeners { mc.player?.let { BaritoneAPI.getSettings().enterPortal.value = !avoidPortals.value } }
        mapArtMode.settingListener = SettingListeners { mc.player?.let { BaritoneAPI.getSettings().mapArtMode.value = mapArtMode.value } }
        renderGoal.settingListener = SettingListeners { mc.player?.let { BaritoneAPI.getSettings().renderGoal.value = renderGoal.value } }
    }

    @EventHandler // this is done because on first init the settings won't change if null
    var localPlayerUpdateEvent = Listener(EventHook { event: LocalPlayerUpdateEvent? ->
        if (!hasRun.value) { // you can use a setting for this and only run it once because then it'll be updated in game, we're only worried about default settings
            BaritoneAPI.getSettings().allowBreak.value = allowBreak.value
            BaritoneAPI.getSettings().allowSprint.value = allowSprint.value
            BaritoneAPI.getSettings().allowPlace.value = allowPlace.value
            BaritoneAPI.getSettings().allowInventory.value = allowInventory.value
            BaritoneAPI.getSettings().freeLook.value = freeLook.value
            BaritoneAPI.getSettings().allowDownward.value = allowDownwardTunneling.value
            BaritoneAPI.getSettings().allowParkour.value = allowParkour.value
            BaritoneAPI.getSettings().allowParkourPlace.value = allowParkourPlace.value
            BaritoneAPI.getSettings().enterPortal.value = !avoidPortals.value
            BaritoneAPI.getSettings().mapArtMode.value = mapArtMode.value
            BaritoneAPI.getSettings().renderGoal.value = renderGoal.value
            hasRun.value = true
        }
    })

    public override fun onDisable() {
        MessageSendHelper.sendErrorMessage("Error: The Baritone module is for configuring Baritone integration, not toggling it.")
        enable()
    }
}