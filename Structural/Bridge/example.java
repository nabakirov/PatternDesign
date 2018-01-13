// Remotes contain references to the device they control.
// Remotes delegate most of the work to their
// device objects.
class Remote is
    protected field device: Device
    constructor Remote(device: Device) is
        this.device = device
    method togglePower() is
        if (device.isEnabled()) then
            device.disable()
        else
            device.enable()
    method volumeDown() is
        device.setVolume(device.getVolume() - 10)
    method volumeUp() is
        device.setVolume(device.getVolume() + 10)
    method channelDown() is
        device.setChannel(device.getChannel() - 1)
    method channelUp() is
        device.setChannel(device.getChannel() + 1)


// You can extend remotes' class hierarchy independently
// from devices' classes.
class AdvancedRemote extends Remote is
    method mute() is
        device.setVolume(0)


// All devices follow the common interface. It makes them
// compatible with all types of remotes.
interface Device is
    method isEnabled()
    method enable()
    method disable()
    method getVolume()
    method setVolume(percent)
    method getChannel()
    method setChannel(channel)


// Each concrete device has its own implementation.
class Tv implements Device is
    // ...

class Radio implements Device is
    // ...


// Somewhere in client code.
tv = new Tv()
remote = new Remote(tv)
remote.power()

radio = new Radio()
remote = new AdvancedRemote(radio)