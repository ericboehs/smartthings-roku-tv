# TCL Roku TV Device Handler for SmartThings

This allows you to turn on and off your TCL Roku TV as if it were a light switch in SmartThings. It will poll for state changes so that if you turn your TV on manually, it will display the correct state.

If you have Alexa integrated with SmartThings, you can turn your TV on and off with your voice.

It doesn't do anything else. It won't let you change inputs, volume or other things a remote would. It very easily could if you would like that. c99koder has made some progress on that front. Check out his [Roku Virtual Buttons](https://community.smartthings.com/t/release-roku-virtual-buttons/79105) if you would like that.


## Configuration

When adding the device, you'll need to specify the "Device Network Id" in the SmartThings web app. The Device Network ID is the IP and port of your TV in hex. miniwebtool has good [IP to Hex Converter](http://www.miniwebtool.com/ip-address-to-hex-converter/) you can use. It doesn't do port but the default port of `8060` is `1F7C` in hex. For example, if your IP was 10.0.1.15, your Device Network Id would be `0A00010F:1F7C` (note the leading 0).

You'll also need to configure the Device IP and MAC Address in the preferences. The MAC address shouldn't contain any delimiters such as spaces or colons. For example a MAC address of `BA:F1:2E:DC:34:2A` should be entered as `BAF12EDC342A`.

A fancier Device Type would handle all this for you. I'm sorry I haven't taken the time for that. I welcome Pull Requests that implement this feature.

## Issues
Please report issues via GitHub Issues.

## License
MIT License. See [License](https://github.com/ericboehs/smartthings-roku-tv/LICENSE).
