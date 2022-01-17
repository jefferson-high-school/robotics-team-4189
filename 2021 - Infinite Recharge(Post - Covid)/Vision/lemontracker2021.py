# Untitled - By: Bryan - Mon Jan 11 2021

import sensor, image, time, pyb

sensor.reset()
sensor.set_pixformat(sensor.RGB565)
sensor.set_framesize(sensor.QVGA)
sensor.skip_frames(time = 2000)
sensor.set_saturation(3)
clock = time.clock()
led = pyb.LED(3)
direction = 2
thresholds = (57, 100, -64, -6, 37, 94)
size = 0;
while(True):
    size = 0
    led.on()
    clock.tick()
    img = sensor.snapshot()
    for blob in img.find_blobs([thresholds], pixels_threshold=20, area_threshold=20, merge=False):
        # These values depend on the blob not being circular - otherwise they will be shaky.
        # These values are stable all the time.
        img.draw_rectangle(blob.rect())
        if(blob.roundness() > .3):
            if(size < blob.pixels()):
                size = blob.pixels()
                img.draw_cross(blob.cx(), blob.cy())
                if(blob.pixels() >= 120):
                    direction = 0
                elif(blob.pixels() < 120):
                    direction = 1
        # Note - the blob rotation is unique to 0-180 only.
   # print(clock.fps())
    #print(direction)
    print(direction)

