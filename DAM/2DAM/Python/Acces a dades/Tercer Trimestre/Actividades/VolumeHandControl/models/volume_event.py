from dataclasses import dataclass
from datetime import datetime
from typing import Optional

@dataclass
class VolumeEvent:
    timestamp: datetime
    previous_volume: float
    new_volume: float
    finger_distance: float
    session_id: Optional[str] = None

    def to_dict(self):
        return {
            "timestamp": self.timestamp,
            "previous_volume": self.previous_volume,
            "new_volume": self.new_volume,
            "finger_distance": self.finger_distance,
            "session_id": self.session_id
        }
