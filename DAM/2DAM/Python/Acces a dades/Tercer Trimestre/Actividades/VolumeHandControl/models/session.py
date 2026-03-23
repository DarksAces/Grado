from dataclasses import dataclass, field
from datetime import datetime
from typing import Optional

@dataclass
class Session:
    start_time: datetime
    end_time: Optional[datetime] = None
    duration_seconds: float = 0.0
    id: Optional[int] = None

    def to_dict(self):
        return {
            "start_time": self.start_time,
            "end_time": self.end_time,
            "duration_seconds": self.duration_seconds
        }
