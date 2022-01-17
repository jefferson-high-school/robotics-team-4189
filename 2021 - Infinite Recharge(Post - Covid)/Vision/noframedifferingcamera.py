import sensor, image, time, math,pyb
threshold_index = 0
thresholds = [(37, 98, -53, -13, -19, 11)]
sensor.reset()
sensor.set_pixformat(sensor.RGB565)
sensor.set_framesize(sensor.QVGA)
sensor.skip_frames(time = 2000)
sensor.set_auto_gain(False) # must be turned off for color tracking
sensor.set_auto_whitebal(False) # must be turned off for color tracking
extra_fb = sensor.alloc_extra_fb(sensor.width(), sensor.height(), sensor.RGB565)
p = pyb.Pin("P1", pyb.Pin.OUT_PP)

clock = time.clock()
led = pyb.LED(1)
distance = -1
distanceOffSet = -1
focalLength = (48 * 242) / 39
zone = -12
pixelsOffset = -1

while(True):
    clock.tick()


    p.high()
    img = sensor.snapshot()
    for blob in img.find_blobs([thresholds[threshold_index]], pixels_threshold=100, area_threshold=100, merge=True):
        img.draw_rectangle(blob.rect())
        img.draw_cross(blob.cx(), blob.cy(),1)
        distance = (39 * focalLength) / blob.w()
        inchesPerPixel = 30 / blob.w()
        distanceOffSet = (170 - blob.cx()) * inchesPerPixel
        pixelsOffset = blob.cx() - 190
        if(blob.area() < 779):
            #distance = 247.14 - distance
            zone = 0
        elif(blob.area() < 1081):
            #distance = 247.14 - distance
            zone = 1
        elif(blob.area() < 1769):
            #distance = 190.4 - distance
            zone = 2
        elif(blob.area() < 3078):
            #distance = 145 - distance
            zone = 3
        else:#if(blob.area() > 5965):
            #distance = 108 - distance
            zone = 4
    print("$",distanceOffSet, "%" , "#" , zone, "@" , "!" , abs(distance + 5) , "^","&",pixelsOffset, "*" )
