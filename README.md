# meta-cherrypi-v3s

## Instruction how to build an image for Cherrypi based on Allwinner V3s in Yocto

### Products:

Home page<br>
http://www.lctech-inc.com/cpzx/LCPIxl/2021/1009/532.html <br>

![Board View Front](cherrypi-v3s-front.png) <br>
![Board View Back](cherrypi-v3s-back.png) <br>
Cherry Pi V3s Version <br>
<br>

## General Note:
Assumed that Linux Ubuntu is installed

## List of tested elements
Example application for GPIO handling

## List of not tested elements
Lcd <br>
Touchscreen <br>

TBD <br>

## How to build an images

1. First make sure to following packages are installed in system

    ***sudo apt-get install gawk wget diffstat unzip texinfo gcc-multilib build-essential chrpath socat libsdl1.2-dev xterm emscripten libmpc-dev libgmp3-dev***

    **Note:**
    More informations can be found on Yocto reference manual.

2. Download necessary Yocto packaged listed below. Be sure to be in root of home folder.

	***mkdir yocto***<br>
	***cd yocto*** <br>
	***mkdir build*** <br>
	***git clone git://git.yoctoproject.org/poky --depth 1 -b kirkstone*** <br>
        ***cd poky*** <br>
	***git clone git://git.openembedded.org/meta-openembedded --depth 1 -b kirkstone*** <br>
	***git clone https://github.com/meta-qt5/meta-qt5.git --depth 1 -b kirkstone*** <br>
	***git clone https://github.com/voloviq/meta-cherrypi-v3s --depth 1 -b kirkstone*** <br>

3. Select directory to build Linux

    Nano version <br>
	***source oe-init-build-env ~/yocto/build/cherrypi-v3s*** <br>

4. Modify bblayers.conf(located in ~/yocto/build/cherrypi-v3s/conf)

    *BBLAYERS ?= " \\\
      ${HOME}/yocto/poky/meta \\\
      ${HOME}/yocto/poky/meta-poky \\\
      ${HOME}/yocto/poky/meta-openembedded/meta-oe \\\
      ${HOME}/yocto/poky/meta-openembedded/meta-networking \\\
      ${HOME}/yocto/poky/meta-openembedded/meta-python \\\
      ${HOME}/yocto/poky/meta-openembedded/meta-multimedia \\\
      ${HOME}/yocto/poky/meta-qt5 \\\
      ${HOME}/yocto/poky/meta-cherrypi-v3s \\\
      "*<br>

    **Note:** Please adapt PATH of conf/bblayers.conf if necessary. <br>

5. Modify local.conf(located in ~/yocto/build/cherrypi-v3s/conf) file

    - modify line with "MACHINE ??" to add "cherrypi-v3s-sdcard" or for SPI NOR Flash "cherrypi-v3s-spinor"

    - align *DL_DIR = "${HOME}/yocto/downloads"* <br>

    - align *SSTATE_DIR = "${HOME}/yocto/sstate-cache"* <br>
    
    - align *TMPDIR = "${HOME}/yocto/tmp"* <br>
    
    - add at the end following records <br> <br>
    	*RM_OLD_IMAGE = "1"* <br>
	*INHERIT += "rm_work"* <br>
	*MACHINEOVERRIDES .= ":use-mailine-graphics"* <br>
	*LICENSE_FLAGS_ACCEPTED = "commercial"* <br>
	
    - for spi flash change DISTRO ?= "poky" to DISTRO ?= "cherrypi-v3s-tiny" <br>

    **Note:** Please adapt rest of conf/local.conf parameters if necessary. <br>

6. Build objects

    - When using SPI NOR Flash use following image
    - core image minimal <br>
      ***bitbake core-image-minimal*** <br>

    - console image <br>
      ***bitbake console-image*** <br>

    - qt5 image <br>
      ***bitbake qt5-image*** <br>

    - qt5 toolchain sdk <br>
      ***bitbake meta-toolchain-qt5*** <br>

7. After compilation images appears in

    Nano version <br>
	*~/yocto/tmp/deploy/images/cherrypi-v3s* <br>

8. Insert SD CARD into dedicated CARD slot and issue following command to write an image

    **Note:** <br>
    Be 100% sure to provide a valid device name (**of=/dev/sde/mmcblk0**). Wrong name "/dev/sde/mmcblk0" dameage Your system file ! <br> <br>
        Nano version <br>
    	***sudo dd if=~/yocto/tmp/deploy/images/cherrypi-v3s-sdcard/core-image-minimal-cherrypi-v3s-sdcard.sunxi-sdimg of=/dev/mmcblk0 bs=1024*** <br>

9. SPI NOR Flash update tool compilation(if valid sunxi-tools installed go to point 10)<br>
    ***git clone https://github.com/Icenowy/sunxi-tools.git -b v3s-spi***<br>
    ***sudo apt-get install libz libusb-1.0-0-dev***<br>
    ***make***<br>
    ***sudo make install***<br>

10. Flash SPI NOR flash<br>
    To enter into bootlader mode it is necessary to erase u-boot section from spi nor flash.<br>
    To do this it is necessary to stop booting U-Boot and enter following commands.<br>
    ***sf probe 0***<br>
    ***sf erase 0 70000***<br>
    ***sunxi-fel -p spiflash-write 0 ~/yocto/tmp/deploy/images/cherrypi-v3s-spinor/core-image-minimal-cherrypi-v3s-spinor.sunxi-spinor***<br>

11. How to handle GPIO from userfs - example (used PE3 as GPIO)<br>

    1. Take a GPIO for instance PE3<br>
    ***echo 131 > /sys/class/gpio/export***<br>
    2. Set as out or in<br>
    ***echo "out" > /sys/class/gpio/gpio131/direction***<br>
    3. Set GPIO state if configured as ouput<br>
    ***echo 1 > /sys/class/gpio/gpio131/value***<br>
    ***echo 0 > /sys/class/gpio/gpio131/value***<br>
    
# Limitation
	
