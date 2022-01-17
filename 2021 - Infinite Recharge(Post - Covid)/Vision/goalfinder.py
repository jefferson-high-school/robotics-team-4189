import sensor, image, time, math,pyb

threshold_index = 0 # 0 for red, 1 for green, 2 for blue

# Color Tracking Thresholds (L Min, L Max, A Min, A Max, B Min, B Max)
# The below thresholds track in general red/green/blue things. You may wish to tune them...
thresholds = [(28, 56, -2, 11, 5, 17)] # generic_blue_thresholds


sensor.reset()
sensor.set_pixformat(sensor.RGB565)
sensor.set_framesize(sensor.QVGA)
sensor.skip_frames(time = 2000)
clock = time.clock()
led = pyb.LED(1)
center = -1
# Only blobs that with more pixels than "pixel_threshold" and more area than "area_threshold" are
# returned by "find_blobs" below. Change "pixels_threshold" and "area_threshold" if you change the
# camera resolution. "merge=True" merges all overlapping blobs in the image.

while(True):
    clock.tick()
    led.on()
    img = sensor.snapshot()
    for blob in img.find_blobs([thresholds[threshold_index]], pixels_threshold=400, area_threshold=200, merge=True):
        # These values depend on the blob not being circular - otherwise they will be shaky.
        # These values are stable all the time.
        img.draw_rectangle(blob.rect())
        if(blob.roundness() > .4):
            img.draw_cross(blob.cx(), blob.cy(),1)
            center = blob.cx();
        # Note - the blob rotation is unique to 0-180 only.
        #img.draw_keypoints([(blob.cx(), blob.cy(), int(math.degrees(blob.rotation())))], size=20)
    print("$",center, "%")
