preferences {
    input("deviceIp", "text", title: "Device IP")
    input("deviceMac", "text", title: "Device MAC Address")
}

metadata {
  definition (name: "Roku TV", namespace: "ericboehs", author: "Eric Boehs") {
    capability "Switch"
    capability "Polling"
    capability "Refresh"
    command "volume_up"
    command "volume_down"
    command "volume_mute"
    command "nickjr"
    command "disneyjr"
    command "hdmi1"
    command "hdmi2"
    command "hdmi3"
    command "hdmi4"
  }

  simulator {
  }

  tiles {
    standardTile("button", "device.switch", width: 2, height: 2, canChangeIcon: true) {
      state "off", label: 'Off', action: "switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff", nextState: "on"
      state "on", label: 'On', action: "switch.off", icon: "st.switches.switch.on", backgroundColor: "#79b821", nextState: "off"
    }

    standardTile("refresh", "device.switch", inactiveLabel: false, decoration: "flat") {
      state "default", label:"", action:"refresh.refresh", icon:"st.secondary.refresh"
    }

    standardTile("volume_up", "device.switch", inactiveLabel: false, decoration: "flat") {
      state "default", label:"Vol +", action:"volume_up", icon:"st.custom.sonos.unmuted"
    }

    standardTile("volume_down", "device.switch", inactiveLabel: false, decoration: "flat") {
      state "default", label:"Vol -", action:"volume_down", icon:"st.custom.sonos.unmuted"
    }

    standardTile("volume_mute", "device.switch", inactiveLabel: false, decoration: "flat") {
      state "default", label:"Mute", action:"volume_mute", icon:"st.custom.sonos.muted"
    }

    standardTile("nickjr", "device.switch", inactiveLabel: false, decoration: "flat") {
      state "default", label:"Nick Jr.", action:"nickjr", icon:"st.Electronics.electronics12"
    }

    standardTile("disneyjr", "device.switch", inactiveLabel: false, decoration: "flat") {
      state "default", label:"Disney Jr.", action:"disneyjr", icon:"st.Electronics.electronics12"
    }

    standardTile("hdmi1", "device.switch", inactiveLabel: false, decoration: "flat") {
      state "default", label:"HDMI 1", action:"hdmi1", icon:"st.Electronics.electronics12"
    }

    standardTile("hdmi2", "device.switch", inactiveLabel: false, decoration: "flat") {
      state "default", label:"HDMI 2", action:"hdmi2", icon:"st.Electronics.electronics12"
    }

    standardTile("hdmi3", "device.switch", inactiveLabel: false, decoration: "flat") {
      state "default", label:"HDMI 3", action:"hdmi3", icon:"st.Electronics.electronics12"
    }

    standardTile("hdmi4", "device.switch", inactiveLabel: false, decoration: "flat") {
      state "default", label:"HDMI 4", action:"hdmi4", icon:"st.Electronics.electronics12"
    }

    main "button"
    details(["button", "refresh", "volume_mute", "volume_down", "volume_up", "hdmi1", "hdmi2", "hdmi3", "hdmi4", "nickjr", "disneyjr"])
  }
}

def installed() {
  updated()
}

def updated() {
  log.debug "updated"
  poll()
  runEvery1Minute(poll)
}

def parse(String description) {
  def msg = parseLanMessage(description)

  if (msg.body && msg.body.contains("PowerOn")) {
    sendEvent(name: "switch", value: "on")
  }
}

def on() {
  sendEvent(name: "switch", value: "on")

  sendHubCommand(new physicalgraph.device.HubAction (
    "wake on lan ${deviceMac}",
    physicalgraph.device.Protocol.LAN,
    null,
    [:]
  ))

  keypress('Power')
}

def off() {
  sendEvent(name: "switch", value: "off")
  keypress('PowerOff')
}

def volume_up() {
  keypress('VolumeUp')
}

def volume_down() {
  keypress('VolumeDown')
}

def volume_mute() {
  keypress('VolumeMute')
}

def nickjr() {
  launchApp('66595')
}

def disneyjr() {
  launchApp('34278')
}

def hdmi1() {
  keypress('InputHDMI1')
}

def hdmi2() {
  keypress('InputHDMI2')
}

def hdmi3() {
  keypress('InputHDMI3')
}

def hdmi4() {
  keypress('InputHDMI4')
}

def poll() {
  log.debug "Executing 'poll'"
  refresh()
}

def refresh() {
  log.debug "Executing 'refresh'"
  queryDeviceState()
}

def queryDeviceState() {
  sendEvent(name: "switch", value: "off")
  sendHubCommand(new physicalgraph.device.HubAction(
    method: "GET",
    path: "/query/device-info",
    headers: [ HOST: "${deviceIp}:8060" ]
  ))
}

def keypress(key) {
  log.debug "Executing '${key}'"
  def result = new physicalgraph.device.HubAction(
    method: "POST",
    path: "/keypress/${key}",
    headers: [ HOST: "${deviceIp}:8060" ],
  )
}

def launchApp(appId) {
  log.debug "Executing 'launchApp ${appId}'"
  def result = new physicalgraph.device.HubAction(
    method: "POST",
    path: "/launch/${appId}",
    headers: [ HOST: "${deviceIp}:8060" ],
  )
}
