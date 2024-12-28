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

1. First make sure the following packages are installed in the system

    ***sudo apt-get install gawk wget diffstat unzip texinfo gcc-multilib build-essential chrpath socat libsdl1.2-dev xterm emscripten libmpc-dev libgmp3-dev swig zstd lz4***

    **Note:**
    More information can be found on the Yocto reference manual.
    For Windows WSL try to set UTF8
    ***sudo locale-gen en_US en_US.UTF-8; sudo dpkg-reconfigure locales***

3. Download the necessary Yocto package listed below. Be sure to be in the root of the home folder.

	***mkdir yocto***<br>
	***cd yocto*** <br>
	***mkdir build*** <br>
	***git clone git://git.yoctoproject.org/poky --depth 1 -b scarthgap*** <br>
        ***cd poky*** <br>
	***git clone git://git.openembedded.org/meta-openembedded --depth 1 -b scarthgap*** <br>
	***git clone https://github.com/meta-qt5/meta-qt5.git --depth 1 -b scarthgap*** <br>
	***git clone https://github.com/voloviq/meta-cherrypi-v3s --depth 1 -b scarthgap*** <br>

4. Select a directory to build Linux

	***source oe-init-build-env ~/yocto/build/cherrypi-v3s*** <br>

5. Modify bblayers.conf(located in ~/yocto/build/cherrypi-v3s/conf)

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

6. Modify local.conf(located in ~/yocto/build/cherrypi-v3s/conf) file

    - modify line with "MACHINE ??" to add "cherrypi-v3s-sdcard" or for SPI NOR Flash "cherrypi-v3s-spinor"

    - align *DL_DIR = "${HOME}/yocto/downloads"* <br>

    - align *SSTATE_DIR = "${HOME}/yocto/sstate-cache"* <br>
    
    - align *TMPDIR = "${HOME}/yocto/tmp"* <br>
    
    - add at the end the following records <br> <br>
    	*RM_OLD_IMAGE = "1"* <br>
	*INHERIT += "rm_work"* <br>
	*MACHINEOVERRIDES .= ":use-mailine-graphics"* <br>
	*LICENSE_FLAGS_ACCEPTED = "commercial"* <br>
	
    - for spi flash change DISTRO ?= "poky" to DISTRO ?= "cherrypi-v3s-tiny" <br>

    **Note:** Please adapt the rest of conf/local.conf parameters if necessary. <br>

7. Build objects

    - When using SPI NOR Flash use the following image
    - core image minimal <br>
      ***bitbake core-image-minimal*** <br>

    - console image <br>
      ***bitbake console-image*** <br>

    - qt5 image <br>
      ***bitbake qt5-image*** <br>

    - qt5 toolchain sdk <br>
      ***bitbake meta-toolchain-qt5*** <br>

8. After compilation images appear in

    Nano version <br>
	*~/yocto/tmp/deploy/images/cherrypi-v3s* <br>

9. Insert SD CARD into the dedicated CARD slot and issue the following command to write an image

    **Note:** <br>
    Be 100% sure to provide a valid device name (**of=/dev/sde/mmcblk0**). The wrong name "/dev/sde/mmcblk0" damages Your system file! <br> <br>
        Nano version <br>
    	***sudo dd if=~/yocto/tmp/deploy/images/cherrypi-v3s-sdcard/core-image-minimal-cherrypi-v3s-sdcard.sunxi-sdimg of=/dev/mmcblk0 bs=1024*** <br>

10. SPI NOR Flash update tool compilation(if valid sunxi-tools installed go to point 10)<br>
    ***git clone https://github.com/Icenowy/sunxi-tools.git -b v3s-spi***<br>
    ***sudo apt-get install libz libusb-1.0-0-dev***<br>
    ***make***<br>
    ***sudo make install***<br>

11. Flash SPI NOR flash<br>
    To enter into bootloader mode, erase the u-boot section from spi nor flash.<br>
    To do this it is necessary to stop booting U-Boot and enter the following commands.<br>
    ***sf probe 0***<br>
    ***sf erase 0 70000***<br>
    ***sunxi-fel -p spiflash-write 0 ~/yocto/tmp/deploy/images/cherrypi-v3s-spinor/core-image-minimal-cherrypi-v3s-spinor.sunxi-spinor***<br>
    To write only u-boot...spl just type<br>
    ***sudo sunxi-fel -v uboot u-boot-sunxi-with-spl.bin***<br>

12. How to handle GPIO from userfs - example (used PE3 as GPIO)<br>

    1. Take a GPIO for instance PE3<br>
    ***echo 131 > /sys/class/gpio/export***<br>
    2. Set as out or in<br>
    ***echo "out" > /sys/class/gpio/gpio131/direction***<br>
    3. Set GPIO state if configured as ouput<br>
    ***echo 1 > /sys/class/gpio/gpio131/value***<br>
    ***echo 0 > /sys/class/gpio/gpio131/value***<br>
    
# Limitation
	
