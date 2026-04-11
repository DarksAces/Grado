import numpy as np
from ctypes import cast, POINTER
from comtypes import CLSCTX_ALL
from pycaw.pycaw import AudioUtilities, IAudioEndpointVolume

class VolumeController:
    def __init__(self):
        self.connected = False
        self.refresh_volume_handle()
        self.vol_bar = 400
        self.vol_per = 0

    def refresh_volume_handle(self):
        try:
            devices = AudioUtilities.GetSpeakers()
            interface = devices.Activate(IAudioEndpointVolume._iid_, CLSCTX_ALL, None)
            self.volume = cast(interface, POINTER(IAudioEndpointVolume))
            self.vol_range = self.volume.GetVolumeRange() 
            self.min_vol = self.vol_range[0]
            self.max_vol = self.vol_range[1]
            self.connected = True
            print("Audio handle refreshed successfully.")
        except Exception as e:
            print(f"Error refreshing audio handle: {e}")
            self.connected = False

    def set_volume(self, length):
        if not self.connected: 
            self.refresh_volume_handle()
            if not self.connected: return 0, 0, 400, 0

        try:
            # Finger distance range: 50 - 250
            vol = np.interp(length, [50, 250], [self.min_vol, self.max_vol])
            self.vol_bar = np.interp(length, [50, 250], [400, 150])
            self.vol_per = np.interp(length, [50, 250], [0, 100])
            
            prev_vol = self.volume.GetMasterVolumeLevel()
            self.volume.SetMasterVolumeLevel(vol, None)
            return prev_vol, vol, self.vol_bar, self.vol_per
        except Exception as e:
            print(f"COM Error setting volume, attempting refresh: {e}")
            self.refresh_volume_handle()
            return 0, 0, 400, 0

    def get_current_volume_per(self):
        if not self.connected: return 0
        try:
            vol = self.volume.GetMasterVolumeLevel()
            return np.interp(vol, [self.min_vol, self.max_vol], [0, 100])
        except:
            return 0
