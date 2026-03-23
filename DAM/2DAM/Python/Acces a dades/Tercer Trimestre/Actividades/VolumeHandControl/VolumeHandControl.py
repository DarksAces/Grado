import numpy as np
from ctypes import cast, POINTER
from comtypes import CLSCTX_ALL
from pycaw.pycaw import AudioUtilities, IAudioEndpointVolume

class VolumeController:
    def __init__(self):
        devices = AudioUtilities.GetSpeakers()
        interface = devices.Activate(IAudioEndpointVolume._iid_, CLSCTX_ALL, None)
        self.volume = cast(interface, POINTER(IAudioEndpointVolume))
        self.vol_range = self.volume.GetVolumeRange() # (-65.25, 0.0, 0.03125)
        self.min_vol = self.vol_range[0]
        self.max_vol = self.vol_range[1]
        self.vol_bar = 400
        self.vol_per = 0

    def set_volume(self, length):
        # Finger distance range: 50 - 300 (typical webcam distance)
        # Volume range: self.min_vol - self.max_vol
        vol = np.interp(length, [50, 250], [self.min_vol, self.max_vol])
        self.vol_bar = np.interp(length, [50, 250], [400, 150])
        self.vol_per = np.interp(length, [50, 250], [0, 100])
        
        current_vol = self.volume.GetMasterVolumeLevel()
        self.volume.SetMasterVolumeLevel(vol, None)
        return current_vol, vol, self.vol_bar, self.vol_per

    def get_current_volume_per(self):
        vol = self.volume.GetMasterVolumeLevel()
        return np.interp(vol, [self.min_vol, self.max_vol], [0, 100])
