import cv2
import time
from datetime import datetime
from HandTrackingModule import HandDetector
from VolumeHandControl import VolumeController
from dao.postgresql_dao import PostgreSQLDAO
from models.session import Session
from models.volume_event import VolumeEvent

def main():
    # Initialization
    w_cam, h_cam = 640, 480
    cap = cv2.VideoCapture(0)
    cap.set(3, w_cam)
    cap.set(4, h_cam)
    
    detector = HandDetector(detection_con=0.7)
    vol_control = VolumeController()
    dao = PostgreSQLDAO()
    
    # Session handling
    start_time = datetime.now()
    session = Session(start_time=start_time)
    session_id = dao.insert_session(session) if dao.connected else None
    
    p_time = 0
    vol_bar = 400
    vol_per = vol_control.get_current_volume_per()
    color_vol = (255, 0, 0)
    
    try:
        while True:
            success, img = cap.read()
            if not success: break
            
            img = detector.find_hands(img)
            lm_list, bbox = detector.find_position(img, draw=True)
            
            if len(lm_list) != 0:
                # Filter based on size (distance range adjustment)
                area = (bbox[2] - bbox[0]) * (bbox[3] - bbox[1]) // 100
                if 250 < area < 1000:
                    # Find distance between Index (8) and Thumb (4)
                    length, img, line_info = detector.find_distance(4, 8, img)
                    
                    # Check which fingers are up
                    fingers = detector.fingers_up()
                    
                    # Change volume ONLY if the pinky is down
                    if not fingers[4]: # Pinky is index 4
                        prev_vol, new_vol, vol_bar, vol_per = vol_control.set_volume(length)
                        color_vol = (0, 255, 0) # Green when adjusting
                        
                        # Log volume event
                        if dao.connected and session_id:
                            event = VolumeEvent(
                                timestamp=datetime.now(),
                                previous_volume=float(prev_vol),
                                new_volume=float(new_vol),
                                finger_distance=float(length),
                                session_id=session_id
                            )
                            # To avoid flooding DB, maybe only log on significant change or throttle
                            # Here we just log as requested
                            dao.insert_volume_event(event)
                    else:
                        color_vol = (255, 0, 0) # Blue when not adjusting
                        
            # Drawings
            # Volume Bar
            cv2.rectangle(img, (50, 150), (85, 400), (255, 0, 0), 3)
            cv2.rectangle(img, (50, int(vol_bar)), (85, 400), (255, 0, 0), cv2.FILLED)
            cv2.putText(img, f'{int(vol_per)} %', (40, 450), cv2.FONT_HERSHEY_COMPLEX, 1, (255, 0, 0), 3)
            
            # FPS
            c_time = time.time()
            fps = 1 / (c_time - p_time)
            p_time = c_time
            cv2.putText(img, f'FPS: {int(fps)}', (40, 50), cv2.FONT_HERSHEY_COMPLEX, 1, (255, 0, 0), 3)
            
            # DB Status
            db_status = "DB: OK" if dao.connected else "DB: --"
            db_color = (0, 255, 0) if dao.connected else (0, 0, 255)
            cv2.putText(img, db_status, (480, 50), cv2.FONT_HERSHEY_COMPLEX, 1, db_color, 2)
            
            cv2.imshow("Hand Volume Control", img)
            key = cv2.waitKey(1) & 0xFF
            if key == ord('q'):
                break
            elif key == ord('r'):
                print("Manual refresh of audio handle requested...")
                vol_control.refresh_volume_handle()
                
    finally:
        # Closing session
        if dao.connected and session_id:
            end_time = datetime.now()
            duration = (end_time - start_time).total_seconds()
            dao.update_session(session_id, end_time, duration)
        
        cap.release()
        cv2.destroyAllWindows()

if __name__ == "__main__":
    main()
